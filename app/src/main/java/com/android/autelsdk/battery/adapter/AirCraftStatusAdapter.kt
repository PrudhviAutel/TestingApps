package com.android.autelsdk.battery.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView
import com.android.autelsdk.BaseActivity
import com.android.autelsdk.battery.BatteryViewModel
import com.android.autelsdk.battery.data.ACDataModel
import com.android.autelsdk.battery.data.HarnessResult
import com.android.autelsdk.databinding.SingleTextitemBinding
import com.android.autelsdk.util.GeneralUtils
import com.android.autelsdk.util.Utils.observeOnce
import com.autel.sdk.battery.AutelBattery

class AirCraftStatusAdapter : RecyclerView.Adapter<AirCraftStatusViewHolder>() {
    val moduleList = ArrayList<ACDataModel>()
    lateinit var context: Context
    var viewModel: BatteryViewModel? = null

    init {
        moduleList.addAll(GeneralUtils.getBatteryAirCraftStatusCommandList())
    }

    fun setViewModels(batteryViewModel: ViewModel) {
        viewModel = batteryViewModel as BatteryViewModel
    }

    lateinit var activity: BaseActivity<AutelBattery>
    fun setContext(activity: BaseActivity<AutelBattery>) {
        this.activity = activity
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
                    viewModel?.isLoading?.postValue(true)
                    viewModel?.getDischargeCount()?.observeOnce(activity) {
                        if (it != null)
                            viewModel?.result?.postValue(HarnessResult(it.value,true))
                        else {
                            viewModel?.result?.postValue(HarnessResult("Error in result", false))
                        }
                    }
                }
                "getVersion" -> {
                    viewModel?.isLoading?.postValue(true)
                    viewModel?.getVersion()?.observeOnce(activity) {
                        if (it != null)
                            viewModel?.result?.postValue(it)
                        else {
                            viewModel?.result?.postValue(HarnessResult("Error in result", false))
                        }
                    }
                }
                "getSerialNumber" -> {
                    viewModel?.isLoading?.postValue(true)
                    viewModel?.getSerialNumber()?.observeOnce(activity) {
                        if (it != null)
                            viewModel?.result?.postValue(it)
                        else {
                            viewModel?.result?.postValue(HarnessResult("Error in result", false))
                        }
                    }
                }
                "getFullChargeCapacity" -> {
                    viewModel?.isLoading?.postValue(true)
                    viewModel?.getFullChargeCapacity()?.observeOnce(activity) {
                        if (it != null)
                            viewModel?.result?.postValue(it)
                        else {
                            viewModel?.result?.postValue(HarnessResult("Error in result", false))
                        }
                    }
                }
                "getCellVoltageRange" -> {
                    viewModel?.isLoading?.postValue(true)
                    viewModel?.getCellVoltageRange()?.observeOnce(activity) {
                        if (it != null)
                            viewModel?.result?.postValue(it)
                        else {
                            viewModel?.result?.postValue(HarnessResult("Error in result", false))
                        }
                    }
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

