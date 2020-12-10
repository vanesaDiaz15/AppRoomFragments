package com.example.approomfragments.database

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Junction
import androidx.room.Relation

class SubjectStudent(
    @Embedded
    var subject: Subject,

    @Relation(
        parentColumn = "name",
        entity = Student::class,
        entityColumn = "idStudent",
        associateBy = Junction(value = SubjectStudentCrossRef::class,
            parentColumn = "name",
            entityColumn = "idStudent")
    )

    var students :List<Student>
)

@Entity(primaryKeys = ["name", "idStudent"])
data class SubjectStudentCrossRef(
    val name: String,
    val idStudent: Int
)