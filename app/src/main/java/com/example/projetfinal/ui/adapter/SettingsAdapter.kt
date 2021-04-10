package com.example.projetfinal.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.projetfinal.R
import com.example.projetfinal.data.SettingsItem

class SettingsAdapter (private val settingsItemList: Array<SettingsItem>, private val onClick: ((selectedDevice: SettingsItem) -> Unit)? = null) : RecyclerView.Adapter<SettingsAdapter.ViewHolder>(){

    // Comment s'affiche ma vue
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun showItem(device: SettingsItem, onClick: ((selectedDevice: SettingsItem) -> Unit)? = null) {
            itemView.findViewById<TextView>(R.id.itemSettings_texte).text = device.name
            itemView.findViewById<ImageView>(R.id.itemSettings_image).setImageResource(device.icon)
            itemView.findViewById<ConstraintLayout>(R.id.itemSetting_constraint).setOnClickListener{
                device.onClick()
            }
            if(onClick != null) {
                itemView.setOnClickListener {
                    onClick(device)
                }
            }
        }
    }

    // Retourne une « vue » / « layout » pour chaque élément de la liste
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_settings, parent, false)
        return ViewHolder(view)
    }

    // Connect la vue ET la données
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.showItem(settingsItemList[position], onClick)
    }

    override fun getItemCount(): Int {
        return settingsItemList.size
    }
}