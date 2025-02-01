package com.pointOf.randomizer.Screens

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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
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
@Preview(showSystemUi = true, showBackground = true)
@Composable
fun SixFaces() {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.sixfaces),
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
            Column(modifier = Modifier
                .fillMaxSize()
                .fillMaxWidth()
                .fillMaxHeight()) {
                Spacer(modifier = Modifier.height(55.dp))
                Spacer(modifier = Modifier.height(55.dp))
                SixFacesData()
                Spacer(modifier = Modifier.height(200.dp))
            }

        }
    }
    SystemBackButtonHandler {
        AppRouter.navigateTo(Screen.HomeScreen)
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun SixFacesData() {
    var result by remember { mutableStateOf( 1) }
    val diceData = mapOf(
        1 to Pair(R.drawable.dice_1b, R.string.dice_text_1),
        2 to Pair(R.drawable.dice_2b, R.string.dice_text_2),
        3 to Pair(R.drawable.dice_3, R.string.dice_text_3),
        4 to Pair(R.drawable.dice_4, R.string.dice_text_4),
        5 to Pair(R.drawable.dice_5, R.string.dice_text_5),
        6 to Pair(R.drawable.dice_6, R.string.dice_text_6),
    )

    val (imageResource, textResource) = diceData.getOrDefault(result, Pair(R.drawable.dice_1b, R.string.dice_text_1))

    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp

    Column (modifier = Modifier, horizontalAlignment = Alignment.CenterHorizontally){


            Image(
                painter = painterResource(imageResource),
                contentDescription = result.toString(),
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f),
            )
        Spacer(modifier = Modifier.height(10.dp))




        Row (horizontalArrangement = Arrangement.Center){
            Column(horizontalAlignment = Alignment.CenterHorizontally){
                Text(stringResource(R.string.resultTitle))
                Spacer(modifier = Modifier.height(1.dp))
                Row(horizontalArrangement = Arrangement.Center) {
                    Text(text = stringResource(textResource), fontSize = 35.sp, fontWeight = FontWeight.Bold)

                }
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        Text(stringResource(R.string.notZero), fontWeight = FontWeight.Thin, fontStyle = FontStyle.Italic, fontSize = 12.sp)


        Spacer(modifier = Modifier.height(10.dp))

        Row (modifier = Modifier.padding(10.dp)){

            Button(
                onClick = {
                    result = (1..7).random()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(48.dp),
                contentPadding = PaddingValues(),
                colors = androidx.compose.material3.ButtonDefaults.buttonColors(Color.Transparent)
            ) {
                Box(modifier = Modifier
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
                    Text(text = stringResource(R.string.roll),
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
    SystemBackButtonHandler {
        AppRouter.navigateTo(Screen.HomeScreen)
    }
}