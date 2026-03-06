package com.example.learning.ui.theme

import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.TimePickerDefaults
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Home
import androidx.compose.animation.fadeOut
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SwipeToDismissBoxState
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.animation.core.tween
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.draw.clip
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import com.example.learning.R
import kotlinx.coroutines.delay
import java.time.LocalDateTime

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Insideapp(navController: NavController, view: viewmodel) {

    Column(
        modifier = Modifier
            .background(Color.White)
            .fillMaxSize()
    ) {
        val maincolor: Color = view.maincolour
        val seccolor: Color = view.seccolour
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    maincolor,
                    shape = RoundedCornerShape(bottomStart = 5.dp, bottomEnd = 5.dp)
                ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(Modifier.width(18.dp))
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center

            ) {
                Icon(
                    Icons.Default.Menu,
                    contentDescription = "profile",
                    modifier = Modifier.size(40.dp),
                    tint = Color.White
                )

            }
            Spacer(modifier = Modifier.fillMaxWidth(0.2f))
            Box(contentAlignment = Alignment.Center) {
                Image(
                    painter = painterResource(id = R.drawable.check),
                    contentDescription = "head",
                    modifier = Modifier.size(width = 170.dp, height = 120.dp)
                )
            }
            Spacer(modifier = Modifier.fillMaxWidth(0.25f))
            Box(
                contentAlignment = Alignment.Center, modifier = Modifier
                    .weight(1f)
                    .size(80.dp)
            ) {
                IconButton(onClick = {

                    view.sharedPreferences.edit().putBoolean("login",false).apply()

                    navController.navigate("sign") {
                        popUpTo("inside") { inclusive = true }
                    }
                }) {
                    Icon(
                        painter =painterResource(R.drawable.iconlogout),
                        contentDescription = "home",
                        modifier = Modifier.size(60.dp),
                        tint = Color.White
                    )
                }
            }

        }
        Spacer(modifier = Modifier.height(20.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(intrinsicSize = IntrinsicSize.Min),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            var content by rememberSaveable { mutableStateOf("") }
            var title by rememberSaveable { mutableStateOf("") }
            var time by rememberSaveable { mutableStateOf("") }
            val context = LocalContext.current
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .verticalScroll(rememberScrollState())
            ) {


                TextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("Title") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp, vertical = 8.dp),
                    shape = CutCornerShape(20),
                    colors = TextFieldDefaults.colors(
                        focusedIndicatorColor = maincolor,
                        unfocusedIndicatorColor = Color.White,
                        focusedLabelColor = maincolor,
                        unfocusedContainerColor = seccolor,
                        focusedContainerColor = seccolor
                    )

                )

                var showtimepicker by rememberSaveable { mutableStateOf(false) }


                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp, vertical = 8.dp)
                        .height(56.dp)
                        .clip(CutCornerShape(20))
                        .background(seccolor)
                        .clickable {
                            showtimepicker = !showtimepicker
                        }
                        .padding(horizontal = 16.dp),
                    contentAlignment = Alignment.CenterStart
                ) {

                    Column {
                        Text(
                            text = "Time",
                            color = maincolor,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.SemiBold
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        Text(
                            text = time,
                            color = Color.Black,
                            fontSize = 16.sp
                        )
                    }
                    Icon(
                        imageVector = Icons.Default.DateRange,
                        contentDescription = null,
                        modifier = Modifier
                            .align(Alignment.CenterEnd)
                    )
                }

                if (showtimepicker) {
                    Dialog(
                        onDismissRequest = { showtimepicker = false },
                        content = {
                            Showtimepicker(
                                view = view,
                                selectedtime = { t ->
                                    time = t
                                    showtimepicker = false

                                }

                            )
                        }
                    )
                }

                TextField(
                    value = content,
                    onValueChange = { content = it },
                    label = { Text("Content") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 15.dp, vertical = 8.dp),
                    shape = CutCornerShape(16),
                    colors = TextFieldDefaults.colors(
                        focusedIndicatorColor = maincolor,
                        unfocusedIndicatorColor = Color.White,
                        focusedLabelColor = maincolor,
                        unfocusedContainerColor = seccolor,
                        focusedContainerColor = seccolor
                    )
                )
            }

            Button(
                onClick = {
                    if (title.isNotBlank() && content.isNotBlank() && time.isNotBlank()) {
                        Toast.makeText(context, "Added Successfully", Toast.LENGTH_SHORT).show()
                        view.addcontent(title = title, content = content)
                        title = ""
                        content = ""
                        time=""
                    } else Toast.makeText(
                        context,
                        "Enter Title and Content and Select Time first",
                        Toast.LENGTH_SHORT
                    ).show()
                }, modifier = Modifier
                    .rotate(90f)
                    .size(height = 62.dp, width = 110.dp),
                shape = RoundedCornerShape(40),
                colors = ButtonDefaults.buttonColors(
                    contentColor = Color.White,
                    containerColor = maincolor
                )
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "ADD",
                    modifier = Modifier.size(40.dp)
                )
            }
        }


        val contentlist = view.content.observeAsState(emptyList())

        LazyColumn(
            modifier = Modifier
                .padding(horizontal = 5.dp, vertical = 12.dp)
                .fillMaxWidth()
        ) {
            items(contentlist.value, key = { it.Id }) { item ->
                SwipeToDeleteContainer(
                    content = item,
                    vieww = view,
                    ondelete = { view.deletecontent(item.Id) },

                    ) { data ->

                    ElevatedCard(
                        elevation = CardDefaults.cardElevation(
                            defaultElevation = 12.dp,
                            pressedElevation = 4.dp
                        ),
                        shape = RoundedCornerShape(23),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(maincolor),
                                horizontalArrangement = Arrangement.Start,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Spacer(Modifier.width(2.dp))
                                Box(modifier = Modifier.width(204.dp)) {
                                    Text(
                                        data.content_heading,
                                        fontSize = 19.sp,
                                        fontWeight = FontWeight.SemiBold,
                                        modifier = Modifier.padding(18.dp), color = Color.White
                                    )
                                }
                                Spacer(modifier = Modifier.weight(1f))

                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.End,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text(
                                            data.time,
                                            fontSize = 14.sp,
                                            fontWeight = FontWeight.SemiBold,
                                            color = Color.White
                                        )
                                        Icon(
                                            Icons.Default.Delete, contentDescription = "delete",
                                            modifier = Modifier
                                                .padding(
                                                    horizontal = 12.dp,
                                                    vertical = 3.dp
                                                )
                                                .clickable { view.deletecontent(data.Id) },
                                            tint = Color.White
                                        )
                                    }

                            }
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 20.dp, vertical = 10.dp)
                            ) {
                                Text(data.content, fontSize = 15.sp)
                            }
                        }
                    }
                }
                Spacer(Modifier.height(20.dp))
            }
        }
    }
}

