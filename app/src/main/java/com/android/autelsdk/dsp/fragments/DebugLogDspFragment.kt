package com.android.autelsdk.dsp.fragments

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
import com.android.autelsdk.databinding.FragmentDebugLogControlDspBinding
import com.android.autelsdk.databinding.FragmentDebugLogControlGimbalBinding
import com.android.autelsdk.dsp.DspViewModel
import com.android.autelsdk.gimbal.GimbalViewModel
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
import com.autel.common.gimbal.GimbalAxisType
import com.autel.common.gimbal.GimbalWorkMode
import kotlinx.coroutines.runBlocking

class DebugLogDspFragment : Fragment() {

    private lateinit var binding: FragmentDebugLogControlDspBinding
    private val viewModel : DspViewModel by activityViewModels()
    private var rfDataList : List<RFData> = ArrayList()

    companion object {
        fun newInstance() = DebugLogDspFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_debug_log_control_dsp , container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.testResults.setText("")
        runTests()
    }

    fun runTests() {

        binding.testResults.setText("It may take a while to run the tests. \n\n")
        runBlocking {

            viewModel.getRFDataListTest(3)
                .observe(viewLifecycleOwner, Observer { msg ->

                    when (msg.status) {
                        Status.SUCCESS -> {
                            rfDataList = msg.data?:ArrayList()
                            binding.testResults.append(Utils.getColoredText(msg.message.toString(), Constants.SUCCESS))
                        }
                        Status.ERROR -> {
                            binding.testResults.append(Utils.getColoredText(msg.message.toString(), Constants.FAILED))
                        }
                        else -> {

                        }
                    }

                })


            for (rfData in rfDataList) {
                viewModel.setCurrentRFDataTest(rfData.value, 3)
                    .observe(viewLifecycleOwner, Observer { msg ->

                        when (msg.status) {
                            Status.SUCCESS -> {
                                binding.testResults.append(Utils.getColoredText(msg.message.toString(), Constants.SUCCESS))
                            }
                            Status.ERROR -> {
                                binding.testResults.append(Utils.getColoredText(msg.message.toString(), Constants.FAILED))
                            }
                            else -> {

                            }
                        }

                    })

                viewModel.getCurrentRFDataTest(3)
                    .observe(viewLifecycleOwner, Observer { msg ->

                        when (msg.status) {
                            Status.SUCCESS -> {
                                binding.testResults.append(Utils.getColoredText(msg.message.toString(), Constants.SUCCESS))
                            }
                            Status.ERROR -> {
                                binding.testResults.append(Utils.getColoredText(msg.message.toString(), Constants.FAILED))
                            }
                            else -> {

                            }
                        }

                    })
            }

            for (appAction in AppAction.values()){
                for (appActionParam in AppActionParam.values()) {
                    viewModel.setSynMsgBroadcastTest(appAction, appActionParam)
                        .observe(viewLifecycleOwner, Observer { msg ->

                            when (msg.status) {
                                Status.SUCCESS -> {
                                    binding.testResults.append(Utils.getColoredText(msg.message.toString(), Constants.SUCCESS))
                                }
                                Status.ERROR -> {
                                    binding.testResults.append(Utils.getColoredText(msg.message.toString(), Constants.FAILED))
                                }
                                else -> {

                                }
                            }

                        })

                    viewModel.setSynMsgBroadcastListenerTest()
                        .observe(viewLifecycleOwner, Observer { msg ->

                            when (msg.status) {
                                Status.SUCCESS -> {
                                    binding.testResults.append(Utils.getColoredText(msg.message.toString(), Constants.SUCCESS))
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

            viewModel.setDspInfoListenerTest()
                .observe(viewLifecycleOwner, Observer { msg ->

                    when (msg.status) {
                        Status.SUCCESS -> {
                            binding.testResults.append(Utils.getColoredText(msg.message.toString(), Constants.SUCCESS))
                        }
                        Status.ERROR -> {
                            binding.testResults.append(Utils.getColoredText(msg.message.toString(), Constants.FAILED))
                        }
                        else -> {

                        }
                    }

                })

            for (bandmode in BandMode.values()) {
                for (bandwidth in Bandwidth.values()) {
                    viewModel.setBandwidthInfoTest(bandmode, bandwidth)
                        .observe(viewLifecycleOwner, Observer { msg ->

                            when (msg.status) {
                                Status.SUCCESS -> {
                                    binding.testResults.append(Utils.getColoredText(msg.message.toString(), Constants.SUCCESS))
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

            for (transferMode in TransferMode.values()) {
                viewModel.setTransferModeTest(transferMode)
                    .observe(viewLifecycleOwner, Observer { msg ->

                        when (msg.status) {
                            Status.SUCCESS -> {
                                binding.testResults.append(Utils.getColoredText(msg.message.toString(), Constants.SUCCESS))
                            }
                            Status.ERROR -> {
                                binding.testResults.append(Utils.getColoredText(msg.message.toString(), Constants.FAILED))
                            }
                            else -> {

                            }
                        }

                    })

                viewModel.getTransferModeTest()
                    .observe(viewLifecycleOwner, Observer { msg ->

                        when (msg.status) {
                            Status.SUCCESS -> {
                                binding.testResults.append(Utils.getColoredText(msg.message.toString(), Constants.SUCCESS))
                            }
                            Status.ERROR -> {
                                binding.testResults.append(Utils.getColoredText(msg.message.toString(), Constants.FAILED))
                            }
                            else -> {

                            }
                        }

                    })
            }

            for (state in listOf(true,false)) {
                viewModel.setVideoLinkStateTest(state)
                    .observe(viewLifecycleOwner, Observer { msg ->

                        when (msg.status) {
                            Status.SUCCESS -> {
                                binding.testResults.append(Utils.getColoredText(msg.message.toString(), Constants.SUCCESS))
                            }
                            Status.ERROR -> {
                                binding.testResults.append(Utils.getColoredText(msg.message.toString(), Constants.FAILED))
                            }
                            else -> {

                            }
                        }

                    })
            }

            for (state in listOf(true,false)) {
                viewModel.setBaseStationEnableTest(state)
                    .observe(viewLifecycleOwner, Observer { msg ->

                        when (msg.status) {
                            Status.SUCCESS -> {
                                binding.testResults.append(Utils.getColoredText(msg.message.toString(), Constants.SUCCESS))
                            }
                            Status.ERROR -> {
                                binding.testResults.append(Utils.getColoredText(msg.message.toString(), Constants.FAILED))
                            }
                            else -> {

                            }
                        }

                    })

                viewModel.isBaseStationEnableTest()
                    .observe(viewLifecycleOwner, Observer { msg ->

                        when (msg.status) {
                            Status.SUCCESS -> {
                                binding.testResults.append(Utils.getColoredText(msg.message.toString(), Constants.SUCCESS))
                            }
                            Status.ERROR -> {
                                binding.testResults.append(Utils.getColoredText(msg.message.toString(), Constants.FAILED))
                            }
                            else -> {

                            }
                        }

                    })
            }

            viewModel.getDeviceVersionInfoTest()
                .observe(viewLifecycleOwner, Observer { msg ->

                    when (msg.status) {
                        Status.SUCCESS -> {
                            binding.testResults.append(Utils.getColoredText(msg.message.toString(), Constants.SUCCESS))
                        }
                        Status.ERROR -> {
                            binding.testResults.append(Utils.getColoredText(msg.message.toString(), Constants.FAILED))
                        }
                        else -> {

                        }
                    }

                })


            viewModel.getVersionInfoTest( )
                .observe(viewLifecycleOwner, Observer { msg ->

                    when (msg.status) {
                        Status.SUCCESS -> {
                            binding.testResults.append(Utils.getColoredText(msg.message.toString(), Constants.SUCCESS))
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


}