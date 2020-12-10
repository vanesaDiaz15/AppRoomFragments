package com.example.approomfragments.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Subject::class, Teacher::class, Student::class, SubjectStudentCrossRef::class, SubjectTeacherCrossRef::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun subjectDao(): SubjectDao
    abstract fun teacherDao(): TeacherDao
    abstract fun studenDao(): StudenDao
    abstract fun subjectTeacherDao(): SubjectTeacherDao
    abstract fun subjectStudentDao() : SubjectStudentDao

    companion object {
        private const val DATABASE_NAME = "subject_database"

        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase? {
            INSTANCE ?: synchronized(this) {
                INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        DATABASE_NAME
                )./*addMigrations(MIGRATION_1_2).*/build()
            }
            return INSTANCE
        }

        /* val MIGRATION_1_2 = object : Migration(1, 2) {
             override fun migrate(database: SupportSQLiteDatabase) {
                 database.execSQL("CREATE TABLE 'Product' ('productId' INTEGER NOT NULL, 'nombre' TEXT, 'precio' INTEGER, PRIMARY KEY('productId'))")
             }
         }*/

    }
}