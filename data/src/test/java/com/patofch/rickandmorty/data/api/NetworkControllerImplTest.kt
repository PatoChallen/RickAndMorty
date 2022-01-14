package com.patofch.rickandmorty.data.api

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.whenever
import com.patofch.rickandmorty.data.data_source.api.NetworkController
import com.patofch.rickandmorty.data.data_source.api.NetworkControllerImpl
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import javax.inject.Inject

class NetworkControllerImplTest {

    @Mock
    private lateinit var context: Context

    @Mock
    private lateinit var connectivityManager: ConnectivityManager

    @Mock
    private lateinit var networkInfo: NetworkInfo

    private lateinit var networkController: NetworkController

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        networkController = NetworkControllerImpl(context)
    }

    @Test
    fun `verifiy isNetworkConnected when networkInfo is not null and connected returns true`() {
        whenever(context.getSystemService(Context.CONNECTIVITY_SERVICE)).doReturn(connectivityManager)
        whenever(connectivityManager.activeNetworkInfo).doReturn(networkInfo)
        whenever(networkInfo.isConnected).doReturn(true)

        assert(networkController.isNetworkConnected())
    }

    @Test
    fun `verifiy isNetworkConnected when networkInfo is null returns false`() {
        whenever(context.getSystemService(Context.CONNECTIVITY_SERVICE)).doReturn(connectivityManager)
        whenever(connectivityManager.activeNetworkInfo).doReturn(null)

        assert(networkController.isNetworkConnected().not())
    }

    @Test
    fun `verifiy isNetworkConnected when networkInfo is not null and disconnected returns false`() {
        whenever(context.getSystemService(Context.CONNECTIVITY_SERVICE)).doReturn(connectivityManager)
        whenever(connectivityManager.activeNetworkInfo).doReturn(networkInfo)
        whenever(networkInfo.isConnected).doReturn(false)

        assert(networkController.isNetworkConnected().not())
    }
}