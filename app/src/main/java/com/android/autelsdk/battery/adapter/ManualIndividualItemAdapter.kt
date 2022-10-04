package com.android.autelsdk.battery.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.text.InputType
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
        holder.binding.editTextView.visibility = View.GONE
        holder.binding.textViewDisplayResult.visibility = View.GONE
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ManualIndividualViewHolder, position: Int) {
        val item = moduleList[position]
        holder.binding.title.text = item.name
        hideOtherLayout(holder)
        if (item.type.equals("setBatteryRealTimeDataListener")) {
            holder.binding.setBtn.text = "Set"
            holder.binding.viewBtn.text = "Reset"
        }
        holder.binding.setBtn.setOnClickListener {
            // populate view according to the function
            hideOtherLayout(holder)
            when (item.type) {
                "setBatteryRealTimeDataListener" -> {
                    setLoadingResult(holder)
                    val result = viewModel?.setBatteryRealTimeDataListener()
                    displayResult(holder, result)
                }
                "setLowBatteryNotifyThresholdEdt" -> {
                    val editText: View = holder.binding.editTextLayout.editText
                    val button: View = holder.binding.buttonLayout.button

                    if (holder.binding.editTextView.visibility != View.VISIBLE) {
                        holder.binding.editTextView.visibility = View.VISIBLE
                        (editText as EditText).inputType = InputType.TYPE_CLASS_NUMBER
                        editText.setHint(context.resources.getString(R.string.hintSetLowBatteryNotifyThresholdEdt))

                        button.setOnClickListener {
                            setLoadingResult(holder)
                            displayResult(
                                holder,
                                viewModel?.setLowBatteryNotifyThresholdEdt(editText.text.toString())
                            )
                        }
                    } else {
                        holder.binding.editTextView.visibility = View.GONE
                    }
                }
                "setCriticalBatteryNotifyThresholdEdt" -> {
                    //populate corresponding view
                    // viewModel.setCriticalBatteryNotifyThresholdEdt()
                    val editText: View = holder.binding.editTextLayout.editText
                    val button: View = holder.binding.buttonLayout.button

                    if (holder.binding.editTextView.visibility != View.VISIBLE) {
                        holder.binding.editTextView.visibility = View.VISIBLE
                        (editText as EditText).inputType = InputType.TYPE_CLASS_NUMBER
                        editText.setHint(context.resources.getString(R.string.hintSetCriticalBatteryNotifyThresholdEdt))

                        button.setOnClickListener {
                            setLoadingResult(holder)
                            displayResult(
                                holder,
                                viewModel?.setCriticalBatteryNotifyThresholdEdt(editText.text.toString())
                            )
                        }
                    } else {
                        holder.binding.editTextView.visibility = View.GONE
                    }
                }
                "setDischargeDayEdt" -> {
                    //populate corresponding view
                    //  viewModel.setDischargeDayEdt()
                    val editText: View = holder.binding.editTextLayout.editText
                    val button: View = holder.binding.buttonLayout.button
                    if (holder.binding.editTextView.visibility != View.VISIBLE) {
                        holder.binding.editTextView.visibility = View.VISIBLE
                        (editText as EditText).inputType = InputType.TYPE_CLASS_NUMBER
                        editText.setHint(context.resources.getString(R.string.hintSetDischargeDayEdt))

                        button.setOnClickListener {
                            setLoadingResult(holder)
                            displayResult(
                                holder,
                                viewModel?.setDischargeDayEdt(editText.text.toString())
                            )
                        }
                    } else {
                        holder.binding.editTextView.visibility = View.GONE
                    }
                }
            }
        }

        holder.binding.viewBtn.setOnClickListener {
            // call get value function here
            hideOtherLayout(holder)
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