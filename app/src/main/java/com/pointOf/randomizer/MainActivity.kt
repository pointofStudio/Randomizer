@file:Suppress("UNUSED_VARIABLE")

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
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.Firebase
import com.google.firebase.appdistribution.FirebaseAppDistributionException
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
            // Text providing notice to your testers about collection and
            // processing of their feedback data
            R.string.additionalFormText,
            // The level of interruption for the notification
            InterruptionLevel.HIGH)
        enableEdgeToEdge()
        setContent {
            RandomizerTheme {
                HomeApp()
            }
            LaunchedEffect(key1 = true) {
                checkForAppUpdates()
            }
        }
    }
}




private fun checkForAppUpdates() {
    val firebaseAppDistribution = Firebase.appDistribution
    firebaseAppDistribution.updateIfNewReleaseAvailable()
        .addOnProgressListener { updateProgress ->
            // (Optional) Implement custom progress updates in addition to
            // automatic NotificationManager updates.
            val progress = updateProgress.apkBytesDownloaded * 100 / updateProgress.apkFileTotalBytes
            println("Download progress: $progress%")
        }
        .addOnFailureListener { e ->
            // (Optional) Handle errors.
            if (e is FirebaseAppDistributionException) {
                when (e.errorCode) {
                    FirebaseAppDistributionException.Status.NOT_IMPLEMENTED -> {
                        // SDK did nothing. This is expected when building for Play.
                        println("App Distribution: NOT_IMPLEMENTED")
                    }

                    else -> {
                        // Handle other errors.
                        println("App Distribution error: ${e.errorCode}")
                    }
                }
            }
        }
}



@OptIn(ExperimentalMaterial3Api::class)
@Preview(device = "spec:width=720px,height=1600px,dpi=269")
@Composable
fun App(){
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
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
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Button(
                            onClick = { AppRouter.navigateTo(Screen.SixFaces) },
                            modifier = Modifier.weight(1f)
                        ) {
                            Text(stringResource(R.string.sixfaces))
                        }
                        Button(
                            onClick = { AppRouter.navigateTo(Screen.Ventinove) },
                            modifier = Modifier.weight(1f)
                        ) {
                            Text(stringResource(R.string.ventinovefaces))
                        }
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Button(
                            onClick = { AppRouter.navigateTo(Screen.Trentanove) },
                            modifier = Modifier.weight(1f)
                        ) {
                            Text(stringResource(R.string.trentanovefaces))
                        }
                        Button(
                            onClick = { AppRouter.navigateTo(Screen.Quarantanove) },
                            modifier = Modifier.weight(1f)
                        ) {
                            Text(stringResource(R.string.quarantanovefaces))
                        }
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Button(
                            onClick = { AppRouter.navigateTo(Screen.Cinquantanove) },
                            modifier = Modifier.weight(1f)
                        ) {
                            Text(stringResource(R.string.cinquantanovefaces))
                        }
                        Button(
                            onClick = { AppRouter.navigateTo(Screen.Settantanove) },
                            modifier = Modifier.weight(1f)
                        ) {
                            Text(stringResource(R.string.settantanovefaces))
                        }
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Button(
                            onClick = { AppRouter.navigateTo(Screen.Ottantanove) },
                            modifier = Modifier.weight(1f)
                        ) {
                            Text(stringResource(R.string.ottantanovefaces))
                        }
                        Button(
                            onClick = { AppRouter.navigateTo(Screen.Novantanove) },
                            modifier = Modifier.weight(1f)
                        ) {
                            Text(stringResource(R.string.novantanovefaces))
                        }
                    }
                }
                }

            }
        Column (verticalArrangement = Arrangement.Bottom, horizontalAlignment = Alignment.CenterHorizontally){
            CrashButtonScreen()
            Spacer(modifier = Modifier.height(25.dp))
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

