package com.android.autelsdk.battery

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.autel.common.product.AutelProductType
import com.autel.sdk.battery.AutelBattery
import com.autel.sdk.gimbal.AutelGimbal
import com.autel.sdk.gimbal.CruiserGimbal
import com.autel.sdk.product.BaseProduct
import com.autel.sdk.product.CruiserAircraft

class BatteryViewModel : ViewModel() {
    private var currentProduct: MutableLiveData<BaseProduct?> = MutableLiveData()
    private var currentProductType: MutableLiveData<AutelProductType> = MutableLiveData(
        AutelProductType.UNKNOWN
    )

    fun setCurrentProduct(product: BaseProduct?) {
        setCurrentProductType(product?.type)
        currentProduct.postValue(product)
    }

    private fun setCurrentProductType(productType : AutelProductType?) {
        currentProductType.postValue(productType ?: AutelProductType.UNKNOWN)
    }

    fun getCurrentProductType(): MutableLiveData<AutelProductType> {
        return currentProductType
    }

}