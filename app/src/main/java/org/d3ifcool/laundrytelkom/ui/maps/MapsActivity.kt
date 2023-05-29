package org.d3ifcool.laundrytelkom.ui.maps

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import org.d3ifcool.laundrytelkom.R
import org.d3ifcool.laundrytelkom.model.Laundry


class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    companion object {
        private val REQUEST_LOCATION_PERMISSION = 1
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_LOCATION_PERMISSION) {
            if (grantResults.isNotEmpty() &&
                grantResults[0] == PackageManager.PERMISSION_GRANTED
            ) {
                enableMyLocation()
            }
        }
    }

    private val viewModel: MapsViewModel by lazy {
        ViewModelProvider(this).get(MapsViewModel::class.java)
    }

    private lateinit var map: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        viewModel.getData().observe(this, { data ->
            data.forEach { createMarker(it) }
        })

        // calling the action bar
        // calling the action bar
        val actionBar: ActionBar? = supportActionBar

        // Customize the back button

        // Customize the back button
        actionBar?.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_ios_new_24)
        actionBar?.title = "Laundry Sekitar Telkom"


        // showing the back button in action bar

        // showing the back button in action bar
        actionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.map_options, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {
        R.id.normal_map -> {
            map.mapType = GoogleMap.MAP_TYPE_NORMAL
            true
        }
        R.id.hybrid_map -> {
            map.mapType = GoogleMap.MAP_TYPE_HYBRID
            true
        }
        R.id.satellite_map -> {
            map.mapType = GoogleMap.MAP_TYPE_SATELLITE
            true
        }
        R.id.terrain_map -> {
            map.mapType = GoogleMap.MAP_TYPE_TERRAIN
            true
        }
        androidx.appcompat.R.id.home -> {
            this.finish()
            true
        }
        else -> super.onOptionsItemSelected(item)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun createMarker(prov: Laundry) {
        val lokasi = LatLng(prov.lokasi.lat, prov.lokasi.lon)
        val snippet = "getString(R.string.jumlah_dirawat, prov.dirawat)"
        map.addMarker(
            MarkerOptions()
                .position(lokasi)
                .title(prov.key)
//                .snippet(snippet)
        )
    }

    override fun onMapReady(gmap: GoogleMap) {
        map = gmap
        viewModel.requestData()

        val lokasi = LatLng(-6.970121773518551, 107.63172735486246)
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(lokasi, 14f))

        enableMyLocation()
    }

    @SuppressLint("MissingPermission")
    private fun enableMyLocation() {
        if (ActivityCompat.checkSelfPermission(
                this.applicationContext,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            map.isMyLocationEnabled = true
        } else {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    android.Manifest.permission.ACCESS_FINE_LOCATION
                ),
                REQUEST_LOCATION_PERMISSION
            )
        }
    }
}