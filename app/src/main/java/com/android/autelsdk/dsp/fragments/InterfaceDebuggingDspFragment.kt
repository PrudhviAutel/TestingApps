package com.android.autelsdk.dsp.fragments

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
import com.android.autelsdk.databinding.FragmentInterfaceDebuggingDspBinding
import com.android.autelsdk.databinding.FragmentInterfaceDebuggingGimbalBinding
import com.android.autelsdk.dsp.DspViewModel
import com.android.autelsdk.event.ProductConnectEvent
import com.android.autelsdk.gimbal.GimbalViewModel
import com.android.autelsdk.util.Constants
import com.android.autelsdk.util.Utils
import com.autel.internal.dsp.cruiser.CruiserDspImpl
import com.autel.internal.gimbal.cruiser.CruiserGimbalImpl
import com.autel.sdk.Autel
import com.autel.sdk.dsp.AutelDsp
import com.autel.sdk.dsp.CruiserDsp
import com.autel.sdk.gimbal.AutelGimbal
import com.autel.sdk.gimbal.CruiserGimbal
import org.greenrobot.eventbus.EventBus

class InterfaceDebuggingDspFragment : Fragment() {

    private lateinit var binding: FragmentInterfaceDebuggingDspBinding

    companion object {
        fun newInstance() = InterfaceDebuggingDspFragment()
    }

    private val viewModel: DspViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_interface_debugging_dsp , container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setSpinnerItems()
        handleListeners()
    }

    private fun handleListeners() {

        binding.chooseDsp.viewBtn.setOnClickListener {
            binding.chooseDsp.showResponseText.visibility = View.VISIBLE
            binding.chooseDsp.extraOptionParent.visibility = View.GONE
            binding.chooseDsp.showResponseText.setText("Currently set to ${getCurrentDspByName(viewModel.getCruiserDspController())}")
        }

        binding.chooseDsp.setBtn.setOnClickListener {
            binding.chooseDsp.showResponseText.visibility = View.GONE
            binding.showResponseText.visibility = View.GONE
            binding.chooseDsp.extraOptionParent.visibility = View.VISIBLE
        }
        
        binding.chooseDsp.extraOption.setOnClickListener {
            val controller = setCurrentDspByName(binding.chooseDsp.extraSpinner.selectedItem.toString())
            viewModel.setController(controller)
            binding.chooseDsp.showResponseText.visibility = View.VISIBLE
            binding.chooseDsp.showResponseText.setText("Currently set to ${getCurrentDspByName(viewModel.getCruiserDspController())}")
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

    private fun getCurrentDspByName(controller : AutelDsp) : String {
        when(controller) {
            is CruiserDsp -> {
                return Constants.CruiserDsp
            }
        }
        return ""
    }

    private fun setCurrentDspByName(name : String) : AutelDsp {
        when(name) {
            Constants.CruiserDsp -> {
                return CruiserDspImpl()
            }
        }
        return viewModel.getCruiserDspController()
    }

    private fun setSpinnerItems() {

        val dsps = arrayOf(Constants.CruiserDsp)
        var spinnerAdapter = ArrayAdapter(
            requireActivity().baseContext,
            android.R.layout.simple_spinner_item,
            dsps
        )
        binding.chooseDsp.extraSpinner.adapter = spinnerAdapter

        val index = dsps?.indexOf(getCurrentDspByName(viewModel.getCruiserDspController()))

        index?.let { index
            binding.chooseDsp.extraSpinner.setSelection(index)
        }
    }

}