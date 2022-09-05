package com.android.autelsdk.remoteController

import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.android.autelsdk.BaseActivity
import com.android.autelsdk.R
import com.android.autelsdk.databinding.ActivityRemoteControllerBinding
import com.android.autelsdk.util.Status
import com.autel.common.remotecontroller.RemoteControllerLanguage
import com.autel.sdk.product.BaseProduct
import com.autel.sdk.remotecontroller.AutelRemoteController
import kotlinx.coroutines.runBlocking


// On Activity Start First getCustomViewResId() is called and then onCreate() --- nothing else on Activity Creation
class RemoteControllerActivity : BaseActivity<AutelRemoteController>() {
    val TAG = RemoteControllerActivity::class.java.simpleName

    private val viewModel: RemoteControllerViewModel by viewModels()
    lateinit var binding: ActivityRemoteControllerBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, customViewResId)

        initUi()
        runTests()

    }

    private fun handleListeners() {

    }

    override fun initController(product: BaseProduct?): AutelRemoteController? {
        if (product != null)
            return product.remoteController

        return null
    }

    override fun getCustomViewResId(): Int {
        return R.layout.activity_remote_controller
    }

    override fun initUi() {
        handleListeners()
    }

    fun runTests() {

        runBlocking {
            viewModel.setLanguageTest(RemoteControllerLanguage.ENGLISH).observe(this@RemoteControllerActivity, Observer { msg ->
                binding.testResults.append(msg.data)
            })

            viewModel.getLanguageTest().observe(this@RemoteControllerActivity, Observer { msg ->
                when (msg.status) {
                    Status.SUCCESS -> {


                    }
                    Status.ERROR -> {

                    }
                    else -> {

                    }
                }
            })
        }
    }

}