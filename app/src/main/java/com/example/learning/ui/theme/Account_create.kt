package com.example.learning.ui.theme

import android.widget.Toast
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideIn
import androidx.compose.animation.slideOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import com.example.learning.R
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults

import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun Create(navController: NavController, view: viewmodel) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White).verticalScroll(rememberScrollState())
    ) {
        var x by remember { mutableStateOf(false) }

        // 🔵 Semi-circle header
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
                        .background(Color(0xFFFF9800), shape = RoundedCornerShape(100))
                )
            }
        }

        Spacer(Modifier.height(35.dp))

        // 🧾 Example Content Under Semi Circle
        Column(
            modifier = Modifier
                .fillMaxHeight(0.5f)
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Image(
                painter = painterResource(id = R.drawable.checktrack),
                contentDescription = "name",
                modifier = Modifier.size(width = 180.dp, height = 40.dp)
            )
            var name by remember { mutableStateOf("") }
            var password by remember { mutableStateOf("") }
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
                    focusedIndicatorColor = Color(0xFFFF9800),
                    unfocusedIndicatorColor = Color.White,
                    focusedLabelColor = Color(0xFFFF9800),
                    unfocusedContainerColor = Color(0xFFFCF6EE),
                    focusedContainerColor = Color(0xFFFCF6EE)
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
                shape = CutCornerShape(16),
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = Color(0xFFFF9800),
                    unfocusedIndicatorColor = Color.White,
                    focusedLabelColor = Color(0xFFFF9800),
                    unfocusedContainerColor = Color(0xFFFCF6EE),
                    focusedContainerColor = Color(0xFFFCF6EE)
                ),
                visualTransformation = if (show) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    IconButton(onClick = { show = !show }) {
                        Image(
                            painter = if (show) painterResource(R.drawable.eyeshow) else painterResource(R.drawable.eyeno),
                            contentDescription = null, modifier = Modifier.size(30.dp)
                        )
                    }
                }

            )
            val context = LocalContext.current
            val main= Color(0xFFFF9800)
            val sec = Color(0xFFFCF6EE)
            Button(
                onClick = {
                    if (name.isNotBlank() && password.isNotBlank()) {
                        view.Accountcreate(name, password) { success ->
                            if (success) {
                                view.changeColor(main, sec)
                                navController.navigate("inside")

                                Toast.makeText(
                                    context,
                                    "Account Created Successful",
                                    Toast.LENGTH_SHORT
                                ).show()
                                name=""
                                password=""
                            } else {
                                name=""
                                password=""
                                Toast.makeText(
                                    context,
                                    "The Username already existed",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    } else {
                        Toast.makeText(
                            context,
                            "Enter Username and Password First",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(9.dp),
                colors = ButtonDefaults.buttonColors(
                    contentColor = Color.White,
                    containerColor = Color(0xFFFF9800)
                )
            ) {
                Text("Create", fontSize = 14.sp)
            }
            BasicText(
                text = buildAnnotatedString {
                    withStyle(style = SpanStyle(Color.Black)) {
                        append("Already have an Account?")
                    }
                    withStyle(style = SpanStyle(Color(0xFFFF9800))) {
                        append("Sign In")
                    }
                },
                modifier = Modifier.clickable {
                    view.delay()
                    navController.navigate("sign") }
            )

        }
        Spacer(Modifier.weight(0.2f))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
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
                        )      // move half up so only semicircle shows
                        .background(Color(0xFFFF9800), shape = RoundedCornerShape(100))
                )

            }
        }

    }
}
