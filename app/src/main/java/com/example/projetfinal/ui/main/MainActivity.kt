package com.example.projetfinal.ui.main

import LocalPreferences
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.projetfinal.R
import com.example.projetfinal.databinding.ActivityMainBinding
import com.example.projetfinal.ui.localisation.LocalisationActivity
import com.example.projetfinal.ui.localisationHistory.LocalisationHistoryActivity
import com.example.projetfinal.ui.settings.SettingsActivity

class MainActivity : AppCompatActivity() {
    companion object {
        fun getStartIntent(context: Context): Intent {
            return Intent(context, MainActivity::class.java) // retourne une intent
        }
    }
    private lateinit var binding: ActivityMainBinding // <-- Référence à notre ViewBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // --> Indique que l'on utilise le ViewBinding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // Ouverture de la vue settings
        binding.mainIsettings.setOnClickListener {
            startActivity(SettingsActivity.getStartIntent(this))
        }
        // Ouverture de la vue localisation
        binding.mainBtnLocalisation.setOnClickListener {
            startActivity(LocalisationActivity.getStartIntent(this))
        }

        // Ouverture de la vue historique de localisation si des données sont présentes
        binding.mainBtnHistoLocalisation.setOnClickListener {
            if (LocalPreferences.getInstance(this).getHistory()?.size != 0) {
                startActivity(LocalisationHistoryActivity.getStartIntent(this))
            }
            else
                Toast.makeText(this, "Pas de localisation disponible", Toast.LENGTH_SHORT).show()
        }
    }
}