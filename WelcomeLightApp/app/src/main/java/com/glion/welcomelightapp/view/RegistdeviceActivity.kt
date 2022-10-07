package com.glion.welcomelightapp.view

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.glion.welcomelightapp.R
import com.glion.welcomelightapp.databinding.ActivityRegistdeviceBinding
import com.glion.welcomelightapp.viewmodel.BluetoothViewModel

class RegistdeviceActivity : AppCompatActivity() {
    private lateinit var binding : ActivityRegistdeviceBinding
    private var btViewModel = BluetoothViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_registdevice)
        binding.viewmodel = btViewModel

        checkBTObserver()
    }
    private fun checkBTObserver(){
        btViewModel.supportBT.observe(this)
        {
            if(btViewModel.supportBT.value == false)
            {
                Toast.makeText(this,"블루투스를 지원하지 않는 기기입니다. 앱을 종료합니다.",Toast.LENGTH_LONG).show()
                finish()
            }
        }
    }
}