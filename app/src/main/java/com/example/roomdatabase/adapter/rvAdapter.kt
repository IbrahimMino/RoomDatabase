package com.example.roomdatabase.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.roomdatabase.MainActivity
import com.example.roomdatabase.OnItemClickedListener
import com.example.roomdatabase.R
import com.example.roomdatabase.entity.User

class rvAdapter(val listOfUser:MutableList<User>,var listener: OnItemClickedListener): RecyclerView.Adapter<rvAdapter.ViewHolder>() {

     var listener2: OnItemClickedListener = listener


    inner class ViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){
         val name = itemView.findViewById<TextView>(R.id.tvName)
        val country = itemView.findViewById<TextView>(R.id.tvCountry)
        fun onBind(user: User) {
            name.text = user.username
            country.text = user.country


            itemView.setOnClickListener{
                listener2.onItemClicked(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_item,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
         holder.onBind(listOfUser[position])
    }

    override fun getItemCount(): Int {
        return listOfUser.size
    }




}