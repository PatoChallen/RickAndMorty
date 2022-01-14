package com.patofch.rickandmorty.data.data_source.api

import android.content.Context
import android.net.ConnectivityManager
import javax.inject.Inject

class NetworkControllerImpl @Inject constructor(
    private val context: Context
) : NetworkController {

    override fun isNetworkConnected(): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
        return cm!!.activeNetworkInfo != null && cm.activeNetworkInfo!!.isConnected
    }
}