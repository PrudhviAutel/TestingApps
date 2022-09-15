package com.android.autelsdk

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.GridLayoutManager
import com.android.autelsdk.databinding.ActivityHomeBinding
import com.android.autelsdk.util.Constants
import com.example.myapplicationdragonfish.adapter.ModuleRecyclerViewAdapter
//import com.example.myapplicationdragonfish.databinding.ActivityHomeBinding
import com.example.myapplicationdragonfish.viewmodel.HomeViewModel

class HomeActivity : AppCompatActivity() {
    lateinit var binding: ActivityHomeBinding
    lateinit var viewModel: HomeViewModel
    lateinit var sdkName: String

    var moduleList = ArrayList<String>()
    val adapter = ModuleRecyclerViewAdapter()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sdkName = intent.getStringExtra("SdkName").toString()

        binding.textSdkName.text = sdkName
        binding.recyclerviewListOfComponent.layoutManager = GridLayoutManager(this, 2)
        binding.recyclerviewListOfComponent.adapter = adapter

        initialAlertDialog()


    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.home_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    fun initiateModuleList() {
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

    fun initialAlertDialog() {
        val items = arrayOf(
            "Connect mobile to remote controller",
            "Connect remote to drone",
            "Switch on drone",
            "Check remote connection to drone",
            "Battery health of drone"
        )
        val selectedList = ArrayList<Int>()
        val builder = AlertDialog.Builder(this)

        builder.setTitle(sdkName)
        builder.setMultiChoiceItems(items, null) { dialog, which, isChecked ->
            if (isChecked) {
                selectedList.add(which)
            } else if (selectedList.contains(which)) {
                selectedList.remove(Integer.valueOf(which))
            }
        }

        builder.setPositiveButton("Skip") { dialogInterface, i ->
            val selectedStrings = ArrayList<String>()

            for (j in selectedList.indices) {
                selectedStrings.add(items[selectedList[j]])
            }
        }

        builder.show()

    }
}