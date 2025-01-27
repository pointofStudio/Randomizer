package com.pointOf.randomizer


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.material3.Icon
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.Firebase
import com.google.firebase.appdistribution.InterruptionLevel
import com.google.firebase.appdistribution.appDistribution
import com.pointOf.randomizer.ui.theme.RandomizerTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Firebase.appDistribution.showFeedbackNotification(
            R.string.additionalFormText,
            InterruptionLevel.HIGH
        )
        enableEdgeToEdge()
        setContent {
            RandomizerTheme {
                App()
            }
        }
    }
}




@Preview(showSystemUi = true)
@Composable
fun DiceRollerApp() {
    DiceWithButtonAndImage()
}

@Preview(showSystemUi = true)
@Composable
fun DiceRollerApp2() {
    App()
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun App() {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.app_name),
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
                DiceWithButtonAndImage()
                Spacer(modifier = Modifier.height(200.dp))
                CrashButtonScreen()
            }

        }
    }
}

@Composable
fun CrashButtonScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = {
            throw RuntimeException("Test Crash from Compose") // Forza un crash
        }, modifier = Modifier.height(50.dp).width(80.dp)) {
            Icon(painter = painterResource(R.drawable.round_app_blocking_24) , contentDescription = null)
        }
    }
}

@Composable
fun DiceWithButtonAndImage() {
    var result by remember { mutableStateOf( 1) }
    var result2 by remember { mutableStateOf( 1) }
    val imageResource = when(result) {
        1 -> R.drawable.dice_0
        2 -> R.drawable.dice_1
        else -> R.drawable.dice_2
    }
    val imageResource2 = when(result2) {
        1 -> R.drawable.dice_0
        2 -> R.drawable.dice_1b
        3 -> R.drawable.dice_2b
        4 -> R.drawable.dice_3
        5 -> R.drawable.dice_4
        6 -> R.drawable.dice_5
        7 -> R.drawable.dice_6
        8 -> R.drawable.dice_7
        9 -> R.drawable.dice_8
        else -> R.drawable.dice_9
    }
    Column (modifier = Modifier){
        
        Row {
            Image(
                painter = painterResource(imageResource),
                contentDescription = result.toString(),
                modifier = Modifier
                    .height(200.dp)
                    .width(200.dp)
            )
            Image(
                painter = painterResource(imageResource2),
                contentDescription = result.toString(),
                modifier = Modifier
                    .height(200.dp)
                    .width(200.dp)
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        Row (modifier = Modifier.padding(10.dp)){

            Button(
                onClick = {
                    result = (1..3).random()
                    result2 = (1..10).random()
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
}

