package com.android.autelsdk.battery.view

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
import com.autel.common.product.AutelProductType

class AirCraft_CommandStatusFragment : Fragment() {
    lateinit var binding: AcStatusCommandFragmentBinding
    lateinit var viewModel: BatteryViewModel

    companion object {
        fun newInstance() = AirCraft_CommandStatusFragment()
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
        initUi()
        return binding.root
    }

    private fun initUi() {
        if (viewModel.getCurrentProductType().value == AutelProductType.UNKNOWN) {
            binding.planeConnectStatus.setText("The plane is not connected")
        } else {
            binding.planeConnectStatus.setText("Connected Plane - ${viewModel.getCurrentProductType().value?.name}")
        }
    }


}