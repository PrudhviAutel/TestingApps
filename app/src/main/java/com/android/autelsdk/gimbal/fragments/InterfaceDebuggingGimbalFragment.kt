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
import com.android.autelsdk.gimbal.GimbalViewModel
import com.android.autelsdk.remoteController.RemoteControllerViewModel
import com.android.autelsdk.util.Constants
import com.android.autelsdk.util.Utils
import com.autel.internal.gimbal.cruiser.CruiserGimbalImpl
import com.autel.internal.remotecontroller.RemoteController10
import com.autel.internal.remotecontroller.RemoteController20
import com.autel.sdk.gimbal.AutelGimbal
import com.autel.sdk.gimbal.CruiserGimbal
import com.autel.sdk.remotecontroller.AutelRemoteController
import org.greenrobot.eventbus.EventBus

class InterfaceDebuggingGimbalFragment : Fragment() {

    private lateinit var binding: FragmentInterfaceDebuggingGimbalBinding

    companion object {
        fun newInstance() = InterfaceDebuggingGimbalFragment()
    }

    private val viewModel: GimbalViewModel by activityViewModels()

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

        binding.chooseGimbal.viewBtn.setOnClickListener {
            binding.chooseGimbal.showResponseText.visibility = View.VISIBLE
            binding.chooseGimbal.extraOptionParent.visibility = View.GONE
            binding.chooseGimbal.showResponseText.setText("Currently set to ${getCurrentGimbalByName(viewModel.getCruiserGimbalController())}")
        }

        binding.chooseGimbal.setBtn.setOnClickListener {
            binding.chooseGimbal.showResponseText.visibility = View.GONE
            binding.showResponseText.visibility = View.GONE
            binding.chooseGimbal.extraOptionParent.visibility = View.VISIBLE
        }
        
        binding.chooseGimbal.extraOption.setOnClickListener {
            val controller = setCurrentGimbalByName(binding.chooseGimbal.extraSpinner.selectedItem.toString())
            viewModel.setController(controller)
            binding.chooseGimbal.showResponseText.visibility = View.VISIBLE
            binding.chooseGimbal.showResponseText.setText("Currently set to ${getCurrentGimbalByName(viewModel.getCruiserGimbalController())}")
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

    private fun getCurrentGimbalByName(controller : AutelGimbal) : String {
        when(controller) {
            is CruiserGimbal -> {
                return Constants.CruiserGimbal
            }
        }
        return ""
    }

    private fun setCurrentGimbalByName(name : String) : AutelGimbal {
        when(name) {
            Constants.CruiserGimbal -> {
                return CruiserGimbalImpl()
            }
        }
        return viewModel.getCruiserGimbalController()
    }

    private fun setSpinnerItems() {

        val gimbals = arrayOf(Constants.CruiserGimbal)
        var spinnerAdapter = ArrayAdapter(
            requireActivity().baseContext,
            android.R.layout.simple_spinner_item,
            gimbals
        )
        binding.chooseGimbal.extraSpinner.adapter = spinnerAdapter

        val index = gimbals?.indexOf(getCurrentGimbalByName(viewModel.getCruiserGimbalController()))

        index?.let { index
            binding.chooseGimbal.extraSpinner.setSelection(index)
        }
    }

}