package com.glion.welcomelightapp.view

import android.Manifest
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothManager
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.glion.welcomelightapp.R
import com.glion.welcomelightapp.Receiver.btReceiver
import com.glion.welcomelightapp.databinding.ActivityRegistdeviceBinding
import com.glion.welcomelightapp.viewmodel.BluetoothViewModel


class RegistdeviceActivity : AppCompatActivity() {
    lateinit var binding : ActivityRegistdeviceBinding
    private lateinit var btViewModel : BluetoothViewModel
    lateinit var bluetoothManager: BluetoothManager
    lateinit var receiver : btReceiver
    lateinit var btArrayAdapter: ArrayAdapter<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_registdevice)
        bluetoothManager = getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
        receiver = btReceiver()
        // 블루투스 이용 전 블루투스 권한이 허용되었는지 확인하는 함수 호출
        if(permissionBT())
        {
            btViewModel = BluetoothViewModel(bluetoothManager)
            binding.viewmodel = btViewModel
            checkBTObserver()
        }
    }
    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(receiver)
    }
    // 블루투스를 사용할 수 있는 장치인지 아닌지 확인. 확인은 뷰모델에서 이루어지고, 뷰에서는 라이브데이터로 bool값 관측하여 확인
    private fun checkBTObserver(){
        btViewModel.useabilityBT.observe(this)
        {
            // 블루투스를 사용할 수 없는 기기라면
            if(btViewModel.useabilityBT.value == false)
            {
                Toast.makeText(this,"블루투스를 지원하지 않는 기기입니다. 앱을 종료합니다.",Toast.LENGTH_LONG).show()
                finish()
            }
            else
            {
                // 권한이 있다면
                btViewModel.onoffBT.observe(this)
                {
                    if(btViewModel.onoffBT.value == false) // 블루투스가 꺼져있을 경우(false)를 인식하면, 권한을 확인하고 블루투스를 켠다.
                    {
                        val reqEnableIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
                        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_CONNECT) == PackageManager.PERMISSION_GRANTED)
                        {
                            startActivity(reqEnableIntent)
                            val intentFilter = IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED)
                            registerReceiver(receiver,intentFilter)
                        }
                    }
                }
            }
        }
        // 어레이리스트의 내용이 바뀌면 감지하여 리스트뷰 업데이트
        btViewModel.btArrayList.observe(this)
        {
            btArrayAdapter = ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,it)
            binding.pairedlistview.adapter = btArrayAdapter
        }
        btViewModel.findDeviceCode.observe(this)
        {
            if(it=="findDevice")
            {
                val filter = IntentFilter(BluetoothDevice.ACTION_FOUND)
                registerReceiver(receiver, filter)
            }
            //            btArrayAdapter = ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,it)
            //            binding.btdevicelistview.adapter = btArrayAdapter
        }
    }

    // 블루투스 권한 체크 함수
    private fun permissionBT() : Boolean {
        var checkPm = false
        if ((ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.BLUETOOTH_CONNECT
            ) == PackageManager.PERMISSION_GRANTED)
            && (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.BLUETOOTH_SCAN
            ) == PackageManager.PERMISSION_GRANTED)
        )
            checkPm = true
        else {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.BLUETOOTH_CONNECT, Manifest.permission.BLUETOOTH_SCAN),
                101
            )
            checkPm = true
        }
        return checkPm
    }

}