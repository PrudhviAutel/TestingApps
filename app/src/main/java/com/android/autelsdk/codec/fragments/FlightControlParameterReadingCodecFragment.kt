package com.android.autelsdk.flyController.fragments

import android.graphics.SurfaceTexture
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
import com.android.autelsdk.codec.CodecViewModel
import com.android.autelsdk.databinding.FragmentFlightControlParameterReadingCodecBinding
import com.android.autelsdk.util.Constants
import com.android.autelsdk.util.Status
import com.android.autelsdk.util.Utils
import com.android.autelsdk.util.Utils.observeOnce
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.properties.Delegates


class FlightControlParameterReadingCodecFragment : Fragment() {

    private lateinit var binding: FragmentFlightControlParameterReadingCodecBinding
    private val viewModel : CodecViewModel by activityViewModels()

    companion object {
        fun newInstance() = FlightControlParameterReadingCodecFragment()
    }


    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_flight_control_parameter_reading_codec,
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
        binding.startDecode.setBtn.setOnClickListener {
            closeAllExtraOptionLayouts()
            binding.startDecode.extraOptionParent.visibility = View.VISIBLE
        }

        binding.setOverExposure.setBtn.setOnClickListener {
            closeAllExtraOptionLayouts()
            binding.setOverExposure.extraOptionParent.visibility = View.VISIBLE
        }

        binding.surfaceSizeChanged.setBtn.setOnClickListener {
            closeAllExtraOptionLayouts()
            binding.surfaceSizeChanged.extraOptionParent.visibility = View.VISIBLE
        }



        binding.startDecode.extraOption.setOnClickListener {
            binding.startDecode.showResponseText.visibility = View.VISIBLE
            binding.startDecode.showResponseText.setText("Please Wait...")

            val state =
                    if (0 == binding.startDecode.spinnerEdittextsOption.selectedItemPosition)
                        true
                    else
                        false
            val surfacetexture = SurfaceTexture(Integer.getInteger(binding.startDecode.extraEdittext.text.toString()))
            val surfacewidth = Integer.getInteger(binding.startDecode.extraEdittext2.text.toString())
            val surfaceheight =Integer.getInteger(binding.startDecode.extraEdittext3.text.toString())
            lifecycleScope.launch(Dispatchers.Main) {
                viewModel.startDecode(surfacetexture,surfacewidth,surfaceheight,state)
                        .observeOnce(viewLifecycleOwner, Observer { msg ->
                            when (msg.status) {
                                Status.SUCCESS -> {
                                    binding.startDecode.showResponseText.setText(Utils.getColoredText(msg.message.toString(), Constants.SUCCESS))
                                }
                                Status.ERROR -> {
                                    binding.startDecode.showResponseText.setText(Utils.getColoredText(msg.message.toString(), Constants.FAILED))
                                }
                                else -> {

                                }
                            }
                        })
            }
        }

        binding.setOverExposure.extraOption.setOnClickListener {
            binding.setOverExposure.showResponseText.visibility = View.VISIBLE
            binding.setOverExposure.showResponseText.setText("Please Wait...")

            val state =
                    if (0 == binding.setOverExposure.extraSpinner.selectedItemPosition)
                        true
                    else
                        false
            val value = Integer.getInteger(binding.setOverExposure.extraEdittext.text.toString())
            lifecycleScope.launch(Dispatchers.Main) {
                viewModel.setOverExposure(state,value)
                        .observeOnce(viewLifecycleOwner, Observer { msg ->
                            when (msg.status) {
                                Status.SUCCESS -> {
                                    binding.setOverExposure.showResponseText.setText(Utils.getColoredText(msg.message.toString(), Constants.SUCCESS))
                                }
                                Status.ERROR -> {
                                    binding.setOverExposure.showResponseText.setText(Utils.getColoredText(msg.message.toString(), Constants.FAILED))
                                }
                                else -> {

                                }
                            }
                        })
            }
        }

        binding.surfaceSizeChanged.extraOption.setOnClickListener {
            binding.surfaceSizeChanged.showResponseText.visibility = View.VISIBLE
            binding.surfaceSizeChanged.showResponseText.setText("Please Wait...")
            var surfacewidth by Delegates.notNull<Int>()
            var surfaceHeight by Delegates.notNull<Int>()
            if(TextUtils.isEmpty(binding.surfaceSizeChanged.extraEdittext.text)) {
                binding.surfaceSizeChanged.showResponseText.setText("Please enter values")
                return@setOnClickListener
            } else {
                surfacewidth = Integer.getInteger(binding.surfaceSizeChanged.extraEdittext.text.toString())
                surfaceHeight = Integer.getInteger(binding.surfaceSizeChanged.extraEdittext2.text.toString())
            }

            //value = Integer.parseInt(binding.rfPower.extraEdittext.text.toString()).toDouble()

            lifecycleScope.launch(Dispatchers.Main) {
                viewModel.surfaceSizeChanged(surfacewidth,surfaceHeight)
                        .observeOnce(viewLifecycleOwner, Observer { msg ->
                            when (msg.status) {
                                Status.SUCCESS -> {
                                    binding.surfaceSizeChanged.showResponseText.setText(Utils.getColoredText(msg.message.toString(), Constants.SUCCESS))
                                }
                                Status.ERROR -> {
                                    binding.surfaceSizeChanged.showResponseText.setText(Utils.getColoredText(msg.message.toString(), Constants.FAILED))
                                }
                                else -> {

                                }
                            }
                        })
            }


//

        }





