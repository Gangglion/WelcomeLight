package com.glion.welcomelightapp.view

import android.bluetooth.BluetoothAdapter
import android.content.Intent
import android.databinding.DataBindingUtil
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.glion.welcomelightapp.R
import com.glion.welcomelightapp.databinding.ActivityRegistdeviceBinding
import com.glion.welcomelightapp.viewmodel.BluetoothViewModel

class RegistdeviceActivity : AppCompatActivity() {
    private lateinit var binding : ActivityRegistdeviceBinding
    private var viewmodel : BluetoothViewModel = BluetoothViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_registdevice)
        binding.viewmodel = viewmodel

        checkBTObserver()
    }
    private fun checkBTObserver(){
        viewmodel.supportBT.observe(this)
        {
            if(viewmodel.supportBT.value == false)
            {
                Toast.makeText(this,"블루투스를 지원하지 않는 기기입니다. 앱을 종료합니다.",Toast.LENGTH_LONG).show()
                finish()
            }
        }
    }
}