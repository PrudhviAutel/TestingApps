package com.android.autelsdk.flyController.fragments

import android.graphics.SurfaceTexture
import android.opengl.GLSurfaceView
import android.os.Bundle
import android.text.Spannable
import android.util.Log
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
import com.android.autelsdk.databinding.FragmentDebugLogControlFcBinding
import com.android.autelsdk.event.ProductConnectEvent
import com.android.autelsdk.flyController.FlyControllerViewModel
import com.android.autelsdk.util.Constants
import com.android.autelsdk.util.Status
import com.android.autelsdk.util.Utils
import com.android.autelsdk.util.Utils.observeOnce
import com.autel.common.flycontroller.LedPilotLamp
import kotlinx.coroutines.runBlocking
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class DebugLogCodecFragment : Fragment() {

    private lateinit var binding: FragmentDebugLogControlFcBinding
    private val viewModel : CodecViewModel by activityViewModels()

    companion object {
        fun newInstance() = DebugLogCodecFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_debug_log_control_fc , container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        runTests()
    }


    fun runTests() {
        lifecycleScope.launchWhenStarted {
            val boolean = arrayListOf<Boolean>(true,false)
            binding.DelayText.visibility = View.VISIBLE
            viewModel.isOverExposureEnabled()
                .observeOnce(this@DebugLogCodecFragment, Observer { msg ->

                    when (msg.status) {
                        Status.SUCCESS -> {
                            val message : Spannable  = Utils.getColoredText(msg.data.toString(),
                                Constants.SUCCESS)
                            binding.testResults.append(message)
                        }
                        Status.ERROR -> {
                            // binding.testResults.append(Utils.getColoredText(msg.message.toString(), Constants.FAILED))
                            val message : Spannable  = Utils.getColoredText(msg.message.toString(),
                                Constants.FAILED)
                            binding.testResults.append(message)

                        }
                        else -> {

                        }
                    }

                })

            viewModel.pause().observeOnce(this@DebugLogCodecFragment, Observer { msg ->
                when (msg.status) {
                    Status.SUCCESS -> {
                        binding.testResults.append(Utils.getColoredText(msg.data.toString(), Constants.SUCCESS))
                    }
                    Status.ERROR -> {
                        binding.testResults.append(Utils.getColoredText(msg.message.toString(), Constants.FAILED))
                    }
                    else -> {

                    }
                }
            })

            viewModel.resume()
                .observeOnce(this@DebugLogCodecFragment, Observer { msg ->

                    when (msg.status) {
                        Status.SUCCESS -> {
                            binding.testResults.append(Utils.getColoredText(msg.data.toString(), Constants.SUCCESS))
                        }
                        Status.ERROR -> {
                            binding.testResults.append(Utils.getColoredText(msg.message.toString(), Constants.FAILED))
                        }
                        else -> {

                        }
                    }

                })

            viewModel.setOnRenderFrameInfoListener().observeOnce(this@DebugLogCodecFragment, Observer { msg ->
                when (msg.status) {
                    Status.SUCCESS -> {
                        binding.testResults.append(Utils.getColoredText(msg.data.toString(), Constants.SUCCESS))
                    }
                    Status.ERROR -> {
                        binding.testResults.append(Utils.getColoredText(msg.message.toString(), Constants.FAILED))
                    }
                    else -> {

                    }
                }
            })
            viewModel.startDecode(SurfaceTexture(0),3,4,true)
                .observeOnce(this@DebugLogCodecFragment, Observer { msg ->

                    when (msg.status) {
                        Status.SUCCESS -> {
                            binding.testResults.append(Utils.getColoredText(msg.data.toString(), Constants.SUCCESS))
                        }
                        Status.ERROR -> {
                            binding.testResults.append(Utils.getColoredText(msg.message.toString(), Constants.FAILED))
                        }
                        else -> {

                        }
                    }

                })
            for(state in boolean) {
                viewModel.setOverExposure(state,3)
                    .observeOnce(this@DebugLogCodecFragment, Observer { msg ->
                        when (msg.status) {
                            Status.SUCCESS -> {
                                binding.testResults.append(
                                    Utils.getColoredText(
                                        msg.data.toString(),
                                        Constants.SUCCESS
                                    )
                                )
                            }
                            Status.ERROR -> {
                                binding.testResults.append(
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

            viewModel.surfaceSizeChanged(3,3)
                .observeOnce(this@DebugLogCodecFragment, Observer { msg ->

                    when (msg.status) {
                        Status.SUCCESS -> {
                            binding.testResults.append(Utils.getColoredText(msg.data.toString(), Constants.SUCCESS))
                        }
                        Status.ERROR -> {
                            binding.testResults.append(Utils.getColoredText(msg.message.toString(), Constants.FAILED))
                        }
                        else -> {

                        }
                    }

                })



        }
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onProductConnectEvent(event: ProductConnectEvent?) {
        if (event != null) {
        }
    }
    }
