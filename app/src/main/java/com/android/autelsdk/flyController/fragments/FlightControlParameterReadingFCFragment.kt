package com.android.autelsdk.flyController.fragments

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
import com.android.autelsdk.databinding.FragmentFlightControlParameterReadingFcBinding
import com.android.autelsdk.flyController.FlyControllerViewModel
import com.android.autelsdk.util.Constants
import com.android.autelsdk.util.Status
import com.android.autelsdk.util.Utils
import com.android.autelsdk.util.Utils.observeOnce
import com.autel.common.flycontroller.LedPilotLamp
import com.autel.common.remotecontroller.RemoteControllerParameterUnit
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class FlightControlParameterReadingFCFragment : Fragment() {

    private lateinit var binding: FragmentFlightControlParameterReadingFcBinding
    private val viewModel : FlyControllerViewModel by activityViewModels()

    companion object {
        fun newInstance() = FlightControlParameterReadingFCFragment()
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
        binding.language.setBtn.setOnClickListener {
            closeAllExtraOptionLayouts()
            binding.language.extraOptionParent.visibility = View.VISIBLE
        }

        binding.rfPower.setBtn.setOnClickListener {
            closeAllExtraOptionLayouts()
            binding.rfPower.extraOptionParent.visibility = View.VISIBLE
        }

        binding.parameterUnit.setBtn.setOnClickListener {
            closeAllExtraOptionLayouts()
            binding.parameterUnit.extraOptionParent.visibility = View.VISIBLE
        }

        binding.yawCoefficient.setBtn.setOnClickListener {
            closeAllExtraOptionLayouts()
            binding.yawCoefficient.extraOptionParent.visibility = View.VISIBLE
        }

        binding.maxhorizontalspeed.setBtn.setOnClickListener {
            closeAllExtraOptionLayouts()
            binding.maxhorizontalspeed.extraOptionParent.visibility = View.VISIBLE
        }

        binding.ledlamppilot.setBtn.setOnClickListener {
            closeAllExtraOptionLayouts()
            binding.ledlamppilot.extraOptionParent.visibility = View.VISIBLE
        }

        binding.attitudemodetest.setBtn.setOnClickListener{
            closeAllExtraOptionLayouts()
            binding.attitudemodetest.extraOptionParent.visibility = View.VISIBLE
        }

        binding.locationashomepoint.setBtn.setOnClickListener{
            closeAllExtraOptionLayouts()
            binding.locationashomepoint.extraOptionParent.visibility = View.VISIBLE
        }

        binding.infoDataListener.setBtn.setOnClickListener {
            closeAllExtraOptionLayouts()
            binding.infoDataListener.showResponseText.visibility = View.VISIBLE
            binding.infoDataListener.showResponseText.setText("Please Wait...")
            lifecycleScope.launch(Dispatchers.Main) {
                viewModel.setWarningListenerTest().observeOnce(viewLifecycleOwner) { msg ->
                    when (msg.status) {
                        Status.SUCCESS -> {
                            binding.infoDataListener.showResponseText.setText(
                                Utils.getColoredText(
                                    msg.message.toString(),
                                    Constants.SUCCESS
                                )
                            )
                        }
                        Status.ERROR -> {
                            binding.infoDataListener.showResponseText.setText(
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



        binding.language.extraOption.setOnClickListener {
            binding.language.showResponseText.visibility = View.VISIBLE
            binding.language.showResponseText.setText("Please Wait...")

            val state =
                if (0 == binding.language.extraSpinner.selectedItemPosition)
                    true
                else
                    false
            lifecycleScope.launch(Dispatchers.Main) {
                viewModel.setBeginnerModeStateTest(state)
                    .observeOnce(viewLifecycleOwner, Observer { msg ->
                        when (msg.status) {
                            Status.SUCCESS -> {
                                binding.language.showResponseText.setText(Utils.getColoredText(msg.message.toString(), Constants.SUCCESS))
                            }
                            Status.ERROR -> {
                                binding.language.showResponseText.setText(Utils.getColoredText(msg.message.toString(), Constants.FAILED))
                            }
                            else -> {

                            }
                        }
                    })
            }
        }

        binding.attitudemodetest.extraOption.setOnClickListener {
            binding.attitudemodetest.showResponseText.visibility = View.VISIBLE
            binding.attitudemodetest.showResponseText.setText("Please Wait...")

            val state =
                if (0 == binding.attitudemodetest.extraSpinner.selectedItemPosition)
                    true
                else
                    false
            lifecycleScope.launch(Dispatchers.Main) {
                viewModel.setAttitudeModeEnableTest(state)
                    .observeOnce(viewLifecycleOwner, Observer { msg ->
                        when (msg.status) {
                            Status.SUCCESS -> {
                                binding.attitudemodetest.showResponseText.setText(Utils.getColoredText(msg.message.toString(), Constants.SUCCESS))
                            }
                            Status.ERROR -> {
                                binding.attitudemodetest.showResponseText.setText(Utils.getColoredText(msg.message.toString(), Constants.FAILED))
                            }
                            else -> {

                            }
                        }
                    })
            }
        }

        binding.rfPower.extraOption.setOnClickListener {
            binding.rfPower.showResponseText.visibility = View.VISIBLE
            var value : Double = 0.0
            if(TextUtils.isEmpty(binding.rfPower.extraEdittext.text)) {
                binding.rfPower.showResponseText.setText("Please enter height value")
                return@setOnClickListener
            } else {
                value = binding.rfPower.extraEdittext.text.toString().toDouble()
            }

            binding.rfPower.showResponseText.setText("Please Wait...")
            //value = Integer.parseInt(binding.rfPower.extraEdittext.text.toString()).toDouble()

            lifecycleScope.launch(Dispatchers.Main) {
                viewModel.setMaxHeightTest(value)
                    .observeOnce(viewLifecycleOwner, Observer { msg ->
                        when (msg.status) {
                            Status.SUCCESS -> {
                                binding.rfPower.showResponseText.setText(Utils.getColoredText(msg.message.toString(), Constants.SUCCESS))
                            }
                            Status.ERROR -> {
                                binding.rfPower.showResponseText.setText(Utils.getColoredText(msg.message.toString(), Constants.FAILED))
                            }
                            else -> {

                            }
                        }
                    })
            }


//

        }

        binding.parameterUnit.extraOption.setOnClickListener {
            var value : Double
            if(!binding.parameterUnit.extraEdittext.toString().equals("")){
                binding.parameterUnit.showResponseText.visibility = View.VISIBLE
                binding.parameterUnit.showResponseText.setText("Please Wait...")
                value = Integer.parseInt(binding.parameterUnit.extraEdittext.toString()).toDouble()

                lifecycleScope.launch(Dispatchers.Main) {
                    viewModel.setMaxRangeTest(value)
                        .observeOnce(viewLifecycleOwner, Observer { msg ->
                            when (msg.status) {
                                Status.SUCCESS -> {
                                    binding.parameterUnit.showResponseText.setText(Utils.getColoredText(msg.message.toString(), Constants.SUCCESS))
                                }
                                Status.ERROR -> {
                                    binding.parameterUnit.showResponseText.setText(Utils.getColoredText(msg.message.toString(), Constants.FAILED))
                                }
                                else -> {

                                }
                            }
                        })
                }

            }else{
                Toast.makeText(
                    context,
                    "Please Enter Value To Continue",
                    Toast.LENGTH_LONG
                ).show()


           }
            }

            binding.yawCoefficient.extraOption.setOnClickListener {
                var value : Double
                if(!binding.yawCoefficient.extraEdittext.toString().equals("")){
                    binding.yawCoefficient.showResponseText.visibility = View.VISIBLE
                    binding.yawCoefficient.showResponseText.setText("Please Wait...")
                    value = Integer.parseInt(binding.yawCoefficient.extraEdittext.toString()).toDouble()

                    lifecycleScope.launch(Dispatchers.Main) {
                        viewModel.setReturnHeightTest(value)
                            .observeOnce(viewLifecycleOwner, Observer { msg ->
                                when (msg.status) {
                                    Status.SUCCESS -> {
                                        binding.yawCoefficient.showResponseText.setText(Utils.getColoredText(msg.message.toString(), Constants.SUCCESS))
                                    }
                                    Status.ERROR -> {
                                        binding.yawCoefficient.showResponseText.setText(Utils.getColoredText(msg.message.toString(), Constants.FAILED))
                                    }
                                    else -> {

                                    }
                                }
                            })
                    }

                }else{
                    Toast.makeText(
                        context,
                        "Please Enter Value To Continue",
                        Toast.LENGTH_LONG
                    ).show()


                }
            }

        binding.locationashomepoint.extraOption.setOnClickListener {
            if(!binding.locationashomepoint.extraEdittext.toString().equals("") && !!binding.locationashomepoint.extraEdittext2.toString().equals("")){
                binding.locationashomepoint.showResponseText.visibility = View.VISIBLE
                binding.locationashomepoint.showResponseText.setText("Please Wait...")
                val lat = Integer.parseInt(binding.locationashomepoint.extraEdittext.toString()).toDouble()
                val lon = Integer.parseInt(binding.locationashomepoint.extraEdittext2.toString()).toDouble()

                lifecycleScope.launch(Dispatchers.Main) {
                    viewModel.setLocationAsHomePointTest(lat,lon)
                        .observeOnce(viewLifecycleOwner, Observer { msg ->
                            when (msg.status) {
                                Status.SUCCESS -> {
                                    binding.locationashomepoint.showResponseText.setText(Utils.getColoredText(msg.message.toString(), Constants.SUCCESS))
                                }
                                Status.ERROR -> {
                                    binding.locationashomepoint.showResponseText.setText(Utils.getColoredText(msg.message.toString(), Constants.FAILED))
                                }
                                else -> {

                                }
                            }
                        })
                }

            }else{
                Toast.makeText(
                    context,
                    "Please Enter Value To Continue",
                    Toast.LENGTH_LONG
                ).show()


            }
        }

        binding.maxhorizontalspeed.extraOption.setOnClickListener {
            var value : Double
            if(!binding.maxhorizontalspeed.extraEdittext.toString().equals("")){
                binding.maxhorizontalspeed.showResponseText.visibility = View.VISIBLE
                binding.maxhorizontalspeed.showResponseText.setText("Please Wait...")
                value = Integer.parseInt(binding.maxhorizontalspeed.extraEdittext.toString()).toDouble()

                lifecycleScope.launch(Dispatchers.Main) {
                    viewModel.setMaxHorizontalSpeedTest(value)
                        .observeOnce(viewLifecycleOwner, Observer { msg ->
                            when (msg.status) {
                                Status.SUCCESS -> {
                                    binding.maxhorizontalspeed.showResponseText.setText(Utils.getColoredText(msg.message.toString(), Constants.SUCCESS))
                                }
                                Status.ERROR -> {
                                    binding.maxhorizontalspeed.showResponseText.setText(Utils.getColoredText(msg.message.toString(), Constants.FAILED))
                                }
                                else -> {

                                }
                            }
                        })
                }

            }else{
                Toast.makeText(
                    context,
                    "Please Enter Value To Continue",
                    Toast.LENGTH_LONG
                ).show()


            }
        }



            binding.language.viewBtn.setOnClickListener {
                closeAllExtraOptionLayouts()
                binding.language.showResponseText.visibility = View.VISIBLE
                binding.language.showResponseText.setText("Please Wait...")
                lifecycleScope.launch(Dispatchers.Main) {
                    viewModel.getBeginnerModeStateTest().observeOnce(viewLifecycleOwner) { msg ->

                        when (msg.status) {
                            Status.SUCCESS -> {
                                binding.language.showResponseText.setText(
                                    Utils.getColoredText(
                                        msg.message.toString(),
                                        Constants.SUCCESS
                                    )
                                )
                            }
                            Status.ERROR -> {
                                binding.language.showResponseText.setText(
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

        binding.ledlamppilot.extraOption.setOnClickListener {

            binding.ledlamppilot.showResponseText.visibility = View.VISIBLE
            binding.ledlamppilot.showResponseText.setText("Please Wait...")
            lateinit var state : LedPilotLamp

            when (binding.ledlamppilot.extraSpinner.selectedItemPosition) {
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
                                binding.ledlamppilot.showResponseText.setText(Utils.getColoredText(msg.message.toString(), Constants.SUCCESS))
                            }
                            Status.ERROR -> {
                                binding.ledlamppilot.showResponseText.setText(Utils.getColoredText(msg.message.toString(), Constants.FAILED))
                            }
                            else -> {

                            }
                        }
                    })
            }
        }

            binding.rfPower.viewBtn.setOnClickListener {
                closeAllExtraOptionLayouts()
                binding.rfPower.showResponseText.visibility = View.VISIBLE
                binding.rfPower.showResponseText.setText("Please Wait...")
                lifecycleScope.launch(Dispatchers.Main) {
                    viewModel.getMaxHeightTest().observeOnce(viewLifecycleOwner) { msg ->


                        when (msg.status) {
                            Status.SUCCESS -> {
                                binding.rfPower.showResponseText.setText(
                                    Utils.getColoredText(
                                        msg.message.toString(),
                                        Constants.SUCCESS
                                    )
                                )
                            }
                            Status.ERROR -> {
                                binding.rfPower.showResponseText.setText(
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


        binding.rfPower.viewBtn.setOnClickListener {
            closeAllExtraOptionLayouts()
            binding.rfPower.showResponseText.visibility = View.VISIBLE
            binding.rfPower.showResponseText.setText("Please Wait...")
            lifecycleScope.launch(Dispatchers.Main) {
                viewModel.getMaxHeightTest().observeOnce(viewLifecycleOwner) { msg ->


                    when (msg.status) {
                        Status.SUCCESS -> {
                            binding.rfPower.showResponseText.setText(
                                Utils.getColoredText(
                                    msg.message.toString(),
                                    Constants.SUCCESS
                                )
                            )
                        }
                        Status.ERROR -> {
                            binding.rfPower.showResponseText.setText(
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

            binding.parameterUnit.viewBtn.setOnClickListener {
                closeAllExtraOptionLayouts()
                binding.parameterUnit.showResponseText.visibility = View.VISIBLE
                binding.parameterUnit.showResponseText.setText("Please Wait...")
                lifecycleScope.launch(Dispatchers.Main) {
                    viewModel.getMaxRangeTest().observeOnce(viewLifecycleOwner) { msg ->


                        when (msg.status) {
                            Status.SUCCESS -> {
                                binding.parameterUnit.showResponseText.setText(
                                    Utils.getColoredText(
                                        msg.message.toString(),
                                        Constants.SUCCESS
                                    )
                                )
                            }
                            Status.ERROR -> {
                                binding.parameterUnit.showResponseText.setText(
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

            binding.yawCoefficient.viewBtn.setOnClickListener {
                closeAllExtraOptionLayouts()
                binding.yawCoefficient.showResponseText.visibility = View.VISIBLE
                binding.yawCoefficient.showResponseText.setText("Please Wait...")
                lifecycleScope.launch(Dispatchers.Main) {
                    viewModel.getReturnHeightTest().observeOnce(viewLifecycleOwner) { msg ->


                        when (msg.status) {
                            Status.SUCCESS -> {
                                binding.yawCoefficient.showResponseText.setText(
                                    Utils.getColoredText(
                                        msg.message.toString(),
                                        Constants.SUCCESS
                                    )
                                )
                            }
                            Status.ERROR -> {
                                binding.yawCoefficient.showResponseText.setText(
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

            binding.maxhorizontalspeed.viewBtn.setOnClickListener {
                closeAllExtraOptionLayouts()
                binding.maxhorizontalspeed.showResponseText.visibility = View.VISIBLE
                binding.maxhorizontalspeed.showResponseText.setText("Please Wait...")
                lifecycleScope.launch(Dispatchers.Main) {
                    viewModel.getMaxHorizontalSpeedTest().observeOnce(viewLifecycleOwner) { msg ->


                        when (msg.status) {
                            Status.SUCCESS -> {
                                binding.maxhorizontalspeed.showResponseText.setText(
                                    Utils.getColoredText(
                                        msg.message.toString(),
                                        Constants.SUCCESS
                                    )
                                )
                            }
                            Status.ERROR -> {
                                binding.maxhorizontalspeed.showResponseText.setText(
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

        binding.ledlamppilot.viewBtn.setOnClickListener {
            closeAllExtraOptionLayouts()
            binding.ledlamppilot.showResponseText.visibility = View.VISIBLE
            binding.ledlamppilot.showResponseText.setText("Please Wait...")
            lifecycleScope.launch(Dispatchers.Main) {
                viewModel.getLedPilotLampTest().observeOnce(viewLifecycleOwner) { msg ->


                    when (msg.status) {
                        Status.SUCCESS -> {
                            binding.ledlamppilot.showResponseText.setText(
                                Utils.getColoredText(
                                    msg.message.toString(),
                                    Constants.SUCCESS
                                )
                            )
                        }
                        Status.ERROR -> {
                            binding.ledlamppilot.showResponseText.setText(
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



            binding.infoDataListener.viewBtn.setOnClickListener {
                closeAllExtraOptionLayouts()

                binding.infoDataListener.showResponseText.visibility = View.VISIBLE
                binding.infoDataListener.showResponseText.setText("Please Wait...")

                viewModel.setWarningListenerTest()
                binding.infoDataListener.showResponseText.setText(
                    Utils.getColoredText(
                        "Info Data Listener set to null",
                        Constants.SUCCESS
                    )
                )
            }

        binding.locationashomepoint.viewBtn.setOnClickListener {
            closeAllExtraOptionLayouts()

            binding.locationashomepoint.showResponseText.visibility = View.VISIBLE
            binding.locationashomepoint.showResponseText.setText("Please Wait...")

            viewModel.setWarningListenerTest()
            binding.locationashomepoint.showResponseText.setText(
                Utils.getColoredText(
                    "Location set to null",
                    Constants.SUCCESS
                )
            )
        }

        binding.attitudemodetest.viewBtn.setOnClickListener {
            closeAllExtraOptionLayouts()

            binding.attitudemodetest.showResponseText.visibility = View.VISIBLE
            binding.attitudemodetest.showResponseText.setText("Please Wait...")

            viewModel.setWarningListenerTest()
            binding.attitudemodetest.showResponseText.setText(
                Utils.getColoredText(
                    "attitude reset to null",
                    Constants.SUCCESS
                )
            )
        }


        }

        private fun closeAllExtraOptionLayouts() {

            binding.language.extraOptionParent.visibility = View.GONE
            binding.rfPower.extraOptionParent.visibility = View.GONE
            binding.parameterUnit.extraOptionParent.visibility = View.GONE
            binding.yawCoefficient.extraOptionParent.visibility = View.GONE
            binding.maxhorizontalspeed.extraOptionParent.visibility = View.GONE
            binding.attitudemodetest.extraOptionParent.visibility=View.GONE
            binding.locationashomepoint.extraOptionParent.visibility=View.GONE
            binding.ledlamppilot.extraOptionParent.visibility=View.GONE

            // We should hide both extraOptionLayout and Response text
            binding.language.showResponseText.visibility = View.GONE
            binding.rfPower.showResponseText.visibility = View.GONE
            binding.parameterUnit.showResponseText.visibility = View.GONE
            binding.yawCoefficient.showResponseText.visibility = View.GONE
            binding.maxhorizontalspeed.showResponseText.visibility = View.GONE
            binding.ledlamppilot.showResponseText.visibility = View.GONE
            binding.infoDataListener.showResponseText.visibility = View.GONE
            binding.attitudemodetest.showResponseText.visibility = View.GONE
            binding.locationashomepoint.showResponseText.visibility = View.GONE
            binding.ledlamppilot.showResponseText.visibility = View.GONE


        }

        private fun setSpinnerItems() {

            var spinnerAdapter = ArrayAdapter.createFromResource(
                requireActivity().baseContext,
                R.array.rc_Boolean,
                android.R.layout.simple_spinner_item
            )
            binding.language.extraSpinner.adapter = spinnerAdapter
            spinnerAdapter = ArrayAdapter.createFromResource(
                requireActivity().baseContext,
                R.array.rc_rfs,
                android.R.layout.simple_spinner_item
            )
            binding.rfPower.extraSpinner.adapter = spinnerAdapter
            spinnerAdapter = ArrayAdapter.createFromResource(
                requireActivity().baseContext,
                R.array.rc_length_unit,
                android.R.layout.simple_spinner_item
            )
            binding.parameterUnit.extraSpinner.adapter = spinnerAdapter

            spinnerAdapter = ArrayAdapter.createFromResource(
                requireActivity().baseContext,
                R.array.rc_led_pilot_lamp,
                android.R.layout.simple_spinner_item
            )
            binding.ledlamppilot.extraSpinner.adapter = spinnerAdapter
            spinnerAdapter = ArrayAdapter.createFromResource(
                requireActivity().baseContext,
                R.array.rc_Boolean,
                android.R.layout.simple_spinner_item
            )
            binding.attitudemodetest.extraSpinner.adapter = spinnerAdapter
        }


    }