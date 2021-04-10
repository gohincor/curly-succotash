package com.example.projetfinal.ui.localisationHistory

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.projetfinal.R
import com.example.projetfinal.databinding.ActivityLocalisationHistoryBinding

class LocalisationHistoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLocalisationHistoryBinding // <-- Référence à notre ViewBinding

    companion object {
        fun getStartIntent(context: Context): Intent {
            return Intent(context, LocalisationHistoryActivity::class.java) // retourne une intent
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_localisation_history)

        binding = ActivityLocalisationHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}