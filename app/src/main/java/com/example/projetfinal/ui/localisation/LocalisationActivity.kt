 package com.example.projetfinal.ui.localisation

import LocalPreferences
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
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
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
    private lateinit var fusedLocationClient: FusedLocationProviderClient // Localisation
    companion object {
        const val PERMISSION_REQUEST_LOCATION = 9999
        fun getStartIntent(context: Context): Intent {
            return Intent(context, LocalisationActivity::class.java) // retourne une intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_localisation)
        /// Affiche ActionBar avec nom de la vue
        supportActionBar?.apply {
            setTitle(getString(R.string.localisation_title))
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }
        binding = ActivityLocalisationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        binding.localisationBtnLocalisation.setOnClickListener {
            requestPermission() // Demander la permission d'accéder à la localisation
        }
    }
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when (requestCode) {
            PERMISSION_REQUEST_LOCATION -> {
                // If request is cancelled, the result arrays are empty.
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) { // Localisation acceptée
                    // Permission obtenue, Nous continuons la suite de la logique.
                    getLocation()
                } else { // Localisation refusée
                    MaterialAlertDialogBuilder(this)
                            .setTitle(getString(R.string.localisationActivity_allowLocation))
                            .setMessage(getString(R.string.localisationActivity_msgLocation))
                            .setNeutralButton(getString(R.string.localisationActivity_validate)) { dialog, which ->
                                startActivity(
                                        Intent(
                                                android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                                                Uri.fromParts("package" , BuildConfig.APPLICATION_ID,null)))
                            }
                            .setNegativeButton(getString(R.string.localisationActivity_quit)) { dialog, which ->
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
            if (!isLocationEnabled(this)) { // Vérifier si localisation activée
                MaterialAlertDialogBuilder(this)
                        .setTitle(getString(R.string.locationActivate))
                        .setMessage(getString(R.string.msgActivationLocation))
                        .setNeutralButton(getString(R.string.activationLocation)) { dialog, which ->
                            val targetIntent = Intent().apply {
                                action = Settings.ACTION_LOCATION_SOURCE_SETTINGS
                            }
                            startActivity(targetIntent);
                        }
                        .setNegativeButton(getString(R.string.refuseActivateLocation)) { dialog, which ->
                            finish()
                        }
                        .show()
            } else {
                val locationManager = applicationContext.getSystemService(LOCATION_SERVICE) as LocationManager?
                locationManager?.run {
                    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 10000F, LocationListener { geoCode(it) });
                }
            }
        }
    }
     private fun isLocationEnabled(mContext: Context): Boolean {
         val lm = mContext.getSystemService(Context.LOCATION_SERVICE) as LocationManager
         return lm.isProviderEnabled(LocationManager.GPS_PROVIDER) || lm.isProviderEnabled(
                 LocationManager.NETWORK_PROVIDER)
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
        /// Distance entre position et ESEO
        val locationEseo = Location(LocationManager.NETWORK_PROVIDER)
        locationEseo.latitude = 47.49313
        locationEseo.longitude = -0.55132
        val distance:Float = location.distanceTo(locationEseo) / 1000 // En kilomètre
        val distanceFormatted = String.format("%.2f",distance).plus(" km")
        // Sauvegarde des données
        val geocoder = Geocoder(this, Locale.getDefault())
        val results = geocoder.getFromLocation(location.latitude, location.longitude, 1)
        if (results.isNotEmpty()) {
            binding.localisationTxtDistance.text = distanceFormatted.toString()
            LocalPreferences.getInstance(this).saveStringValue(results[0].getAddressLine(0)+" |"+location.latitude+","+location.longitude)
            LocalPreferences.getInstance(this).addToHistory(results[0].getAddressLine(0)+" |"+location.latitude+","+location.longitude)
            Toast.makeText(this, results[0].getAddressLine(0)+"|"+location.latitude+","+location.longitude, Toast.LENGTH_SHORT).show()
        }
    }
     // bouton de retour
    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}