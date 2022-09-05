package com.android.autelsdk.flyController

//import com.android.autelsdk.remoteController.RemoteControllerActivity

import android.Manifest
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import com.android.autelsdk.BaseActivity
import com.android.autelsdk.R
import com.android.autelsdk.databinding.ActivityFlyControllerBinding
import com.android.autelsdk.util.ExcelWorkbook
import com.autel.sdk.flycontroller.AutelFlyController
import com.autel.sdk.product.BaseProduct


//import com.android.autelsdk.R
//import com.android.autelsdk.databinding.ActivityRemoteControllerBinding
//import com.android.myapplication.R
//import com.android.myapplication.databinding.ActivityFlyControllerBinding

class FlyControllerActivity :  BaseActivity<AutelFlyController>() {
    val TAG = FlyControllerActivity::class.java.simpleName
    lateinit var binding : ActivityFlyControllerBinding
    private val viewModel : FlyControllerViewModel<AutelFlyController> by viewModels()
    var TestArray = arrayOf("one","two")
    private  var  ExcelTest = TestArray





    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this , customViewResId)
        //val viewModel : FlyControllerViewModel<AutelFlyController> = ViewModelProvider.
        requestPermission()
        initUi()
        createReport()

    }

    private fun handleListeners() {
    }

    override fun initController(product: BaseProduct?): AutelFlyController? {
        if (product != null)
            return product!!.flyController

        return null
    }

    override fun getCustomViewResId(): Int {
        return R.layout.activity_fly_controller
    }

    override fun initUi() {

    }

    private fun createReport(){
        val excelWorkbook : ExcelWorkbook = ExcelWorkbook()
        excelWorkbook.createExcelWorkbook()
        excelWorkbook.exportDataIntoWorkbook(applicationContext)
        ///excelWorkbook.storeExcelInStorage(applicationContext,"TestDemo")
    }

    private fun requestPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(
                this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
        ) {
            Toast.makeText(
                this,
                "Write External Storage permission allows us to save files. Please allow this permission in App Settings.",
                Toast.LENGTH_LONG
            ).show()
        } else {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                500
            )
        }
    }


}




