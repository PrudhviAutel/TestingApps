package com.android.autelsdk.battery.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.android.autelsdk.R
import com.android.autelsdk.battery.BatteryViewModel
import com.android.autelsdk.databinding.FragmentInterfaceDebuggingGimbalBinding

class ManualTest_BatteryFragment : Fragment() {
    private lateinit var binding: FragmentInterfaceDebuggingGimbalBinding
    private val viewModel: BatteryViewModel by activityViewModels()

    companion object {
        fun newInstance() = ManualTest_BatteryFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_interface_debugging_gimbal,
            container,
            false
        )
        return binding.root
    }
}