package com.glion.welcomelightapp.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.bluetooth.BluetoothAdapter
import android.util.Log
import com.glion.welcomelightapp.model.Model

class BluetoothViewModel {
    private var model : Model = Model()
    private val _supportBT = MutableLiveData<Boolean>()
    var supportBT : LiveData<Boolean> = _supportBT
    private val mBluetoothAdapter : BluetoothAdapter? = BluetoothAdapter.getDefaultAdapter()

    init{
        if(mBluetoothAdapter == null)
            _supportBT.value = false
        else
        {
            _supportBT.value = true
            Log.d("tmdguq","블루투스 목록 불러오기")
        }
    }
    // 리스트 새로고침 함수
    fun refreshBtnClick(){
        Log.d("tmdguq","리스트 새로고침 - 블루투스 검색 함수 호출")
    }

    fun bTList(){
        // TODO :블루투스 목록 불러오는 함수(목록 불러오기 -> 리스트어탭터에 추가 -> 리스트뷰에 표시 순)

    }
}