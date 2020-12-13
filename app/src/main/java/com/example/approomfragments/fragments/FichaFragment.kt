package com.example.approomfragments.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.approomfragments.R
import com.example.approomfragments.database.Student

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FichaFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class FichaFragment : Fragment() {
    var textViewNombre: TextView? = null
    var textViewDireccion: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v =  inflater.inflate(R.layout.fragment_ficha, container, false)

        textViewNombre = v.findViewById<View>(R.id.textViewFichaNombreP) as TextView
        textViewDireccion = v.findViewById<View>(R.id.textViewFichaApellidoP) as TextView
        return v
    }

    fun updateData(item: Student?) {
        if (item!=null) {
            textViewNombre!!.text = item.nameStudent
            textViewDireccion!!.text = item.apeStudent
        }
    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
                FichaFragment().apply {
                }
    }
}