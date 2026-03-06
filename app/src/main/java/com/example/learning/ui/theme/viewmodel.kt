package com.example.learning.ui.theme


import android.app.Application
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import androidx.lifecycle.LiveData
import androidx.lifecycle.switchMap
import kotlinx.coroutines.delay
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

class viewmodel(application: Application) : AndroidViewModel(application) {

    val dao = DDatabase.getdatabase(application).userdao()

    var username by mutableStateOf("")

    val namelive = MutableLiveData<String>("")



    val sharedPreferences = application.getSharedPreferences("learning", Application.MODE_PRIVATE)

    fun Accountcreate(name: String, password: String, result: (Boolean) -> Unit) {
        user()
        namelive.value = name

        username = name
        viewModelScope.launch {
            val r = dao.insertlogindetail(userentity(username, password))
            if (r != -1L) {

                sharedPreferences.edit()
                    .putBoolean("login", true)
                    .putString("username", name)
                    .apply()

                result(true)
            } else {
                result(false)
            }
        }
    }

    fun user(){
        namelive.value =sharedPreferences.getString("username","")
    }



    fun Accountlogin(name: String, password: String, result: (Boolean) -> Unit) {
        user()
        namelive.value = name
        username = name
        viewModelScope.launch {
            dao.checklogin(username, password).observeForever { user ->
                if (user != null) {

                    sharedPreferences.edit()
                        .putBoolean("login", true)
                        .putString("username", name)
                        .apply()

                    result(true)
                } else {
                    result(false)
                }
            }
        }
    }

    val content: LiveData<List<usercontent>> = namelive.switchMap { username ->
        dao.displaycontent(username)
    }
    var Hour by mutableIntStateOf(0)
    var Min by mutableIntStateOf(0)
    fun timechangeforAlarm(hour: Int, min: Int) {
        Hour = hour
        Min = min
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun addcontent(title: String, content: String) {
        val foramter = DateTimeFormatter.ofPattern("dd/MM/yyyy  HH:mm", Locale.getDefault())
        val dateTime = LocalDateTime.now().format(foramter)
        viewModelScope.launch {
            val x = usercontent(
                username = username,
                content = content,
                content_heading = title,
                time = dateTime,
                Alarmhour = Hour,
                AlarmMin = Min
            )
            try {
                dao.addcontent(x)
                Alarmsheduler(
                    context = getApplication(),
                    hour = Hour,
                    min = Min,
                    Id_Code = x.Id,
                    content,
                    title
                )
            } catch (e: SecurityException) {
                e.printStackTrace()
            }
        }
    }

    fun deletecontent(id: Int) {
        viewModelScope.launch {
            dao.deletecontent(id)
        }
    }

    var maincolour: Color = Color(0xFF169CFC)
    var seccolour: Color = Color(0xFFF0F4F8)
    fun changeColor(x: Color, y: Color) {

        maincolour = x
        seccolour = y
    }

    fun delay() {
        viewModelScope.launch {
            delay(200)
        }
    }

}
