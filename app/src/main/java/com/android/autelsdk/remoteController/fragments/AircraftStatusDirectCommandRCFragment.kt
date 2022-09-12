package com.android.autelsdk.remoteController.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.android.autelsdk.R
import com.android.autelsdk.databinding.FragmentAircraftStatusDirectCommandRcBinding
import com.android.autelsdk.remoteController.RemoteControllerViewModel
import com.android.autelsdk.util.Constants
import com.android.autelsdk.util.Status
import com.android.autelsdk.util.Utils
import com.android.autelsdk.util.Utils.observeOnce
import com.autel.common.remotecontroller.RemoteControllerStickCalibration
import com.autel.common.remotecontroller.TeachingMode
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AircraftStatusDirectCommandRCFragment : Fragment() {

    private lateinit var binding: FragmentAircraftStatusDirectCommandRcBinding

    companion object {
        fun newInstance() = AircraftStatusDirectCommandRCFragment()
    }

    private lateinit var viewModel: RemoteControllerViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_aircraft_status_direct_command_rc , container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(RemoteControllerViewModel::class.java)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        handleListeners()
    }

    private fun handleListeners() {

        binding.enterBinding.setOnClickListener {
            lifecycleScope.launch(Dispatchers.Main) {
                viewModel.enterPairingTest()
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

        binding.exitBinding.setOnClickListener {
            viewModel.exitPairing()
            binding.testResults.setText(Utils.getColoredText("Exit Pairing Successful", Constants.SUCCESS))
        }

        binding.disableTeachingMode.setOnClickListener {
            lifecycleScope.launch(Dispatchers.Main) {
                viewModel.setTeacherStudentModeTest(TeachingMode.DISABLED)
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

        binding.enableStudentMode.setOnClickListener {
            lifecycleScope.launch(Dispatchers.Main) {
                viewModel.setTeacherStudentModeTest(TeachingMode.STUDENT)
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

        binding.enableTeachingMode.setOnClickListener {
            lifecycleScope.launch(Dispatchers.Main) {
                viewModel.setTeacherStudentModeTest(TeachingMode.TEACHER)
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

        binding.startStickCalibration.setOnClickListener {
            lifecycleScope.launch(Dispatchers.Main) {
                viewModel.setStickCalibrationTest(RemoteControllerStickCalibration.START)
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

        binding.completeStickCalibration.setOnClickListener {
            lifecycleScope.launch(Dispatchers.Main) {
                viewModel.setStickCalibrationTest(RemoteControllerStickCalibration.COMPLETE)
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

    }

}