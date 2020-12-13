package com.example.approomfragments.fragments

import android.os.Bundle
import android.os.TestLooperManager
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Spinner
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.approomfragments.MainActivity
import com.example.approomfragments.R
import com.example.approomfragments.adapters.ItemAdapter
import com.example.approomfragments.database.DataRepository
import com.example.approomfragments.database.Student
import com.example.approomfragments.database.Teacher
import kotlinx.coroutines.selects.select

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ProfesorFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ProfesorFragment : Fragment() {
    var selected : String = "BBDD"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val appContext = context!!.applicationContext
        val v= inflater.inflate(R.layout.fragment_profesor, container, false)


        var textViewName = v.findViewById<TextView>(R.id.textViewFichaNombreP)
        var textViewApe = v.findViewById<TextView>(R.id.textViewFichaApellidoP)
        var dataRepository = DataRepository(appContext)

        var students = dataRepository.getSubjectTeacher(selected)
        val numProductos = students.component1().teacher.size.toString()

        for (i in 0..numProductos.toInt() - 1){
            var nameStudent = students.component1().teacher[0].nameTeacher.toString()
            var apeStudent = students.component1().teacher[0].apeTeacher.toString()

            textViewName.text = nameStudent
            textViewApe.text = apeStudent
        }



        return v
    }

    companion object {
        @JvmStatic
        fun newInstance() =
                ProfesorFragment().apply {}
    }
}