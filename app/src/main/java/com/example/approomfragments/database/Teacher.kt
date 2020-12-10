package com.example.approomfragments.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Teacher (
    @PrimaryKey var idTeacher: Int,
    @ColumnInfo var nameTeacher : String,
    @ColumnInfo var apeTeacher : String
)