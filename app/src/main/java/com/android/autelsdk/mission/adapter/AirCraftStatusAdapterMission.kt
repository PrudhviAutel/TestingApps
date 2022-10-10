package com.android.autelsdk.mission.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.autelsdk.battery.data.ACDataModel
import com.android.autelsdk.databinding.SingleTextitemBinding
import com.android.autelsdk.util.GeneralUtils

class AirCraftStatusAdapterMission : RecyclerView.Adapter<AirCraftStatusViewHolder>() {
    val moduleList = ArrayList<ACDataModel>()
    lateinit var context: Context

    init {
        moduleList.addAll(GeneralUtils.getMissionAirCraftStatusCommandList())
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
    }

    override fun getItemCount(): Int {
        return moduleList.size
    }
}


class AirCraftStatusViewHolder(val binding: SingleTextitemBinding) :
    RecyclerView.ViewHolder(binding.root) {
}

