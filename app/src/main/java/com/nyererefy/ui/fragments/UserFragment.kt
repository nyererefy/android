package com.nyererefy.ui.fragments

import android.os.Bundle
import android.view.*
import com.nyererefy.R
import com.nyererefy.databinding.FragmentProfileBinding
import com.nyererefy.utilities.common.BaseFragment
import com.nyererefy.utilities.common.Constants.NAME
import com.nyererefy.utilities.common.Constants.USERNAME


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