package com.android.autelsdk.mission.view

import android.graphics.Color
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
import com.android.autelsdk.mission.DFViewModel
import com.android.autelsdk.mission.adapter.AirCraftStatusAdapterMission

class AirCraftMission_CommandStatusFragment : Fragment() {
    lateinit var binding: AcStatusCommandFragmentBinding
    lateinit var viewModel: DFViewModel

    companion object {
        fun newInstance() = AirCraftMission_CommandStatusFragment()
    }

    val adapter = AirCraftStatusAdapterMission()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = AcStatusCommandFragmentBinding.inflate(layoutInflater, container, false)
        viewModel = ViewModelProvider(this).get(DFViewModel::class.java)

        binding.recyclerCommandList.layoutManager = LinearLayoutManager(activity)
        adapter.setViewModels(viewModel)
        binding.recyclerCommandList.adapter = adapter
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.result.observe(viewLifecycleOwner) {
            if (it.status) {
                setSuccessResult(it.value)
            } else {
                setFailureResult(it.value)
            }
        }
        viewModel.isLoading.observe(viewLifecycleOwner) {
            setLoadingResult()
        }
    }
    fun setSuccessResult(value: String) {
        binding.testResults.text = value
        binding.testResults.setTextColor(Color.parseColor("#00ff00"))
        binding.testResults.visibility = View.VISIBLE
    }

    fun setFailureResult(value: String) {
        binding.testResults.text = value
        binding.testResults.setTextColor(Color.parseColor("#ff0000"))
        binding.testResults.visibility = View.VISIBLE
    }

    fun setLoadingResult() {
        var value = "Please wait..."
        binding.testResults.text = value
        binding.testResults.setTextColor(Color.parseColor("#000000"))
        binding.testResults.visibility = View.VISIBLE
    }
}