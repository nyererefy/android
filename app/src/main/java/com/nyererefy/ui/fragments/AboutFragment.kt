package com.nyererefy.ui.fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.nyererefy.databinding.FragmentAboutBinding
import org.jetbrains.anko.support.v4.browse


class AboutFragment : Fragment() {

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?): View? {
        val binding = FragmentAboutBinding.inflate(inflater, container, false)
        binding.fragment = this
        return binding.root
    }

    fun navigateToDevelopers() {
        val direction = AboutFragmentDirections.actionAboutToDevelopers()
        findNavController().navigate(direction)
    }

    fun openGithub() {
        browse("https://github.com/nyererefy/nyererefy-android")
    }

    fun openPrivacyPolicy() {
        browse("https://nyererefy.blogspot.com")
    }

}
