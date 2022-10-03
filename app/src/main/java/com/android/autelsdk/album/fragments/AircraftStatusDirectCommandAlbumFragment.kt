package com.android.autelsdk.album.fragments

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
import com.android.autelsdk.databinding.FragmentAircraftStatusDirectCommandDspBinding
import com.android.autelsdk.dsp.DspViewModel
import com.android.autelsdk.util.Constants
import com.android.autelsdk.util.Status
import com.android.autelsdk.util.Utils
import com.android.autelsdk.util.Utils.observeOnce
import com.autel.common.product.AutelProductType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AircraftStatusDirectCommandAlbumFragment : Fragment() {

    private lateinit var binding: FragmentAircraftStatusDirectCommandDspBinding
    private val viewModel: DspViewModel by activityViewModels()

    companion object {
        fun newInstance() = AircraftStatusDirectCommandAlbumFragment()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_aircraft_status_direct_command_dsp , container, false)
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

        binding.setDspInfoListener.setOnClickListener {
            binding.testResults.setText(Utils.getColoredText("Please Wait..."))
            lifecycleScope.launch(Dispatchers.Main) {
                viewModel.setDspInfoListenerTest()
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

        binding.enableVideoLinkState.setOnClickListener {
            binding.testResults.setText(Utils.getColoredText("Please Wait..."))
            lifecycleScope.launch(Dispatchers.Main) {
                viewModel.setVideoLinkStateTest(true)
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

        binding.disableVideoLinkState.setOnClickListener {
            binding.testResults.setText(Utils.getColoredText("Please Wait..."))
            lifecycleScope.launch(Dispatchers.Main) {
                viewModel.setVideoLinkStateTest(false)
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

        binding.getDeviceVersionInfo.setOnClickListener {
            binding.testResults.setText(Utils.getColoredText("Please Wait..."))
            lifecycleScope.launch(Dispatchers.Main) {
                viewModel.getDeviceVersionInfoTest()
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