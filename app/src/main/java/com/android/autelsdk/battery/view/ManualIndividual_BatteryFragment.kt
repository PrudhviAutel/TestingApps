package com.android.autelsdk.battery.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.autelsdk.R
import com.android.autelsdk.battery.BatteryViewModel
import com.android.autelsdk.battery.adapter.ManualIndividualItemAdapter
import com.android.autelsdk.databinding.AcManualIndividualFragmentBinding
import com.android.autelsdk.util.GeneralUtils

class ManualIndividual_BatteryFragment : Fragment() {
    lateinit var binding: AcManualIndividualFragmentBinding
    lateinit var viewModel: BatteryViewModel
    val adapter = ManualIndividualItemAdapter()

    companion object {
        fun newInstance() = ManualIndividual_BatteryFragment()
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.individualRecyclerView.layoutManager = LinearLayoutManager(activity)
        binding.individualRecyclerView.adapter = adapter
    }

}