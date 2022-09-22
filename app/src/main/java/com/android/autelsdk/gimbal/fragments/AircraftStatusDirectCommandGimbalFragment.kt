package com.android.autelsdk.gimbal.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.android.autelsdk.R
import com.android.autelsdk.databinding.FragmentAircraftStatusDirectCommandGimbalBinding
import com.android.autelsdk.gimbal.GimbalViewModel
import com.android.autelsdk.util.Constants
import com.android.autelsdk.util.Status
import com.android.autelsdk.util.Utils
import com.android.autelsdk.util.Utils.observeOnce
import com.autel.common.product.AutelProductType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AircraftStatusDirectCommandGimbalFragment : Fragment() {

    private lateinit var binding: FragmentAircraftStatusDirectCommandGimbalBinding
    private val viewModel: GimbalViewModel by activityViewModels()

    companion object {
        fun newInstance() = AircraftStatusDirectCommandGimbalFragment()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_aircraft_status_direct_command_gimbal , container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUi()
        handleListeners()
    }

    private fun initUi() {
        if (viewModel.getCurrentProductType().value == AutelProductType.UNKNOWN) {
            binding.planeConnectStatus.setText("The plane is not connected")
        } else {
            binding.planeConnectStatus.setText("Connected Plane - ${viewModel.getCurrentProductType().value?.name}")
        }
    }

    private fun handleListeners() {

        binding.setAngleListener.setOnClickListener {
            binding.testResults.setText(Utils.getColoredText("Please Wait..."))
            lifecycleScope.launch(Dispatchers.Main) {
                viewModel.setAngleListenerTest()
                    .observeOnce(viewLifecycleOwner, Observer { msg ->

                        when (msg.status) {
                            Status.SUCCESS -> {
                                binding.testResults.setText(Utils.getColoredText(msg.message.toString(), Constants.SUCCESS))
                            }
                            Status.ERROR -> {
                                binding.testResults.setText(Utils.getColoredText(msg.message.toString(), Constants.FAILED))
                            }
                            else -> {

                            }
                        }

                    })
            }
        }

        binding.getAdjustGimbalAngle.setOnClickListener {
            binding.testResults.setText(Utils.getColoredText("Please Wait..."))
            lifecycleScope.launch(Dispatchers.Main) {
                viewModel.getAdjustGimbalAngelDataTest()
                    .observeOnce(viewLifecycleOwner, Observer { msg ->
                        when (msg.status) {
                            Status.SUCCESS -> {
                                binding.testResults.setText(Utils.getColoredText(msg.message.toString(), Constants.SUCCESS))
                            }
                            Status.ERROR -> {
                                binding.testResults.setText(Utils.getColoredText(msg.message.toString(), Constants.FAILED))
                            }
                            else -> {

                            }
                        }
                    })
            }
        }

        binding.getAngleRange.setOnClickListener {
            binding.testResults.setText(Utils.getColoredText("Please Wait..."))
            lifecycleScope.launch(Dispatchers.Main) {
                viewModel.getAngleRangeTest()
                    .observeOnce(viewLifecycleOwner, Observer { msg ->
                        when (msg.status) {
                            Status.SUCCESS -> {
                                binding.testResults.setText(Utils.getColoredText(msg.message.toString(), Constants.SUCCESS))
                            }
                            Status.ERROR -> {
                                binding.testResults.setText(Utils.getColoredText(msg.message.toString(), Constants.FAILED))
                            }
                            else -> {

                            }
                        }

                    })
            }
        }

        binding.getAngleSpeedRange.setOnClickListener {
            binding.testResults.setText(Utils.getColoredText("Please Wait..."))
            lifecycleScope.launch(Dispatchers.Main) {
                viewModel.getAngleSpeedRangeTest()
                    .observeOnce(viewLifecycleOwner, Observer { msg ->
                        when (msg.status) {
                            Status.SUCCESS -> {
                                binding.testResults.setText(Utils.getColoredText(msg.message.toString(), Constants.SUCCESS))
                            }
                            Status.ERROR -> {
                                binding.testResults.setText(Utils.getColoredText(msg.message.toString(), Constants.FAILED))
                            }
                            else -> {

                            }
                        }

                    })
            }
        }

        binding.startGimbalCalibration.setOnClickListener {
            binding.testResults.setText(Utils.getColoredText("Please Wait..."))
            lifecycleScope.launch(Dispatchers.Main) {
                viewModel.setGimbalCalibrationTest()
                    .observeOnce(viewLifecycleOwner, Observer { msg ->
                        when (msg.status) {
                            Status.SUCCESS -> {
                                binding.testResults.setText(Utils.getColoredText(msg.message.toString(), Constants.SUCCESS))
                            }
                            Status.ERROR -> {
                                binding.testResults.setText(Utils.getColoredText(msg.message.toString(), Constants.FAILED))
                            }
                            else -> {

                            }
                        }
                    })
            }
        }

        binding.stopGimbalCalibration.setOnClickListener {
            binding.testResults.setText(Utils.getColoredText("Please Wait..."))
            lifecycleScope.launch(Dispatchers.Main) {
                viewModel.stopGimbalCalibrationTest()
                    .observeOnce(viewLifecycleOwner, Observer { msg ->
                        when (msg.status) {
                            Status.SUCCESS -> {
                                binding.testResults.setText(Utils.getColoredText(msg.message.toString(), Constants.SUCCESS))
                            }
                            Status.ERROR -> {
                                binding.testResults.setText(Utils.getColoredText(msg.message.toString(), Constants.FAILED))
                            }
                            else -> {

                            }
                        }
                    })
            }
        }

        binding.getVersionInfo.setOnClickListener {
            binding.testResults.setText(Utils.getColoredText("Please Wait..."))
            lifecycleScope.launch(Dispatchers.Main) {
                viewModel.getVersionInfoTest()
                    .observeOnce(viewLifecycleOwner, Observer { msg ->
                        when (msg.status) {
                            Status.SUCCESS -> {
                                binding.testResults.setText(Utils.getColoredText(msg.message.toString(), Constants.SUCCESS))
                            }
                            Status.ERROR -> {
                                binding.testResults.setText(Utils.getColoredText(msg.message.toString(), Constants.FAILED))
                            }
                            else -> {

                            }
                        }
                    })
            }
        }

    }

}