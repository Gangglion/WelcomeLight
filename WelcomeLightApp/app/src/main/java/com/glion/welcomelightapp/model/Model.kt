package com.glion.welcomelightapp.model

import android.util.Log

class Model {
    fun saveLocation(lat:Double,lng:Double)
    {
        // TODO : sqlite 열어서 db에 위도, 경도 저장
        Log.d("tmdguq","위도 : {$lat} 경도 : {$lng}")
    }
}