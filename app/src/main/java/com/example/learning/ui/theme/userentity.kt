package com.example.learning.ui.theme

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "userentity", indices = [Index(value = ["username"], unique = true)])
data class userentity(

    @PrimaryKey()
    val username : String,
    val userpassword : String
)

@Entity(
    tableName = "usercontent",
    foreignKeys = [ForeignKey(
        entity = userentity::class,
        parentColumns = ["username"],
        childColumns = ["username"]
    )],
    indices = [Index("username")]
)
data class usercontent(
    val username: String,
    @PrimaryKey(autoGenerate = true)
    val Id: Int=0,
    val content_heading: String,
    val content: String,
    val time:String,
    val Alarmhour : Int,
    val AlarmMin : Int
)
