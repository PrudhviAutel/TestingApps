package com.android.autelsdk.dsp.fragments

import android.icu.util.TimeUnit.values
import android.net.wifi.WifiInfo
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.android.autelsdk.R
import com.android.autelsdk.databinding.FragmentFlightControlParameterReadingDspBinding
import com.android.autelsdk.databinding.FragmentFlightControlParameterReadingRcBinding
import com.android.autelsdk.dsp.DspViewModel
import com.android.autelsdk.gimbal.GimbalViewModel
import com.android.autelsdk.remoteController.RemoteControllerViewModel
import com.android.autelsdk.util.Constants
import com.android.autelsdk.util.Status
import com.android.autelsdk.util.Utils
import com.android.autelsdk.util.Utils.observeOnce
import com.autel.common.dsp.evo.BandMode
import com.autel.common.dsp.evo.Bandwidth
import com.autel.common.dsp.evo.TransferMode
import com.autel.common.remotecontroller.RFPower
import com.autel.common.remotecontroller.RemoteControllerLanguage
import com.autel.common.remotecontroller.RemoteControllerParameterUnit
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.chrono.JapaneseEra.values


class FlightControlParameterReadingDspFragment : Fragment() {

    private lateinit var binding: FragmentFlightControlParameterReadingDspBinding
    private val viewModel : DspViewModel by activityViewModels()

    companion object {
        fun newInstance() = FlightControlParameterReadingDspFragment()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_flight_control_parameter_reading_dsp,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUi()
        setSpinnerItems()
        handleListeners()
    }

    private fun initUi() {
        binding.bandwidthInfo.viewBtn.visibility = View.GONE
        binding.routeWifiConfig.viewBtn.visibility = View.GONE
    }

