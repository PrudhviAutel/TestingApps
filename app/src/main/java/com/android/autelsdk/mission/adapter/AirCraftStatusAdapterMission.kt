package com.android.autelsdk.mission.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.autelsdk.battery.data.ACDataModel
import com.android.autelsdk.battery.data.HarnessResult
import com.android.autelsdk.databinding.SingleTextitemBinding
import com.android.autelsdk.mission.DFViewModel
import com.android.autelsdk.util.GeneralUtils

class AirCraftStatusAdapterMission : RecyclerView.Adapter<AirCraftStatusViewHolder>() {
    val moduleList = ArrayList<ACDataModel>()
    lateinit var context: Context

    init {
        moduleList.addAll(GeneralUtils.getMissionAirCraftStatusCommandList())
    }

    lateinit var viewModel: DFViewModel
    fun setViewModels(viewModel: DFViewModel) {
        this.viewModel = viewModel
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

        holder.binding.getButton.setOnClickListener() {
            var harnessResult: HarnessResult
            if (viewModel != null)
                when (item.type) {
                    "writeMissionTestData" -> {
                        viewModel.isLoading.postValue(true)
                        harnessResult = viewModel.writeMissionTestData()
                        viewModel.result.postValue(harnessResult)
                    }
                    "testWayPoint" -> {
                        viewModel.isLoading.postValue(true)
                        harnessResult = viewModel.testWaypoint()
                        viewModel.result.postValue(harnessResult)
                    }
                    "testMap" -> {
                        viewModel.isLoading.postValue(true)
                        harnessResult = viewModel.testMapping()
                        viewModel.result.postValue(harnessResult)
                    }
                    /*"autoCheck" -> {
                        viewModel.autoCheck()
                    }*/
                    "prepare" -> {
                        viewModel.isLoading.postValue(true)
                        harnessResult = viewModel.doPrepare()
                        viewModel.result.postValue(harnessResult)
                    }
                    "start" -> {
                        viewModel.isLoading.postValue(true)
                        harnessResult = viewModel.start()
                        viewModel.result.postValue(harnessResult)
                    }
                    "pause" -> {
                        viewModel.isLoading.postValue(true)
                        harnessResult = viewModel.pause()
                        viewModel.result.postValue(harnessResult)
                    }/*
            "continue" -> {
                viewModel.con
            }*/
                    "cancel" -> {
                        viewModel.isLoading.postValue(true)
                        harnessResult = viewModel.cancel()
                        viewModel.result.postValue(harnessResult)
                    }
                    "download" -> {
                        viewModel.isLoading.postValue(true)
                        harnessResult = viewModel.download()
                        viewModel.result.postValue(harnessResult)
                    }
                }
        }
    }

    override fun getItemCount(): Int {
        return moduleList.size
    }
}


class AirCraftStatusViewHolder(val binding: SingleTextitemBinding) :
    RecyclerView.ViewHolder(binding.root) {
}

