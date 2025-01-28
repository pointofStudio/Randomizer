package com.pointOf.randomizer


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.Image
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
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.Firebase
import com.google.firebase.appdistribution.InterruptionLevel
import com.google.firebase.appdistribution.appDistribution
import com.pointOf.randomizer.Screens.Cinquantanove
import com.pointOf.randomizer.Screens.Novantanove
import com.pointOf.randomizer.Screens.Ottantanove
import com.pointOf.randomizer.Screens.Quarantanove
import com.pointOf.randomizer.Screens.Settantanove
import com.pointOf.randomizer.Screens.SixFaces
import com.pointOf.randomizer.Screens.Trentanove
import com.pointOf.randomizer.Screens.Ventinove
import com.pointOf.randomizer.navigation.AppRouter
import com.pointOf.randomizer.navigation.Screen
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
                HomeApp()
            }
        }
    }
}






@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun App(){
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
                .fillMaxHeight()
                .padding(5.dp),
                horizontalAlignment = Alignment.CenterHorizontally
                ) {

                Column (horizontalAlignment = Alignment.CenterHorizontally){
                    Image(painter = painterResource(R.drawable.dice_6), contentDescription = stringResource(R.string.app_name))
                    Text(stringResource(R.string.welcome), fontSize = 24.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                    Spacer(modifier = Modifier.height(15.dp))
                    Text(stringResource(R.string.description), fontSize = 18.sp, fontWeight = FontWeight.Normal, color = MaterialTheme.colorScheme.secondary)
                }
                Spacer(modifier = Modifier.height(55.dp))
                Column {
                    Row {
                        Button(onClick = { AppRouter.navigateTo(Screen.SixFaces) }) {
                            Text(stringResource(R.string.sixfaces))
                        }
                        Spacer(modifier = Modifier.width(15.dp))
                        Button(onClick = { AppRouter.navigateTo(Screen.Ventinove) }) {
                            Text(stringResource(R.string.ventinovefaces))

                        }
                    }
                    Row {
                        Button(onClick = { AppRouter.navigateTo(Screen.Trentanove) }) {
                            Text(stringResource(R.string.trentanovefaces))
                        }
                        Spacer(modifier = Modifier.width(15.dp))
                        Button(onClick = { AppRouter.navigateTo(Screen.Quarantanove) }) {
                            Text(stringResource(R.string.quarantanovefaces))
                        }
                    }

                    Row {
                        Button(onClick = { AppRouter.navigateTo(Screen.Cinquantanove) }) {
                            Text(stringResource(R.string.cinquantanovefaces))
                        }
                        Spacer(modifier = Modifier.width(15.dp))
                        Button(onClick = { AppRouter.navigateTo(Screen.Settantanove) }) {
                            Text(stringResource(R.string.settantanovefaces))
                        }
                    }

                    Row(modifier = Modifier, horizontalArrangement = Arrangement.Center) {
                        Button(onClick = { AppRouter.navigateTo(Screen.Ottantanove) }) {
                            Text(stringResource(R.string.novantanovefaces) )
                        }
                        Spacer(modifier = Modifier.width(15.dp))
                        Button(onClick = { AppRouter.navigateTo(Screen.Novantanove) }) {
                            Text(stringResource(R.string.novantanovefaces) )
                        }
                    }

                }
            }

        }
    }

}


@Composable
fun HomeApp(){
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ){
        Crossfade (targetState = AppRouter.currentScreen) { currentState ->
            when(currentState.value){
                is Screen.HomeScreen -> {
                    App()
                }
                is Screen.SixFaces -> {
                    SixFaces()
                }
                is Screen.Ventinove -> {
                    Ventinove()
                }
                is Screen.Trentanove -> {
                    Trentanove()
                }
                is Screen.Quarantanove -> {
                    Quarantanove()
                }
                is Screen.Cinquantanove -> {
                    Cinquantanove()
                }
                is Screen.Settantanove -> {
                    Settantanove()
                }
                is Screen.Ottantanove -> {
                    Ottantanove()
                }
                is Screen.Novantanove -> {
                    Novantanove()
                }

            }

        }


    }
}