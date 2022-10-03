package com.android.autelsdk.battery.adapter

import android.content.Context
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView
import com.android.autelsdk.R
import com.android.autelsdk.battery.BatteryViewModel
import com.android.autelsdk.battery.data.ACDataModel
import com.android.autelsdk.databinding.AdManualIndividualItemBinding
import com.android.autelsdk.databinding.ModuleItemBinding
import com.android.autelsdk.util.Constants
import com.android.autelsdk.util.GeneralUtils
import com.example.myapplicationdragonfish.adapter.ModuleViewHolder

class ManualIndividualItemAdapter : RecyclerView.Adapter<ManualIndividualViewHolder>() {
    val moduleList = ArrayList<ACDataModel>()
    lateinit var context: Context
    lateinit var viewModel: BatteryViewModel

    init {
        moduleList.addAll(GeneralUtils.getBatteryManualIndividualArrayList())
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ManualIndividualViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = AdManualIndividualItemBinding.inflate(inflater, parent, false)
        context = parent.context
        return ManualIndividualViewHolder(binding)
    }

    fun hideOtherLayout(holder: ManualIndividualViewHolder) {
        //val view: View = holder.binding.editTextLayout.editTextLayout
        holder.binding.editTextView.visibility = View.GONE
    }

    override fun onBindViewHolder(holder: ManualIndividualViewHolder, position: Int) {
        val item = moduleList[position]
        holder.binding.title.text = item.name
        hideOtherLayout(holder)
        holder.binding.setBtn.setOnClickListener(View.OnClickListener {
            // populate view according to the function
            hideOtherLayout(holder)
            when (item.type) {
                "setBatteryRealTimeDataListener" -> {
                    viewModel.setBatteryRealTimeDataListener()
                }
                "setLowBatteryNotifyThresholdEdt" -> {
                    val editText: View = holder.binding.editTextLayout.editText
                    val button: View = holder.binding.buttonLayout.button

                    holder.binding.editTextView.visibility = View.VISIBLE
                    (editText as EditText).inputType = InputType.TYPE_NUMBER_FLAG_DECIMAL
                    editText.setHint(context.resources.getString(R.string.hintSetLowBatteryNotifyThresholdEdt))

                    button.setOnClickListener(View.OnClickListener {
                        viewModel.setLowBatteryNotifyThresholdEdt(editText!!.text.toString())
                    })
                }
                "setCriticalBatteryNotifyThresholdEdt" -> {
                    //populate corresponding view
                    // viewModel.setCriticalBatteryNotifyThresholdEdt()
                    val editText: View = holder.binding.editTextLayout.editText
                    val button: View = holder.binding.buttonLayout.button

                    holder.binding.editTextView.visibility = View.VISIBLE
                    (editText as EditText).inputType = InputType.TYPE_NUMBER_FLAG_DECIMAL
                    editText.setHint(context.resources.getString(R.string.hintSetCriticalBatteryNotifyThresholdEdt))

                    button.setOnClickListener(View.OnClickListener {
                        viewModel.setCriticalBatteryNotifyThresholdEdt(editText!!.text.toString())
                    })
                }
                "setDischargeDayEdt" -> {
                    //populate corresponding view
                    //  viewModel.setDischargeDayEdt()
                    val editText: View = holder.binding.editTextLayout.editText
                    val button: View = holder.binding.buttonLayout.button

                    holder.binding.editTextView.visibility = View.VISIBLE
                    (editText as EditText).inputType = InputType.TYPE_CLASS_NUMBER
                    editText.setHint(context.resources.getString(R.string.hintSetDischargeDayEdt))

                    button.setOnClickListener(View.OnClickListener {
                        viewModel.setDischargeDayEdt(editText!!.text.toString())
                    })
                }
            }
        })

        holder.binding.viewBtn.setOnClickListener(View.OnClickListener {
            // call get value function here
            hideOtherLayout(holder)
            when (item.type) {
                "getBatteryRealTimeDataListener" -> {
                    viewModel.getBatteryRealTimeDataListener()
                }
                "getLowBatteryNotifyThresholdEdt" -> {
                    viewModel.getLowBatteryNotifyThresholdEdt()
                }
                "getCriticalBatteryNotifyThresholdEdt" -> {
                    viewModel.getCriticalBatteryNotifyThresholdEdt()
                }
                "getDischargeDayEdt" -> {
                    viewModel.getDischargeDayEdt()
                }
            }
        })
    }

    override fun getItemCount(): Int {
        return moduleList.size
    }
}

class ManualIndividualViewHolder(val binding: AdManualIndividualItemBinding) :
    RecyclerView.ViewHolder(binding.root) {
}