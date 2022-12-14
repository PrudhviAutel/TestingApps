package com.android.autelsdk.mission.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.autelsdk.battery.BatteryViewModel
import com.android.autelsdk.battery.adapter.AirCraftStatusAdapter
import com.android.autelsdk.databinding.AcStatusCommandFragmentBinding

class AirCraftMission_CommandStatusFragment : Fragment() {
    lateinit var binding: AcStatusCommandFragmentBinding
    lateinit var viewModel: BatteryViewModel

    companion object {
        fun newInstance() = AirCraftMission_CommandStatusFragment()
    }

    val adapter = AirCraftStatusAdapter()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = AcStatusCommandFragmentBinding.inflate(layoutInflater, container, false)
        viewModel = ViewModelProvider(this).get(BatteryViewModel::class.java)

        binding.recyclerCommandList.layoutManager = LinearLayoutManager(activity)
        binding.recyclerCommandList.adapter = adapter
        return binding.root
    }
}