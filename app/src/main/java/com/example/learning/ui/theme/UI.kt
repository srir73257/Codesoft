package com.example.learning.ui.theme

import android.widget.Toast

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*

import androidx.compose.foundation.shape.RoundedCornerShape

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

import androidx.compose.material3.IconButton

import androidx.compose.animation.slideIn
import androidx.compose.animation.slideOut

import androidx.compose.ui.unit.IntOffset
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

import androidx.compose.ui.unit.dp
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

import androidx.compose.animation.core.tween

import androidx.compose.foundation.Image

import androidx.compose.foundation.clickable
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons


import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon

import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.LaunchedEffect

import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.learning.R


@Composable
fun Signin(navController: NavController, view: viewmodel) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .verticalScroll(rememberScrollState())
    ) {
        var x by remember { mutableStateOf(false) }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.25f),   // only this portion is visible
            contentAlignment = Alignment.TopCenter
        ) {
            LaunchedEffect(Unit) {
                x = true
            }
            androidx.compose.animation.AnimatedVisibility(
                visible = x,
                enter = slideIn(
                    initialOffset = { fullSize ->
                        // Start from top-right (Positive X, Negative Y)
                        IntOffset((-fullSize.width * 1f).toInt(), (fullSize.height * 0.9f).toInt())
                    },
                    animationSpec = tween(durationMillis = 1200)
                ),
                exit = slideOut(
                    targetOffset = { fullSize ->
                        // Exit back to top-right
                        IntOffset(fullSize.width, -fullSize.height)
                    }
                ),
                label = "s"
            ) {
                Box(
                    modifier = Modifier
                        .size(210.dp)               // full circle size
                        .offset(
                            y = (-60).dp,
                            x = 160.dp
                        )      // move half up so only semicircle shows
                        .background(Color(0xFF169CFC), shape = RoundedCornerShape(100))
                )
            }
        }


Spacer(modifier = Modifier.height(38.dp))

        // 🧾 Example Content Under Semi Circle
        Column(
            modifier = Modifier
                .fillMaxHeight(0.5f)
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                ,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            var name by remember { mutableStateOf("") }
            var password by remember { mutableStateOf("") }
            Image(
                painter = painterResource(id = R.drawable.checktrack),
                contentDescription = "name",
                modifier = Modifier.size(width = 180.dp, height = 40.dp)
            )
            Spacer(modifier = Modifier.height(12.dp))
            TextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("username") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                shape = CutCornerShape(20),
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = Color(0xFF169CFC),
                    unfocusedIndicatorColor = Color.White,
                    focusedLabelColor = Color(0xFF169CFC),
                    unfocusedContainerColor = Color(0xFFF0F4F8),
                    focusedContainerColor = Color(0xFFF0F4F8)
                )

            )
            var show by remember { mutableStateOf(false) }
            TextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("password") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                shape = CutCornerShape(20),
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = Color(0xFF169CFC),
                    unfocusedIndicatorColor = Color.White,
                    focusedLabelColor = Color(0xFF169CFC),
                    unfocusedContainerColor = Color(0xFFF0F4F8),
                    focusedContainerColor = Color(0xFFF0F4F8)
                ),
                visualTransformation = if (show) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    IconButton(onClick = { show = !show }) {
                        Image(
                            painter = if (show) painterResource(R.drawable.eyeshow) else painterResource(R.drawable.eyeno),
                            contentDescription = null,modifier = Modifier.size(30.dp)
                        )
                    }
                }
            )

            val main = Color(0xFF169CFC)
            val sec= Color(0xFFF0F4F8)
            val content = LocalContext.current
            Button(
                onClick = {
                    if (name.isNotBlank() && password.isNotBlank()) {
                        view.Accountlogin(name, password) { sucess ->
                            if (sucess) {
                                view.changeColor(main, sec)
                                navController.navigate("inside")
                                Toast.makeText(content, "Login Sucsessfull ", Toast.LENGTH_SHORT)
                                    .show()
                                name = ""
                                password = ""
                            } else {
                                Toast.makeText(
                                    content,
                                    "Check Username and Password",
                                    Toast.LENGTH_SHORT
                                ).show()
                                name = ""
                                password = ""
                            }
                        }
                    } else {
                        Toast.makeText(
                            content,
                            "Enter Username and Password First ",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(9.dp),
                colors = ButtonDefaults.buttonColors(
                    contentColor = Color.White,
                    containerColor = Color(0xFF169CFC)
                )
            ) {
                Text("Sign In", fontSize = 14.sp)
            }
            BasicText(
                text = buildAnnotatedString {
                    withStyle(style = SpanStyle(Color.Black)) {
                        append("Dont't have an Account?")
                    }
                    withStyle(style = SpanStyle(Color(0xFF169CFC))) {
                        append("Create an Account")
                    }
                },
                modifier = Modifier.clickable {
                    view.delay()
                    navController.navigate("create") }
            )

        }
        Spacer(modifier = Modifier.weight(0.3f))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                ,
            contentAlignment = Alignment.BottomCenter
        ) {
            var y by remember { mutableStateOf(false) }
            LaunchedEffect(Unit) {
                y = true
            }
            androidx.compose.animation.AnimatedVisibility(
                visible = y,
                enter = slideIn(
                    initialOffset = { fullSize ->
                        IntOffset(x = (fullSize.width), y = (-fullSize.height))
                    },
                    animationSpec = tween(durationMillis = 1200)
                ),
                exit = slideOut(
                    targetOffset = { fullSize ->
                        // Exit back to top-right
                        IntOffset(fullSize.width, -fullSize.height)
                    }
                )
            ) {
                Box(
                    modifier = Modifier
                        .size(210.dp)
                        .offset(
                            y = 55.dp,
                            x = (-160).dp
                        )
                        .background(Color(0xFF169CFC), shape = RoundedCornerShape(100))
                )

            }
        }

    }

}

