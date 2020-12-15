package com.example.approomfragments.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.approomfragments.R
import com.example.approomfragments.database.Student

class ItemAdapter(var items: ArrayList<Student>, private val listener: (Student) -> Unit) : RecyclerView.Adapter<ItemAdapter.ViewHolder>() {

    //this method is returning the view for each item in the list
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.custom_item_list, parent, false)
        val viewHolder = ViewHolder(v)
        return viewHolder
    }

    //this method is binding the data on the list
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(items[position])
        holder.itemView.setOnClickListener {listener(items[position]) }
    }

    //this method is giving the size of the list
    override fun getItemCount(): Int {
        if (items != null){
            return items.size
        }else{
            return 0
        }
    }


    //the class is hodling the list view
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewNombre :TextView
        val textViewApe : TextView
        init {
            textViewNombre = itemView.findViewById<TextView>(R.id.textViewName)
            textViewApe = itemView.findViewById<TextView>(R.id.textViewApe)
        }

        fun bindItems(student: Student) {

            textViewNombre.text = student.nameStudent

            textViewApe.text = student.apeStudent
        }
    }
}