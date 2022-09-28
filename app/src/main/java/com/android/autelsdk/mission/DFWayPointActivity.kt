package com.android.autelsdk.mission

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.android.autelsdk.R

class DFWayPointActivity : AppCompatActivity(), View.OnClickListener {
    lateinit var viewModel : DFViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(DFViewModel::class.java)
    }

    override fun onClick(p0: View?) {
        var  id = p0?.id
        when(id){
            R.id.autoCheck -> {
            }

        }
    }
}