package com.pointOf.randomizer.navigation

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview

sealed class Screen(){

    object HomeScreen: Screen()
    object SixFaces: Screen()
    object Ventinove: Screen()
    object Trentanove: Screen()
    object Quarantanove: Screen()
    object Cinquantanove: Screen()
    object Settantanove: Screen()
    object Ottantanove: Screen()
    object Novantanove: Screen()

    object Roller: Screen()

    object Credits: Screen()

}

object AppRouter {

    var currentScreen: MutableState<Screen> = mutableStateOf(Screen.HomeScreen)

    fun navigateTo(destination: Screen){
        currentScreen.value = destination
    }
}
