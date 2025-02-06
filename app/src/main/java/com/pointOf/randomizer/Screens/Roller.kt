package com.pointOf.randomizer.Screens

import androidx.compose.animation.core.copy
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.layout
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import com.pointOf.randomizer.R
import com.pointOf.randomizer.navigation.AppRouter
import com.pointOf.randomizer.navigation.Screen
import com.pointOf.randomizer.navigation.SystemBackButtonHandler


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Roller(){
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.roller),
                        fontWeight = FontWeight.Bold
                    )
                },
                colors = TopAppBarDefaults.largeTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                )
            )
        }
    ) { innerPadding ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(innerPadding),
            color = MaterialTheme.colorScheme.background
        ) {
            val scrollState = rememberScrollState()

            Column(modifier = Modifier
                .fillMaxSize()
                .fillMaxWidth()
                .fillMaxHeight()
                .verticalScroll(scrollState)) {
                Spacer(modifier = Modifier.height(24.dp))
                DynamicImageRow()
            }

        }
    }
    SystemBackButtonHandler {
        AppRouter.navigateTo(Screen.HomeScreen)
    }
}



@Preview(showSystemUi = true, showBackground = true)
@Composable
fun DynamicImageRow() {
    var numberOfImages by remember { mutableIntStateOf(1) }
    val imageResources = listOf(
        R.drawable.dice_1b,
        R.drawable.dice_2b,
        R.drawable.dice_3,
        R.drawable.dice_4,
        R.drawable.dice_5,
        R.drawable.dice_6
    )
    val diceData = mapOf(
        1 to Pair(R.drawable.dice_1b, R.string.dice_text_1),
        2 to Pair(R.drawable.dice_2b, R.string.dice_text_2),
        3 to Pair(R.drawable.dice_3, R.string.dice_text_3),
        4 to Pair(R.drawable.dice_4, R.string.dice_text_4),
        5 to Pair(R.drawable.dice_5, R.string.dice_text_5),
        6 to Pair(R.drawable.dice_6, R.string.dice_text_6)
    )
    var results by remember { mutableStateOf(List(imageResources.size) { (1..6).random() }) }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(16.dp)
    ) {

        Slider(
            value = numberOfImages.toFloat(),
            onValueChange = {
                val newNumberOfImages = it.toInt()
                val currentResults = results.take(numberOfImages)
                val newResults = if (newNumberOfImages > currentResults.size) {
                    List(newNumberOfImages - currentResults.size) { (1..6).random() }
                } else {
                    emptyList()
                }
                results = currentResults + newResults + results.drop(newNumberOfImages)
                numberOfImages = newNumberOfImages
            },
            valueRange = 1f..imageResources.size.toFloat(),
            steps = imageResources.size - 2,
            modifier = Modifier.padding(16.dp)
        )

        Text(stringResource(R.string.rollerexplain), fontWeight = FontWeight.Thin, fontStyle = FontStyle.Italic, fontSize = 10.sp)

        Spacer(modifier = Modifier.height(16.dp))

        Row {
            Text(text = stringResource(R.string.thenumberis))
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = "$numberOfImages")
        }




        CustomImageRow(numberOfImages = numberOfImages, results = results, diceData = diceData)
        Button(
            onClick = {
                results = List(numberOfImages) { (1..6).random() }
            },
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(48.dp),
            contentPadding = PaddingValues(),
            colors = androidx.compose.material3.ButtonDefaults.buttonColors(Color.Transparent)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(48.dp)
                    .background(
                        brush = Brush.horizontalGradient(
                            listOf(
                                MaterialTheme.colorScheme.primary,
                                MaterialTheme.colorScheme.secondary
                            )
                        ),
                        shape = RoundedCornerShape(50.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stringResource(R.string.roll),
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }

    }
}

@Composable
fun CustomImageRow(numberOfImages: Int, results: List<Int>, diceData: Map<Int, Pair<Int, Int>>) {
    CustomImageRowLayout(
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp),
        numberOfImages = numberOfImages
    ) {
        for (i in 0 until numberOfImages) {
            val (imageResource, textResource) =
                diceData.getOrDefault(results[i], Pair(R.drawable.dice_0, R.string.dice_text_0))
            Image(
                painter = painterResource(id = imageResource),
                contentDescription = "Image $i",
                modifier = Modifier
                    .aspectRatio(1f),
                contentScale = ContentScale.Fit
            )
        }
        Spacer(modifier = Modifier.width(16.dp))
    }
}

@Composable
fun CustomImageRowLayout(
    modifier: Modifier = Modifier,
    numberOfImages: Int,
    content: @Composable () -> Unit,
) {
    Layout(
        modifier = modifier,
        content = content
    ) { measurables, constraints ->
        val buttonMeasurables = measurables.dropLast(1)
        val spacerMeasurable = measurables.lastOrNull()

        val spacerWidth = spacerMeasurable?.maxIntrinsicWidth(constraints.maxHeight) ?: 0
        val buttonWidth = (constraints.maxWidth - spacerWidth) / 2
        val buttonConstraints = constraints.copy(minWidth = buttonWidth, maxWidth = buttonWidth)
        val buttonPlaceables = buttonMeasurables.map { measurable ->
            measurable.measure(buttonConstraints)
        }
        val spacerPlaceable = spacerMeasurable?.measure(constraints) ?: return@Layout layout(0, 0) {}
        val maxImagesPerRow = 2
        val numberOfRows = (numberOfImages + maxImagesPerRow - 1) / maxImagesPerRow
        val totalHeight = numberOfRows * buttonPlaceables.firstOrNull()?.height!!

        layout(constraints.maxWidth, totalHeight) {
            var xPos = 0
            var yPos = 0
            buttonPlaceables.forEachIndexed { index, placeable ->
                val row = index / maxImagesPerRow
                val col = index % maxImagesPerRow
                xPos = col * (buttonWidth + spacerWidth)
                yPos = row * placeable.height
                placeable.placeRelative(xPos, yPos)
            }
        }
    }
}