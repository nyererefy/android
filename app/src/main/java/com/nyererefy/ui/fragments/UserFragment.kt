package com.nyererefy.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.nyererefy.databinding.FragmentProfileBinding
import com.nyererefy.utilities.common.BaseFragment


class UserFragment : BaseFragment() {

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val bind = FragmentProfileBinding.inflate(inflater, container, false)

        return bind.root
    }

}