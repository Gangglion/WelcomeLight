package com.glion.welcomelightapp.viewmodel

import android.util.Log
import com.glion.welcomelightapp.model.Model
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import kotlin.properties.Delegates
import kotlin.reflect.typeOf

class MapProcessViewModel(mapFragment: SupportMapFragment) : OnMapReadyCallback {
    private var model = Model()
    private lateinit var mMap : GoogleMap
    private var mapFragment : SupportMapFragment
    private lateinit var markerOptions : MarkerOptions
    private val defaultZoom = 18f
    private lateinit var prevMarker : Marker // 기존 마커 위치
    private var lat : Double = 0.0 // 위도 - 사용자가 선택한 위치의 위도 저장하는 변수
    private var lng : Double = 0.0 // 경도 - 사용자가 선택한 위치의 경도 저장하는 변수
    // TODO : 지도에서 현재위치 등록, MVVM 형식으로 변경

    init{
        // mapFragment 초기화와 지도 준비. 비동기 식으로 준비되면 fragment에 붙여서 화면에 띄워주는 방식
        this.mapFragment = mapFragment
        mapFragment.getMapAsync(this)
    }
    override fun onMapReady(googleMap : GoogleMap) {
        mMap = googleMap
        val defaultLocation = LatLng(37.56,126.97)
        markerOptions = MarkerOptions()
        markerOptions.position(defaultLocation)
        markerOptions.title("현재위치")
        markerOptions.snippet("지도를 끌어 위치를 지정하세요")
        prevMarker = mMap.addMarker(markerOptions)!!
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(defaultLocation,defaultZoom)) // 줌의 정도
        googleMap.mapType = GoogleMap.MAP_TYPE_HYBRID
        touchCreateMarker()
    }
    // 터치하여 마커 생성하는 함수
    private fun touchCreateMarker(){
        mMap.setOnMapLongClickListener{
            prevMarker.remove() // 기존 마커 위치 삭제
            markerOptions.position(it)
            prevMarker=mMap.addMarker(markerOptions)!!
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(it,defaultZoom))
            lat = it.latitude // 지정한 위치의 위도
            lng = it.longitude // 지정한 위치의 경도
        }
    }

    fun checkBtnClick(){
        // TODO : 마커의 위치를 파라미터로 model에 위치값 저장, DB(sqlite) 저장 -> true 반환 시 다음 액티비티로 이동
        model.saveLocation(lat,lng)
    }
}