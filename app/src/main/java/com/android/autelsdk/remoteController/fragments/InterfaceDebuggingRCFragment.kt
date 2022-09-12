package com.android.autelsdk.remoteController.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.android.autelsdk.R
import com.android.autelsdk.databinding.FragmentInterfaceDebuggingRcBinding
import com.android.autelsdk.remoteController.RemoteControllerViewModel

class InterfaceDebuggingRCFragment : Fragment() {

    private lateinit var binding: FragmentInterfaceDebuggingRcBinding

    companion object {
        fun newInstance() = InterfaceDebuggingRCFragment()
    }

    private lateinit var viewModel: RemoteControllerViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_interface_debugging_rc , container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(RemoteControllerViewModel::class.java)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        Log.i("KKKKKK","OnViewCreated")
    }

}