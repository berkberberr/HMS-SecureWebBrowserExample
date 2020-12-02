package com.berkberber.hms_securewebbrowser.ui.malicious

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.berkberber.hms_securewebbrowser.adapter.MaliciousAppsAdapter
import com.berkberber.hms_securewebbrowser.data.model.ErrorItem
import com.berkberber.hms_securewebbrowser.data.model.MaliciousApps
import com.berkberber.hms_securewebbrowser.databinding.FragmentMaliciousBinding
import com.berkberber.hms_securewebbrowser.ui.base.BaseFragment
import com.berkberber.hms_securewebbrowser.ui.base.BaseViewModel
import com.berkberber.hms_securewebbrowser.utils.Constants

class MaliciousFragment: BaseFragment<BaseViewModel, FragmentMaliciousBinding>(){

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getData()
    }

    private fun getData(){
        arguments?.let { arguments ->
            binding.errorItem = arguments.getSerializable(Constants.ERROR_ITEM) as ErrorItem?

            var maliciousApps = arguments.getSerializable(Constants.MALICIOUS_APPS) as List<MaliciousApps>
            setRecyclerView(maliciousApps)
        }
    }

    override fun getViewModel(): BaseViewModel = BaseViewModel()

    override fun getFragmentBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentMaliciousBinding {
        return FragmentMaliciousBinding.inflate(inflater, container, false)
    }

    private fun setRecyclerView(maliciousApps: List<MaliciousApps>){
        binding.maliciousAppsRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.maliciousAppsRecyclerView.adapter = MaliciousAppsAdapter(maliciousApps)
    }
}