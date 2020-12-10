package com.example.approomfragments.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface StudenDao {
    @Query("SELECT * FROM student")
    fun getAll():List<Student>

    @Query("SELECT count(*) FROM student")
    fun getCountStudent() : Int

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAll(students : List<Student>)
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAll(vararg students: Student)
}