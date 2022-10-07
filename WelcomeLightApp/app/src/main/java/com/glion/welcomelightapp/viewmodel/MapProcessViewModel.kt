package com.glion.welcomelightapp.viewmodel

import android.util.Log
import com.glion.welcomelightapp.R
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapProcessViewModel(mapFragment: SupportMapFragment) : OnMapReadyCallback {
    private lateinit var mMap : GoogleMap
    private var mapFragment : SupportMapFragment
    // TODO : 지도에서 현재위치 등록, MVVM 형식으로 변경

    init{
        this.mapFragment = mapFragment
        mapFragment.getMapAsync(this)
    }
    override fun onMapReady(googleMap : GoogleMap) {
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

    fun checkBtnClick(){
        // TODO : 마커의 위치를 파라미터로 model에 위치값 저장, DB 저장 -> true 반환 시 다음 액티비티로 이동
        Log.d("tmdguq","다음 화면 이동 버튼 클릭")
    }
}