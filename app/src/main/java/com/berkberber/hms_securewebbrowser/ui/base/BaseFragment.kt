package com.berkberber.hms_securewebbrowser.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding

abstract class BaseFragment<VM: BaseViewModel, B:ViewBinding>: Fragment(){
    protected lateinit var binding: B

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = getFragmentBinding(inflater, container)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setup()
    }

    abstract fun getViewModel(): VM

    abstract fun getFragmentBinding(inflater: LayoutInflater, container: ViewGroup?): B

    open fun setupObservers(){
        getViewModel().navigationAction.observe(viewLifecycleOwner, Observer {
            findNavController().navigate(it.actionId, it.bundle)
        })
    }

    private fun setup(){
        setupObservers()
    }
}