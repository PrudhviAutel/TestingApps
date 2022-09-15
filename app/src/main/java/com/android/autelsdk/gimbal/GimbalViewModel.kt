package com.android.autelsdk.gimbal

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.autel.common.product.AutelProductType
import com.autel.internal.gimbal.cruiser.CruiserGimbalImpl
import com.autel.sdk.gimbal.AutelGimbal
import com.autel.sdk.product.BaseProduct

class GimbalViewModel() : ViewModel() {

    private var mController: AutelGimbal = CruiserGimbalImpl()
    private var currentProduct: MutableLiveData<BaseProduct?> = MutableLiveData()
    private var currentProductType: MutableLiveData<AutelProductType> = MutableLiveData(AutelProductType.UNKNOWN)
    private val gimbalRepository: GimbalRepository = GimbalRepositoryImpl()



    fun setCurrentProduct(product : BaseProduct?) {
        setCurrentProductType(product?.type)
        currentProduct.postValue(product)
    }

    fun getCurrentProduct(): MutableLiveData<BaseProduct?> {
        return currentProduct
    }

    fun setCurrentProductType(productType : AutelProductType?) {
        currentProductType.postValue(productType ?: AutelProductType.UNKNOWN)
    }

    fun getCurrentProductType(): MutableLiveData<AutelProductType> {
        return currentProductType
    }

    fun setController(controller : AutelGimbal) {
        mController = controller
        gimbalRepository.setController(mController)
    }

    fun getController() : AutelGimbal {
        return mController
    }



//    // When we will have to Pass the Values to ViewModel, we can use this code
//    class Factory(private val mController: AutelRemoteController?) : ViewModelProvider.Factory {
//        override fun <T : ViewModel> create(modelClass: Class<T>): T {
//            return RemoteControllerViewModel(mController) as T
//        }
//    }
}