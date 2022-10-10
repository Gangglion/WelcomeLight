package com.glion.welcomelightapp.view

import android.bluetooth.BluetoothManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.glion.welcomelightapp.R
import com.glion.welcomelightapp.databinding.ActivityRegistdeviceBinding
import com.glion.welcomelightapp.viewmodel.BluetoothViewModel
import java.util.jar.Manifest

class RegistdeviceActivity : AppCompatActivity() {
    private lateinit var binding : ActivityRegistdeviceBinding
    private lateinit var btViewModel : BluetoothViewModel
    lateinit var bluetoothManager: BluetoothManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_registdevice)
        bluetoothManager = getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
        btViewModel = BluetoothViewModel(bluetoothManager)
        binding.viewmodel = this.btViewModel
        enableDisableBT()
        checkBTObserver()
    }
    private fun checkBTObserver(){
        btViewModel.supportBT.observe(this)
        {
            if(btViewModel.supportBT.value == false)
            {
                Toast.makeText(this,"블루투스를 지원하지 않는 기기입니다. 앱을 종료합니다.",Toast.LENGTH_LONG).show()
                // finish()
            }
        }
    }
    // 블루투스 사용할수 있는지 없는지 여부 체크 함수
    private fun enableDisableBT(){
        when
        {
            ContextCompat.checkSelfPermission(this,android.Manifest.permission.BLUETOOTH_CONNECT) == PackageManager.PERMISSION_GRANTED->{
                Log.d("tmdguq","1번호출 - checkSelfPermission")
            }
            shouldShowRequestPermissionRationale(android.Manifest.permission.BLUETOOTH_CONNECT)->{
                ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.BLUETOOTH_CONNECT),101)
                Log.d("tmdguq","2번호출 - requestPermissions")
            }
        }

    }
}