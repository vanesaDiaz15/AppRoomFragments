package com.example.approomfragments.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.approomfragments.database.Subject

@Dao
interface SubjectDao {
    @Query("SELECT count(*) FROM subject")
    fun getCount() : Int

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAll(vararg subject: Subject)
}