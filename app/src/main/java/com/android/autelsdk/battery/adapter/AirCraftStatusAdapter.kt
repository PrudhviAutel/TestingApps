package com.android.autelsdk.battery.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView
import com.android.autelsdk.battery.BatteryViewModel
import com.android.autelsdk.battery.data.ACDataModel
import com.android.autelsdk.databinding.SingleTextitemBinding
import com.android.autelsdk.util.GeneralUtils

class AirCraftStatusAdapter : RecyclerView.Adapter<AirCraftStatusViewHolder>() {
    val moduleList = ArrayList<ACDataModel>()
    lateinit var context: Context
    lateinit var viewModel: BatteryViewModel

    init {
        moduleList.addAll(GeneralUtils.getBatteryAirCraftStatusCommandList())
    }

    fun setViewModel(batteryViewModel: ViewModel) {
        viewModel = batteryViewModel as BatteryViewModel
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AirCraftStatusViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = SingleTextitemBinding.inflate(inflater, parent, false)
        context = parent.context
        return AirCraftStatusViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AirCraftStatusViewHolder, position: Int) {
        val item = moduleList[position]
        holder.binding.getButton.text = item.name

        holder.binding.getButton.setOnClickListener(View.OnClickListener {
            when (item.type) {
                "getDischargeCount" -> {
                    viewModel.getDischargeCount()
                }
                "getVersion" -> {
                    viewModel.getVersion()
                }
                "getSerialNumber" -> {
                    viewModel.getSerialNumber()
                }
                "getFullChargeCapacity" -> {
                    viewModel.getFullChargeCapacity()
                }
                "getCellVoltageRange" -> {
                    viewModel.getCellVoltageRange()
                }
            }
        })
    }

    override fun getItemCount(): Int {
        return moduleList.size
    }
}


class AirCraftStatusViewHolder(val binding: SingleTextitemBinding) :
    RecyclerView.ViewHolder(binding.root) {
}

