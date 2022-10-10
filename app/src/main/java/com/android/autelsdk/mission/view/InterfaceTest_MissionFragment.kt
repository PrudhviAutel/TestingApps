package com.android.autelsdk.mission.view

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
import com.android.autelsdk.databinding.FragmentInterfaceTestBatteryBinding
import com.android.autelsdk.databinding.FragmentInterfaceTestMissionBinding
import com.android.autelsdk.mission.DFViewModel

class InterfaceTest_MissionFragment : Fragment() {
    private lateinit var binding: FragmentInterfaceTestMissionBinding
    private val viewModel: DFViewModel by activityViewModels()

    companion object {
        fun newInstance() = InterfaceTest_MissionFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_interface_test_mission,
            container,
            false
        )
        return binding.root
    }
}