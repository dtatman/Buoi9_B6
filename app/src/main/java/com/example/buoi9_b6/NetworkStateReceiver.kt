package com.example.buoi9_b6

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.widget.Toast

class NetworkStateReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = connectivityManager.activeNetworkInfo

        if (activeNetwork != null && activeNetwork.isConnected) {
            Toast.makeText(context, "Đã kết nối mạng", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "Không có kết nối mạng", Toast.LENGTH_SHORT).show()
        }
    }
}
