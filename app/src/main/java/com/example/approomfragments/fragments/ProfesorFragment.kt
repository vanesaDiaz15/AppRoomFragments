package com.example.approomfragments.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.approomfragments.R
import com.example.approomfragments.database.DataRepository

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
    var textViewName :TextView? = null
    var textViewApe : TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v= inflater.inflate(R.layout.fragment_profesor, container, false)


        textViewName = v.findViewById<TextView>(R.id.textViewFichaNombreP)
        textViewApe = v.findViewById<TextView>(R.id.textViewFichaApellidoP)

        return v
    }

    fun update(subject :String){
        var appContext = context!!.applicationContext
        var dataRepository = DataRepository(appContext)

        var teachers = dataRepository.getSubjectTeacher(subject)
        val numTeachers = teachers.component1().teacher.size.toString()

        for (i in 0..numTeachers.toInt() - 1){
            var nameTeacher = teachers.component1().teacher[0].nameTeacher.toString()
            var apeTeacher = teachers.component1().teacher[0].apeTeacher.toString()

            textViewName!!.text = nameTeacher
            textViewApe!!.text = apeTeacher
        }
    }
    companion object {
        @JvmStatic
        fun newInstance() =
                ProfesorFragment().apply {}
    }
}