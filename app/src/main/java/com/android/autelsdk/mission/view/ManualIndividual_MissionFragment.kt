package com.android.autelsdk.mission.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.autelsdk.battery.BatteryViewModel
import com.android.autelsdk.battery.adapter.ManualIndividualItemAdapter
import com.android.autelsdk.databinding.AcManualIndividualFragmentBinding

class ManualIndividual_MissionFragment : Fragment() {
    lateinit var binding: AcManualIndividualFragmentBinding
    lateinit var viewModel: BatteryViewModel

    companion object {
        fun newInstance() = ManualIndividual_MissionFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = AcManualIndividualFragmentBinding.inflate(layoutInflater, container, false)
        viewModel = ViewModelProvider(this).get(BatteryViewModel::class.java)
        return binding.root
    }

    val adapter = ManualIndividualItemAdapter()
    fun initData() {
        binding.individualRecyclerView.layoutManager = LinearLayoutManager(activity)
        binding.individualRecyclerView.adapter = adapter
    }
}