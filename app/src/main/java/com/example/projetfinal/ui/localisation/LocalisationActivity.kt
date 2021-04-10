package com.example.projetfinal.ui.localisation

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.ActivityCompat
import com.example.projetfinal.BuildConfig
import com.example.projetfinal.R
import com.example.projetfinal.databinding.ActivityLocalisationBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import java.util.*

class LocalisationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLocalisationBinding // <-- Référence à notre ViewBinding
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    companion object {
        const val PERMISSION_REQUEST_LOCATION = 9999
        fun getStartIntent(context: Context): Intent {
            return Intent(context, LocalisationActivity::class.java) // retourne une intent
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_localisation)

        binding = ActivityLocalisationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        binding.localisationBtnLocalisation.setOnClickListener {
            requestPermission()
        }
    }
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when (requestCode) {
            PERMISSION_REQUEST_LOCATION -> {
                // If request is cancelled, the result arrays are empty.
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    // Permission obtenue, Nous continuons la suite de la logique.
                    getLocation()
                } else {
                    MaterialAlertDialogBuilder(this)
                            .setTitle("Autoriser la localisation ?")
                            .setMessage("Cette autorisation est nécessaire au bon fonctionnement de l'application")
                            .setNeutralButton("Valider") { dialog, which ->
                                startActivity(
                                        Intent(
                                                android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                                                Uri.parse("package" + BuildConfig.APPLICATION_ID)))
                            }
                            .setNegativeButton("Quitter") { dialog, which ->
                                finish()
                            }
                            .show()
                }
                return
            }
        }
    }

    @SuppressLint("MissingPermission")
    private fun getLocation() {
        if (hasPermission()) {
            val locationManager = applicationContext.getSystemService(LOCATION_SERVICE) as LocationManager?
            locationManager?.run {
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 10000F, LocationListener { geoCode(it) });
            }
        }
    }
    private fun requestPermission() {
        if (!hasPermission()) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), PERMISSION_REQUEST_LOCATION)
        } else {
            getLocation()
        }
    }
    private fun hasPermission(): Boolean {
        return ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
    }
    private fun geoCode(location: Location){
        val geocoder = Geocoder(this, Locale.getDefault())
        val results = geocoder.getFromLocation(location.latitude, location.longitude, 1)
        if (results.isNotEmpty()) {
            binding.localisationTxtDistance.text = results[0].getAddressLine(0)
        }
    }
    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}