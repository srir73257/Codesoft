package com.example.learning.ui.theme

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query


@Dao
interface Userdao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertlogindetail(user : userentity): Long

    @Insert
    suspend fun addcontent(content: usercontent)

    @Query("DELETE FROM usercontent WHERE id=:id  ")
     suspend fun deletecontent(id: Int)

    @Query("SELECT * FROM usercontent WHERE username=:name")
    fun displaycontent(name: String): LiveData<List<usercontent>>

    @Query("SELECT * FROM userentity WHERE username=:name AND userpassword=:password")
    fun checklogin(name: String,password: String): LiveData<userentity?>

}

