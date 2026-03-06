package com.example.learning

import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

import com.example.learning.ui.theme.Create
import com.example.learning.ui.theme.Insideapp
import com.example.learning.ui.theme.Signin
import kotlin.getValue
import com.example.learning.ui.theme.viewmodel

class MainActivity : ComponentActivity() {

    @RequiresApi(Build.VERSION_CODES.O)
    @OptIn(ExperimentalAnimationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel: viewmodel by viewModels()

        val sharedPreferences = getSharedPreferences("learning", MODE_PRIVATE)
        val login = sharedPreferences.getBoolean("login", false)
        setContent {
            val navController = rememberNavController()
            viewModel.user()
            NavHost(
                navController = navController,
                startDestination = if (!login) "sign" else "inside",
                enterTransition = {
                    slideInHorizontally(
                        initialOffsetX = { full -> full },
                        animationSpec = tween(600)
                    ) + fadeIn(tween(900))
                },
                exitTransition = {
                    slideOutHorizontally(
                        targetOffsetX = { f -> -f },
                        animationSpec = tween(600)
                    ) + fadeOut(tween(600))
                }
            ) {
                composable("sign") { Signin(navController, viewModel) }
                composable("create") { Create(navController, viewModel) }
                composable("inside") { Insideapp(navController, viewModel) }
            }
        }
    }
}

@Composable
fun a() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Text("ddddddddddddddd")
    }
}

@Preview
@Composable
fun aaa() {
    a()
}
