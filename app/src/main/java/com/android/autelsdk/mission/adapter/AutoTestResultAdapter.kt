package com.android.autelsdk.mission.adapter

import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.autelsdk.BaseActivity
import com.android.autelsdk.battery.BatteryViewModel
import com.android.autelsdk.battery.data.HarnessResult
import com.android.autelsdk.databinding.AdManualIndividualItemBinding
import com.android.autelsdk.databinding.SingleTextviewLayoutBinding
import com.autel.sdk.battery.AutelBattery

class AutoTestResultAdapter : RecyclerView.Adapter<AutoTestResultAdapterViewHolder>() {

    private val listOfResult = ArrayList<HarnessResult>()
    lateinit var context: Context
    lateinit var activity: BaseActivity<AutelBattery>
    var viewModel: BatteryViewModel? = null

    fun setListOfResult(value: HarnessResult) {
        listOfResult.add(value)
        Log.e("Rahul ", value.value)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AutoTestResultAdapterViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = SingleTextviewLayoutBinding.inflate(inflater, parent, false)
        context = parent.context
        return AutoTestResultAdapterViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AutoTestResultAdapterViewHolder, position: Int) {
        val item = listOfResult[position]
        holder.binding.textViewResult.text = item.value
        if (item.status) {
            holder.binding.textViewResult.setTextColor(
                Color.parseColor(
                    "#00ff00"
                )
            )
        } else {
            holder.binding.textViewResult.setTextColor(
                Color.parseColor(
                    "#ff0000"
                )
            )
        }
    }

    override fun getItemCount(): Int {
        return listOfResult.size
    }
}


class AutoTestResultAdapterViewHolder(val binding: SingleTextviewLayoutBinding) :
    RecyclerView.ViewHolder(binding.root) {
}

