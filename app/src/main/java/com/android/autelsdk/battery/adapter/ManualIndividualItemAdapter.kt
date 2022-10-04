package com.android.autelsdk.battery.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.text.InputType
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.android.autelsdk.BaseActivity
import com.android.autelsdk.R
import com.android.autelsdk.battery.BatteryViewModel
import com.android.autelsdk.battery.data.ACDataModel
import com.android.autelsdk.battery.data.HarnessResult
import com.android.autelsdk.databinding.AdManualIndividualItemBinding
import com.android.autelsdk.util.GeneralUtils
import com.autel.sdk.battery.AutelBattery

class ManualIndividualItemAdapter() : RecyclerView.Adapter<ManualIndividualViewHolder>() {
    val moduleList = ArrayList<ACDataModel>()
    lateinit var context: Context
    var viewModel: BatteryViewModel? = null

    init {
        moduleList.addAll(GeneralUtils.getBatteryManualIndividualArrayList())
    }

    lateinit var activity: BaseActivity<AutelBattery>
    fun setContext(activity: BaseActivity<AutelBattery>) {
        this.activity = activity
    }

    fun setViewModels(viewModel: BatteryViewModel) {
        this.viewModel = viewModel
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ManualIndividualViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = AdManualIndividualItemBinding.inflate(inflater, parent, false)
        context = parent.context
        return ManualIndividualViewHolder(binding)
    }

    fun hideOtherLayout(holder: ManualIndividualViewHolder) {
        //val view: View = holder.binding.editTextLayout.editTextLayout
        holder.binding.editTextViewRelative.visibility = View.GONE
        holder.binding.textViewDisplayResult.visibility = View.GONE
    }

