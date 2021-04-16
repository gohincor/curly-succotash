package com.example.projetfinal.ui.settings

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.projetfinal.BuildConfig
import com.example.projetfinal.R
import com.example.projetfinal.data.SettingsItem
import com.example.projetfinal.databinding.ActivitySettingsBinding
import com.example.projetfinal.ui.adapter.SettingsAdapter

class SettingsActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySettingsBinding // <-- Référence à notre ViewBinding

    companion object {
        fun getStartIntent(context: Context): Intent {
            return Intent(context, SettingsActivity::class.java) // retourne une intent
        }
    }
    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.apply {
            setTitle(getString(R.string.nomVueParam))
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }
        binding.settingsRecycleView.layoutManager = LinearLayoutManager(this) // type linéaire
        binding.settingsRecycleView.adapter = SettingsAdapter(arrayOf(
                SettingsItem(getString(R.string.settingsItem_paramApp), R.drawable.logo_settings) {
                    startActivity(
                            Intent(
                                    android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                                    Uri.fromParts("package" , BuildConfig.APPLICATION_ID,null)))
                },
                SettingsItem(getString(R.string.settingsItem_paramLocalisation), R.drawable.logo_location) {
                    val targetIntent = Intent().apply {
                        action = Settings.ACTION_LOCATION_SOURCE_SETTINGS
                    }
                    startActivity(targetIntent);
                },
                SettingsItem(getString(R.string.settingsItem_posEseo), R.drawable.logo_location_searching) {
                    val targetIntent = Intent().apply {
                        action = Settings.ACTION_SETTINGS
                    }
                    startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("geo:47.49313,-0.55132")));
                },
                SettingsItem(getString(R.string.settingsItem_siteEseo), R.drawable.eseo) {
                    startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("http://my.eseo.fr")));
                },
                SettingsItem(getString(R.string.settingsItem_email), R.drawable.logo_mail ) {
                    startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("mailto:corentin.gohin@reseau.eseo.fr")));
                }

        ))
    }
}