package com.android.autelsdk.gimbal.fragments

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.android.autelsdk.R
import com.android.autelsdk.databinding.FragmentFlightControlParameterReadingGimbalBinding
import com.android.autelsdk.databinding.FragmentFlightControlParameterReadingRcBinding
import com.android.autelsdk.gimbal.GimbalViewModel
import com.android.autelsdk.remoteController.RemoteControllerViewModel
import com.android.autelsdk.util.Constants
import com.android.autelsdk.util.Status
import com.android.autelsdk.util.Utils
import com.android.autelsdk.util.Utils.observeOnce
import com.autel.common.gimbal.GimbalWorkMode
import com.autel.common.remotecontroller.RFPower
import com.autel.common.remotecontroller.RemoteControllerLanguage
import com.autel.common.remotecontroller.RemoteControllerParameterUnit
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class FlightControlParameterReadingGimbalFragment : Fragment() {

    private lateinit var binding: FragmentFlightControlParameterReadingGimbalBinding
    private val viewModel : GimbalViewModel by activityViewModels()

    companion object {
        fun newInstance() = FlightControlParameterReadingGimbalFragment()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_flight_control_parameter_reading_gimbal,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUI()
        setSpinnerItems()
        handleListeners()
    }

    private fun initUI() {
        binding.gimbalDirection.viewBtn.visibility = View.GONE
    }

    private fun handleListeners() {

        binding.gimbalWorkMode.setBtn.setOnClickListener {
            closeAllExtraOptionLayouts()
            binding.gimbalWorkMode.extraOptionParent.visibility = View.VISIBLE
        }

        binding.gimbalAngle.setBtn.setOnClickListener {
            closeAllExtraOptionLayouts()
            binding.gimbalAngle.setValuesParent.visibility = View.VISIBLE
        }

        binding.gimbalSpeed.setBtn.setOnClickListener {
            closeAllExtraOptionLayouts()
            binding.gimbalSpeed.setValuesParent.visibility = View.VISIBLE
        }

        binding.gimbalDirection.setBtn.setOnClickListener {
            closeAllExtraOptionLayouts()
            binding.gimbalDirection.setValuesParent.visibility = View.VISIBLE
        }

        binding.roll.setBtn.setOnClickListener {
            closeAllExtraOptionLayouts()
            binding.roll.extraOptionParent.visibility = View.VISIBLE
        }

        binding.pitch.setBtn.setOnClickListener {
            closeAllExtraOptionLayouts()
            binding.pitch.extraOptionParent.visibility = View.VISIBLE
        }

        binding.yaw.setBtn.setOnClickListener {
            closeAllExtraOptionLayouts()
            binding.yaw.extraOptionParent.visibility = View.VISIBLE
        }

        binding.leaseRadar.setBtn.setOnClickListener {
            closeAllExtraOptionLayouts()
            binding.leaseRadar.showResponseText.visibility = View.VISIBLE
            binding.leaseRadar.showResponseText.setText("Please Wait...")
            lifecycleScope.launch(Dispatchers.Main) {
                viewModel.setLeaserRadarListenerTest().observeOnce(viewLifecycleOwner) { msg ->
                    when (msg.status) {
                        Status.SUCCESS -> {
                            binding.leaseRadar.showResponseText.setText(Utils.getColoredText(msg.message.toString(),Constants.SUCCESS))
                        }
                        Status.ERROR -> {
                            binding.leaseRadar.showResponseText.setText(Utils.getColoredText(msg.message.toString(),Constants.FAILED))
                        }
                        else -> {

                        }
                    }
                }
            }
        }

        binding.gimbalWorkMode.extraOption.setOnClickListener {
            binding.gimbalWorkMode.showResponseText.visibility = View.VISIBLE
            binding.gimbalWorkMode.showResponseText.setText("Please Wait...")
            val gimbalWorkMode = GimbalWorkMode.values()[binding.gimbalWorkMode.extraSpinner.selectedItemPosition]
            lifecycleScope.launch(Dispatchers.Main) {
                viewModel.setGimbalWorkModeTest(gimbalWorkMode)
                    .observeOnce(viewLifecycleOwner, Observer { msg ->
                        when (msg.status) {
                            Status.SUCCESS -> {
                                binding.gimbalWorkMode.showResponseText.setText(Utils.getColoredText(msg.message.toString(), Constants.SUCCESS))
                            }
                            Status.ERROR -> {
                                binding.gimbalWorkMode.showResponseText.setText(Utils.getColoredText(msg.message.toString(), Constants.FAILED))
                            }
                            else -> {

                            }
                        }
                    })
            }
        }

        binding.gimbalAngle.setValuesOption.setOnClickListener {
            binding.gimbalAngle.showResponseText.visibility = View.VISIBLE
            binding.gimbalAngle.showResponseText.setText("Please Wait...")
            val support = viewModel.getCruiserGimbalParameterRangeManager().angleSpeedRange
            var (pitch: Float, roll:Float, yaw: Float) = listOf(0.0F, 0.0F, 0.0F)

            if (TextUtils.isEmpty(binding.gimbalAngle.extraEdittext1.text)) {
                binding.gimbalAngle.showResponseText.setText(Utils.getColoredText("Please enter Pitch Angle from ${support.valueFrom} to ${support.valueTo}", Constants.FAILED))
                return@setOnClickListener
            } else if(TextUtils.isEmpty(binding.gimbalAngle.extraEdittext2.text)) {
                binding.gimbalAngle.showResponseText.setText(Utils.getColoredText("Please enter Roll Angle from ${support.valueFrom} to ${support.valueTo}", Constants.FAILED))
                return@setOnClickListener
            } else if(TextUtils.isEmpty(binding.gimbalAngle.extraEdittext3.text)) {
                binding.gimbalAngle.showResponseText.setText(Utils.getColoredText("Please enter Yaw Angle from ${support.valueFrom} to ${support.valueTo}", Constants.FAILED))
                return@setOnClickListener
            } else {
                pitch = binding.gimbalAngle.extraEdittext1.text.toString().toFloat()
                roll = binding.gimbalAngle.extraEdittext2.text.toString().toFloat()
                yaw = binding.gimbalAngle.extraEdittext3.text.toString().toFloat()
                if (pitch > support.valueTo || pitch < support.valueFrom) {
                    binding.gimbalAngle.showResponseText.setText(Utils.getColoredText("Please enter Pitch Angle from ${support.valueFrom} to ${support.valueTo}", Constants.SUCCESS))
                    return@setOnClickListener
                } else if (roll > support.valueTo || roll < support.valueFrom) {
                    binding.gimbalAngle.showResponseText.setText(Utils.getColoredText("Please enter Roll Angle from ${support.valueFrom} to ${support.valueTo}", Constants.SUCCESS))
                    return@setOnClickListener
                } else if (yaw > support.valueTo || yaw < support.valueFrom) {
                    binding.gimbalAngle.showResponseText.setText(Utils.getColoredText("Please enter Yaw Angle from ${support.valueFrom} to ${support.valueTo}", Constants.SUCCESS))
                    return@setOnClickListener
                }
            }
            lifecycleScope.launch(Dispatchers.Main) {
                viewModel.setGimbalAngleTest(pitch, roll, yaw)
                viewModel.setSaveParamsTest()
                    .observeOnce(viewLifecycleOwner, Observer { msg ->
                        when (msg.status) {
                            Status.SUCCESS -> {
                                binding.gimbalAngle.showResponseText.setText(Utils.getColoredText(msg.message.toString(), Constants.SUCCESS))
                            }
                            Status.ERROR -> {
                                binding.gimbalAngle.showResponseText.setText(Utils.getColoredText(msg.message.toString(), Constants.FAILED))
                            }
                            else -> {
                                binding.gimbalAngle.showResponseText.setText("No Response From Server")
                            }
                        }
                    })
            }
        }

        binding.gimbalSpeed.setValuesOption.setOnClickListener {
            binding.gimbalSpeed.showResponseText.visibility = View.VISIBLE
            binding.gimbalSpeed.showResponseText.setText("Please Wait...")
            binding.gimbalSpeed.extraEdittext2.visibility = View.GONE
            val support = viewModel.getCruiserGimbalParameterRangeManager().angleSpeedRange
            var (pitch: Float, yaw: Float) = listOf(0.0F, 0.0F)

            if (TextUtils.isEmpty(binding.gimbalSpeed.extraEdittext1.text)) {
                binding.gimbalSpeed.showResponseText.setText(Utils.getColoredText("Please enter Pitch Angle from ${support.valueFrom} to ${support.valueTo}", Constants.FAILED))
                return@setOnClickListener
            } else if(TextUtils.isEmpty(binding.gimbalSpeed.extraEdittext3.text)) {
                binding.gimbalSpeed.showResponseText.setText(Utils.getColoredText("Please enter Yaw Angle from ${support.valueFrom} to ${support.valueTo}", Constants.FAILED))
                return@setOnClickListener
            } else {
                pitch = binding.gimbalSpeed.extraEdittext1.text.toString().toFloat()
                yaw = binding.gimbalSpeed.extraEdittext3.text.toString().toFloat()
                if (pitch > support.valueTo || pitch < support.valueFrom) {
                    binding.gimbalSpeed.showResponseText.setText(Utils.getColoredText("Please enter Pitch Angle from ${support.valueFrom} to ${support.valueTo}", Constants.SUCCESS))
                    return@setOnClickListener
                } else if (yaw > support.valueTo || yaw < support.valueFrom) {
                    binding.gimbalSpeed.showResponseText.setText(Utils.getColoredText("Please enter Yaw Angle from ${support.valueFrom} to ${support.valueTo}", Constants.SUCCESS))
                    return@setOnClickListener
                }
            }
            lifecycleScope.launch(Dispatchers.Main) {
                viewModel.setGimbalAngleSpeedTest(pitch.toInt(), yaw.toInt())
                viewModel.setSaveParamsTest()
                    .observeOnce(viewLifecycleOwner, Observer { msg ->
                        when (msg.status) {
                            Status.SUCCESS -> {
                                binding.gimbalSpeed.showResponseText.setText(Utils.getColoredText(msg.message.toString(), Constants.SUCCESS))
                            }
                            Status.ERROR -> {
                                binding.gimbalSpeed.showResponseText.setText(Utils.getColoredText(msg.message.toString(), Constants.FAILED))
                            }
                            else -> {
                                binding.gimbalSpeed.showResponseText.setText("No Response From Server")
                            }
                        }
                    })
            }
        }

        binding.gimbalDirection.setValuesOption.setOnClickListener {
            binding.gimbalDirection.showResponseText.visibility = View.VISIBLE
            binding.gimbalDirection.showResponseText.setText("Please Wait...")
            val support = viewModel.getCruiserGimbalParameterRangeManager().angleSpeedRange
            var (pitch: Float, roll:Float, yaw: Float, x: Float, y:Float) = listOf(0.0F, 0.0F, 0.0F, 0.0F, 0.0F)

            if (TextUtils.isEmpty(binding.gimbalDirection.extraEdittext1.text)) {
                binding.gimbalDirection.showResponseText.setText(Utils.getColoredText("Please enter Pitch value", Constants.FAILED))
                return@setOnClickListener
            } else if(TextUtils.isEmpty(binding.gimbalDirection.extraEdittext2.text)) {
                binding.gimbalDirection.showResponseText.setText(Utils.getColoredText("Please enter Roll value", Constants.FAILED))
                return@setOnClickListener
            } else if(TextUtils.isEmpty(binding.gimbalDirection.extraEdittext3.text)) {
                binding.gimbalDirection.showResponseText.setText(Utils.getColoredText("Please enter Yaw value", Constants.FAILED))
                return@setOnClickListener
            } else if(TextUtils.isEmpty(binding.gimbalDirection.extraEdittext4.text)) {
                binding.gimbalDirection.showResponseText.setText(Utils.getColoredText("Please enter X value", Constants.FAILED))
                return@setOnClickListener
            } else if(TextUtils.isEmpty(binding.gimbalDirection.extraEdittext5.text)) {
                binding.gimbalDirection.showResponseText.setText(Utils.getColoredText("Please enter Y value", Constants.FAILED))
                return@setOnClickListener
            } else {
                pitch = binding.gimbalDirection.extraEdittext1.text.toString().toFloat()
                roll = binding.gimbalDirection.extraEdittext2.text.toString().toFloat()
                yaw = binding.gimbalDirection.extraEdittext3.text.toString().toFloat()
                x = binding.gimbalDirection.extraEdittext4.text.toString().toFloat()
                y = binding.gimbalDirection.extraEdittext5.text.toString().toFloat()

            }
            lifecycleScope.launch(Dispatchers.Main) {
                viewModel.adjustGimbalDirectionTest(x, y, roll, pitch, yaw)
                    .observeOnce(viewLifecycleOwner, Observer { msg ->
                        when (msg.status) {
                            Status.SUCCESS -> {
                                binding.gimbalDirection.showResponseText.setText(Utils.getColoredText(msg.message.toString(), Constants.SUCCESS))
                            }
                            Status.ERROR -> {
                                binding.gimbalDirection.showResponseText.setText(Utils.getColoredText(msg.message.toString(), Constants.FAILED))
                            }
                            else -> {
                                binding.gimbalDirection.showResponseText.setText("No Response From Server")
                            }
                        }
                    })
            }
        }

        binding.roll.extraOption.setOnClickListener {
            binding.roll.showResponseText.visibility = View.VISIBLE
            binding.roll.showResponseText.setText("Please Wait...")
            val support = viewModel.getCruiserGimbalParameterRangeManager().rollAngleAdjust
            var adjustRollValue = 0.3f
            if (!TextUtils.isEmpty(binding.roll.extraEdittext.text)) {
                adjustRollValue = binding.roll.extraEdittext.text.toString().toFloat()
            } else {
                binding.roll.showResponseText.setText(Utils.getColoredText("Please enter Roll Adjust Data", Constants.FAILED))
                return@setOnClickListener
            }
            lifecycleScope.launch(Dispatchers.Main) {
                viewModel.setRollAdjustDataTest(adjustRollValue)
                viewModel.setSaveParamsTest()
                    .observeOnce(viewLifecycleOwner, Observer { msg ->
                        when (msg.status) {
                            Status.SUCCESS -> {
                                binding.roll.showResponseText.setText(Utils.getColoredText(msg.message.toString(), Constants.SUCCESS))
                            }
                            Status.ERROR -> {
                                binding.roll.showResponseText.setText(Utils.getColoredText(msg.message.toString(), Constants.FAILED))
                            }
                            else -> {
                                binding.roll.showResponseText.setText("No Response From Server")
                            }
                        }
                    })
            }
        }

        binding.pitch.extraOption.setOnClickListener {
            binding.pitch.showResponseText.visibility = View.VISIBLE
            binding.pitch.showResponseText.setText("Please Wait...")
            val support = viewModel.getCruiserGimbalParameterRangeManager().rollAngleAdjust
            var adjustPitchValue = 0.0f
            if (!TextUtils.isEmpty(binding.pitch.extraEdittext.text)) {
                adjustPitchValue = binding.pitch.extraEdittext.text.toString().toFloat()
            } else {
                binding.pitch.showResponseText.setText(Utils.getColoredText("Please enter Pitch Adjust Data", Constants.FAILED))
                return@setOnClickListener
            }
            lifecycleScope.launch(Dispatchers.Main) {
                viewModel.setPitchAdjustDataTest(adjustPitchValue)
                viewModel.setSaveParamsTest()
                    .observeOnce(viewLifecycleOwner, Observer { msg ->
                        when (msg.status) {
                            Status.SUCCESS -> {
                                binding.pitch.showResponseText.setText(Utils.getColoredText(msg.message.toString(), Constants.SUCCESS))
                            }
                            Status.ERROR -> {
                                binding.pitch.showResponseText.setText(Utils.getColoredText(msg.message.toString(), Constants.FAILED))
                            }
                            else -> {
                                binding.pitch.showResponseText.setText("No Response From Server")
                            }
                        }
                    })
            }
        }

        binding.yaw.extraOption.setOnClickListener {
            binding.yaw.showResponseText.visibility = View.VISIBLE
            binding.yaw.showResponseText.setText("Please Wait...")
            val support = viewModel.getCruiserGimbalParameterRangeManager().rollAngleAdjust
            var adjustYawValue = 0.0f
            if (!TextUtils.isEmpty(binding.yaw.extraEdittext.text)) {
                adjustYawValue = binding.yaw.extraEdittext.text.toString().toFloat()
            } else {
                binding.yaw.showResponseText.setText(Utils.getColoredText("Please enter Yaw Adjust Data", Constants.FAILED))
                return@setOnClickListener
            }
            lifecycleScope.launch(Dispatchers.Main) {
                viewModel.setYawAdjustDataTest(adjustYawValue)
                viewModel.setSaveParamsTest()
                    .observeOnce(viewLifecycleOwner, Observer { msg ->
                        when (msg.status) {
                            Status.SUCCESS -> {
                                binding.yaw.showResponseText.setText(Utils.getColoredText(msg.message.toString(), Constants.SUCCESS))
                            }
                            Status.ERROR -> {
                                binding.yaw.showResponseText.setText(Utils.getColoredText(msg.message.toString(), Constants.FAILED))
                            }
                            else -> {
                                binding.yaw.showResponseText.setText("No Response From Server")
                            }
                        }
                    })
            }
        }

        binding.gimbalWorkMode.viewBtn.setOnClickListener {
            closeAllExtraOptionLayouts()
            binding.gimbalWorkMode.showResponseText.visibility = View.VISIBLE
            binding.gimbalWorkMode.showResponseText.setText("Please Wait...")
            lifecycleScope.launch(Dispatchers.Main) {
                viewModel.getGimbalWorkModeTest().observeOnce(viewLifecycleOwner) { msg ->
                    when (msg.status) {
                        Status.SUCCESS -> {
                            binding.gimbalWorkMode.showResponseText.setText(Utils.getColoredText(msg.message.toString(),Constants.SUCCESS))
                        }
                        Status.ERROR -> {
                            binding.gimbalWorkMode.showResponseText.setText(Utils.getColoredText(msg.message.toString(),Constants.FAILED))
                        }
                        else -> {

                        }
                    }
                }
            }
        }

        binding.gimbalAngle.viewBtn.setOnClickListener {
            closeAllExtraOptionLayouts()
            binding.gimbalAngle.showResponseText.visibility = View.VISIBLE
            binding.gimbalAngle.showResponseText.setText("Please Wait...")
            lifecycleScope.launch(Dispatchers.Main) {
                viewModel.getAngleRangeTest().observeOnce(viewLifecycleOwner) { msg ->
                    when (msg.status) {
                        Status.SUCCESS -> {
                            binding.gimbalAngle.showResponseText.setText(Utils.getColoredText(msg.message.toString(),Constants.SUCCESS))
                        }
                        Status.ERROR -> {
                            binding.gimbalAngle.showResponseText.setText(Utils.getColoredText(msg.message.toString(),Constants.FAILED))
                        }
                        else -> {

                        }
                    }
                }
            }
        }

        binding.gimbalSpeed.viewBtn.setOnClickListener {
            closeAllExtraOptionLayouts()
            binding.gimbalSpeed.showResponseText.visibility = View.VISIBLE
            binding.gimbalSpeed.showResponseText.setText("Please Wait...")
            lifecycleScope.launch(Dispatchers.Main) {
                viewModel.getAngleSpeedRangeTest().observeOnce(viewLifecycleOwner) { msg ->
                    when (msg.status) {
                        Status.SUCCESS -> {
                            binding.gimbalSpeed.showResponseText.setText(Utils.getColoredText(msg.message.toString(),Constants.SUCCESS))
                        }
                        Status.ERROR -> {
                            binding.gimbalSpeed.showResponseText.setText(Utils.getColoredText(msg.message.toString(),Constants.FAILED))
                        }
                        else -> {

                        }
                    }
                }
            }
        }

        binding.roll.viewBtn.setOnClickListener {
            closeAllExtraOptionLayouts()
            binding.roll.showResponseText.visibility = View.VISIBLE
            binding.roll.showResponseText.setText("Please Wait...")
            lifecycleScope.launch(Dispatchers.Main) {
                viewModel.getRollAdjustDataTest().observeOnce(viewLifecycleOwner) { msg ->
                    when (msg.status) {
                        Status.SUCCESS -> {
                            binding.roll.showResponseText.setText(Utils.getColoredText(msg.message.toString(),Constants.SUCCESS))
                        }
                        Status.ERROR -> {
                            binding.roll.showResponseText.setText(Utils.getColoredText(msg.message.toString(),Constants.FAILED))
                        }
                        else -> {

                        }
                    }
                }
            }
        }

        binding.yaw.viewBtn.setOnClickListener {
            closeAllExtraOptionLayouts()
            binding.yaw.showResponseText.visibility = View.VISIBLE
            binding.yaw.showResponseText.setText("Please Wait...")
            lifecycleScope.launch(Dispatchers.Main) {
                viewModel.getYawAdjustDataTest().observeOnce(viewLifecycleOwner) { msg ->
                    when (msg.status) {
                        Status.SUCCESS -> {
                            binding.yaw.showResponseText.setText(Utils.getColoredText(msg.message.toString(),Constants.SUCCESS))
                        }
                        Status.ERROR -> {
                            binding.yaw.showResponseText.setText(Utils.getColoredText(msg.message.toString(),Constants.FAILED))
                        }
                        else -> {

                        }
                    }
                }
            }
        }

        binding.pitch.viewBtn.setOnClickListener {
            closeAllExtraOptionLayouts()
            binding.pitch.showResponseText.visibility = View.VISIBLE
            binding.pitch.showResponseText.setText("Please Wait...")
            lifecycleScope.launch(Dispatchers.Main) {
                viewModel.getPitchAdjustDataTest().observeOnce(viewLifecycleOwner) { msg ->
                    when (msg.status) {
                        Status.SUCCESS -> {
                            binding.pitch.showResponseText.setText(Utils.getColoredText(msg.message.toString(),Constants.SUCCESS))
                        }
                        Status.ERROR -> {
                            binding.pitch.showResponseText.setText(Utils.getColoredText(msg.message.toString(),Constants.FAILED))
                        }
                        else -> {

                        }
                    }
                }
            }
        }

        binding.leaseRadar.viewBtn.setOnClickListener {
            closeAllExtraOptionLayouts()
            binding.leaseRadar.showResponseText.visibility = View.VISIBLE
            binding.leaseRadar.showResponseText.setText(Utils.getColoredText("Reset Lease Radar Listener successful if device is connected.", Constants.SUCCESS))
        }

    }

    private fun closeAllExtraOptionLayouts() {

        binding.gimbalWorkMode.extraOptionParent.visibility = View.GONE
        binding.gimbalAngle.extraOptionParent.visibility = View.GONE
        binding.gimbalSpeed.extraOptionParent.visibility = View.GONE
        binding.gimbalDirection.extraOptionParent.visibility = View.GONE
        binding.leaseRadar.extraOptionParent.visibility = View.GONE
        binding.roll.extraOptionParent.visibility = View.GONE
        binding.pitch.extraOptionParent.visibility = View.GONE
        binding.yaw.extraOptionParent.visibility = View.GONE

        // We should hide both extraOptionLayout and Response text
        binding.gimbalWorkMode.showResponseText.visibility = View.GONE
        binding.gimbalAngle.showResponseText.visibility = View.GONE
        binding.gimbalSpeed.showResponseText.visibility = View.GONE
        binding.roll.showResponseText.visibility = View.GONE
        binding.pitch.showResponseText.visibility = View.GONE
        binding.yaw.showResponseText.visibility = View.GONE
        binding.leaseRadar.showResponseText.visibility = View.GONE

        binding.gimbalAngle.setValuesParent.visibility = View.GONE
        binding.gimbalSpeed.setValuesParent.visibility = View.GONE
        binding.gimbalDirection.setValuesParent.visibility = View.GONE

    }

    private fun setSpinnerItems() {

        var spinnerAdapter = ArrayAdapter(
            requireActivity().baseContext,
            android.R.layout.simple_spinner_item,
            getGimbalWorkModes()
        )
        binding.gimbalWorkMode.extraSpinner.adapter = spinnerAdapter
        spinnerAdapter = ArrayAdapter.createFromResource(
            requireActivity().baseContext,
            R.array.rc_rfs,
            android.R.layout.simple_spinner_item
        )
        binding.gimbalAngle.extraSpinner.adapter = spinnerAdapter
        spinnerAdapter = ArrayAdapter.createFromResource(
            requireActivity().baseContext,
            R.array.rc_length_unit,
            android.R.layout.simple_spinner_item
        )
        binding.gimbalSpeed.extraSpinner.adapter = spinnerAdapter
    }

    private fun getGimbalWorkModes() : Array<CharSequence> {

        var modes = mutableListOf<CharSequence>()
        for (mode in GimbalWorkMode.values()) {
            modes.add(mode.name)
        }
        return modes.toList().toTypedArray()
    }

}