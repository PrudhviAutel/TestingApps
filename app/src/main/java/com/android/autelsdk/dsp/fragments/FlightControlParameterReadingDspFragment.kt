package com.android.autelsdk.dsp.fragments

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
import com.android.autelsdk.dsp.DspViewModel
import com.android.autelsdk.util.Constants
import com.android.autelsdk.util.Status
import com.android.autelsdk.util.Utils
import com.android.autelsdk.util.Utils.observeOnce
import com.autel.common.dsp.AppAction
import com.autel.common.dsp.AppActionParam
import com.autel.common.dsp.RFData
import com.autel.common.dsp.evo.BandMode
import com.autel.common.dsp.evo.Bandwidth
import com.autel.common.dsp.evo.TransferMode
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking


class FlightControlParameterReadingDspFragment : Fragment() {

    private lateinit var binding: FragmentFlightControlParameterReadingDspBinding
    private val viewModel : DspViewModel by activityViewModels()
    private var rfDataList : List<RFData> = ArrayList()
    private var fetchRFDataList = false
    private var fetchRFDataListMessage :String? = null

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
        fetchRFDataList()
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
            if (fetchRFDataList)
                binding.rfData.extraOptionParent.visibility = View.VISIBLE
            else {
                binding.rfData.showResponseText.visibility = View.VISIBLE
                binding.rfData.showResponseText.setText(Utils.getColoredText(fetchRFDataListMessage?:"Fetching RFData List...", Constants.FAILED))
            }
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
            val rfData = rfDataList.get(binding.rfData.extraSpinner.selectedItemPosition)
            var maxRetryCount : Int = 3
            if (!TextUtils.isEmpty(binding.rfData.extraEdittext.text))
                maxRetryCount = binding.rfData.extraEdittext.text.toString().toInt()
            lifecycleScope.launch(Dispatchers.Main) {
                viewModel.setCurrentRFDataTest(rfData.value, maxRetryCount)
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
            val appAction = AppAction.values()[binding.syncMsgBroadcastListener.extraSpinner.selectedItemPosition]
            val appActionParam = AppActionParam.values()[binding.syncMsgBroadcastListener.extraSpinner2.selectedItemPosition]
            lifecycleScope.launch(Dispatchers.Main) {
                viewModel.setSynMsgBroadcastTest(appAction, appActionParam)
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
            var maxRetryCount : Int = 3
            if (!TextUtils.isEmpty(binding.rfData.extraEdittext.text))
                maxRetryCount = binding.rfData.extraEdittext.text.toString().toInt()
            lifecycleScope.launch(Dispatchers.Main) {
                viewModel.getCurrentRFDataTest(maxRetryCount).observeOnce(viewLifecycleOwner) { msg ->
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

        var spinnerAdapter = ArrayAdapter(
            requireActivity().baseContext,
            android.R.layout.simple_spinner_item,
            getRFDataList()
        )
        binding.rfData.extraSpinner.adapter = spinnerAdapter
        var spinnerAdapter2 = ArrayAdapter(
            requireActivity().baseContext,
            android.R.layout.simple_spinner_item,
            AppAction.values()
        )
        binding.syncMsgBroadcastListener.extraSpinner.adapter = spinnerAdapter2
        var spinnerAdapter3 = ArrayAdapter(
            requireActivity().baseContext,
            android.R.layout.simple_spinner_item,
            AppActionParam.values()
        )
        binding.syncMsgBroadcastListener.extraSpinner2.adapter = spinnerAdapter3

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

    private fun fetchRFDataList() {
        runBlocking {
            viewModel.getRFDataListTest(3).observeOnce(viewLifecycleOwner, Observer { msg ->
                fetchRFDataListMessage = msg.message
                when (msg.status) {
                    Status.SUCCESS -> {
                        fetchRFDataList = true
                        rfDataList = ArrayList(msg.data)
                        setSpinnerItems()
                    }
                    Status.ERROR -> {
                        fetchRFDataList = false
                        binding.rfData.showResponseText.setText(Utils.getColoredText(fetchRFDataListMessage?:"Fetching RFData List Failed", Constants.FAILED))
                    }
                    else -> {
                        fetchRFDataList = false
                        binding.rfData.showResponseText.setText(Utils.getColoredText(fetchRFDataListMessage?:"Fetching RFData List Failed", Constants.FAILED))
                    }
                }
            })
        }
    }

    private fun getRFDataList() : Array<CharSequence> {
        var rfFreqList = mutableListOf<CharSequence>()
        for (rfData in rfDataList) {
            rfFreqList.add(rfData.hz.toString())
        }
        return rfFreqList.toList().toTypedArray()
    }

}