package com.example.projetfinal.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.projetfinal.R

class LocationHistoryAdapter(private val stringList: MutableSet<String>?) : RecyclerView.Adapter<LocationHistoryAdapter.ViewHolder>(){ // (private val stringList: Array<MutableSet<String>?>, private val onClick: ((selectedDevice: MutableSet<String>?) -> Unit)? = null) : RecyclerView.Adapter<LocationHistoryAdapter.ViewHolder>(){
    // Mutable : collection
    // Comment s'affiche ma vue
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // fun showItem(device: MutableSet<String>?, onClick: ((selectedDevice: MutableSet<String>?) -> Unit)? = null) {
        fun showItem(item: String) {
            itemView.findViewById<TextView>(R.id.title).text = item.toString()
            itemView.setOnClickListener{

            }
        }
    }

    // Retourne une « vue » / « layout » pour chaque élément de la liste
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_string, parent, false)
        return ViewHolder(view)
    }

    // Connecte la vue ET la données
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if(stringList != null){
            holder.showItem(stringList.elementAt(position))//holder.showItem(stringList[position], onClick)
        }
    }

    override fun getItemCount(): Int {
        if(stringList != null) {
            return stringList.count()//return stringList.size
        }
        return 0
    }
}