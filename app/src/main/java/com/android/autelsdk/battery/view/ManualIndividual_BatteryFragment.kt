package com.android.autelsdk.battery.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.autelsdk.BaseActivity
import com.android.autelsdk.battery.BatteryViewModel
import com.android.autelsdk.battery.adapter.ManualIndividualItemAdapter
import com.android.autelsdk.databinding.AcManualIndividualFragmentBinding
import com.autel.sdk.battery.AutelBattery

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
        adapter.setContext(activity as BaseActivity<AutelBattery>)
        adapter.setViewModels(viewModel)
        binding.individualRecyclerView.adapter = adapter
    }

}