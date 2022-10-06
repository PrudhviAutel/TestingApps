package com.android.autelsdk.codec.fragments

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
        initUi()
        setSpinnerItems()
        handleListeners()
    }

    private fun initUi() {
        binding.startDecode.viewBtn.visibility = View.GONE
        binding.setOverExposure.viewBtn.visibility = View.GONE
        binding.surfaceSizeChanged.viewBtn.visibility = View.GONE
    }

    private fun handleListeners() {
        binding.startDecode.setBtn.setOnClickListener {
            closeAllExtraOptionLayouts()
            binding.startDecode.spinnerEditTextsParent.visibility = View.VISIBLE
        }

        binding.setOverExposure.setBtn.setOnClickListener {
            closeAllExtraOptionLayouts()
            binding.setOverExposure.extraOptionParent.visibility = View.VISIBLE
        }

        binding.surfaceSizeChanged.setBtn.setOnClickListener {
            closeAllExtraOptionLayouts()
            binding.surfaceSizeChanged.extraOptionParent.visibility = View.VISIBLE
        }



        binding.startDecode.saveButton.setOnClickListener {
            closeAllExtraOptionLayouts()
            binding.startDecode.spinnerEditTextsParent.visibility = View.VISIBLE
            binding.startDecode.showResponseText.visibility = View.VISIBLE
            binding.startDecode.showResponseText.setText("Please Wait...")

            var surfacetexture  by Delegates.notNull<SurfaceTexture>()
            var surfacewidth by Delegates.notNull<Int>()
            var surfaceheight by Delegates.notNull<Int>()

            val state =
                    if (0 == binding.startDecode.spinnerEdittextsOption.selectedItemPosition)
                        true
                    else
                        false
            if(TextUtils.isEmpty(binding.startDecode.extraEdittext7.text) || TextUtils.isEmpty(binding.startDecode.extraEdittext6.text) || TextUtils.isEmpty(binding.startDecode.extraEdittext8.text)) {
                binding.startDecode.showResponseText.setText("Please enter values")
                return@setOnClickListener
            } else {
                binding.startDecode.showResponseText.setText("Please Wait...")
                 surfacetexture = SurfaceTexture(binding.startDecode.extraEdittext6.text.toString().toInt())
                 surfacewidth = binding.startDecode.extraEdittext7.text.toString().toInt()
                 surfaceheight =binding.startDecode.extraEdittext8.text.toString().toInt()
            }

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
            var value by Delegates.notNull<Int>()
            val state =
                    if (0 == binding.setOverExposure.extraSpinner.selectedItemPosition)
                        true
                    else
                        false
            if(TextUtils.isEmpty(binding.setOverExposure.extraEdittext.text)) {
                binding.setOverExposure.showResponseText.setText("Please enter values")
                return@setOnClickListener
            } else {
                binding.startDecode.showResponseText.setText("Please Wait...")
                value = binding.setOverExposure.extraEdittext.text.toString().toInt()
            }

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
            if(TextUtils.isEmpty(binding.surfaceSizeChanged.extraEdittext.text) || TextUtils.isEmpty(binding.surfaceSizeChanged.extraEdittext2.text)) {
                binding.surfaceSizeChanged.showResponseText.setText("Please enter values")
                return@setOnClickListener
            } else {
                binding.startDecode.showResponseText.setText("Please Wait...")
                surfacewidth = binding.surfaceSizeChanged.extraEdittext.text.toString().toInt()
                surfaceHeight = binding.surfaceSizeChanged.extraEdittext2.text.toString().toInt()
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

        binding.startDecode.spinnerEditTextsParent.visibility = View.GONE
        binding.setOverExposure.extraOptionParent.visibility = View.GONE
        binding.surfaceSizeChanged.extraOptionParent.visibility = View.GONE
        //binding.setOverExposure.extraOptionParent.visibility=View.GONE

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