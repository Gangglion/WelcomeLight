package com.glion.welcomelightapp.Receiver

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast

class btReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val action: String? = intent?.action
        if(action == BluetoothAdapter.ACTION_STATE_CHANGED){
            when(intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, BluetoothAdapter.ERROR)){
                BluetoothAdapter.STATE_ON->{
                    Toast.makeText(context,"블루투스가 켜졌습니다.",Toast.LENGTH_SHORT).show()
                }
                // 블루투스가 꺼져있는 상태거나 꺼지고 있는 중이라면 토스트로 안내문구 띄움
                BluetoothAdapter.STATE_OFF->{
                    Toast.makeText(context,"블루투스가 꺼져있습니다. 블루투스를 켜주세요.",Toast.LENGTH_SHORT).show()
                }
                BluetoothAdapter.STATE_TURNING_OFF->{
                    Toast.makeText(context,"블루투스가 꺼져있습니다. 블루투스를 켜주세요.",Toast.LENGTH_SHORT).show()
                }
                BluetoothAdapter.STATE_TURNING_ON->{
                    Toast.makeText(context,"블루투스가 켜졌습니다.",Toast.LENGTH_SHORT).show()
                }
            }
        }
        // 주변기기 찾기 행동시
        else if(action == BluetoothDevice.ACTION_FOUND)
        {
            Log.d("tmdguq","서비스로 넘어옴 ")
        }
    }
}