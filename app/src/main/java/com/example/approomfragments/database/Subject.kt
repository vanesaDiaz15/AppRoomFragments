package com.example.approomfragments.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Subject (
    @PrimaryKey val name: String
)