package dev.sourav.currencyconverter.others

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities

/**
 * Created by Sourav
 * On 3/26/2023 12:09 PM
 * For Currency Converter
 */

object NetworkUtils {

    // connection states ---------------------------------------------------------------------------
    private const val TYPE_NOT_CONNECTED = 0
    private const val TYPE_WIFI = 1
    private const val TYPE_MOBILE = 2
    private const val NETWORK_STATUS_NOT_CONNECTED = 0
    private const val NETWORK_STATUS_WIFI = 1
    private const val NETWORK_STATUS_MOBILE = 2
    // ---------------------------------------------------------------------------------------------

    private fun getConnectivityStatus(context: Context): Int {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
        if (cm != null) {
            val network = cm.activeNetwork
            val capabilities = cm.getNetworkCapabilities(network)
            if (capabilities != null) {
                if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                    return TYPE_WIFI
                }
                if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                    return TYPE_MOBILE
                }
            } else {
                return TYPE_NOT_CONNECTED
            }
        }
        return TYPE_NOT_CONNECTED
    }

    private fun getConnectivityStatusString(context: Context): Int {
        val conn = getConnectivityStatus(context)
        var status = 0
        if (conn == TYPE_WIFI) {
            status = NETWORK_STATUS_WIFI
        } else if (conn == TYPE_MOBILE) {
            status = NETWORK_STATUS_MOBILE
        }
        return status
    }

    fun hasInternetConnection(context: Context): Boolean {
        return getConnectivityStatusString(context) != NETWORK_STATUS_NOT_CONNECTED
    }

    fun isWifiConnected(context: Context): Boolean {
        return getConnectivityStatusString(context) == NETWORK_STATUS_WIFI
    }

    fun isMobileNetworkConnected(context: Context): Boolean {
        return getConnectivityStatusString(context) == NETWORK_STATUS_MOBILE
    }
}
