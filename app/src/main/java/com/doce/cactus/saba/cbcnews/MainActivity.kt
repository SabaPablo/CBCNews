package com.doce.cactus.saba.cbcnews

import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.doce.cactus.saba.cbcnews.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        isOnline()
    }

    fun isOnline() {
        val networkRequest = NetworkRequest.Builder()
            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
            .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
            .build()
        val networkCallback = object : ConnectivityManager.NetworkCallback() {
            // network is available for use
            override fun onAvailable(network: Network) {
                super.onAvailable(network)
                Log.d("Connection","available")
                Handler(Looper.getMainLooper()).post {
                    binding.wifiStatusIv.visibility = View.GONE
                }

            }



            // lost network connection
            override fun onLost(network: Network) {
                super.onLost(network)
                Log.d("Connection","Lost")

                Handler(Looper.getMainLooper()).post {
                    binding.wifiStatusIv.visibility = View.VISIBLE
                }
            }
        }
        val connectivityManager = ContextCompat.getSystemService(
            this,
            ConnectivityManager::class.java
        ) as ConnectivityManager
        connectivityManager.requestNetwork(networkRequest, networkCallback)
    }


}