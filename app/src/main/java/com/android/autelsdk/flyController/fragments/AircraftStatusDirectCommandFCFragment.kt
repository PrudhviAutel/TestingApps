package com.android.autelsdk.flyController.fragments

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
import com.android.autelsdk.databinding.FragmentAircraftStatusDirectCommandFcBinding
import com.android.autelsdk.databinding.FragmentAircraftStatusDirectCommandRcBinding
import com.android.autelsdk.flyController.FlyControllerViewModel
import com.android.autelsdk.remoteController.RemoteControllerViewModel
import com.android.autelsdk.remoteController.fragments.AircraftStatusDirectCommandRCFragment
import com.android.autelsdk.util.Constants
import com.android.autelsdk.util.Status
import com.android.autelsdk.util.Utils
import com.android.autelsdk.util.Utils.observeOnce
import com.autel.common.product.AutelProductType
import com.autel.common.remotecontroller.RemoteControllerStickCalibration
import com.autel.common.remotecontroller.TeachingMode
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AircraftStatusDirectCommandFCFragment : Fragment() {

    private lateinit var binding: FragmentAircraftStatusDirectCommandFcBinding
    private val viewModel: FlyControllerViewModel by activityViewModels()

    companion object {
        fun newInstance() = AircraftStatusDirectCommandFCFragment()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_aircraft_status_direct_command_fc , container, false)
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
        binding.enterBinding.setOnClickListener {
            lifecycleScope.launch(Dispatchers.Main) {
                viewModel.goHomeTest()
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

        binding.cancelreturn.setOnClickListener {
            lifecycleScope.launch(Dispatchers.Main) {
                viewModel.cancelReturnTest()
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

        binding.cancelland.setOnClickListener {
            lifecycleScope.launch(Dispatchers.Main) {
                viewModel.cancelLandTest()
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

        binding.craftlocationashomepoint.setOnClickListener {
            lifecycleScope.launch(Dispatchers.Main) {
                viewModel.setAircraftLocationAsHomePointTest()
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

        binding.land.setOnClickListener {
            lifecycleScope.launch(Dispatchers.Main) {
                viewModel.landTest()
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

        binding.startcalibretingcompass.setOnClickListener {
            lifecycleScope.launch(Dispatchers.Main) {
                viewModel.startCalibrateCompassTest()
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

        binding.getSerialNumber.setOnClickListener {
            lifecycleScope.launch(Dispatchers.Main) {
                viewModel.getSerialNumberTest()
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

        binding.isattitudemodeenabled.setOnClickListener {
            lifecycleScope.launch(Dispatchers.Main) {
                viewModel.isAttitudeModeEnableTest()
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