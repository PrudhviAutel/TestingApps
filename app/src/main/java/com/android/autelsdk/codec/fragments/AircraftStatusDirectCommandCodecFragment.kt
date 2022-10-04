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
import com.android.autelsdk.codec.CodecViewModel
import com.android.autelsdk.databinding.FragmentAircraftStatusDirectCommandCodecBinding
import com.android.autelsdk.databinding.FragmentAircraftStatusDirectCommandFcBinding
import com.android.autelsdk.flyController.FlyControllerViewModel
import com.android.autelsdk.util.Constants
import com.android.autelsdk.util.Status
import com.android.autelsdk.util.Utils
import com.android.autelsdk.util.Utils.observeOnce
import com.autel.common.product.AutelProductType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AircraftStatusDirectCommandCodecFragment : Fragment() {

    private lateinit var binding: FragmentAircraftStatusDirectCommandCodecBinding
    private val viewModel: CodecViewModel by activityViewModels()

    companion object {
        fun newInstance() = AircraftStatusDirectCommandCodecFragment()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_aircraft_status_direct_command_codec , container, false)
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
        binding.isOverExposureEnabledTest.setOnClickListener {
            binding.testResults.setText(Utils.getColoredText("Please Wait...", Constants.COMMON))

            lifecycleScope.launch(Dispatchers.Main) {
                viewModel.isOverExposureEnabled()
                    .observeOnce(viewLifecycleOwner, Observer { msg ->

                        when (msg.status) {
                            Status.SUCCESS -> {
                                binding.testResults.setText(
                                    Utils.getColoredText(
                                        msg.message.toString(),
                                        Constants.SUCCESS
                                    )
                                )
                            }
                            Status.ERROR -> {
                                binding.testResults.setText(
                                    Utils.getColoredText(
                                        msg.message.toString(),
                                        Constants.FAILED
                                    )
                                )
                            }
                            else -> {

                            }
                        }

                    })
            }
        }

        binding.pause.setOnClickListener {
            binding.testResults.setText(Utils.getColoredText("Please Wait...", Constants.COMMON))
            lifecycleScope.launch(Dispatchers.Main) {
                viewModel.pause()
                    .observeOnce(viewLifecycleOwner, Observer { msg ->

                        when (msg.status) {
                            Status.SUCCESS -> {
                                binding.testResults.setText(
                                    Utils.getColoredText(
                                        msg.message.toString(),
                                        Constants.SUCCESS
                                    )
                                )
                            }
                            Status.ERROR -> {
                                binding.testResults.setText(
                                    Utils.getColoredText(
                                        msg.message.toString(),
                                        Constants.FAILED
                                    )
                                )
                            }
                            else -> {

                            }
                        }

                    })
            }
        }

        binding.resume.setOnClickListener {
            binding.testResults.setText(Utils.getColoredText("Please Wait...", Constants.COMMON))

            lifecycleScope.launch(Dispatchers.Main) {
                viewModel.resume()
                    .observeOnce(viewLifecycleOwner, Observer { msg ->
                        binding.testResults.setText("No response for this")

                        when (msg.status) {
                            Status.SUCCESS -> {
                                binding.testResults.setText(
                                    Utils.getColoredText(
                                        msg.message.toString(),
                                        Constants.SUCCESS
                                    )
                                )
                            }
                            Status.ERROR -> {
                                binding.testResults.setText(
                                    Utils.getColoredText(
                                        msg.message.toString(),
                                        Constants.FAILED
                                    )
                                )
                            }
                            else -> {

                            }
                        }

                    })
            }
        }

        binding.setOnRenderFrameInfoListener.setOnClickListener {
            binding.testResults.setText(Utils.getColoredText("Please Wait...", Constants.COMMON))

            lifecycleScope.launch(Dispatchers.Main) {
                viewModel.setOnRenderFrameInfoListener()
                    .observeOnce(viewLifecycleOwner, Observer { msg ->

                        when (msg.status) {
                            Status.SUCCESS -> {
                                binding.testResults.setText(
                                    Utils.getColoredText(
                                        msg.message.toString(),
                                        Constants.SUCCESS
                                    )
                                )
                            }
                            Status.ERROR -> {
                                binding.testResults.setText(
                                    Utils.getColoredText(
                                        msg.message.toString(),
                                        Constants.FAILED
                                    )
                                )
                            }
                            else -> {

                            }
                        }

                    })
            }
        }

        binding.stopcodec.setOnClickListener {
            binding.testResults.setText(Utils.getColoredText("Please Wait...", Constants.COMMON))

            lifecycleScope.launch(Dispatchers.Main) {

                viewModel.stopCodec()
                    .observeOnce(viewLifecycleOwner, Observer { msg ->

                        when (msg.status) {
                            Status.SUCCESS -> {
                                binding.testResults.setText(
                                    Utils.getColoredText(
                                        msg.message.toString(),
                                        Constants.SUCCESS
                                    )
                                )
                            }
                            Status.ERROR -> {
                                binding.testResults.setText(
                                    Utils.getColoredText(
                                        msg.message.toString(),
                                        Constants.FAILED
                                    )
                                )
                            }
                            else -> {

                            }
                        }

                    })
            }
        }

    }

}