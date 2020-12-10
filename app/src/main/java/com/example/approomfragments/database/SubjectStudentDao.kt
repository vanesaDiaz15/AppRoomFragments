package com.example.approomfragments.database

import androidx.room.*
import com.example.approomfragments.database.SubjectStudent
import com.example.approomfragments.database.SubjectStudentCrossRef

@Dao
interface SubjectStudentDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertSS(join: SubjectStudentCrossRef)

    @Transaction
    @Query("SELECT * FROM Subject")
    fun getSubjectsS() :List<SubjectStudent>

    @Transaction
    @Query("SELECT * FROM Subject WHERE name = :name")
    fun getSubjectsSOne(name: String) : List<SubjectStudent>
}