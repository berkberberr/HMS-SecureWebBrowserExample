package com.berkberber.hms_securewebbrowser.ui.splash

import android.view.LayoutInflater
import android.view.ViewGroup
import com.berkberber.hms_securewebbrowser.databinding.FragmentSplashBinding
import com.berkberber.hms_securewebbrowser.ui.base.BaseFragment
import org.koin.android.viewmodel.ext.android.viewModel

class SplashFragment: BaseFragment<SplashViewModel, FragmentSplashBinding>() {
    private val splashViewModel: SplashViewModel by viewModel()

    override fun onResume() {
        super.onResume()
        getViewModel().makeSecurityControls()
    }

    override fun getViewModel(): SplashViewModel = splashViewModel

    override fun getFragmentBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentSplashBinding {
        return FragmentSplashBinding.inflate(inflater, container, false)
    }
}