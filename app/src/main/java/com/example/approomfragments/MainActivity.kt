package com.example.approomfragments

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import com.example.approomfragments.database.*
import com.example.approomfragments.fragments.ListaFragment
import com.example.approomfragments.fragments.FichaFragment
import com.example.approomfragments.fragments.ProfesorFragment
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
    var profesorFragment : ProfesorFragment? = null

    var segundoFragmentActivo = false

    lateinit var select:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        frameLayout = findViewById(R.id.frameLayoutFragment)
        frameLayoutLista = findViewById(R.id.frameLayoutFragmentLista)
        frameLayoutFicha = findViewById(R.id.frameLayoutFragmentLFicha)
        frameLayoutProfesorFragment = findViewById(R.id.frameLayoutProf)

        var dataRepository = DataRepository(this)
        var numStudents = dataRepository.getCountStudent()
        var numSubjects = dataRepository.getCuntSubject()
        var numTeachers = dataRepository.getCountTeacher()

        if (numStudents.toString().toInt() == 0 && numSubjects.toString().toInt() == 0 && numTeachers.toString().toInt() == 0) {
            rellenarDatos()
        }

        var subjects = ArrayList<String>()
        var data = DataRepository(this)
        var sub = data.getSubjects()

        val bbdd = sub.component1().name
        val prog = sub.component2().name
        subjects.add(bbdd)
        subjects.add(prog)

        var spinner = findViewById<Spinner>(R.id.spinner)
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, subjects)
        spinner.adapter = adapter

        listaFragment = ListaFragment.newInstance()
        listaFragment!!.activityListener = activityListener

        fichaFragent = FichaFragment()
        profesorFragment = ProfesorFragment.newInstance()

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View?,
                    position: Int,
                    id: Long
            ) {
                select = parent.getItemAtPosition(position).toString()
                profesorFragment!!.update(select)
                listaFragment!!.leer(select)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                select = parent!!.getItemAtPosition(0).toString()
                profesorFragment!!.update("BBDD")
                listaFragment!!.leer("BBDD")
            }
        }

        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()

        if (frameLayout == null) {
            fragmentTransaction.add(R.id.frameLayoutProf, profesorFragment!!)
            fragmentTransaction.add(R.id.frameLayoutFragmentLista, listaFragment!!)
            fragmentTransaction.add(R.id.frameLayoutFragmentLFicha, fichaFragent!!)
        } else {
            fragmentTransaction.add(R.id.frameLayoutProf, profesorFragment!!)
            fragmentTransaction.add(R.id.frameLayoutFragment, listaFragment!!)
        }

        fragmentTransaction.commit()
    }
    var activityListener = View.OnClickListener {
        if (frameLayout !=null) {
            val fragmentManager = supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.frameLayoutFragment, fichaFragent!!)
            fragmentTransaction.hide(profesorFragment!!)
            fragmentTransaction.commit()
            fragmentManager.executePendingTransactions()
            segundoFragmentActivo = true
        }

        fichaFragent!!.updateData(listaFragment!!.itemSeleccionado)
    }

    override fun onBackPressed() {
        if (segundoFragmentActivo && frameLayout != null){
            val fragmentManager = supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.frameLayoutFragment, listaFragment!!)
            fragmentTransaction.show(profesorFragment!!)
            fragmentTransaction.commit()
            fragmentManager.executePendingTransactions()
            segundoFragmentActivo = false
        }
        else{
            val builder = AlertDialog.Builder(this)

            builder.setTitle("Atención: ")
            builder.setMessage("¿Desea salir de la aplicación?")

            builder.setPositiveButton("SI"){ dialog, _ -> finish()}
            builder.setNegativeButton("NO"){ dialog, which ->  }
            builder.show()
        }
    }

    fun rellenarDatos(){
        var BBDD: Subject? = null
        var programacion: Subject? = null
        var bufferedReaderRecurso = BufferedReader(InputStreamReader(resources.openRawResource(R.raw.datos)))
        var textoLeido = bufferedReaderRecurso.readLine()
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

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("select", select)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        select = savedInstanceState.getString("select").toString()
    }
}

