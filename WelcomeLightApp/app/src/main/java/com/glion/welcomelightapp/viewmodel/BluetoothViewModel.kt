package com.glion.welcomelightapp.viewmodel

import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothManager
import android.util.Log
import android.widget.ArrayAdapter
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.glion.welcomelightapp.model.Model

class BluetoothViewModel(bluetoothManager: BluetoothManager) {
    private var model : Model = Model()
    private val _useabilityBT = MutableLiveData<Boolean>()
    private val _onoffBT = MutableLiveData<Boolean>()
    private var _btArrayAdapter = MutableLiveData<ArrayAdapter<String>>()
    var useabilityBT : LiveData<Boolean> = _useabilityBT
    var onoffBT : LiveData<Boolean> = _onoffBT
    var btArrayAdapter : LiveData<ArrayAdapter<String>> = _btArrayAdapter
    lateinit var bluetoothAdapter: BluetoothAdapter
    lateinit var pairedDevices : Set<BluetoothDevice>
    lateinit var deviceAddressArray : ArrayList<String>



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
            _onoffBT.value = false;
        }
        else
        {
            btPairedList()
        }
    }
    // 리스트 새로고침 함수
    fun refreshBtnClick() {
        Log.d("tmdguq", "리스트 새로고침 - 블루투스 검색 함수 호출")
    }
    @SuppressLint("MissingPermission")
    fun btPairedList(){
         Log.d("tmdguq","페어링기기 목록실행")
        // TODO :블루투스 목록 불러오는 함수(목록 불러오기 -> 리스트어탭터에 추가 -> 리스트뷰에 표시 순)
        _btArrayAdapter.value?.clear()
        deviceAddressArray = ArrayList()
        if(deviceAddressArray.isNotEmpty())
            deviceAddressArray.clear()
        pairedDevices = bluetoothAdapter.bondedDevices
        if(pairedDevices.isNotEmpty())
        {
            for(device : BluetoothDevice in pairedDevices)
            {
                Log.d("tmdguq","페어링된 리스트 함수 실행 중")
                var deviceName = device.name
                var deviceHdAddr = device.address
                _btArrayAdapter.value?.add(deviceName)
                deviceAddressArray.add(deviceHdAddr)
            }
        }
        else
        {
            Log.d("tmdguq","페어링된 기기가 없다면")
            _btArrayAdapter.value?.add("페어링된 기기가 없습니다")
        }
    }
}