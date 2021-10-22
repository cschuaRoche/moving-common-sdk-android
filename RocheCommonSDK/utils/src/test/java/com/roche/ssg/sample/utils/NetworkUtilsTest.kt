package com.roche.ssg.sample.utils

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import io.mockk.every
import io.mockk.impl.annotations.MockK
import org.junit.Assert
import org.junit.Test

class NetworkUtilsTest : BaseMockkTest() {

    @MockK(relaxed = true)
    private lateinit var appContext: Application

    @MockK(relaxed = true)
    private lateinit var connectivityManager: ConnectivityManager

    @MockK(relaxed = true)
    private lateinit var networkCapabilities: NetworkCapabilities

    override fun setup() {
        super.setup()
        every { appContext.getSystemService(Context.CONNECTIVITY_SERVICE) } returns connectivityManager
        every { connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork) } returns networkCapabilities
    }

    @Test
    fun `hasInternetConnection returns true when only TRANSPORT_WIFI is available`() {
        every { networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) } returns true
        every { networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) } returns false
        every { networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) } returns false
        val result = NetworkUtils.hasInternetConnection(appContext)
        Assert.assertTrue("TRANSPORT_WIFI not available", result)
    }

    @Test
    fun `hasInternetConnection returns true when only TRANSPORT_CELLULAR is available`() {
        every { networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) } returns false
        every { networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) } returns true
        every { networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) } returns false
        val result = NetworkUtils.hasInternetConnection(appContext)
        Assert.assertTrue("TRANSPORT_CELLULAR not available", result)
    }

    @Test
    fun `hasInternetConnection returns true when only TRANSPORT_ETHERNET is available`() {
        every { networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) } returns false
        every { networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) } returns false
        every { networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) } returns true
        val result = NetworkUtils.hasInternetConnection(appContext)
        Assert.assertTrue("TRANSPORT_ETHERNET not available", result)
    }

    @Test
    fun `hasInternetConnection returns true when TRANSPORT_WIFI, TRANSPORT_CELLULAR and TRANSPORT_ETHERNET are available`() {
        every { networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) } returns true
        every { networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) } returns true
        every { networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) } returns true
        val result = NetworkUtils.hasInternetConnection(appContext)
        Assert.assertTrue(
            "Either TRANSPORT_WIFI, TRANSPORT_CELLULAR or TRANSPORT_ETHERNET not available",
            result
        )
    }

    @Test
    fun `hasInternetConnection returns false when no network capabilities are available`() {
        every { networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) } returns false
        every { networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) } returns false
        every { networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) } returns false
        val result = NetworkUtils.hasInternetConnection(appContext)
        Assert.assertFalse("Network capabilities are available", result)
    }

    @Test
    fun `isWifiConnected returns true when TRANSPORT_WIFI is available`() {
        every { networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) } returns true
        val result = NetworkUtils.isWifiConnected(appContext)
        Assert.assertTrue("TRANSPORT_WIFI not available", result)
    }

    @Test
    fun `isWifiConnected returns false when TRANSPORT_WIFI is not available`() {
        every { networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) } returns false
        val result = NetworkUtils.isWifiConnected(appContext)
        Assert.assertFalse("TRANSPORT_WIFI is available", result)
    }
}