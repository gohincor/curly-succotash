package com.example.projetfinal.ui.localisationHistory

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.projetfinal.R
import com.example.projetfinal.databinding.ActivityLocalisationHistoryBinding
import com.example.projetfinal.ui.adapter.LocationHistoryAdapter

class LocalisationHistoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLocalisationHistoryBinding // <-- Référence à notre ViewBinding

    companion object {
        fun getStartIntent(context: Context): Intent {
            return Intent(context, LocalisationHistoryActivity::class.java) // retourne une intent
        }
    }
    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_localisation_history)

        supportActionBar?.apply {
            setTitle(getString(R.string.localisationHistory_title))
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }
        binding = ActivityLocalisationHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.localisationHistoryRecycleView.layoutManager = LinearLayoutManager(this) // type linéaire
        binding.localisationHistoryRecycleView.adapter = LocationHistoryAdapter(LocalPreferences.getInstance(this).getHistory())

    }
}