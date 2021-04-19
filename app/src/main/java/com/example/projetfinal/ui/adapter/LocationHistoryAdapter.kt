package com.example.projetfinal.ui.adapter

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.projetfinal.R

class LocationHistoryAdapter(private val stringList: MutableSet<String>?) : RecyclerView.Adapter<LocationHistoryAdapter.ViewHolder>(){
    // Mutable : collection
    // Comment s'affiche ma vue
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun showItem(item: String) {
            itemView.findViewById<TextView>(R.id.title).text = item.toString()
            var list = item.split("|")
            // Récuperer les données de longitude et de latitude
            if(list.size == 2){
                var adress= list[0]
                var position = list[1]
                var listPosition = position.split(",")
                var longitude = listPosition[0]
                var latitude = listPosition[1]
                // Ouverture de la localisation enregistrée sur une application
                itemView.setOnClickListener{
                    it.context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("geo:$longitude,$latitude")))
                }
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
            holder.showItem(stringList.elementAt(position))
        }
    }

    override fun getItemCount(): Int {
        if(stringList != null) {
            return stringList.count()
        }
        return 0
    }
}