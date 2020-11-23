package com.berkberber.hms_securewebbrowser.ui.error

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.berkberber.hms_securewebbrowser.data.model.ErrorItem
import com.berkberber.hms_securewebbrowser.databinding.FragmentErrorBinding
import com.berkberber.hms_securewebbrowser.ui.base.BaseFragment
import com.berkberber.hms_securewebbrowser.ui.base.BaseViewModel
import com.berkberber.hms_securewebbrowser.utils.Constants

class ErrorFragment: BaseFragment<BaseViewModel, FragmentErrorBinding>() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getData()
    }

    private fun getData(){
        arguments?.let { arguments ->
            binding.errorItem = arguments.getSerializable(Constants.ERROR_ITEM) as ErrorItem?
        }
    }

    override fun getViewModel(): BaseViewModel = BaseViewModel()

    override fun getFragmentBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentErrorBinding {
        return FragmentErrorBinding.inflate(inflater, container, false)
    }
}