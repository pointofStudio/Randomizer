package com.pointOf.randomizer.Screens

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pointOf.randomizer.R
import com.pointOf.randomizer.navigation.AppRouter
import com.pointOf.randomizer.navigation.Screen
import com.pointOf.randomizer.navigation.SystemBackButtonHandler
import kotlin.text.append
import kotlin.text.firstOrNull

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Credits(){
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.credits),
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
                .fillMaxHeight(),
                horizontalAlignment = Alignment.CenterHorizontally) {
                Spacer(modifier = Modifier.height(55.dp))
                Card {
                    Image(
                        painter = painterResource(R.drawable.dice_6), contentDescription = stringResource(R.string.app_name)
                    )
                }
                Spacer(modifier = Modifier.height(55.dp))
                Text(stringResource(R.string.creditstext), fontWeight = FontWeight.Bold, fontSize = 24.sp, color = MaterialTheme.colorScheme.primary)
                Spacer(modifier = Modifier.height(24.dp))
                Text(stringResource(R.string.creditstextintro),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Normal,
                    color = MaterialTheme.colorScheme.secondary)
                Spacer(modifier = Modifier.height(24.dp))

                Row {
                    Text(stringResource(R.string.creditstext2))
                    Spacer(modifier = Modifier.width(8.dp))
                    AnnotatedLinkText()
                }
            }
            Column(
                verticalArrangement = Arrangement.Bottom,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CrashButtonScreen()
                Spacer(modifier = Modifier.height(30.dp))
            }

        }
    }
    SystemBackButtonHandler {
        AppRouter.navigateTo(Screen.HomeScreen)
    }
}


@Preview
@Composable
fun AnnotatedLinkText() {
    val context = LocalContext.current
    val annotatedText = buildAnnotatedString {
        pushStringAnnotation(
            tag = "URL",
            annotation = "https://github.com/pointofStudio/Randomizer"
        )
        withStyle(
            style = SpanStyle(
                color = Color.Blue,
                textDecoration = TextDecoration.Underline
            )
        ) {
            append(stringResource(R.string.link))
        }
        pop()
    }

    Text(
        modifier = Modifier
            .clickable {
                annotatedText.getStringAnnotations(
                    tag = "URL",
                    start = 0,
                    end = annotatedText.length
                ).firstOrNull()?.let { annotation ->
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(annotation.item))
                    context.startActivity(intent)
                }
            },
        text = annotatedText
    )
}

@Preview(showBackground = true)
@Composable
fun CrashButtonScreen() {
    OutlinedButton(
        onClick = {
            throw RuntimeException("Test Crash") // Forza un crash
        },
        modifier = Modifier
            .height(1.dp)
            .width(1.dp),
        colors = androidx.compose.material3.ButtonDefaults.buttonColors(Color.Transparent)
    ) {}
}