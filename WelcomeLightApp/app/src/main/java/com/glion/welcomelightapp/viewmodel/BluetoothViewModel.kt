package com.glion.welcomelightapp.viewmodel

import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.content.IntentFilter
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.glion.welcomelightapp.Receiver.btReceiver
import com.glion.welcomelightapp.model.Model

class BluetoothViewModel(bluetoothManager: BluetoothManager) {
    private var model : Model = Model()
    private val _useabilityBT = MutableLiveData<Boolean>()
    private val _onoffBT = MutableLiveData<Boolean>()
    var useabilityBT : LiveData<Boolean> = _useabilityBT
    var onoffBT : LiveData<Boolean> = _onoffBT
    lateinit var bluetoothAdapter: BluetoothAdapter

    init{
        // 블루투스가 가능한 기기인지 확인. 확인 후 가능한 기기라면 블루투스가 켜져있는지 확인. 가능한 기기가 아니라면 앱을 종료
        if(bluetoothManager.adapter !=null)
        {
            bluetoothAdapter = bluetoothManager.adapter
            _useabilityBT.value=true
            checkBTOnOff();
            // TODO : 블루투스가 켜져있는지 여부 확인 후 켜져있다면 목록 자동탐색, 꺼져있다면 키는 동작 후 목록 자동탐색
        }else
        {
            _useabilityBT.value = false
        }
    }
    @SuppressLint("MissingPermission")
    fun checkBTOnOff(){
        if(!bluetoothAdapter.isEnabled)
        {
            Log.d("tmdguq","클릭-!bluetoothAdapter.isEnabled")
            bluetoothAdapter.enable()
            _onoffBT.value = false;
        }
    }
    // 리스트 새로고침 함수
    fun refreshBtnClick() {
        Log.d("tmdguq", "리스트 새로고침 - 블루투스 검색 함수 호출")
    }
    fun bTList(){
        // TODO :블루투스 목록 불러오는 함수(목록 불러오기 -> 리스트어탭터에 추가 -> 리스트뷰에 표시 순)

    }
}