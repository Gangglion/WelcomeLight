package com.glion.welcomelightapp.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.glion.welcomelightapp.R
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class RegistLocationActivity : AppCompatActivity(), OnMapReadyCallback {
    private lateinit var mMap : GoogleMap
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registlocation)

        val mapFragment : SupportMapFragment? = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(this)
        // TODO : 지도에서 현재위치 등록, MVVM 형식으로 변경
    }
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        val SEOUL = LatLng(37.56,126.97)
        val markerOptions = MarkerOptions()
        markerOptions.position(SEOUL)
        markerOptions.title("현재위치")
        markerOptions.snippet("지도를 끌어 위치를 지정하세요")
        mMap.addMarker(markerOptions)
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(SEOUL,18f)) // 줌의 정도
        googleMap.mapType = GoogleMap.MAP_TYPE_HYBRID
    }
}