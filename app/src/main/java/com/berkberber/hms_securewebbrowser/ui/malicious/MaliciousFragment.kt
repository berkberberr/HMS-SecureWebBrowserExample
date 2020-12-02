package com.berkberber.hms_securewebbrowser.ui.malicious

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.berkberber.hms_securewebbrowser.R
import com.berkberber.hms_securewebbrowser.adapter.MaliciousAppsAdapter
import com.berkberber.hms_securewebbrowser.data.model.ErrorItem
import com.berkberber.hms_securewebbrowser.data.model.MaliciousApps
import com.berkberber.hms_securewebbrowser.databinding.FragmentMaliciousBinding
import com.berkberber.hms_securewebbrowser.ui.base.BaseFragment
import com.berkberber.hms_securewebbrowser.ui.base.BaseViewModel
import com.berkberber.hms_securewebbrowser.utils.Constants

class MaliciousFragment: BaseFragment<BaseViewModel, FragmentMaliciousBinding>(){
    private lateinit var maliciousApps: MutableList<MaliciousApps>
    private val DELETE_REQUEST_CODE = 1001

    private var selectedMaliciousApp: MaliciousApps? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getData()
    }

    private fun getData(){
        arguments?.let { arguments ->
            binding.errorItem = arguments.getSerializable(Constants.ERROR_ITEM) as ErrorItem?

            maliciousApps = arguments.getSerializable(Constants.MALICIOUS_APPS) as MutableList<MaliciousApps>
            setRecyclerView()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == DELETE_REQUEST_CODE){
            when(resultCode){
                Activity.RESULT_OK -> {
                    maliciousApps.remove(selectedMaliciousApp)
                    setRecyclerView()
                    selectedMaliciousApp = null
                }
                Activity.RESULT_CANCELED -> {
                    Toast.makeText(requireContext(), requireContext().getString(R.string.should_delete_app), Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private var deleteClickListener = object: DeleteClickListener{
        override fun selectedApp(maliciousApp: MaliciousApps) {
            selectedMaliciousApp = maliciousApp

            var deleteIntent = Intent(Intent.ACTION_DELETE).apply {
                data = Uri.parse("package:${maliciousApp.packageName}")
                putExtra(Intent.EXTRA_RETURN_RESULT, true)
            }
            startActivityForResult(deleteIntent, DELETE_REQUEST_CODE)
        }

    }

    override fun getViewModel(): BaseViewModel = BaseViewModel()

    override fun getFragmentBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentMaliciousBinding {
        return FragmentMaliciousBinding.inflate(inflater, container, false)
    }

    private fun setRecyclerView(){
        if(maliciousApps.size > 0){
            binding.maliciousAppsRecyclerView.layoutManager = LinearLayoutManager(requireContext())
            binding.maliciousAppsRecyclerView.adapter = MaliciousAppsAdapter(maliciousApps, deleteClickListener)
        } else{
            findNavController().navigate(R.id.action_maliciousFragment_to_browserFragment)
        }
    }
}

interface DeleteClickListener{
    fun selectedApp(maliciousApps: MaliciousApps)
}