package com.android.autelsdk.flyController.fragments

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
import com.android.autelsdk.databinding.FragmentInterfaceDebuggingFcBinding
import com.android.autelsdk.event.ProductConnectEvent
import com.android.autelsdk.flyController.FlyControllerViewModel
import com.android.autelsdk.util.Constants
import com.android.autelsdk.util.Utils
import com.autel.internal.flycontroller.cruiser.CruiserFlyControllerImpl
import com.autel.sdk.flycontroller.AutelFlyController
import org.greenrobot.eventbus.EventBus

class InterfaceDebuggingFCFragment : Fragment() {

    private lateinit var binding: FragmentInterfaceDebuggingFcBinding

    companion object {
        fun newInstance() = InterfaceDebuggingCodecFragment()
    }

    private val viewModel: FlyControllerViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_interface_debugging_fc , container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        handleListeners()
        setSpinnerItems()
    }



    private fun setSpinnerItems() {

        var spinnerAdapter = ArrayAdapter.createFromResource(
            requireActivity().baseContext,
            R.array.fly_controllers,
            android.R.layout.simple_spinner_item
        )
        binding.chooseFlyController.extraSpinner.adapter = spinnerAdapter

        val index = context?.resources?.getStringArray(R.array.fly_controllers)?.indexOf(getCurrentFlyControllerByName(viewModel.getFlyController()))

        index?.let { index
            binding.chooseFlyController.extraSpinner.setSelection(index)
        }
    }

    private fun getCurrentFlyControllerByName(controller : AutelFlyController) : String {
        when(controller) {
            is CruiserFlyControllerImpl -> {
                return Constants.CruiserFlyController
            }
        }
        return ""
    }

    private fun setCurrentRemoteControllerByName(name : String) : AutelFlyController {
        when(name) {
            Constants.CruiserFlyController -> {
                return CruiserFlyControllerImpl()
            }
        }
        return viewModel.getFlyController()
    }

    private fun handleListeners() {

        binding.chooseFlyController.viewBtn.setOnClickListener {
            binding.chooseFlyController.showResponseText.visibility = View.VISIBLE
            binding.chooseFlyController.extraOptionParent.visibility = View.GONE
            binding.chooseFlyController.showResponseText.setText("Currently set to ${getCurrentFlyControllerByName(viewModel.getFlyController())}")
        }

        binding.chooseFlyController.setBtn.setOnClickListener {
            binding.chooseFlyController.showResponseText.visibility = View.GONE
            binding.showResponseText.visibility = View.GONE
            binding.chooseFlyController.extraOptionParent.visibility = View.VISIBLE
        }

        binding.chooseFlyController.extraOption.setOnClickListener {
            val controller = setCurrentRemoteControllerByName(binding.chooseFlyController.extraSpinner.selectedItem.toString())
            viewModel.setFlyController(controller)
            binding.chooseFlyController.showResponseText.visibility = View.VISIBLE
            binding.chooseFlyController.extraOptionParent.visibility = View.GONE
            binding.chooseFlyController.showResponseText.setText("Currently set to " + binding.chooseFlyController.extraSpinner.selectedItem.toString())
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

}