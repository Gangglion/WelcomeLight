package com.glion.welcomelightapp.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.glion.welcomelightapp.R
import com.glion.welcomelightapp.databinding.ActivityRegistlocationBinding
import com.glion.welcomelightapp.viewmodel.MapProcessViewModel
import com.google.android.gms.maps.SupportMapFragment


class RegistLocationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegistlocationBinding
    private lateinit var mapViewModel : MapProcessViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_registlocation)
        val mapFragment : SupportMapFragment? = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapViewModel = mapFragment?.let { MapProcessViewModel(it) }!!
        binding.mapProcessViewModel = mapViewModel
    }
}