@Composable
fun Background(
    dimissstate: SwipeToDismissBoxState, view: viewmodel
) {
    val seccolor: Color = view.seccolour
    val color =
        if (dimissstate.dismissDirection == SwipeToDismissBoxValue.EndToStart) seccolor else Color.Transparent
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color)
            .padding(20.dp),
        contentAlignment = Alignment.CenterEnd
    ) {
        Icon(
            Icons.Default.Delete,
            modifier = Modifier.size(30.dp),
            contentDescription = "deletee",
            tint = Color.White
        )
    }
}

@Composable
fun <T> SwipeToDeleteContainer(
    content: T,
    ondelete: (T) -> Unit,
    time: Int = 1200,
    vieww: viewmodel,
    contentt: @Composable (T) -> Unit
) {
    var removed by remember { mutableStateOf(false) }
    val state = rememberSwipeToDismissBoxState(
        confirmValueChange = { value ->
            if (value == SwipeToDismissBoxValue.EndToStart) {
                removed = true
                true
            } else {
                false
            }
        }, positionalThreshold = { f -> f * 3f }
    )

    LaunchedEffect(removed) {
        if (removed) {
            delay(time.toLong())
            ondelete(content)
        }
    }

    AnimatedVisibility(
        visible = !removed,
        exit = shrinkVertically(
            animationSpec = tween(durationMillis = time),
            shrinkTowards = Alignment.Top
        ) + fadeOut()
    ) {
        SwipeToDismissBox(
            state = state,
            backgroundContent = { Background(state, vieww) },
            enableDismissFromEndToStart = true,
            content = { contentt(content) }

        )
    }


}

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Showtimepicker(
    selectedtime: (String) -> Unit, view: viewmodel
) {
    val datatime = LocalDateTime.now()
    val timepicker = TimePickerState(
        initialHour = datatime.hour,
        initialMinute = datatime.minute,
        is24Hour = false
    )
    Surface(shape = RoundedCornerShape(9), tonalElevation = 8.dp) {

        Column(
            modifier = Modifier.padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val maincolor: Color = view.maincolour
            val seccolor: Color = view.seccolour
            TimePicker(
                state = timepicker, colors = TimePickerDefaults.colors(
                    clockDialColor = Color(0xFFF1F1F1),
                    clockDialSelectedContentColor = Color.White,
                    clockDialUnselectedContentColor = Color.Black,
                    selectorColor = maincolor,
                    periodSelectorBorderColor = maincolor,
                    periodSelectorSelectedContainerColor = maincolor,
                    periodSelectorSelectedContentColor = Color.White,
                    periodSelectorUnselectedContainerColor = seccolor,
                    periodSelectorUnselectedContentColor = Color.Black,
                    timeSelectorSelectedContainerColor = maincolor,
                    timeSelectorSelectedContentColor = Color.White,
                    timeSelectorUnselectedContainerColor = Color.Transparent,
                    timeSelectorUnselectedContentColor = Color.Black
                )
            )
            Spacer(Modifier.height(6.dp))
            Button(
                onClick = {
                    val hour = timepicker.hour
                    val min = timepicker.minute
                    val formattedTime = String.format("%02d:%02d", hour, min)

                    selectedtime(formattedTime)
                    view.timechangeforAlarm(hour, min)

                }, colors = ButtonDefaults.buttonColors(
                    contentColor = seccolor, containerColor = maincolor
                )
            ) {
                Text("OK")
            }
        }


    }
}

