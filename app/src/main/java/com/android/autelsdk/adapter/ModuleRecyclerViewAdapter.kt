package com.example.myapplicationdragonfish.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.autelsdk.databinding.ModuleItemBinding
//import com.example.myapplicationdragonfish.databinding.ModuleItemBinding

class ModuleRecyclerViewAdapter : RecyclerView.Adapter<ModuleViewHolder>() {


    val moduleList = ArrayList<String>()

    init {
        moduleList.add("RemoteController")
        moduleList.add("FlyController")
        moduleList.add("Gimbal")
        moduleList.add("Codec")
        moduleList.add("Album")
        moduleList.add("Dsp")
        moduleList.add("Mission")
        moduleList.add("Evo2 Mission")
        moduleList.add("Battery")
        moduleList.add("Camera")
        moduleList.add("RTK")
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ModuleViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ModuleItemBinding.inflate(inflater, parent, false)
        return ModuleViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ModuleViewHolder, position: Int) {
        val module = moduleList[position]
        holder.binding.textViewModuleName.text = module
    }

    override fun getItemCount(): Int {
        return moduleList.size
    }
}


class ModuleViewHolder(val binding: ModuleItemBinding) : RecyclerView.ViewHolder(binding.root) {
}