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
import com.android.autelsdk.codec.CodecViewModel
import com.android.autelsdk.databinding.FragmentInterfaceDebuggingCodecBinding
import com.android.autelsdk.event.ProductConnectEvent
import com.android.autelsdk.util.Constants
import com.android.autelsdk.util.Utils
import com.autel.internal.video.AutelCodec_Ranger
import com.autel.sdk.video.AutelCodec
import org.greenrobot.eventbus.EventBus

class InterfaceDebuggingCodecFragment : Fragment() {

    private lateinit var binding: FragmentInterfaceDebuggingCodecBinding

    companion object {
        fun newInstance() = InterfaceDebuggingCodecFragment()
    }

    private val viewModel: CodecViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_interface_debugging_codec , container, false)
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
            R.array.codec,
            android.R.layout.simple_spinner_item
        )
        binding.ChooseCodec.extraSpinner.adapter = spinnerAdapter

        val index = context?.resources?.getStringArray(R.array.codec)?.indexOf(getCurrentCodec(viewModel.getCodec()))

        index?.let { index
            binding.ChooseCodec.extraSpinner.setSelection(index)
        }
    }

    private fun getCurrentCodec(controller : AutelCodec) : String {
        when(controller) {
            is AutelCodec_Ranger -> {
                return Constants.AutelCodec_Ranger
            }
        }
        return ""
    }

    private fun setCurrentCodecByName(name : String) : AutelCodec {
        when(name) {
            Constants.AutelCodec_Ranger -> {
                return AutelCodec_Ranger()
            }
        }
        return viewModel.getCodec()
    }

    private fun handleListeners() {

        binding.ChooseCodec.viewBtn.setOnClickListener {
            binding.ChooseCodec.showResponseText.visibility = View.VISIBLE
            binding.ChooseCodec.extraOptionParent.visibility = View.GONE
            binding.ChooseCodec.showResponseText.setText("Currently set to ${getCurrentCodec(viewModel.getCodec())}")
        }

        binding.ChooseCodec.setBtn.setOnClickListener {
            binding.ChooseCodec.showResponseText.visibility = View.GONE
            binding.showResponseText.visibility = View.GONE
            binding.ChooseCodec.extraOptionParent.visibility = View.VISIBLE
        }

        binding.ChooseCodec.extraOption.setOnClickListener {
            val controller = setCurrentCodecByName(binding.ChooseCodec.extraSpinner.selectedItem.toString())
            viewModel.setCodec(controller)
            binding.ChooseCodec.showResponseText.visibility = View.VISIBLE
            binding.ChooseCodec.extraOptionParent.visibility = View.GONE
            binding.ChooseCodec.showResponseText.setText("Currently set to " + binding.ChooseCodec.extraSpinner.selectedItem.toString())
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