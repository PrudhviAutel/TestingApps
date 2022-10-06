package com.android.autelsdk.flyController.fragments

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
import com.android.autelsdk.codec.fragments.FlightControlParameterReadingCodecFragment
import com.android.autelsdk.databinding.FragmentFlightControlParameterReadingFcBinding
import com.android.autelsdk.flyController.FlyControllerViewModel
import com.android.autelsdk.util.Constants
import com.android.autelsdk.util.Status
import com.android.autelsdk.util.Utils
import com.android.autelsdk.util.Utils.observeOnce
import com.autel.common.flycontroller.LedPilotLamp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class FlightControlParameterReadingFCFragment : Fragment() {

    private lateinit var binding: FragmentFlightControlParameterReadingFcBinding
    private val viewModel : FlyControllerViewModel by activityViewModels()

    companion object {
        fun newInstance() = FlightControlParameterReadingCodecFragment()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_flight_control_parameter_reading_fc,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setSpinnerItems()
        handleListeners()
    }

    private fun handleListeners() {
        binding.beginnerMode.setBtn.setOnClickListener {
            closeAllExtraOptionLayouts()
            binding.beginnerMode.extraOptionParent.visibility = View.VISIBLE
        }

        binding.maxHeight.setBtn.setOnClickListener {
            closeAllExtraOptionLayouts()
            binding.maxHeight.extraOptionParent.visibility = View.VISIBLE
        }

        binding.maxRange.setBtn.setOnClickListener {
            closeAllExtraOptionLayouts()
            binding.maxRange.extraOptionParent.visibility = View.VISIBLE
        }

        binding.returnHeight.setBtn.setOnClickListener {
            closeAllExtraOptionLayouts()
            binding.returnHeight.extraOptionParent.visibility = View.VISIBLE
        }

        binding.maxHorizontalSpeed.setBtn.setOnClickListener {
            closeAllExtraOptionLayouts()
            binding.maxHorizontalSpeed.extraOptionParent.visibility = View.VISIBLE
        }

        binding.ledLampPilot.setBtn.setOnClickListener {
            closeAllExtraOptionLayouts()
            binding.ledLampPilot.extraOptionParent.visibility = View.VISIBLE
        }

        binding.attitudeModeTest.setBtn.setOnClickListener{
            closeAllExtraOptionLayouts()
            binding.attitudeModeTest.extraOptionParent.visibility = View.VISIBLE
        }

        binding.locationasHomePoint.setBtn.setOnClickListener{
            closeAllExtraOptionLayouts()
            binding.locationasHomePoint.extraOptionParent.visibility = View.VISIBLE
        }

        binding.warningListener.setBtn.setOnClickListener {
            closeAllExtraOptionLayouts()
            binding.warningListener.showResponseText.visibility = View.VISIBLE
            binding.warningListener.showResponseText.setText("Please Wait...")
            lifecycleScope.launch(Dispatchers.Main) {
                viewModel.setWarningListenerTest().observeOnce(viewLifecycleOwner) { msg ->
                    when (msg.status) {
                        Status.SUCCESS -> {
                            binding.warningListener.showResponseText.setText(
                                Utils.getColoredText(
                                    msg.message.toString(),
                                    Constants.SUCCESS
                                )
                            )
                        }
                        Status.ERROR -> {
                            binding.warningListener.showResponseText.setText(
                                Utils.getColoredText(
                                    msg.message.toString(),
                                    Constants.FAILED
                                )
                            )
                        }
                        else -> {

                        }
                    }
                }
            }
        }



        binding.beginnerMode.extraOption.setOnClickListener {
            binding.beginnerMode.showResponseText.visibility = View.VISIBLE
            binding.beginnerMode.showResponseText.setText("Please Wait...")

            val state =
                if (0 == binding.beginnerMode.extraSpinner.selectedItemPosition)
                    true
                else
                    false
            lifecycleScope.launch(Dispatchers.Main) {
                viewModel.setBeginnerModeStateTest(state)
                    .observeOnce(viewLifecycleOwner, Observer { msg ->
                        when (msg.status) {
                            Status.SUCCESS -> {
                                binding.beginnerMode.showResponseText.setText(Utils.getColoredText(msg.message.toString(), Constants.SUCCESS))
                            }
                            Status.ERROR -> {
                                binding.beginnerMode.showResponseText.setText(Utils.getColoredText(msg.message.toString(), Constants.FAILED))
                            }
                            else -> {

                            }
                        }
                    })
            }
        }

        binding.attitudeModeTest.extraOption.setOnClickListener {
            binding.attitudeModeTest.showResponseText.visibility = View.VISIBLE
            binding.attitudeModeTest.showResponseText.setText("Please Wait...")

            val state =
                if (0 == binding.attitudeModeTest.extraSpinner.selectedItemPosition)
                    true
                else
                    false
            lifecycleScope.launch(Dispatchers.Main) {
                viewModel.setAttitudeModeEnableTest(state)
                    .observeOnce(viewLifecycleOwner, Observer { msg ->
                        when (msg.status) {
                            Status.SUCCESS -> {
                                binding.attitudeModeTest.showResponseText.setText(Utils.getColoredText(msg.message.toString(), Constants.SUCCESS))
                            }
                            Status.ERROR -> {
                                binding.attitudeModeTest.showResponseText.setText(Utils.getColoredText(msg.message.toString(), Constants.FAILED))
                            }
                            else -> {

                            }
                        }
                    })
            }
        }

        binding.maxHeight.extraOption.setOnClickListener {
            binding.maxHeight.showResponseText.visibility = View.VISIBLE
            binding.maxHeight.showResponseText.setText("Please Wait...")
            var value : Double = 0.0
            if(TextUtils.isEmpty(binding.maxHeight.extraEdittext.text)) {
                binding.maxHeight.showResponseText.setText("Please enter height")
                return@setOnClickListener
            } else {
                value = binding.maxHeight.extraEdittext.text.toString().toDouble()
            }

            //value = Integer.parseInt(binding.rfPower.extraEdittext.text.toString()).toDouble()

            lifecycleScope.launch(Dispatchers.Main) {
                viewModel.setMaxHeightTest(value)
                    .observeOnce(viewLifecycleOwner, Observer { msg ->
                        when (msg.status) {
                            Status.SUCCESS -> {
                                binding.maxHeight.showResponseText.setText(Utils.getColoredText(msg.message.toString(), Constants.SUCCESS))
                            }
                            Status.ERROR -> {
                                binding.maxHeight.showResponseText.setText(Utils.getColoredText(msg.message.toString(), Constants.FAILED))
                            }
                            else -> {

                            }
                        }
                    })
            }


//

        }

        binding.maxRange.extraOption.setOnClickListener {
            binding.maxRange.showResponseText.visibility = View.VISIBLE
            binding.maxRange.showResponseText.setText("Please Wait...")

            var value : Double
            if(TextUtils.isEmpty(binding.maxRange.extraEdittext.text)) {
                binding.maxRange.showResponseText.setText("Please enter range")
                return@setOnClickListener
            } else {
                value = binding.maxRange.extraEdittext.text.toString().toDouble()
            }
                lifecycleScope.launch(Dispatchers.Main) {
                    viewModel.setMaxRangeTest(value)
                        .observeOnce(viewLifecycleOwner, Observer { msg ->
                            when (msg.status) {
                                Status.SUCCESS -> {
                                    binding.maxRange.showResponseText.setText(Utils.getColoredText(msg.message.toString(), Constants.SUCCESS))
                                }
                                Status.ERROR -> {
                                    binding.maxRange.showResponseText.setText(Utils.getColoredText(msg.message.toString(), Constants.FAILED))
                                }
                                else -> {

                                }
                            }
                        })
                }

            }


            binding.returnHeight.extraOption.setOnClickListener {
                binding.returnHeight.showResponseText.visibility = View.VISIBLE
                binding.returnHeight.showResponseText.setText("Please Wait...")
                var value : Double
                if(TextUtils.isEmpty(binding.returnHeight.extraEdittext.text)) {
                    binding.returnHeight.showResponseText.setText("Please enter height")
                    return@setOnClickListener
                } else {
                    value = binding.returnHeight.extraEdittext.text.toString().toDouble()
                }
                    lifecycleScope.launch(Dispatchers.Main) {
                        viewModel.setReturnHeightTest(value)
                            .observeOnce(viewLifecycleOwner, Observer { msg ->
                                when (msg.status) {
                                    Status.SUCCESS -> {
                                        binding.returnHeight.showResponseText.setText(Utils.getColoredText(msg.message.toString(), Constants.SUCCESS))
                                    }
                                    Status.ERROR -> {
                                        binding.returnHeight.showResponseText.setText(Utils.getColoredText(msg.message.toString(), Constants.FAILED))
                                    }
                                    else -> {

                                    }
                                }
                            })
                    }

                }

        binding.locationasHomePoint.extraOption.setOnClickListener {
            binding.locationasHomePoint.showResponseText.visibility = View.VISIBLE
            binding.locationasHomePoint.showResponseText.setText("Please Wait...")
            val lat : Double
            val lon : Double
            if(TextUtils.isEmpty(binding.locationasHomePoint.extraEdittext.text)) {
                binding.locationasHomePoint.showResponseText.setText("Please enter Both values")
                return@setOnClickListener
            }
            if(TextUtils.isEmpty(binding.locationasHomePoint.extraEdittext2.text)) {
                binding.locationasHomePoint.showResponseText.setText("Please enter Both values")
                return@setOnClickListener
            } else {
                lat = binding.locationasHomePoint.extraEdittext.text.toString().toDouble()
                lon = binding.locationasHomePoint.extraEdittext2.text.toString().toDouble()
            }
                lifecycleScope.launch(Dispatchers.Main) {
                    viewModel.setLocationAsHomePointTest(lat,lon)
                        .observeOnce(viewLifecycleOwner, Observer { msg ->
                            when (msg.status) {
                                Status.SUCCESS -> {
                                    binding.locationasHomePoint.showResponseText.setText(Utils.getColoredText(msg.message.toString(), Constants.SUCCESS))
                                }
                                Status.ERROR -> {
                                    binding.locationasHomePoint.showResponseText.setText(Utils.getColoredText(msg.message.toString(), Constants.FAILED))
                                }
                                else -> {

                                }
                            }
                        })
                }

            }


        binding.maxHorizontalSpeed.extraOption.setOnClickListener {
            binding.maxHorizontalSpeed.showResponseText.visibility = View.VISIBLE
            binding.maxHorizontalSpeed.showResponseText.setText("Please Wait...")
            var value : Double
            if(TextUtils.isEmpty(binding.maxHorizontalSpeed.extraEdittext.text)) {
                binding.maxHorizontalSpeed.showResponseText.setText("Please enter value")
                return@setOnClickListener
            } else {
                value = binding.maxHorizontalSpeed.extraEdittext.text.toString().toDouble()
            }
                lifecycleScope.launch(Dispatchers.Main) {
                    viewModel.setMaxHorizontalSpeedTest(value)
                        .observeOnce(viewLifecycleOwner, Observer { msg ->
                            when (msg.status) {
                                Status.SUCCESS -> {
                                    binding.maxHorizontalSpeed.showResponseText.setText(Utils.getColoredText(msg.message.toString(), Constants.SUCCESS))
                                }
                                Status.ERROR -> {
                                    binding.maxHorizontalSpeed.showResponseText.setText(Utils.getColoredText(msg.message.toString(), Constants.FAILED))
                                }
                                else -> {

                                }
                            }
                        })
                }

            }



            binding.beginnerMode.viewBtn.setOnClickListener {
                closeAllExtraOptionLayouts()
                binding.beginnerMode.showResponseText.visibility = View.VISIBLE
                binding.beginnerMode.showResponseText.setText("Please Wait...")
                lifecycleScope.launch(Dispatchers.Main) {
                    viewModel.getBeginnerModeStateTest().observeOnce(viewLifecycleOwner) { msg ->

                        when (msg.status) {
                            Status.SUCCESS -> {
                                binding.beginnerMode.showResponseText.setText(
                                    Utils.getColoredText(
                                        msg.message.toString(),
                                        Constants.SUCCESS
                                    )
                                )
                            }
                            Status.ERROR -> {
                                binding.beginnerMode.showResponseText.setText(
                                    Utils.getColoredText(
                                        msg.message.toString(),
                                        Constants.FAILED
                                    )
                                )
                            }
                            else -> {

                            }
                        }
                    }
                }
            }

        binding.ledLampPilot.extraOption.setOnClickListener {

            binding.ledLampPilot.showResponseText.visibility = View.VISIBLE
            binding.ledLampPilot.showResponseText.setText("Please Wait...")
            lateinit var state : LedPilotLamp

            when (binding.ledLampPilot.extraSpinner.selectedItemPosition) {
                    0 -> state = LedPilotLamp.ALL_OFF
                    1 -> state = LedPilotLamp.BACK_ONLY
                    2 -> state = LedPilotLamp.FRONT_ONLY
                    3 -> state = LedPilotLamp.ALL_ON
                }

            lifecycleScope.launch(Dispatchers.Main) {
                viewModel.setLedPilotLampTest(state)

                    .observeOnce(viewLifecycleOwner, Observer { msg ->

                        when (msg.status) {

                            Status.SUCCESS -> {
                                binding.ledLampPilot.showResponseText.setText(Utils.getColoredText(msg.message.toString(), Constants.SUCCESS))
                            }
                            Status.ERROR -> {
                                binding.ledLampPilot.showResponseText.setText(Utils.getColoredText(msg.message.toString(), Constants.FAILED))
                            }
                            else -> {

                            }
                        }
                    })
            }
        }

            binding.maxHeight.viewBtn.setOnClickListener {
                closeAllExtraOptionLayouts()
                binding.maxHeight.showResponseText.visibility = View.VISIBLE
                binding.maxHeight.showResponseText.setText("Please Wait...")
                lifecycleScope.launch(Dispatchers.Main) {
                    viewModel.getMaxHeightTest().observeOnce(viewLifecycleOwner) { msg ->


                        when (msg.status) {
                            Status.SUCCESS -> {
                                binding.maxHeight.showResponseText.setText(
                                    Utils.getColoredText(
                                        msg.message.toString(),
                                        Constants.SUCCESS
                                    )
                                )
                            }
                            Status.ERROR -> {
                                binding.maxHeight.showResponseText.setText(
                                    Utils.getColoredText(
                                        msg.message.toString(),
                                        Constants.FAILED
                                    )
                                )
                            }
                            else -> {

                            }
                        }
                    }
                }
            }


        binding.maxHeight.viewBtn.setOnClickListener {
            closeAllExtraOptionLayouts()
            binding.maxHeight.showResponseText.visibility = View.VISIBLE
            binding.maxHeight.showResponseText.setText("Please Wait...")
            lifecycleScope.launch(Dispatchers.Main) {
                viewModel.getMaxHeightTest().observeOnce(viewLifecycleOwner) { msg ->


                    when (msg.status) {
                        Status.SUCCESS -> {
                            binding.maxHeight.showResponseText.setText(
                                Utils.getColoredText(
                                    msg.message.toString(),
                                    Constants.SUCCESS
                                )
                            )
                        }
                        Status.ERROR -> {
                            binding.maxHeight.showResponseText.setText(
                                Utils.getColoredText(
                                    msg.message.toString(),
                                    Constants.FAILED
                                )
                            )
                        }
                        else -> {

                        }
                    }
                }
            }
        }

            binding.maxRange.viewBtn.setOnClickListener {
                closeAllExtraOptionLayouts()
                binding.maxRange.showResponseText.visibility = View.VISIBLE
                binding.maxRange.showResponseText.setText("Please Wait...")
                lifecycleScope.launch(Dispatchers.Main) {
                    viewModel.getMaxRangeTest().observeOnce(viewLifecycleOwner) { msg ->


                        when (msg.status) {
                            Status.SUCCESS -> {
                                binding.maxRange.showResponseText.setText(
                                    Utils.getColoredText(
                                        msg.message.toString(),
                                        Constants.SUCCESS
                                    )
                                )
                            }
                            Status.ERROR -> {
                                binding.maxRange.showResponseText.setText(
                                    Utils.getColoredText(
                                        msg.message.toString(),
                                        Constants.FAILED
                                    )
                                )
                            }
                            else -> {

                            }
                        }
                    }
                }
            }

            binding.returnHeight.viewBtn.setOnClickListener {
                closeAllExtraOptionLayouts()
                binding.returnHeight.showResponseText.visibility = View.VISIBLE
                binding.returnHeight.showResponseText.setText("Please Wait...")
                lifecycleScope.launch(Dispatchers.Main) {
                    viewModel.getReturnHeightTest().observeOnce(viewLifecycleOwner) { msg ->


                        when (msg.status) {
                            Status.SUCCESS -> {
                                binding.returnHeight.showResponseText.setText(
                                    Utils.getColoredText(
                                        msg.message.toString(),
                                        Constants.SUCCESS
                                    )
                                )
                            }
                            Status.ERROR -> {
                                binding.returnHeight.showResponseText.setText(
                                    Utils.getColoredText(
                                        msg.message.toString(),
                                        Constants.FAILED
                                    )
                                )
                            }
                            else -> {

                            }
                        }
                    }
                }
            }

            binding.maxHorizontalSpeed.viewBtn.setOnClickListener {
                closeAllExtraOptionLayouts()
                binding.maxHorizontalSpeed.showResponseText.visibility = View.VISIBLE
                binding.maxHorizontalSpeed.showResponseText.setText("Please Wait...")
                lifecycleScope.launch(Dispatchers.Main) {
                    viewModel.getMaxHorizontalSpeedTest().observeOnce(viewLifecycleOwner) { msg ->


                        when (msg.status) {
                            Status.SUCCESS -> {
                                binding.maxHorizontalSpeed.showResponseText.setText(
                                    Utils.getColoredText(
                                        msg.message.toString(),
                                        Constants.SUCCESS
                                    )
                                )
                            }
                            Status.ERROR -> {
                                binding.maxHorizontalSpeed.showResponseText.setText(
                                    Utils.getColoredText(
                                        msg.message.toString(),
                                        Constants.FAILED
                                    )
                                )
                            }
                            else -> {

                            }
                        }
                    }
                }
            }

        binding.ledLampPilot.viewBtn.setOnClickListener {
            closeAllExtraOptionLayouts()
            binding.ledLampPilot.showResponseText.visibility = View.VISIBLE
            binding.ledLampPilot.showResponseText.setText("Please Wait...")
            lifecycleScope.launch(Dispatchers.Main) {
                viewModel.getLedPilotLampTest().observeOnce(viewLifecycleOwner) { msg ->


                    when (msg.status) {
                        Status.SUCCESS -> {
                            binding.ledLampPilot.showResponseText.setText(
                                Utils.getColoredText(
                                    msg.message.toString(),
                                    Constants.SUCCESS
                                )
                            )
                        }
                        Status.ERROR -> {
                            binding.ledLampPilot.showResponseText.setText(
                                Utils.getColoredText(
                                    msg.message.toString(),
                                    Constants.FAILED
                                )
                            )
                        }
                        else -> {

                        }
                    }
                }
            }
        }



            binding.warningListener.viewBtn.setOnClickListener {
                closeAllExtraOptionLayouts()

                binding.warningListener.showResponseText.visibility = View.VISIBLE
                binding.warningListener.showResponseText.setText("Please Wait...")

                viewModel.setWarningListenerTest()
                binding.warningListener.showResponseText.setText(
                    Utils.getColoredText(
                        "Warning Listener set to null",
                        Constants.SUCCESS
                    )
                )
            }

        binding.locationasHomePoint.viewBtn.setOnClickListener {
            closeAllExtraOptionLayouts()

            binding.locationasHomePoint.showResponseText.visibility = View.VISIBLE
            binding.locationasHomePoint.showResponseText.setText("Please Wait...")

            viewModel.setWarningListenerTest()
            binding.locationasHomePoint.showResponseText.setText(
                Utils.getColoredText(
                    "Location for Homepoint set to null",
                    Constants.SUCCESS
                )
            )
        }

        binding.attitudeModeTest.viewBtn.setOnClickListener {
            closeAllExtraOptionLayouts()

            binding.attitudeModeTest.showResponseText.visibility = View.VISIBLE
            binding.attitudeModeTest.showResponseText.setText("Please Wait...")

            viewModel.setWarningListenerTest()
            binding.attitudeModeTest.showResponseText.setText(
                Utils.getColoredText(
                    "Attitude Mode reset to null",
                    Constants.SUCCESS
                )
            )
        }


        }

        private fun closeAllExtraOptionLayouts() {

            binding.beginnerMode.extraOptionParent.visibility = View.GONE
            binding.maxHeight.extraOptionParent.visibility = View.GONE
            binding.maxRange.extraOptionParent.visibility = View.GONE
            binding.returnHeight.extraOptionParent.visibility = View.GONE
            binding.maxHorizontalSpeed.extraOptionParent.visibility = View.GONE
            binding.attitudeModeTest.extraOptionParent.visibility=View.GONE
            binding.locationasHomePoint.extraOptionParent.visibility=View.GONE
            binding.ledLampPilot.extraOptionParent.visibility=View.GONE

            // We should hide both extraOptionLayout and Response text
            binding.beginnerMode.showResponseText.visibility = View.GONE
            binding.maxHeight.showResponseText.visibility = View.GONE
            binding.maxRange.showResponseText.visibility = View.GONE
            binding.returnHeight.showResponseText.visibility = View.GONE
            binding.maxHorizontalSpeed.showResponseText.visibility = View.GONE
            binding.ledLampPilot.showResponseText.visibility = View.GONE
            binding.warningListener.showResponseText.visibility = View.GONE
            binding.attitudeModeTest.showResponseText.visibility = View.GONE
            binding.locationasHomePoint.showResponseText.visibility = View.GONE
            binding.ledLampPilot.showResponseText.visibility = View.GONE


        }

        private fun setSpinnerItems() {

            var spinnerAdapter = ArrayAdapter.createFromResource(
                requireActivity().baseContext,
                R.array.rc_Boolean,
                android.R.layout.simple_spinner_item
            )
            binding.beginnerMode.extraSpinner.adapter = spinnerAdapter
            spinnerAdapter = ArrayAdapter.createFromResource(
                requireActivity().baseContext,
                R.array.rc_rfs,
                android.R.layout.simple_spinner_item
            )
//            binding.maxHeight.extraSpinner.adapter = spinnerAdapter
//            spinnerAdapter = ArrayAdapter.createFromResource(
//                requireActivity().baseContext,
//                R.array.rc_length_unit,
//                android.R.layout.simple_spinner_item
//            )
//            binding.ledLampPilot.extraSpinner.adapter = spinnerAdapter

            spinnerAdapter = ArrayAdapter.createFromResource(
                requireActivity().baseContext,
                R.array.rc_led_pilot_lamp,
                android.R.layout.simple_spinner_item
            )
            binding.ledLampPilot.extraSpinner.adapter = spinnerAdapter
            spinnerAdapter = ArrayAdapter.createFromResource(
                requireActivity().baseContext,
                R.array.rc_Boolean,
                android.R.layout.simple_spinner_item
            )
            binding.attitudeModeTest.extraSpinner.adapter = spinnerAdapter
        }


    }