    private fun handleListeners() {

        binding.rfData.setBtn.setOnClickListener {
            closeAllExtraOptionLayouts()
            binding.rfData.extraOptionParent.visibility = View.VISIBLE
        }

        binding.syncMsgBroadcastListener.setBtn.setOnClickListener {
            closeAllExtraOptionLayouts()
            binding.syncMsgBroadcastListener.extraOptionParent.visibility = View.VISIBLE
        }

        binding.bandwidthInfo.setBtn.setOnClickListener {
            closeAllExtraOptionLayouts()
            binding.bandwidthInfo.extraOptionParent.visibility = View.VISIBLE
        }

        binding.transferMode.setBtn.setOnClickListener {
            closeAllExtraOptionLayouts()
            binding.transferMode.extraOptionParent.visibility = View.VISIBLE
        }

        binding.baseStationState.setBtn.setOnClickListener {
            closeAllExtraOptionLayouts()
            binding.baseStationState.extraOptionParent.visibility = View.VISIBLE
        }

        binding.routeWifiConfig.setBtn.setOnClickListener {
            closeAllExtraOptionLayouts()
            binding.routeWifiConfig.extraOptionParent.visibility = View.VISIBLE
        }

        binding.rfData.extraOption.setOnClickListener {
            binding.rfData.showResponseText.visibility = View.VISIBLE
            binding.rfData.showResponseText.setText("Please Wait...")
            val language =
                if (0 == binding.rfData.extraSpinner.selectedItemPosition)
                    RemoteControllerLanguage.ENGLISH
                else
                    RemoteControllerLanguage.CHINESE
            lifecycleScope.launch(Dispatchers.Main) {
                viewModel.setLanguageTest(language)
                    .observeOnce(viewLifecycleOwner, Observer { msg ->
                        when (msg.status) {
                            Status.SUCCESS -> {
                                binding.rfData.showResponseText.setText(Utils.getColoredText(msg.message.toString(), Constants.SUCCESS))
                            }
                            Status.ERROR -> {
                                binding.rfData.showResponseText.setText(Utils.getColoredText(msg.message.toString(), Constants.FAILED))
                            }
                            else -> {

                            }
                        }
                    })
            }
        }

        binding.syncMsgBroadcastListener.extraOption.setOnClickListener {
            binding.syncMsgBroadcastListener.showResponseText.visibility = View.VISIBLE
            binding.syncMsgBroadcastListener.showResponseText.setText("Please Wait...")
            val bandMode = BandMode.values()[binding.syncMsgBroadcastListener.extraSpinner.selectedItemPosition]
            val bandwidth = Bandwidth.values()[binding.syncMsgBroadcastListener.extraSpinner2.selectedItemPosition]
            lifecycleScope.launch(Dispatchers.Main) {
                viewModel.setS(RFPower.CE)
                    .observeOnce(viewLifecycleOwner, Observer { msg ->
                        when (msg.status) {
                            Status.SUCCESS -> {
                                binding.syncMsgBroadcastListener.showResponseText.setText(Utils.getColoredText(msg.message.toString(), Constants.SUCCESS))
                            }
                            Status.ERROR -> {
                                binding.syncMsgBroadcastListener.showResponseText.setText(Utils.getColoredText(msg.message.toString(), Constants.FAILED))
                            }
                            else -> {

                            }
                        }
                    })
            }
        }

        binding.bandwidthInfo.extraOption.setOnClickListener {
            binding.bandwidthInfo.showResponseText.visibility = View.VISIBLE
            binding.bandwidthInfo.showResponseText.setText("Please Wait...")
            val bandMode = BandMode.values()[binding.bandwidthInfo.extraSpinner.selectedItemPosition]
            val bandwidth = Bandwidth.values()[binding.bandwidthInfo.extraSpinner2.selectedItemPosition]
            lifecycleScope.launch(Dispatchers.Main) {
                viewModel.setBandwidthInfoTest(bandMode, bandwidth)
                    .observeOnce(viewLifecycleOwner, Observer { msg ->
                        when (msg.status) {
                            Status.SUCCESS -> {
                                binding.bandwidthInfo.showResponseText.setText(Utils.getColoredText(msg.message.toString(), Constants.SUCCESS))
                            }
                            Status.ERROR -> {
                                binding.bandwidthInfo.showResponseText.setText(Utils.getColoredText(msg.message.toString(), Constants.FAILED))
                            }
                            else -> {

                            }
                        }
                    })
            }
        }

        binding.transferMode.extraOption.setOnClickListener {
            binding.transferMode.showResponseText.visibility = View.VISIBLE
            binding.transferMode.showResponseText.setText("Please Wait...")
            val transferMode = TransferMode.values()[binding.transferMode.extraSpinner.selectedItemPosition]
            lifecycleScope.launch(Dispatchers.Main) {
                viewModel.setTransferModeTest(transferMode)
                    .observeOnce(viewLifecycleOwner, Observer { msg ->
                        when (msg.status) {
                            Status.SUCCESS -> {
                                binding.transferMode.showResponseText.setText(Utils.getColoredText(msg.message.toString(), Constants.SUCCESS))
                            }
                            Status.ERROR -> {
                                binding.transferMode.showResponseText.setText(Utils.getColoredText(msg.message.toString(), Constants.FAILED))
                            }
                            else -> {

                            }
                        }
                    })
            }
        }

        binding.baseStationState.extraOption.setOnClickListener {
            binding.baseStationState.showResponseText.visibility = View.VISIBLE
            binding.baseStationState.showResponseText.setText("Please Wait...")
            var baseStationState : Boolean = true
            when(binding.baseStationState.extraSpinner.selectedItemPosition) {
                0 -> baseStationState = true
                1 -> baseStationState = false
            }
            lifecycleScope.launch(Dispatchers.Main) {
                viewModel.setBaseStationEnableTest(baseStationState)
                    .observeOnce(viewLifecycleOwner, Observer { msg ->
                        when (msg.status) {
                            Status.SUCCESS -> {
                                binding.baseStationState.showResponseText.setText(Utils.getColoredText(msg.message.toString(), Constants.SUCCESS))
                            }
                            Status.ERROR -> {
                                binding.baseStationState.showResponseText.setText(Utils.getColoredText(msg.message.toString(), Constants.FAILED))
                            }
                            else -> {

                            }
                        }
                    })
            }
        }

        binding.routeWifiConfig.extraOption.setOnClickListener {
            binding.routeWifiConfig.showResponseText.visibility = View.VISIBLE
            binding.routeWifiConfig.showResponseText.setText("Please Wait...")
            //val wifiInfo = WifiInfo.values()[binding.routeWifiConfig.extraSpinner.selectedItemPosition]
//            lifecycleScope.launch(Dispatchers.Main) {
//                viewModel.setRouteWifiConfigTest(wifiInfo)
//                    .observeOnce(viewLifecycleOwner, Observer { msg ->
//                        when (msg.status) {
//                            Status.SUCCESS -> {
//                                binding.routeWifiConfig.showResponseText.setText(Utils.getColoredText(msg.message.toString(), Constants.SUCCESS))
//                            }
//                            Status.ERROR -> {
//                                binding.routeWifiConfig.showResponseText.setText(Utils.getColoredText(msg.message.toString(), Constants.FAILED))
//                            }
//                            else -> {
//
//                            }
//                        }
//                    })
//            }
        }

        binding.rfData.viewBtn.setOnClickListener {
            closeAllExtraOptionLayouts()
            lifecycleScope.launch(Dispatchers.Main) {
                viewModel.getCurrentRFDataTest().observeOnce(viewLifecycleOwner) { msg ->
                    binding.rfData.showResponseText.visibility = View.VISIBLE
                    when (msg.status) {
                        Status.SUCCESS -> {
                            binding.rfData.showResponseText.setText(Utils.getColoredText(msg.message.toString(),Constants.SUCCESS))
                        }
                        Status.ERROR -> {
                            binding.rfData.showResponseText.setText(Utils.getColoredText(msg.message.toString(),Constants.FAILED))
                        }
                        else -> {

                        }
                    }
                }
            }
        }

        binding.syncMsgBroadcastListener.viewBtn.setOnClickListener {
            closeAllExtraOptionLayouts()
            lifecycleScope.launch(Dispatchers.Main) {
                viewModel.setSynMsgBroadcastListenerTest().observeOnce(viewLifecycleOwner) { msg ->
                    binding.syncMsgBroadcastListener.showResponseText.visibility = View.VISIBLE
                    when (msg.status) {
                        Status.SUCCESS -> {
                            binding.syncMsgBroadcastListener.showResponseText.setText(Utils.getColoredText(msg.message.toString(),Constants.SUCCESS))
                        }
                        Status.ERROR -> {
                            binding.syncMsgBroadcastListener.showResponseText.setText(Utils.getColoredText(msg.message.toString(),Constants.FAILED))
                        }
                        else -> {

                        }
                    }
                }
            }
        }

        binding.transferMode.viewBtn.setOnClickListener {
            closeAllExtraOptionLayouts()
            lifecycleScope.launch(Dispatchers.Main) {
                viewModel.getTransferModeTest().observeOnce(viewLifecycleOwner) { msg ->
                    binding.transferMode.showResponseText.visibility = View.VISIBLE
                    when (msg.status) {
                        Status.SUCCESS -> {
                            binding.transferMode.showResponseText.setText(Utils.getColoredText(msg.message.toString(),Constants.SUCCESS))
                        }
                        Status.ERROR -> {
                            binding.transferMode.showResponseText.setText(Utils.getColoredText(msg.message.toString(),Constants.FAILED))
                        }
                        else -> {

                        }
                    }
                }
            }
        }

        binding.baseStationState.viewBtn.setOnClickListener {
            closeAllExtraOptionLayouts()
            lifecycleScope.launch(Dispatchers.Main) {
                viewModel.isBaseStationEnableTest().observeOnce(viewLifecycleOwner) { msg ->
                    binding.baseStationState.showResponseText.visibility = View.VISIBLE
                    when (msg.status) {
                        Status.SUCCESS -> {
                            binding.baseStationState.showResponseText.setText(Utils.getColoredText(msg.message.toString(),Constants.SUCCESS))
                        }
                        Status.ERROR -> {
                            binding.baseStationState.showResponseText.setText(Utils.getColoredText(msg.message.toString(),Constants.FAILED))
                        }
                        else -> {

                        }
                    }
                }
            }
        }

    }

