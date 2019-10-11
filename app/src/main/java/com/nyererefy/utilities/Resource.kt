package com.nyererefy.utilities

import androidx.lifecycle.LiveData
import com.nyererefy.utilities.common.NetworkState

/**
 * Data class that is necessary for a UI to show a listing and interact w/ the rest of the system
 */
data class Resource<T>(
        val data: LiveData<T>,
        val networkState: LiveData<NetworkState>
)
