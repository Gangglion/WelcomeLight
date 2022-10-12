package com.glion.welcomelightapp.viewmodel

import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothManager
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.glion.welcomelightapp.model.Model

class BluetoothViewModel(bluetoothManager: BluetoothManager) {
    private var model : Model = Model()

    private val _useabilityBT = MutableLiveData<Boolean>()
    private val _onoffBT = MutableLiveData<Boolean>()
    private val _btArrayList = MutableLiveData<ArrayList<String>>()
    private var _findDeviceCode = MutableLiveData<String>()

    var useabilityBT : LiveData<Boolean> = _useabilityBT
    var onoffBT : LiveData<Boolean> = _onoffBT
    var btArrayList : LiveData<ArrayList<String>> = _btArrayList
    var findDeviceCode : LiveData<String> = _findDeviceCode

    lateinit var bluetoothAdapter: BluetoothAdapter
    private lateinit var pairedDevices : Set<BluetoothDevice>
    var pairedList = ArrayList<String>()

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
        if(!bluetoothAdapter.isEnabled) // 블루투스가 꺼져있을때
        {
            _onoffBT.value = false;
            btPairedList()
            btDeviceList()
        }
        else // 블루투스가 켜져있을때
        {
            _onoffBT.value = true;
            btPairedList()
            btDeviceList()
        }
    }
    // 리스트 새로고침 함수
    fun refreshBtnClick() {
        btPairedList()
        btDeviceList()
    }
    @SuppressLint("MissingPermission")
    fun btPairedList(){
        pairedList.clear()
        _btArrayList.value?.clear()
        pairedDevices = bluetoothAdapter.bondedDevices
        if(pairedDevices.isNotEmpty())
        {
            for(device : BluetoothDevice in pairedDevices)
            {
                val deviceName = device.name
                val deviceHdAddr = device.address
                pairedList.add(deviceName+"\n"+deviceHdAddr)
            }
            _btArrayList.value = pairedList
        }
        else
        {
            pairedList.add("페어링된 기기가 없습니다.")
            _btArrayList.value=pairedList
        }
    }
    @SuppressLint("MissingPermission")
    fun btDeviceList(){
        Log.d("tmdguq","btDeviceList 시작")
        if(bluetoothAdapter.isDiscovering) // 주변기기 탐색 중이라면 탐색 중지
            bluetoothAdapter.cancelDiscovery()
        else
        {
            if(bluetoothAdapter.isEnabled) // 블루투스가 켜져있을때
            {
                Log.d("tmdguq","블루투스 켜진거 인식")
                bluetoothAdapter.startDiscovery() // 주변기기 탐색 시작
                _findDeviceCode.value = "findDevice" // view에 탐색 시작했다는 코드 보냄. 뷰에서 확인후 리시버에서 주변기기 정보 받는다
            }
        }
    }
}