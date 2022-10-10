package com.android.autelsdk.battery.view

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.autelsdk.BaseActivity
import com.android.autelsdk.battery.BatteryViewModel
import com.android.autelsdk.battery.adapter.AirCraftStatusAdapter
import com.android.autelsdk.databinding.AcStatusCommandFragmentBinding
import com.autel.common.product.AutelProductType
import com.autel.sdk.battery.AutelBattery

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
        adapter.setContext(activity as BaseActivity<AutelBattery>)
        adapter.setViewModels(viewModel)
        binding.recyclerCommandList.adapter = adapter
        initUi()

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
        return binding.root
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

    private fun initUi() {
        if (viewModel.getCurrentProductType().value == AutelProductType.UNKNOWN) {
            binding.planeConnectStatus.setText("The plane is not connected")
        } else {
            binding.planeConnectStatus.setText("Connected Plane - ${viewModel.getCurrentProductType().value?.name}")
        }
    }
}