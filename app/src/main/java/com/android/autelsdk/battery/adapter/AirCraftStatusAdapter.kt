package com.android.autelsdk.battery.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.autelsdk.databinding.SingleTextitemBinding
import com.android.autelsdk.util.GeneralUtils

class AirCraftStatusAdapter : RecyclerView.Adapter<AirCraftStatusViewHolder>() {
    val moduleList = ArrayList<String>()
    lateinit var context: Context

    init {
        moduleList.addAll(GeneralUtils.getACStatusCommandForBattery())
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AirCraftStatusViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = SingleTextitemBinding.inflate(inflater, parent, false)
        context = parent.context
        return AirCraftStatusViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AirCraftStatusViewHolder, position: Int) {
        val item = moduleList[position]
        holder.binding.getButton.text = item
    }

    override fun getItemCount(): Int {
        return moduleList.size
    }
}


class AirCraftStatusViewHolder(val binding: SingleTextitemBinding) :
    RecyclerView.ViewHolder(binding.root) {
}

