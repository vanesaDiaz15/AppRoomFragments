package com.example.approomfragments.database

import androidx.room.*
import com.example.approomfragments.database.SubjectTeacher
import com.example.approomfragments.database.SubjectTeacherCrossRef

@Dao
interface SubjectTeacherDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertST(join: SubjectTeacherCrossRef)

    @Transaction
    @Query("SELECT * FROM Subject")
    fun getSubjectsT() :List<SubjectTeacher>

    @Transaction
    @Query("SELECT * FROM Subject WHERE name = :name")
    fun getSubjectsTOne(name: String) : List<SubjectTeacher>
}