        binding.startDecode.viewBtn.setOnClickListener {
            closeAllExtraOptionLayouts()
            binding.startDecode.showResponseText.visibility = View.VISIBLE
            binding.startDecode.showResponseText.setText("Please Wait...")
            var state by Delegates.notNull<Boolean>()
            val surfacetexture = SurfaceTexture(0)
            val surfacewidth = 3
            val surfaceHeight = 4
            when (binding.setOverExposure.extraSpinner.selectedItemPosition) {
                0 -> state = true
                1 -> state = false

            }
            lifecycleScope.launch(Dispatchers.Main) {
                viewModel.startDecode(surfacetexture,surfacewidth,surfaceHeight,state).observeOnce(viewLifecycleOwner) { msg ->

                    when (msg.status) {
                        Status.SUCCESS -> {
                            binding.startDecode.showResponseText.setText(
                                    Utils.getColoredText(
                                            msg.message.toString(),
                                            Constants.SUCCESS
                                    )
                            )
                        }
                        Status.ERROR -> {
                            binding.startDecode.showResponseText.setText(
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



        binding.surfaceSizeChanged.viewBtn.setOnClickListener {
            closeAllExtraOptionLayouts()
            binding.surfaceSizeChanged.showResponseText.visibility = View.VISIBLE
            binding.surfaceSizeChanged.showResponseText.setText("Please Wait...")
            val surfacewidth = 4
            val surfaceheight = 5
            lifecycleScope.launch(Dispatchers.Main) {
                viewModel.surfaceSizeChanged(surfacewidth,surfaceheight).observeOnce(viewLifecycleOwner) { msg ->


                    when (msg.status) {
                        Status.SUCCESS -> {
                            binding.surfaceSizeChanged.showResponseText.setText(
                                    Utils.getColoredText(
                                            msg.message.toString(),
                                            Constants.SUCCESS
                                    )
                            )
                        }
                        Status.ERROR -> {
                            binding.surfaceSizeChanged.showResponseText.setText(
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




        binding.setOverExposure.viewBtn.setOnClickListener {
            closeAllExtraOptionLayouts()
            binding.setOverExposure.showResponseText.visibility = View.VISIBLE
            binding.setOverExposure.showResponseText.setText("Please Wait...")
            var state by Delegates.notNull<Boolean>()
            val value = 3
            when (binding.setOverExposure.extraSpinner.selectedItemPosition) {
                0 -> state = true
                1 -> state = false

            }
            lifecycleScope.launch(Dispatchers.Main) {
                viewModel.setOverExposure(state,value).observeOnce(viewLifecycleOwner) { msg ->


                    when (msg.status) {
                        Status.SUCCESS -> {
                            binding.setOverExposure.showResponseText.setText(
                                    Utils.getColoredText(
                                            msg.message.toString(),
                                            Constants.SUCCESS
                                    )
                            )
                        }
                        Status.ERROR -> {
                            binding.setOverExposure.showResponseText.setText(
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





    }

    private fun closeAllExtraOptionLayouts() {

        binding.startDecode.extraOptionParent.visibility = View.GONE
        binding.setOverExposure.extraOptionParent.visibility = View.GONE
        binding.surfaceSizeChanged.extraOptionParent.visibility = View.GONE
        binding.setOverExposure.extraOptionParent.visibility=View.GONE

        // We should hide both extraOptionLayout and Response text
        binding.startDecode.showResponseText.visibility = View.GONE
        binding.surfaceSizeChanged.showResponseText.visibility = View.GONE
        binding.setOverExposure.showResponseText.visibility = View.GONE


    }

    private fun setSpinnerItems() {

        var spinnerAdapter = ArrayAdapter.createFromResource(
                requireActivity().baseContext,
                R.array.rc_Boolean,
                android.R.layout.simple_spinner_item
        )
        binding.setOverExposure.extraSpinner.adapter = spinnerAdapter
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
//            binding.setOverExposure.extraSpinner.adapter = spinnerAdapter

        spinnerAdapter = ArrayAdapter.createFromResource(
                requireActivity().baseContext,
                R.array.rc_Boolean,
                android.R.layout.simple_spinner_item
        )
        binding.setOverExposure.extraSpinner.adapter = spinnerAdapter
        spinnerAdapter = ArrayAdapter.createFromResource(
                requireActivity().baseContext,
                R.array.rc_Boolean,
                android.R.layout.simple_spinner_item
        )
        binding.startDecode.spinnerEdittextsOption.adapter = spinnerAdapter
    }


}