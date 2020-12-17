
package com.example.approomfragments.database

import android.content.Context
import android.os.AsyncTask

class DataRepository(context: Context) {
    private val subjectDao: SubjectDao? = AppDatabase.getInstance(context)?.subjectDao()
    private val studentDao: StudenDao? = AppDatabase.getInstance(context)?.studenDao()
    private val teacherDao: TeacherDao? = AppDatabase.getInstance(context)?.teacherDao()
    private val subjectStudenDao: SubjectStudentDao? = AppDatabase.getInstance(context)?.subjectStudentDao()
    private val subjectTeacherDao: SubjectTeacherDao? = AppDatabase.getInstance(context)?.subjectTeacherDao()

    fun insertSS(subjectStudent: SubjectStudent):Int{
        if(subjectDao != null && studentDao != null && subjectStudenDao != null){
            return InsertSubjectStudentAsyncTask(subjectDao, studentDao, subjectStudenDao).execute(subjectStudent).get()
        }
        return -1
    }

    fun getSubjectStudent(name: String):List<SubjectStudent>{
        return GetSubjectStudentOne(subjectStudenDao!!, name).execute().get()
    }

    fun getSubjectTeacher(name:String):List<SubjectTeacher>{
        return GetSubjectTeacherOne(subjectTeacherDao!!, name).execute().get()
    }

    private class InsertSubjectStudentAsyncTask(private val subjectDao: SubjectDao, private val studentDao: StudenDao, private val subjectStudentDao: SubjectStudentDao): AsyncTask<SubjectStudent, Void, Int>(){
        override fun doInBackground(vararg subjectStudent: SubjectStudent?): Int {
            try{
                for (subjectStudent1 in subjectStudent){
                    if (subjectStudent1 !=null){
                        subjectDao.insertAll(subjectStudent1.subject)
                        studentDao.insertAll(subjectStudent1.students)
                        for (student in subjectStudent1.students){
                            subjectStudentDao.insertSS(SubjectStudentCrossRef(subjectStudent1.subject.name, student.idStudent))
                        }
                    }
                }

                return 0
            }
            catch (exception: Exception){
                return -1
            }
        }
    }

    fun insertST(subjectTeacher: SubjectTeacher):Int{
        if(subjectDao != null && teacherDao != null && subjectTeacherDao != null){
            return InsertSubjectTeacherAsyncTask(subjectDao, teacherDao, subjectTeacherDao).execute(subjectTeacher).get()
        }
        return -1
    }

    private class InsertSubjectTeacherAsyncTask(private val subjectDao: SubjectDao, private val teacherDao: TeacherDao, private val subjectTeacherDao: SubjectTeacherDao): AsyncTask<SubjectTeacher, Void, Int>(){
        override fun doInBackground(vararg subjectTeacher: SubjectTeacher?): Int {
            try{
                for (subjectTeacher1 in subjectTeacher){
                    if (subjectTeacher1 !=null){
                        subjectDao.insertAll(subjectTeacher1.subject)
                        teacherDao.insertAll(subjectTeacher1.teacher)
                        for (teacher in subjectTeacher1.teacher){
                            subjectTeacherDao.insertST(SubjectTeacherCrossRef(subjectTeacher1.subject.name, teacher.idTeacher))
                        }
                    }
                }

                return 0
            }
            catch (exception: Exception){
                return -1
            }
        }
    }


    private class GetCountStudent(private val studentDao: StudenDao):AsyncTask<Void, Void, Int>(){
        override fun doInBackground(vararg params: Void?): Int {
            return studentDao.getCountStudent()
        }
    }

    fun getCountStudent(): Int {
        var getCount = GetCountStudent(studentDao!!).execute().get()
        return getCount
    }

    private class GetCountSubject(private val subjectDao1: SubjectDao):AsyncTask<Void, Void, Int>(){
        override fun doInBackground(vararg params: Void?): Int {
            return subjectDao1.getCount()
        }
    }

    fun getCountTeacher():Int{
        var getCount = GetCountTeacher(teacherDao!!).execute().get()
        return getCount
    }

    private class GetCountTeacher(private val teacherDao: TeacherDao):AsyncTask<Void, Void, Int>(){
        override fun doInBackground(vararg params: Void?): Int {
            return teacherDao.getCount()
        }
    }

    fun getCuntSubject():Int{
        var getCountSubject = GetCountSubject(subjectDao!!).execute().get()
        return getCountSubject
    }

    private class GetSubjectsTeacher(private val subjectTeacherDao1: SubjectTeacherDao) :AsyncTask<Void, Void, List<SubjectTeacher>>(){
        override fun doInBackground(vararg params: Void?): List<SubjectTeacher> {
            return subjectTeacherDao1.getSubjectsT()
        }
    }
    private class GetSubjectTeacherOne(private val subjectTeacherDao1: SubjectTeacherDao, private val name: String) :AsyncTask<Void, Void, List<SubjectTeacher>>(){
        override fun doInBackground(vararg params: Void?): List<SubjectTeacher> {
            return subjectTeacherDao1.getSubjectsTOne(name)
        }
    }

    private class GetSubjectsStudent(private val subjectStudentDao: SubjectStudentDao) :AsyncTask<Void, Void, List<SubjectStudent>>(){
        override fun doInBackground(vararg params: Void?): List<SubjectStudent> {
            return subjectStudentDao.getSubjectsS()
        }
    }
    private class GetSubjectStudentOne(private val subjectStudentDao: SubjectStudentDao, private val name: String) :AsyncTask<Void, Void, List<SubjectStudent>>(){
        override fun doInBackground(vararg params: Void?): List<SubjectStudent> {
            return subjectStudentDao.getSubjectsSOne(name)
        }
    }

    private class GetSubjects(private val subjectDao: SubjectDao):AsyncTask<Void, Void, List<Subject>>(){
        override fun doInBackground(vararg params: Void?): List<Subject> {
            return subjectDao.getSubjects()
        }
    }

    fun getSubjects():List<Subject>{
        var getCountSubject = GetSubjects(subjectDao!!).execute().get()
        return getCountSubject
    }
}
