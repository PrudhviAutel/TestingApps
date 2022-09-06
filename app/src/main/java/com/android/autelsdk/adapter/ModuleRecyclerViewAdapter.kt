package com.example.myapplicationdragonfish.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.autelsdk.databinding.ModuleItemBinding
import com.android.autelsdk.flyController.FlyControllerActivity
import com.android.autelsdk.flyController.FlyControllerRepositoryImpl
import com.android.autelsdk.remoteController.RemoteControllerActivity
import com.android.autelsdk.util.Constants

//import com.example.myapplicationdragonfish.databinding.ModuleItemBinding

class ModuleRecyclerViewAdapter : RecyclerView.Adapter<ModuleViewHolder>() {


    val moduleList = ArrayList<String>()
    lateinit var context : Context

    init {
        moduleList.add(Constants.RemoteController)
        moduleList.add(Constants.FlyController)
        moduleList.add(Constants.Gimbal)
        moduleList.add(Constants.Codec)
        moduleList.add(Constants.Album)
        moduleList.add(Constants.Dsp)
        moduleList.add(Constants.Mission)
        moduleList.add(Constants.Evo2_Mission)
        moduleList.add(Constants.Battery)
        moduleList.add(Constants.Camera)
        moduleList.add(Constants.RTK)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ModuleViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ModuleItemBinding.inflate(inflater, parent, false)
        context = parent.context
        return ModuleViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ModuleViewHolder, position: Int) {
        val module = moduleList[position]
        holder.binding.textViewModuleName.text = module

        holder.binding.parentLayout.setOnClickListener(View.OnClickListener {
            when (module) {
                Constants.RemoteController -> {
                    context.startActivity(Intent(context, RemoteControllerActivity::class.java))
                }
                Constants.FlyController -> {
                    context.startActivity(Intent(context, FlyControllerActivity::class.java))
                }
            }
        })

    }

    override fun getItemCount(): Int {
        return moduleList.size
    }
}


class ModuleViewHolder(val binding: ModuleItemBinding) : RecyclerView.ViewHolder(binding.root) {
}