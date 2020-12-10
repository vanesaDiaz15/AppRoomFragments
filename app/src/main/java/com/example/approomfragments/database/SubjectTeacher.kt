package com.example.approomfragments.database

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Junction
import androidx.room.Relation

data class SubjectTeacher (
    @Embedded var subject: Subject,

    @Relation(
        parentColumn = "name",
        entity = Teacher::class,
        entityColumn = "idTeacher",
        associateBy = Junction(value = SubjectTeacherCrossRef::class,
        parentColumn = "name",
        entityColumn = "idTeacher")
    )

    var teacher :List<Teacher>
)

@Entity(primaryKeys = ["name", "idTeacher"])
data class SubjectTeacherCrossRef(
    val name: String,
    val idTeacher: Int
)