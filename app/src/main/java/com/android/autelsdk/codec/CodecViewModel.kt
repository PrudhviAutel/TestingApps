package com.android.autelsdk.codec

import androidx.lifecycle.ViewModel
import com.android.autelsdk.flyController.FlyControllerRepository
import com.android.autelsdk.flyController.FlyControllerRepositoryImpl

class CodecViewModel : ViewModel() {
    val codecRepository: CodecRepository = CodecRepositoryImpl()

}