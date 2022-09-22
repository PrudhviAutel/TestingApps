package com.android.autelsdk.gimbal.fragments

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
import com.android.autelsdk.databinding.FragmentInterfaceDebuggingGimbalBinding
import com.android.autelsdk.event.ProductConnectEvent
import com.android.autelsdk.remoteController.RemoteControllerViewModel
import com.android.autelsdk.util.Constants
import com.android.autelsdk.util.Utils
import com.autel.internal.remotecontroller.RemoteController10
import com.autel.internal.remotecontroller.RemoteController20
import com.autel.sdk.remotecontroller.AutelRemoteController
import org.greenrobot.eventbus.EventBus

class InterfaceDebuggingGimbalFragment : Fragment() {

    private lateinit var binding: FragmentInterfaceDebuggingGimbalBinding

    companion object {
        fun newInstance() = InterfaceDebuggingGimbalFragment()
    }

    private val viewModel: RemoteControllerViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_interface_debugging_gimbal , container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setSpinnerItems()
        handleListeners()
    }

    private fun handleListeners() {

        binding.chooseRemoteController.viewBtn.setOnClickListener {
            binding.chooseRemoteController.showResponseText.visibility = View.VISIBLE
            binding.chooseRemoteController.extraOptionParent.visibility = View.GONE
            binding.chooseRemoteController.showResponseText.setText("Currently set to ${getCurrentRemoteControllerByName(viewModel.getRemoteController())}")
        }

        binding.chooseRemoteController.setBtn.setOnClickListener {
            binding.chooseRemoteController.showResponseText.visibility = View.GONE
            binding.showResponseText.visibility = View.GONE
            binding.chooseRemoteController.extraOptionParent.visibility = View.VISIBLE
        }
        
        binding.chooseRemoteController.extraOption.setOnClickListener {
            val controller = setCurrentRemoteControllerByName(binding.chooseRemoteController.extraSpinner.selectedItem.toString())
            viewModel.setRemoteController(controller)
            binding.chooseRemoteController.showResponseText.visibility = View.VISIBLE
            binding.chooseRemoteController.extraOptionParent.visibility = View.GONE
            binding.chooseRemoteController.showResponseText.setText("Currently set to ${getCurrentRemoteControllerByName(viewModel.getRemoteController())}")
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

    private fun getCurrentRemoteControllerByName(controller : AutelRemoteController) : String {
        when(controller) {
            is RemoteController10 -> {
                return Constants.RemoteController10
            }
            is RemoteController20 -> {
                return Constants.RemoteController20
            }
        }
        return ""
    }

    private fun setCurrentRemoteControllerByName(name : String) : AutelRemoteController {
        when(name) {
            Constants.RemoteController10 -> {
                return RemoteController10()
            }
            Constants.RemoteController20 -> {
                return RemoteController20()
            }
        }
        return viewModel.getRemoteController()
    }

    private fun setSpinnerItems() {

        var spinnerAdapter = ArrayAdapter.createFromResource(
            requireActivity().baseContext,
            R.array.remote_controllers,
            android.R.layout.simple_spinner_item
        )
        binding.chooseRemoteController.extraSpinner.adapter = spinnerAdapter

        val index = context?.resources?.getStringArray(R.array.remote_controllers)?.indexOf(getCurrentRemoteControllerByName(viewModel.getRemoteController()))

        index?.let { index
            binding.chooseRemoteController.extraSpinner.setSelection(index)
        }
    }

}