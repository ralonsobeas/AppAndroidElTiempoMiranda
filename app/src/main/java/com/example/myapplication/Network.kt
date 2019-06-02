package com.example.myapplication

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.support.v7.app.AppCompatActivity
import android.widget.Toast

class Network {

    companion object {
        @SuppressLint("MissingPermission")
        fun hayRed(activity: AppCompatActivity):Boolean{

            val connectivityManager = activity.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val networkInfo = connectivityManager.activeNetworkInfo
            return networkInfo != null && networkInfo.isConnected
        }
    }
}