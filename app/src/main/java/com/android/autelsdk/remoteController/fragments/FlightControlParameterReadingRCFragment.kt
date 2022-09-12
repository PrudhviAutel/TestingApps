package com.android.autelsdk.remoteController.fragments

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.android.autelsdk.R
import com.android.autelsdk.databinding.FragmentFlightControlParameterReadingRcBinding
import com.android.autelsdk.remoteController.RemoteControllerViewModel
import com.android.autelsdk.util.Constants
import com.android.autelsdk.util.Status
import com.android.autelsdk.util.Utils
import com.android.autelsdk.util.Utils.observeOnce
import com.autel.common.remotecontroller.RFPower
import com.autel.common.remotecontroller.RemoteControllerLanguage
import com.autel.common.remotecontroller.RemoteControllerParameterUnit
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class FlightControlParameterReadingRCFragment : Fragment() {

    private lateinit var binding: FragmentFlightControlParameterReadingRcBinding

    companion object {
        fun newInstance() = FlightControlParameterReadingRCFragment()
    }

    private lateinit var viewModel: RemoteControllerViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_flight_control_parameter_reading_rc,
            container,
            false
        )
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(RemoteControllerViewModel::class.java)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setSpinnerItems()
        handleListeners()
    }

    private fun handleListeners() {

        binding.language.setBtn.setOnClickListener {
            closeAllExtraOptionLayouts()
            binding.language.extraOptionParent.visibility = View.VISIBLE
        }

        binding.rfPower.setBtn.setOnClickListener {
            closeAllExtraOptionLayouts()
            binding.rfPower.extraOptionParent.visibility = View.VISIBLE
        }

        binding.parameterUnit.setBtn.setOnClickListener {
            closeAllExtraOptionLayouts()
            binding.parameterUnit.extraOptionParent.visibility = View.VISIBLE
        }

        binding.yawCoefficient.setBtn.setOnClickListener {
            closeAllExtraOptionLayouts()
            binding.yawCoefficient.extraOptionParent.visibility = View.VISIBLE
        }

        binding.language.extraOption.setOnClickListener {
            binding.language.showResponseText.visibility = View.VISIBLE
            binding.language.showResponseText.setText("Please Wait...")
            val language =
                if (0 == binding.language.extraSpinner.selectedItemPosition)
                    RemoteControllerLanguage.ENGLISH
                else
                    RemoteControllerLanguage.CHINESE
            lifecycleScope.launch(Dispatchers.Main) {
                viewModel.setLanguageTest(language)
                    .observeOnce(viewLifecycleOwner, Observer { msg ->
                        when (msg.status) {
                            Status.SUCCESS -> {
                                binding.language.showResponseText.setText(Utils.getColoredText(msg.message.toString(), Constants.SUCCESS))
                            }
                            Status.ERROR -> {
                                binding.language.showResponseText.setText(Utils.getColoredText(msg.message.toString(), Constants.FAILED))
                            }
                            else -> {

                            }
                        }
                    })
            }
        }

        binding.rfPower.extraOption.setOnClickListener {
            binding.rfPower.showResponseText.visibility = View.VISIBLE
            binding.rfPower.showResponseText.setText("Please Wait...")
            val rfPower =
                if (0 == binding.rfPower.extraSpinner.selectedItemPosition)
                    RFPower.FCC
                else
                    RFPower.CE
            lifecycleScope.launch(Dispatchers.Main) {
                viewModel.setRFPowerTest(rfPower)
                    .observeOnce(viewLifecycleOwner, Observer { msg ->
                        when (msg.status) {
                            Status.SUCCESS -> {
                                binding.rfPower.showResponseText.setText(Utils.getColoredText(msg.message.toString(), Constants.SUCCESS))
                            }
                            Status.ERROR -> {
                                binding.rfPower.showResponseText.setText(Utils.getColoredText(msg.message.toString(), Constants.FAILED))
                            }
                            else -> {

                            }
                        }
                    })
            }
        }

        binding.parameterUnit.extraOption.setOnClickListener {
            binding.parameterUnit.showResponseText.visibility = View.VISIBLE
            binding.parameterUnit.showResponseText.setText("Please Wait...")
            lateinit var parameterUnit : RemoteControllerParameterUnit
            when (binding.rfPower.extraSpinner.selectedItemPosition) {
                0 -> parameterUnit = RemoteControllerParameterUnit.METRIC
                1 -> parameterUnit = RemoteControllerParameterUnit.IMPERIAL
                2 -> parameterUnit = RemoteControllerParameterUnit.METRIC_KM_H
            }
            lifecycleScope.launch(Dispatchers.Main) {
                viewModel.setParameterUnitTest(parameterUnit)
                    .observeOnce(viewLifecycleOwner, Observer { msg ->
                        when (msg.status) {
                            Status.SUCCESS -> {
                                binding.parameterUnit.showResponseText.setText(Utils.getColoredText(msg.message.toString(), Constants.SUCCESS))
                            }
                            Status.ERROR -> {
                                binding.parameterUnit.showResponseText.setText(Utils.getColoredText(msg.message.toString(), Constants.FAILED))
                            }
                            else -> {

                            }
                        }
                    })
            }
        }

        binding.yawCoefficient.extraOption.setOnClickListener {
            binding.yawCoefficient.showResponseText.visibility = View.VISIBLE
            binding.yawCoefficient.showResponseText.setText("Please Wait...")
            val support = viewModel.getParameterRangeManager().yawCoefficient
            var yawCoefficient = 0.3f
            if (!TextUtils.isEmpty(binding.yawCoefficient.extraEdittext.text)) {
                yawCoefficient = binding.yawCoefficient.extraEdittext.text.toString().toFloat()
                if (yawCoefficient > support.valueTo || yawCoefficient < support.valueFrom)
                    binding.yawCoefficient.showResponseText.setText(Utils.getColoredText("Please enter Yaw Coefficient from ${support.valueFrom} to ${support.valueTo}", Constants.SUCCESS))
                return@setOnClickListener
            } else {
                binding.yawCoefficient.showResponseText.setText(Utils.getColoredText("Please enter Yaw Coefficient from ${support.valueFrom} to ${support.valueTo}", Constants.FAILED))
                return@setOnClickListener
            }
            lifecycleScope.launch(Dispatchers.Main) {
                viewModel.setYawCoefficientTest(yawCoefficient)
                    .observeOnce(viewLifecycleOwner, Observer { msg ->
                        when (msg.status) {
                            Status.SUCCESS -> {
                                binding.yawCoefficient.showResponseText.setText(Utils.getColoredText(msg.message.toString(), Constants.SUCCESS))
                            }
                            Status.ERROR -> {
                                binding.yawCoefficient.showResponseText.setText(Utils.getColoredText(msg.message.toString(), Constants.FAILED))
                            }
                            else -> {
                                binding.yawCoefficient.showResponseText.setText("No Response From Server")
                            }
                        }
                    })
            }
        }

        binding.language.viewBtn.setOnClickListener {
            closeAllExtraOptionLayouts()
            lifecycleScope.launch(Dispatchers.Main) {
                viewModel.getLanguageTest().observeOnce(viewLifecycleOwner) { msg ->
                    binding.language.showResponseText.visibility = View.VISIBLE
                    when (msg.status) {
                        Status.SUCCESS -> {
                            binding.language.showResponseText.setText(Utils.getColoredText(msg.message.toString(),Constants.SUCCESS))
                        }
                        Status.ERROR -> {
                            binding.language.showResponseText.setText(Utils.getColoredText(msg.message.toString(),Constants.FAILED))
                        }
                        else -> {

                        }
                    }
                }
            }
        }

        binding.rfPower.viewBtn.setOnClickListener {
            closeAllExtraOptionLayouts()
            lifecycleScope.launch(Dispatchers.Main) {
                viewModel.getRFPowerTest().observeOnce(viewLifecycleOwner) { msg ->
                    binding.rfPower.showResponseText.visibility = View.VISIBLE
                    when (msg.status) {
                        Status.SUCCESS -> {
                            binding.rfPower.showResponseText.setText(Utils.getColoredText(msg.message.toString(),Constants.SUCCESS))
                        }
                        Status.ERROR -> {
                            binding.rfPower.showResponseText.setText(Utils.getColoredText(msg.message.toString(),Constants.FAILED))
                        }
                        else -> {

                        }
                    }
                }
            }
        }

        binding.parameterUnit.viewBtn.setOnClickListener {
            closeAllExtraOptionLayouts()
            lifecycleScope.launch(Dispatchers.Main) {
                viewModel.getParameterUnitTest().observeOnce(viewLifecycleOwner) { msg ->
                    binding.parameterUnit.showResponseText.visibility = View.VISIBLE
                    when (msg.status) {
                        Status.SUCCESS -> {
                            binding.parameterUnit.showResponseText.setText(Utils.getColoredText(msg.message.toString(),Constants.SUCCESS))
                        }
                        Status.ERROR -> {
                            binding.parameterUnit.showResponseText.setText(Utils.getColoredText(msg.message.toString(),Constants.FAILED))
                        }
                        else -> {

                        }
                    }
                }
            }
        }

        binding.yawCoefficient.viewBtn.setOnClickListener {
            closeAllExtraOptionLayouts()
            lifecycleScope.launch(Dispatchers.Main) {
                viewModel.getYawCoefficientTest().observeOnce(viewLifecycleOwner) { msg ->
                    binding.yawCoefficient.showResponseText.visibility = View.VISIBLE
                    when (msg.status) {
                        Status.SUCCESS -> {
                            binding.yawCoefficient.showResponseText.setText(Utils.getColoredText(msg.message.toString(),Constants.SUCCESS))
                        }
                        Status.ERROR -> {
                            binding.yawCoefficient.showResponseText.setText(Utils.getColoredText(msg.message.toString(),Constants.FAILED))
                        }
                        else -> {

                        }
                    }
                }
            }
        }

    }

    private fun closeAllExtraOptionLayouts() {

        binding.language.extraOptionParent.visibility = View.GONE
        binding.rfPower.extraOptionParent.visibility = View.GONE
        binding.parameterUnit.extraOptionParent.visibility = View.GONE
        binding.yawCoefficient.extraOptionParent.visibility = View.GONE

        // We should hide both extraOptionLayout and Response text
        binding.language.showResponseText.visibility = View.GONE
        binding.rfPower.showResponseText.visibility = View.GONE
        binding.parameterUnit.showResponseText.visibility = View.GONE
        binding.yawCoefficient.showResponseText.visibility = View.GONE

    }

    private fun setSpinnerItems() {

        var spinnerAdapter = ArrayAdapter.createFromResource(
            activity!!.baseContext,
            R.array.rc_languages,
            android.R.layout.simple_spinner_item
        )
        binding.language.extraSpinner.adapter = spinnerAdapter
        spinnerAdapter = ArrayAdapter.createFromResource(
            activity!!.baseContext,
            R.array.rc_rfs,
            android.R.layout.simple_spinner_item
        )
        binding.rfPower.extraSpinner.adapter = spinnerAdapter
        spinnerAdapter = ArrayAdapter.createFromResource(
            activity!!.baseContext,
            R.array.rc_length_unit,
            android.R.layout.simple_spinner_item
        )
        binding.parameterUnit.extraSpinner.adapter = spinnerAdapter
    }

}