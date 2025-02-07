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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import com.google.firebase.appdistribution.InterruptionLevel
import com.google.firebase.appdistribution.appDistribution
import com.pointOf.randomizer.Screens.Cinquantanove
import com.pointOf.randomizer.Screens.Credits
import com.pointOf.randomizer.Screens.Novantanove
import com.pointOf.randomizer.Screens.Ottantanove
import com.pointOf.randomizer.Screens.Quarantanove
import com.pointOf.randomizer.Screens.Roller
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
        enableEdgeToEdge()
        setContent {
            RandomizerTheme {
                HomeApp()
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
            val scrollState = rememberScrollState()

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .fillMaxWidth()
                    .padding(5.dp)
                    .verticalScroll(scrollState),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Image(
                        painter = painterResource(R.drawable.dice_6),
                        contentDescription = stringResource(R.string.app_name),
                        modifier = Modifier.clickable {
                            AppRouter.navigateTo(Screen.Credits)
                        }
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

                    Spacer(modifier = Modifier.height(5.dp))

                    Button(
                        onClick = { AppRouter.navigateTo(Screen.Roller) },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(stringResource(R.string.roller))
                    }
                }

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

                is Screen.Roller -> {
                    Roller()
                }

                is Screen.Credits -> {
                    Credits()
                }

            }

        }


    }
}
