package com.example.approomfragments.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.approomfragments.database.Teacher

@Dao
interface TeacherDao {
    @Query ("SELECT * FROM teacher")
    fun getAll() :List<Teacher>

    @Query("SELECT count(*) FROM teacher")
    fun getCount(): Int

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAll(teachers : List<Teacher>)
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAll(vararg teachers: Teacher)


}