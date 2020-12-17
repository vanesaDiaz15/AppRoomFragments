package com.example.approomfragments.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.approomfragments.R
import com.example.approomfragments.adapters.ItemAdapter
import com.example.approomfragments.database.DataRepository
import com.example.approomfragments.database.Student

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ListaFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ListaFragment : Fragment() {
    var activityListener: View.OnClickListener? = null
    var itemSeleccionado: Student? = null
    var recyclerViewLista: RecyclerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v= inflater.inflate(R.layout.fragment_lista, container, false)

        recyclerViewLista = v.findViewById<View>(R.id.recyclerView) as RecyclerView

        return v
    }

    fun leer(selected:String){
        var appContext = context!!.applicationContext

        var items = ArrayList<Student>()
        var dataRepository = DataRepository(appContext)
        var students = dataRepository.getSubjectStudent(selected)

        val numStudents = students.component1().students.size.toString()

        for (i in 0..numStudents.toInt() - 1) {
            val nameStudent = students.component1().students[i].nameStudent.toString()
            val apeStudent = students.component1().students[i].apeStudent.toString()
            items.add(Student(0, nameStudent, apeStudent))
        }

        var adapter = ItemAdapter(items) { item ->
            itemSeleccionado = item
            if (activityListener != null) {
                activityListener!!.onClick(view)
            }
        }

        recyclerViewLista!!.setLayoutManager(LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false))
        recyclerViewLista!!.setAdapter(adapter)
    }



    companion object {
        @JvmStatic
        fun newInstance() =
                ListaFragment().apply {}
    }
}