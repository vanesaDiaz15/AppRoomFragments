package com.example.approomfragments.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.approomfragments.database.Student
import com.example.approomfragments.R

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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v= inflater.inflate(R.layout.fragment_lista, container, false)

        val recyclerViewLista: RecyclerView = v.findViewById<View>(R.id.recyclerView) as RecyclerView

        var items = ArrayList<Student>()
        /*for (i in 1..20){
            items.add(Student(i.toString(), i.toString()))
        }

        val adapter = ItemAdapter(items) { item ->
            itemSeleccionado = item
            if (activityListener != null) {
                activityListener!!.onClick(view)
            }
        }*/

        //recyclerViewLista.setAdapter(adapter)
        recyclerViewLista.setLayoutManager(LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false))
        return v
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            ListaFragment().apply {}
    }
}