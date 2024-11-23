package com.example.buoi9_b6

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.widget.Toast

class NetworkReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val connectivityManager = context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = connectivityManager.activeNetworkInfo

        if (activeNetwork != null && activeNetwork.isConnected) {
            // Nếu có kết nối mạng
            Toast.makeText(context, "Đã kết nối mạng", Toast.LENGTH_SHORT).show()
        } else {
            // Nếu không có kết nối mạng
            Toast.makeText(context, "Không có kết nối mạng", Toast.LENGTH_SHORT).show()
        }
    }
}
