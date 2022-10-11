package com.android.autelsdk.mission.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.autelsdk.R
import com.android.autelsdk.battery.BatteryViewModel
import com.android.autelsdk.battery.adapter.AutoTestResultAdapter
import com.android.autelsdk.battery.data.HarnessResult
import com.android.autelsdk.databinding.FragmentAutoTestDebugLogBinding
import com.android.autelsdk.databinding.FragmentDebugLogControlGimbalBinding
import com.android.autelsdk.mission.DFViewModel
import com.android.autelsdk.util.Utils.observeOnce

class AutoTest_MissionFragment : Fragment() {
    private lateinit var binding: FragmentAutoTestDebugLogBinding
    private val viewModel: DFViewModel by activityViewModels()
    val adapter = AutoTestResultAdapter()

    companion object {
        fun newInstance() = AutoTest_MissionFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_auto_test_debug_log,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.testResults.text = ""
        // adapter.setListOfResult(HarnessResult("It may take a while to run the tests.", true))
        binding.resultRecyclerView.layoutManager =
            LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
        binding.resultRecyclerView.adapter = adapter
        runTests()
    }

    private fun startProgress(isToStart: Boolean) {
        if (isToStart) {
            binding.testResults.text = "It may take a while to run the tests."
            binding.progressBar2.visibility = View.VISIBLE
        } else {
            binding.testResults.text = "Test completed..."
            binding.progressBar2.visibility = View.GONE
        }
    }

    private fun runTests() {
        startProgress(true)
        lifecycleScope.launchWhenStarted {
            adapter.setListOfResult(viewModel.setMissionManager())
            adapter.setListOfResult(
                HarnessResult(
                    "resetMissionManager()  Mission Manager is reset now",
                    true
                )
            )

            adapter.setListOfResult(viewModel.setMissionManagerListener())
            adapter.setListOfResult(
                HarnessResult(
                    "resetMissionManagerListener() Mission Manager Listener is reset now",
                    true
                )
            )

            adapter.setListOfResult(viewModel.setBattery())
            adapter.setListOfResult(HarnessResult("resetBattery() Battery is reset now", true))

            adapter.setListOfResult(viewModel.setBatteryManagerListener())
            adapter.setListOfResult(
                HarnessResult(
                    "resetBatteryManagerListener() Battery State Listener is reset now",
                    true
                )
            )

            adapter.setListOfResult(viewModel.setFlyController())
            adapter.setListOfResult(
                HarnessResult(
                    "resetFlyController() Fly Controller is reset now",
                    true
                )
            )

            adapter.setListOfResult(viewModel.setRemoteControllers())
            adapter.setListOfResult(
                HarnessResult(
                    "resetRemoteControllers() Remote Controller is reset now",
                    true
                )
            )

            adapter.setListOfResult(viewModel.getLowBatteryNotifyThreshold())

            adapter.setListOfResult(viewModel.writeMissionTestData())
            adapter.setListOfResult(viewModel.testWaypoint())
            adapter.setListOfResult(viewModel.testMapping())
            adapter.setListOfResult(viewModel.doPrepare())
            adapter.setListOfResult(viewModel.start())
            adapter.setListOfResult(viewModel.pause())
            adapter.setListOfResult(viewModel.cancel())
            adapter.setListOfResult(viewModel.download())

            if (adapter.itemCount > 20)
                startProgress(false)
        }
    }
}