    private fun closeAllExtraOptionLayouts() {

        binding.rfData.extraOptionParent.visibility = View.GONE
        binding.syncMsgBroadcastListener.extraOptionParent.visibility = View.GONE
        binding.bandwidthInfo.extraOptionParent.visibility = View.GONE
        binding.transferMode.extraOptionParent.visibility = View.GONE
        binding.baseStationState.extraOptionParent.visibility = View.GONE
        binding.routeWifiConfig.extraOptionParent.visibility = View.GONE

        // We should hide both extraOptionLayout and Response text
        binding.rfData.showResponseText.visibility = View.GONE
        binding.syncMsgBroadcastListener.showResponseText.visibility = View.GONE
        binding.bandwidthInfo.showResponseText.visibility = View.GONE
        binding.transferMode.showResponseText.visibility = View.GONE
        binding.baseStationState.showResponseText.visibility = View.GONE
        binding.routeWifiConfig.showResponseText.visibility = View.GONE

    }

    private fun setSpinnerItems() {

        var spinnerAdapter = ArrayAdapter.createFromResource(
            requireActivity().baseContext,
            R.array.rc_languages,
            android.R.layout.simple_spinner_item
        )
        binding.rfData.extraSpinner.adapter = spinnerAdapter
        spinnerAdapter = ArrayAdapter.createFromResource(
            requireActivity().baseContext,
            R.array.rc_rfs,
            android.R.layout.simple_spinner_item
        )
        binding.syncMsgBroadcastListener.extraSpinner.adapter = spinnerAdapter
        spinnerAdapter = ArrayAdapter.createFromResource(
            requireActivity().baseContext,
            R.array.rc_length_unit,
            android.R.layout.simple_spinner_item
        )
        binding.bandwidthInfo.extraSpinner.adapter = spinnerAdapter

        spinnerAdapter = ArrayAdapter.createFromResource(
            requireActivity().baseContext,
            R.array.rc_rfs,
            android.R.layout.simple_spinner_item
        )
        binding.transferMode.extraSpinner.adapter = spinnerAdapter

        spinnerAdapter = ArrayAdapter.createFromResource(
            requireActivity().baseContext,
            R.array.rc_rfs,
            android.R.layout.simple_spinner_item
        )
        binding.baseStationState.extraSpinner.adapter = spinnerAdapter

        spinnerAdapter = ArrayAdapter.createFromResource(
            requireActivity().baseContext,
            R.array.rc_rfs,
            android.R.layout.simple_spinner_item
        )
        binding.routeWifiConfig.extraSpinner.adapter = spinnerAdapter
    }

}