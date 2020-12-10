package com.example.approomfragments.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Student (
    @PrimaryKey var idStudent : Int,
    @ColumnInfo var nameStudent : String,
    @ColumnInfo var apeStudent : String
)