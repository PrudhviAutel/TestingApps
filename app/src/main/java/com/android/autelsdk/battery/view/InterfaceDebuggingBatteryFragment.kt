package com.android.autelsdk.battery.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.android.autelsdk.R
import com.android.autelsdk.battery.BatteryViewModel
import com.android.autelsdk.databinding.FragmentInterfaceTestBatteryBinding
import com.android.autelsdk.event.ProductConnectEvent
import com.android.autelsdk.util.Constants
import com.android.autelsdk.util.Utils
import com.autel.internal.battery.cruiser.CruiserBatteryImpl
import com.autel.sdk.battery.AutelBattery
import com.autel.sdk.battery.CruiserBattery
import org.greenrobot.eventbus.EventBus

class InterfaceDebuggingBatteryFragment : Fragment() {

    private lateinit var binding: FragmentInterfaceTestBatteryBinding

    companion object {
        fun newInstance() = InterfaceDebuggingBatteryFragment()
    }

    private val viewModel: BatteryViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_interface_test_battery , container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setSpinnerItems()
        handleListeners()
    }

    private fun handleListeners() {

        binding.chooseBattery.viewBtn.setOnClickListener {
            binding.chooseBattery.showResponseText.visibility = View.VISIBLE
            binding.chooseBattery.extraOptionParent.visibility = View.GONE
            binding.chooseBattery.showResponseText.setText("Currently set to ${getCurrentBatteryControllerByName(viewModel.getController<CruiserBattery>())}")
        }

        binding.chooseBattery.setBtn.setOnClickListener {
            binding.chooseBattery.showResponseText.visibility = View.GONE
            binding.showResponseText.visibility = View.GONE
            binding.chooseBattery.extraOptionParent.visibility = View.VISIBLE
        }
        
        binding.chooseBattery.extraOption.setOnClickListener {
            val controller = setCurrentBatteryControllerByName(binding.chooseBattery.extraSpinner.selectedItem.toString())
            viewModel.setController(controller)
            binding.chooseBattery.showResponseText.visibility = View.VISIBLE
            binding.chooseBattery.showResponseText.setText("Currently set to ${getCurrentBatteryControllerByName(viewModel.getController<CruiserBattery>())}")
        }

        binding.connectDevice.setOnClickListener {
            binding.showResponseText.visibility = View.VISIBLE
            binding.showResponseText.setText("")
            binding.showResponseText.setText("Trying to connect.Waiting For Response...")
            EventBus.getDefault().post(ProductConnectEvent())
        }

        viewModel.getCurrentProduct().observe(viewLifecycleOwner, Observer { product ->
            binding.showResponseText.visibility = View.VISIBLE
            if (product == null) {
                binding.showResponseText.setText(Utils.getColoredText("Product is Not Connected", Constants.FAILED))
            } else {
                binding.showResponseText.setText(Utils.getColoredText("Product Connected Successfully. Type = ${product.type.name}", Constants.SUCCESS))
            }
        })

    }

    private fun <T> getCurrentBatteryControllerByName(controller : T) : String {
        when(controller) {
            is CruiserBattery -> {
                return Constants.CruiserBattery
            }
        }
        return ""
    }

    private fun setCurrentBatteryControllerByName(name : String) : AutelBattery {
        when(name) {
            Constants.CruiserBattery -> {
                return CruiserBatteryImpl()
            }
        }
        return viewModel.getController<CruiserBattery>()
    }

    private fun setSpinnerItems() {

        val batteryControllers = arrayOf(Constants.CruiserBattery)
        var spinnerAdapter = ArrayAdapter(
            requireActivity().baseContext,
            android.R.layout.simple_spinner_item,
            batteryControllers
        )
        binding.chooseBattery.extraSpinner.adapter = spinnerAdapter

        val index = batteryControllers?.indexOf(getCurrentBatteryControllerByName(viewModel.getController<CruiserBattery>()))

        index?.let { index
            binding.chooseBattery.extraSpinner.setSelection(index)
        }
    }

}