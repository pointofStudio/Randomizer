@file:Suppress("UNUSED_VARIABLE")

package com.pointOf.randomizer


import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.Image
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
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
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
            InterruptionLevel.MIN
        )
        enableEdgeToEdge()
        setContent {
            RandomizerTheme {
                val requestPermissionLauncher =
                    rememberLauncherForActivityResult(
                        ActivityResultContracts.RequestPermission()
                    ) { isGranted: Boolean ->
                        if (isGranted) {
                            // Permission granted, proceed with sending notifications
                            println("Notification permission granted")
                        } else {
                            // Permission denied, handle accordingly
                            println("Notification permission denied")
                        }
                    }
                val context = LocalContext.current
                LaunchedEffect(key1 = true) {
                    val sharedPreferences =
                        context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
                    val isFirstTime = sharedPreferences.getBoolean("isFirstTime", true)
                    if (isFirstTime) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                            val permissionCheckResult = ContextCompat.checkSelfPermission(
                                context,
                                android.Manifest.permission.POST_NOTIFICATIONS
                            )
                            if (permissionCheckResult == PackageManager.PERMISSION_GRANTED) {
                                // Permission is granted
                                println("Notification permission already granted")
                            } else {
                                // Permission is not granted, request it
                                requestPermissionLauncher.launch(android.Manifest.permission.POST_NOTIFICATIONS)
                            }
                        }
                        sharedPreferences.edit().putBoolean("isFirstTime", false).apply()
                    }
                }



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
            val progress =
                updateProgress.apkBytesDownloaded * 100 / updateProgress.apkFileTotalBytes
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
fun App() {
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
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .fillMaxWidth()
                    .padding(5.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Image(
                        painter = painterResource(R.drawable.dice_6),
                        contentDescription = stringResource(R.string.app_name)
                    )
                    Text(
                        stringResource(R.string.welcome),
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.height(15.dp))
                    Text(
                        stringResource(R.string.description),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Normal,
                        color = MaterialTheme.colorScheme.secondary
                    )
                }
                Spacer(modifier = Modifier.height(55.dp))

                Column(
                    modifier = Modifier
                        .fillMaxWidth() // Make the Column fill the width
                        .padding(horizontal = 16.dp) // Add horizontal padding
                    ,
                    verticalArrangement = Arrangement.spacedBy(5.dp)
                ) {

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(5.dp)
                    ) {
                        Button(
                            onClick = { AppRouter.navigateTo(Screen.SixFaces) },
                            modifier = Modifier.weight(1f)
                        ) {
                            Text(stringResource(R.string.sixfaces))
                        }
                        Spacer(modifier = Modifier.width(5.dp)) // Add space between buttons
                        Button(
                            onClick = { AppRouter.navigateTo(Screen.Ventinove) },
                            modifier = Modifier.weight(1f) // Make the Button fill the width
                        ) {
                            Text(stringResource(R.string.ventinovefaces))
                        }
                    }

                    Spacer(modifier = Modifier.height(5.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(5.dp)
                    ) {
                        Button(
                            onClick = { AppRouter.navigateTo(Screen.Trentanove) },
                            modifier = Modifier.weight(1f) // Make the Button fill the width
                        ) {
                            Text(stringResource(R.string.trentanovefaces))
                        }
                        Spacer(modifier = Modifier.width(5.dp)) // Add space between buttons
                        Button(
                            onClick = { AppRouter.navigateTo(Screen.Quarantanove) },
                            modifier = Modifier.weight(1f) // Make the Button fill the width

                        ) {
                            Text(stringResource(R.string.quarantanovefaces))
                        }
                    }

                    Spacer(modifier = Modifier.height(5.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(5.dp)
                    ) {
                        Button(
                            onClick = { AppRouter.navigateTo(Screen.Cinquantanove) },
                            modifier = Modifier.weight(1f) // Make the Button fill the width
                        ) {
                            Text(stringResource(R.string.cinquantanovefaces))
                        }
                        Spacer(modifier = Modifier.width(5.dp)) // Add space between buttons
                        Button(
                            onClick = { AppRouter.navigateTo(Screen.Settantanove) },
                            modifier = Modifier.weight(1f)
                        ) {
                            Text(stringResource(R.string.settantanovefaces))
                        }
                    }

                    Spacer(modifier = Modifier.height(5.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(5.dp)
                    ) {
                        Button(
                            onClick = { AppRouter.navigateTo(Screen.Ottantanove) },
                            modifier = Modifier.weight(1f)
                        ) {
                            Text(stringResource(R.string.ottantanovefaces))
                        }
                        Spacer(modifier = Modifier.width(5.dp)) // Add space between buttons
                        Button(
                            onClick = { AppRouter.navigateTo(Screen.Novantanove) },
                            modifier = Modifier.weight(1f)
                        ) {
                            Text(stringResource(R.string.novantanovefaces))
                        }
                    }
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
}


@Composable
fun HomeApp() {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Crossfade(targetState = AppRouter.currentScreen) { currentState ->
            when (currentState.value) {
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


@Composable
fun ButtonGrid() {
    Box(modifier = Modifier.padding(16.dp)) {
        CustomButtonLayout(
            modifier = Modifier.fillMaxWidth()
        ) {
            Button(
                onClick = { AppRouter.navigateTo(Screen.SixFaces) },
                contentPadding = PaddingValues(10.dp),
            ) {
                Text(stringResource(R.string.sixfaces))
            }
            Button(
                onClick = { AppRouter.navigateTo(Screen.Ventinove) },
                contentPadding = PaddingValues(10.dp),
            ) {
                Text(stringResource(R.string.ventinovefaces))
            }
            Button(
                onClick = { AppRouter.navigateTo(Screen.Trentanove) },
                contentPadding = PaddingValues(10.dp),
            ) {
                Text(stringResource(R.string.trentanovefaces))
            }
            Button(
                onClick = { AppRouter.navigateTo(Screen.Quarantanove) },
                contentPadding = PaddingValues(10.dp),
            ) {
                Text(stringResource(R.string.quarantanovefaces))
            }
            Button(
                onClick = { AppRouter.navigateTo(Screen.Cinquantanove) },
                contentPadding = PaddingValues(10.dp),
            ) {
                Text(stringResource(R.string.cinquantanovefaces))
            }
            Button(
                onClick = { AppRouter.navigateTo(Screen.Settantanove) },
                contentPadding = PaddingValues(10.dp),
            ) {
                Text(stringResource(R.string.settantanovefaces))
            }
            Button(
                onClick = { AppRouter.navigateTo(Screen.Ottantanove) },
                contentPadding = PaddingValues(10.dp),
            ) {
                Text(stringResource(R.string.ottantanovefaces))
            }
            Button(
                onClick = { AppRouter.navigateTo(Screen.Novantanove) },
                contentPadding = PaddingValues(10.dp),
            ) {
                Text(stringResource(R.string.novantanovefaces))
            }
            Spacer(modifier = Modifier.width(16.dp))
        }
    }
}

@Composable
fun CustomButtonLayout(
    modifier: Modifier = Modifier,
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
        val spacerPlaceable =
            spacerMeasurable?.measure(constraints) ?: return@Layout layout(0, 0) {}

        layout(constraints.maxWidth, constraints.maxHeight) {
            var xPos = 0
            var yPos = 0
            buttonPlaceables.forEachIndexed { index, placeable ->
                val row = index / 2
                val col = index % 2
                xPos = col * (buttonWidth + spacerWidth)
                yPos = row * placeable.height
                placeable.placeRelative(xPos, yPos)
            }
            spacerPlaceable.placeRelative(buttonWidth, 0)
        }
    }
}