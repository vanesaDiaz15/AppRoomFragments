package com.example.approomfragments

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import com.example.approomfragments.database.*
import com.example.approomfragments.fragments.ListaFragment
import com.example.approomfragments.fragments.FichaFragment
import org.json.JSONArray
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader

class MainActivity : AppCompatActivity() {
    var frameLayout : FrameLayout? = null
    var frameLayoutLista: FrameLayout? = null
    var frameLayoutFicha: FrameLayout? = null
    var frameLayoutProfesorFragment : FrameLayout? = null

    var listaFragment : ListaFragment? = null
    var fichaFragent : FichaFragment? = null

    var segundoFragmentActivo = false
    lateinit var textView:TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        frameLayout = findViewById(R.id.frameLayoutFragment)
        frameLayoutLista = findViewById(R.id.frameLayoutFragmentLista)
        frameLayoutFicha = findViewById(R.id.frameLayoutFragmentLFicha)
        frameLayoutProfesorFragment = findViewById(R.id.frameLayoutProf)
        textView = findViewById(R.id.textView)

        listaFragment = ListaFragment.newInstance()
        listaFragment!!.activityListener = activityListener
        var dataRepository = DataRepository(this)
        var numeroClientes = dataRepository.getCountStudent()
        if(numeroClientes.toString().toInt() == 0){
            rellenarDatos()
        }


    }

    var activityListener = View.OnClickListener {
        if (frameLayout !=null) {
            val fragmentManager = supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.frameLayoutFragment, fichaFragent!!)
            fragmentTransaction.commit()
            fragmentManager.executePendingTransactions()
            segundoFragmentActivo = true
        }


        //fichaFragent!!.updateData(listaFragment!!.itemSeleccionado)
    }

    fun rellenarDatos(){
        var BBDD: Subject? = null
        var programacion: Subject? = null
        var bufferedReaderRecurso = BufferedReader(InputStreamReader(resources.openRawResource(R.raw.datos)))
        var textoLeido = bufferedReaderRecurso.readLine()
        textView.text = textoLeido
        var jsonjObject = JSONObject(textoLeido)
        val subjects: JSONArray = jsonjObject.getJSONArray("asignaturas")
        for (i in 0 until subjects.length()) {
            if(subjects[i].toString().equals("BBDD")){
                BBDD = Subject(subjects[i].toString())
            }else {
                programacion = Subject(subjects[i].toString())
            }
        }

        val teachers : JSONArray = jsonjObject.getJSONArray("profesores")
        var teacherBBDD = ArrayList<Teacher>()
        var teacherProg = ArrayList<Teacher>()
        for (i in 0 until teachers.length()) {
            val teacher = teachers.getJSONObject(i)
            textView.text = teacher.getString("asignatura")
            if(teacher.getString("asignatura").equals("programacion")){
                val teacherB : Teacher = Teacher(teacher.getInt("codigo"), teacher.getString("nombre"), teacher.getString("apellido"))
                teacherProg.add(teacherB)
            }else {
                val teacherP: Teacher = Teacher(teacher.getInt("codigo"), teacher.getString("nombre"), teacher.getString("apellido"))
                teacherBBDD.add(teacherP)
            }
        }

        val students : JSONArray = jsonjObject.getJSONArray("alumnos")
        var studentsBBDD = ArrayList<Student>()
        var studentsProg = ArrayList<Student>()
        for(i in 0 until students.length()){
            val student = students.getJSONObject(i)
            var s = student.getJSONArray("Asignaturas")
            for(j in 0 until s.length()){
                if(s[j].toString().equals("programacion")){
                    val st = Student(student.getInt("codigo"), student.getString("nombre"), student.getString("apellido"))
                    studentsProg.add(st)
                }else{
                    val stP = Student(student.getInt("codigo"), student.getString("nombre"), student.getString("apellido"))
                    studentsBBDD.add(stP)
                }
            }
        }

        var subTecBBDD = SubjectTeacher(BBDD!!, teacherBBDD)
        var subStuBBDD = SubjectStudent(BBDD!!, studentsBBDD)
        var subTecProg = SubjectTeacher(programacion!!, teacherProg)
        var subStuProg = SubjectStudent(programacion!!, studentsProg)

        var dataRepository = DataRepository(this)
        dataRepository.insertSS(subStuBBDD)
        dataRepository.insertSS(subStuProg)
        dataRepository.insertST(subTecBBDD)
        dataRepository.insertST(subTecProg)
        bufferedReaderRecurso.close()
    }
}