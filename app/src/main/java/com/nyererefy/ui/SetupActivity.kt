package com.nyererefy.ui

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.nyererefy.R
import com.nyererefy.viewmodels.SetupActivityViewModel
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import kotlinx.android.synthetic.main.activity_setup.*
import javax.inject.Inject

class SetupActivity : AppCompatActivity(), HasSupportFragmentInjector {
    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>
    private val viewModel: SetupActivityViewModel by viewModels()

    override fun supportFragmentInjector() = dispatchingAndroidInjector

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setup)

        viewModel.hasConfirmed.observe(this, Observer {
            if (it) {
                confirmClassFragment.view?.visibility = View.GONE
                setInfoFragment.view?.visibility = View.VISIBLE
            } else {
                confirmClassFragment.view?.visibility = View.VISIBLE
                setInfoFragment.view?.visibility = View.GONE
            }
        })
    }
}
