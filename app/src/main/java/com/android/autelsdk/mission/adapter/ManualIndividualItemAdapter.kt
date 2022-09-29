package com.android.autelsdk.mission.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.autelsdk.databinding.AdManualIndividualItemBinding
import com.android.autelsdk.databinding.ModuleItemBinding
import com.android.autelsdk.util.Constants
import com.android.autelsdk.util.GeneralUtils
import com.example.myapplicationdragonfish.adapter.ModuleViewHolder

class ManualIndividualItemAdapter : RecyclerView.Adapter<ManualIndividualViewHolder>() {
    val moduleList = ArrayList<String>()
    lateinit var context: Context

    init {
        moduleList.addAll(GeneralUtils.getBatteryManualIndividualArrayList())
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ManualIndividualViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = AdManualIndividualItemBinding.inflate(inflater, parent, false)
        context = parent.context
        return ManualIndividualViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ManualIndividualViewHolder, position: Int) {
        val item = moduleList[position]
        holder.binding.title.text = item

        holder.binding.setBtn.setOnClickListener(View.OnClickListener {
            // populate view according to the function
        })

        holder.binding.viewBtn.setOnClickListener(View.OnClickListener {

        })
    }

    override fun getItemCount(): Int {
        return moduleList.size
    }
}

class ManualIndividualViewHolder(val binding: AdManualIndividualItemBinding) :
    RecyclerView.ViewHolder(binding.root) {
}