    fun hideOtherFun(holder: ManualIndividualViewHolder, position: Int) {
        for (item in moduleList.indices)
            if (item != position)
                holder.binding.editTextViewRelative.visibility = View.GONE
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ManualIndividualViewHolder, position: Int) {
        val item = moduleList[position]
        holder.binding.title.text = item.name
        val editText: View = holder.binding.editTextLayouts.editText
        val button: View = holder.binding.buttonLayout.button
        hideOtherLayout(holder)
        if (item.type.equals("setBatteryRealTimeDataListener")) {
            holder.binding.setBtn.text = "Set"
            holder.binding.viewBtn.text = "Reset"
        }
        holder.binding.setBtn.setOnClickListener {

            when (item.type) {
                "setBatteryRealTimeDataListener" -> {
                    setLoadingResult(holder)
                    val result = viewModel?.setBatteryRealTimeDataListener()
                    displayResult(holder, result)
                }
                "setLowBatteryNotifyThresholdEdt" -> {
                    setEditLayout(holder, editText, item)
                    button.setOnClickListener {
                        setLoadingResult(holder)
                        displayResult(
                            holder,
                            viewModel?.setLowBatteryNotifyThresholdEdt((editText as EditText).text.toString())
                        )
                    }
                }
                "setCriticalBatteryNotifyThresholdEdt" -> {
                    setEditLayout(holder, editText, item)
                    button.setOnClickListener {
                        setLoadingResult(holder)
                        displayResult(
                            holder,
                            viewModel?.setCriticalBatteryNotifyThresholdEdt((editText as EditText).text.toString())
                        )
                    }
                }
                "setDischargeDayEdt" -> {
                    setEditLayout(holder, editText, item)
                    button.setOnClickListener {
                        setLoadingResult(holder)
                        displayResult(
                            holder,
                            viewModel?.setDischargeDayEdt((editText as EditText).text.toString())
                        )
                    }
                }
            }
        }

        holder.binding.viewBtn.setOnClickListener {
            // call get value function here
            when (item.type) {
                "setBatteryRealTimeDataListener" -> {
                    setLoadingResult(holder)
                    val result = viewModel?.resetBatteryRealTimeDataListener()
                    displayResult(holder, result)
                }
                "setLowBatteryNotifyThresholdEdt" -> {
                    setLoadingResult(holder)
                    displayResult(holder, viewModel?.getLowBatteryNotifyThresholdEdt())
                }
                "setCriticalBatteryNotifyThresholdEdt" -> {
                    setLoadingResult(holder)
                    displayResult(holder, viewModel?.getCriticalBatteryNotifyThresholdEdt())
                }
                "setDischargeDayEdt" -> {
                    setLoadingResult(holder)
                    displayResult(holder, viewModel?.getDischargeDayEdt())
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return moduleList.size
    }

    private fun hideByType(
        selectedItem: String,
        holder: ManualIndividualViewHolder
    ) {
        for (i in 0 until (moduleList.size - 1))
            if (moduleList[i].type == selectedItem) {
                Log.e("Selected module " + moduleList[i].type, " don't do anything")
                /* if (holder.binding.editTextViewRelative.visibility != View.VISIBLE)
                     holder.binding.editTextViewRelative.visibility = View.VISIBLE
                 else
                     holder.binding.editTextViewRelative.visibility = View.GONE*/
            } else {
                Log.e("Selected module " + moduleList[i].type, " not hide ")
                holder.binding.editTextViewRelative.visibility = View.GONE
            }
    }

    private fun setEditLayout(
        holder: ManualIndividualViewHolder,
        editText: View,
        data: ACDataModel
    ) {
        when (data.type) {
            "setLowBatteryNotifyThresholdEdt" -> {
                if (holder.binding.editTextViewRelative.visibility != View.VISIBLE) {
                    holder.binding.editTextViewRelative.visibility = View.VISIBLE
                    (editText as EditText).inputType = InputType.TYPE_CLASS_NUMBER
                    editText.hint =
                        context.resources.getString(R.string.hintSetLowBatteryNotifyThresholdEdt)
                } else {
                    holder.binding.editTextViewRelative.visibility = View.GONE
                }
            }
            "setCriticalBatteryNotifyThresholdEdt" -> {
                if (holder.binding.editTextViewRelative.visibility != View.VISIBLE) {
                    holder.binding.editTextViewRelative.visibility = View.VISIBLE
                    (editText as EditText).inputType = InputType.TYPE_CLASS_NUMBER
                    editText.hint =
                        context.resources.getString(R.string.hintSetCriticalBatteryNotifyThresholdEdt)
                } else {
                    holder.binding.editTextViewRelative.visibility = View.GONE
                }
            }
            "setDischargeDayEdt" -> {
                if (holder.binding.editTextViewRelative.visibility != View.VISIBLE) {
                    holder.binding.editTextViewRelative.visibility = View.VISIBLE
                    (editText as EditText).inputType = InputType.TYPE_CLASS_NUMBER
                    editText.hint = context.resources.getString(R.string.hintSetDischargeDayEdt)
                } else {
                    holder.binding.editTextViewRelative.visibility = View.GONE
                }
            }
        }
    }

    private fun displayResult(
        holder: ManualIndividualViewHolder,
        result: MutableLiveData<HarnessResult?>?
    ) {
        result?.observe(activity) {
            if (it != null) {
                if (it.status) {
                    setSuccessResult(holder, it.value)
                } else {
                    setFailureResult(holder, it.value)
                }
            } else {
                setFailureResult(holder, "error in loading data")
            }
        }
    }

    private fun setSuccessResult(holder: ManualIndividualViewHolder, value: String) {
        holder.binding.textViewDisplayResult.text = value
        holder.binding.textViewDisplayResult.setTextColor(Color.parseColor("#00ff00"))
        holder.binding.textViewDisplayResult.visibility = View.VISIBLE
    }

    private fun setFailureResult(holder: ManualIndividualViewHolder, value: String) {
        holder.binding.textViewDisplayResult.text = value
        holder.binding.textViewDisplayResult.setTextColor(Color.parseColor("#ff0000"))
        holder.binding.textViewDisplayResult.visibility = View.VISIBLE
    }

    private fun setLoadingResult(holder: ManualIndividualViewHolder) {
        val value = "Please wait..."
        holder.binding.textViewDisplayResult.text = value
        holder.binding.textViewDisplayResult.setTextColor(Color.parseColor("#000000"))
        holder.binding.textViewDisplayResult.visibility = View.VISIBLE
    }
}


class ManualIndividualViewHolder(val binding: AdManualIndividualItemBinding) :
    RecyclerView.ViewHolder(binding.root) {
}