package com.android.autelsdk.mission.view

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.autelsdk.R
import com.android.autelsdk.battery.BatteryViewModel
import com.android.autelsdk.battery.adapter.ManualIndividualItemAdapter
import com.android.autelsdk.battery.data.HarnessResult
import com.android.autelsdk.databinding.AcManualIndividualFragmentBinding
import com.android.autelsdk.databinding.AcManualIndividualMissionFragmentBinding
import com.android.autelsdk.mission.DFViewModel

class ManualIndividual_MissionFragment : Fragment(), View.OnClickListener {
    lateinit var binding: AcManualIndividualMissionFragmentBinding
    lateinit var viewModel: DFViewModel

    companion object {
        fun newInstance() = ManualIndividual_MissionFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = AcManualIndividualMissionFragmentBinding.inflate(layoutInflater, container, false)
        viewModel = ViewModelProvider(this).get(DFViewModel::class.java)
        initData()
        return binding.root
    }

    fun initData() {
        binding.setBtnMissionManager.setOnClickListener(this)
        binding.resetBtnMissionManager.setOnClickListener(this)

        binding.setBtnMissionManagerListener.setOnClickListener(this)
        binding.resetBtnMissionManagerListener.setOnClickListener(this)

        binding.setBtnBattery.setOnClickListener(this)
        binding.resetBtnBattery.setOnClickListener(this)

        binding.setBtnBatteryStateListener.setOnClickListener(this)
        binding.resetBtnBatteryStateListener.setOnClickListener(this)

        binding.setBtnFlyController.setOnClickListener(this)
        binding.resetBtnFlyController.setOnClickListener(this)

        binding.setBtnRemoteController.setOnClickListener(this)
        binding.resetBtnRemoteController.setOnClickListener(this)

        binding.resetBtnLowBatteryNotifyThreshold.setOnClickListener(this)
    }

    private fun hideAllView() {
        binding.textViewMissionManager.visibility = View.GONE
        binding.textViewMissionManagerListener.visibility = View.GONE
        binding.textViewBattery.visibility = View.GONE
        binding.textViewBatteryStateListener.visibility = View.GONE
        binding.textViewLowBatteryNotifyThreshold.visibility = View.GONE
        binding.textViewRemoteController.visibility = View.GONE
        binding.textViewFlyController.visibility = View.GONE
    }

    override fun onClick(p0: View?) {
        hideAllView()
        val result = MutableLiveData<HarnessResult>()
        when (p0?.id) {
            R.id.setBtnMissionManager -> {
                setLoadingResult(binding.textViewMissionManager)
                result.postValue(viewModel.setMissionManager())
                displayResult(result, "missionManager")
            }
            R.id.resetBtnMissionManager -> {
                setLoadingResult(binding.textViewMissionManager)
                result.postValue(
                    HarnessResult(
                        "resetMissionManager() Mission Manager is reset now",
                        true
                    )
                )
                displayResult(result, "missionManager")
            }
            R.id.setBtnMissionManagerListener -> {
                setLoadingResult(binding.textViewMissionManagerListener)
                result.postValue(viewModel.setMissionManagerListener())
                displayResult(result, "missionManagerListener")
            }
            R.id.resetBtnMissionManagerListener -> {
                setLoadingResult(binding.textViewMissionManagerListener)
                result.postValue(
                    HarnessResult(
                        "resetMissionManagerListener() Mission Manager Listener is reset now",
                        true
                    )
                )
                displayResult(result, "missionManagerListener")
            }
            R.id.setBtnBattery -> {
                setLoadingResult(binding.textViewBattery)
                result.postValue(viewModel.setBattery())
                displayResult(result, "battery")
            }
            R.id.resetBtnBattery -> {
                setLoadingResult(binding.textViewBattery)
                result.postValue(HarnessResult("resetBattery() Battery is reset now", true))
                displayResult(result, "battery")
            }
            R.id.setBtnBatteryStateListener -> {
                setLoadingResult(binding.textViewBatteryStateListener)
                result.postValue(viewModel.setBatteryManagerListener())
                displayResult(result, "batteryListener")
            }
            R.id.resetBtnBatteryStateListener -> {
                setLoadingResult(binding.textViewBatteryStateListener)
                result.postValue(
                    HarnessResult(
                        "resetBatteryManagerListener() Battery State Listener is reset now",
                        true
                    )
                )
                displayResult(result, "batteryListener")
            }
            R.id.setBtnFlyController -> {
                setLoadingResult(binding.textViewFlyController)
                result.postValue(viewModel.setFlyController())
                displayResult(result, "flyController")
            }
            R.id.resetBtnFlyController -> {
                setLoadingResult(binding.textViewFlyController)
                result.postValue(
                    HarnessResult(
                        "resetFlyController() Fly Controller is reset now",
                        true
                    )
                )
                displayResult(result, "flyController")
            }
            R.id.setBtnRemoteController -> {
                setLoadingResult(binding.textViewRemoteController)
                result.postValue(viewModel.setRemoteControllers())
                displayResult(result, "remoteController")
            }
            R.id.resetBtnRemoteController -> {
                setLoadingResult(binding.textViewRemoteController)
                result.postValue(
                    HarnessResult(
                        "resetRemoteControllers() Remote Controller is reset now",
                        true
                    )
                )
                displayResult(result, "remoteController")
            }
            R.id.resetBtnLowBatteryNotifyThreshold -> {
                setLoadingResult(binding.textViewLowBatteryNotifyThreshold)
                result.postValue(viewModel.getLowBatteryNotifyThreshold())
                displayResult(result, "lowBatteryNotifyThreshold")
            }
        }
    }

