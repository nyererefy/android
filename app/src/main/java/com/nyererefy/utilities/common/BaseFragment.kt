package com.nyererefy.utilities.common

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.nyererefy.di.Injectable
import com.nyererefy.utilities.Pref

/**
 * Share common functionality for all fragments that extends it.
 */
abstract class BaseFragment : Fragment(), Injectable {
    protected lateinit var pref: Pref

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        pref = Pref(this.requireContext())
    }
}