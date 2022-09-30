package com.android.autelsdk.battery

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.autel.common.product.AutelProductType
import com.autel.internal.battery.cruiser.CruiserBatteryImpl
import com.autel.sdk.battery.CruiserBattery
import com.autel.sdk.product.BaseProduct
import com.autel.sdk.product.CruiserAircraft

class BatteryViewModel : ViewModel() {
    private var mController: CruiserBattery = CruiserBatteryImpl()
    private var currentProduct: MutableLiveData<BaseProduct?> = MutableLiveData()
    private var currentProductType: MutableLiveData<AutelProductType> = MutableLiveData(
        AutelProductType.UNKNOWN
    )

    fun setCurrentProduct(product : BaseProduct?) {
        setCurrentProductType(product?.type)
        currentProduct.postValue(product)
        setController(product?.gimbal)
        val cruiserGimbalController = (product as CruiserAircraft).gimbal
        setController(cruiserGimbalController)
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

    fun <T> getController() : T {
        return mController as T
    }

    fun <T> setController (controller: T) {
        when (controller) {
            is CruiserBattery->{
                mController = controller
            }
        }
    }

}