    private fun displayResult(
        result: MutableLiveData<HarnessResult>?, type: String
    ) {
        result?.observe(viewLifecycleOwner) {
            if (it != null) {
                if (it.status) {
                    setSuccessResult(it.value, type)
                } else {
                    setFailureResult(it.value, type)
                }
            } else {
                setFailureResult("error in loading data", type)
            }
        }
    }

    private fun setSuccessResult(value: String, type: String) {
        when (type) {
            "missionManager" -> {
                binding.textViewMissionManager.text = value
                binding.textViewMissionManager.setTextColor(
                    Color.parseColor(
                        "#00ff00"
                    )
                )
                binding.textViewMissionManager.visibility = View.VISIBLE
            }
            "missionManagerListener" -> {
                binding.textViewMissionManagerListener.text = value
                binding.textViewMissionManagerListener.setTextColor(
                    Color.parseColor(
                        "#00ff00"
                    )
                )
                binding.textViewMissionManagerListener.visibility = View.VISIBLE
            }
            "battery" -> {
                binding.textViewBattery.text = value
                binding.textViewBattery.setTextColor(
                    Color.parseColor(
                        "#00ff00"
                    )
                )
                binding.textViewBattery.visibility =
                    View.VISIBLE
            }
            "batteryListener" -> {
                binding.textViewBatteryStateListener.text = value
                binding.textViewBatteryStateListener.setTextColor(
                    Color.parseColor(
                        "#00ff00"
                    )
                )
                binding.textViewBatteryStateListener.visibility = View.VISIBLE
            }
            "lowBatteryNotifyThreshold" -> {
                binding.textViewLowBatteryNotifyThreshold.text = value
                binding.textViewLowBatteryNotifyThreshold.setTextColor(
                    Color.parseColor(
                        "#00ff00"
                    )
                )
                binding.textViewLowBatteryNotifyThreshold.visibility = View.VISIBLE
            }
            "flyController" -> {
                binding.textViewFlyController.text = value
                binding.textViewFlyController.setTextColor(
                    Color.parseColor(
                        "#00ff00"
                    )
                )
                binding.textViewFlyController.visibility = View.VISIBLE
            }
            "remoteController" -> {
                binding.textViewRemoteController.text = value
                binding.textViewRemoteController.setTextColor(
                    Color.parseColor(
                        "#00ff00"
                    )
                )
                binding.textViewRemoteController.visibility = View.VISIBLE
            }
        }
    }

    private fun setFailureResult(value: String, type: String) {
        when (type) {
            "missionManager" -> {
                binding.textViewMissionManager.text = value
                binding.textViewMissionManager.setTextColor(
                    Color.parseColor(
                        "#ff0000"
                    )
                )
                binding.textViewMissionManager.visibility = View.VISIBLE
            }
            "missionManagerListener" -> {
                binding.textViewMissionManagerListener.text = value
                binding.textViewMissionManagerListener.setTextColor(
                    Color.parseColor(
                        "#ff0000"
                    )
                )
                binding.textViewMissionManagerListener.visibility = View.VISIBLE
            }
            "battery" -> {
                binding.textViewBattery.text = value
                binding.textViewBattery.setTextColor(
                    Color.parseColor(
                        "#ff0000"
                    )
                )
                binding.textViewBattery.visibility =
                    View.VISIBLE
            }
            "batteryListener" -> {
                binding.textViewBatteryStateListener.text = value
                binding.textViewBatteryStateListener.setTextColor(
                    Color.parseColor(
                        "#ff0000"
                    )
                )
                binding.textViewBatteryStateListener.visibility = View.VISIBLE
            }
            "lowBatteryNotifyThreshold" -> {
                binding.textViewLowBatteryNotifyThreshold.text = value
                binding.textViewLowBatteryNotifyThreshold.setTextColor(
                    Color.parseColor(
                        "#ff0000"
                    )
                )
                binding.textViewLowBatteryNotifyThreshold.visibility = View.VISIBLE
            }
            "flyController" -> {
                binding.textViewFlyController.text = value
                binding.textViewFlyController.setTextColor(
                    Color.parseColor(
                        "#ff0000"
                    )
                )
                binding.textViewFlyController.visibility = View.VISIBLE
            }
            "remoteController" -> {
                binding.textViewRemoteController.text = value
                binding.textViewRemoteController.setTextColor(
                    Color.parseColor(
                        "#ff0000"
                    )
                )
                binding.textViewRemoteController.visibility = View.VISIBLE
            }
        }
    }

    private fun setLoadingResult(viewTextView: View) {
        val value = "Please wait..."
        (viewTextView as TextView).text = value
        (viewTextView as TextView).setTextColor(Color.parseColor("#000000"))
        (viewTextView as TextView).visibility = View.VISIBLE
    }
}