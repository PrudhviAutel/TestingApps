package com.android.autelsdk.album

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.autelsdk.util.Resource
import com.autel.common.RangePair
import com.autel.common.album.AlbumParameterRangeManager
import com.autel.common.album.AlbumType
import com.autel.common.album.MediaInfo
import com.autel.common.camera.media.VideoResolutionAndFps
import com.autel.common.dsp.*
import com.autel.common.dsp.evo.*
import com.autel.common.gimbal.*
import com.autel.common.gimbal.evo.EvoAngleInfo
import com.autel.common.gimbal.evo.EvoGimbalParameterRangeManager
import com.autel.common.gimbal.evo.GimbalAngleRange
import com.autel.common.product.AutelProductType
import com.autel.internal.album.Album10
import com.autel.internal.dsp.cruiser.CruiserDspImpl
import com.autel.internal.gimbal.cruiser.CruiserGimbalImpl
import com.autel.sdk.Autel
import com.autel.sdk.album.AutelAlbum
import com.autel.sdk.dsp.AutelDsp
import com.autel.sdk.dsp.CruiserDsp
import com.autel.sdk.gimbal.AutelGimbal
import com.autel.sdk.gimbal.CruiserGimbal
import com.autel.sdk.product.BaseProduct
import com.autel.sdk.product.CruiserAircraft
import java.io.File

class AlbumViewModel() : ViewModel() {

    private var mController: AutelAlbum = Album10()
    private var currentProduct: MutableLiveData<BaseProduct?> = MutableLiveData()
    private var currentProductType: MutableLiveData<AutelProductType> = MutableLiveData(AutelProductType.UNKNOWN)
    private val albumRepository: AlbumRepository = AlbumRepositoryImpl()
    private val mediaList : MutableLiveData<ArrayList<MediaInfo>> = MutableLiveData()

    fun setCurrentProduct(product : BaseProduct?) {
        setCurrentProductType(product?.type)
        currentProduct.postValue(product)
        setController(product?.album)
        mController = (product as CruiserAircraft).album
        setController(mController)
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

    fun <T> setController(controller : T) {
        albumRepository.setController(controller)
    }

    fun getController() : AutelAlbum {
        return mController
    }


    suspend fun getMedia(albumType: AlbumType, start: Int, count: Int) : MutableLiveData<Resource<List<MediaInfo>>> {
        return albumRepository.getMedia(albumType, start, count)
    }

    suspend fun getMedia(start: Int, count: Int) : MutableLiveData<Resource<List<MediaInfo>>> {
        return albumRepository.getMedia(start, count)
    }

    suspend fun deleteMedia(mediaList: List<MediaInfo>) : MutableLiveData<Resource<List<MediaInfo>>> {
        return albumRepository.deleteMedia(mediaList)
    }

    suspend fun deleteMedia(media: MediaInfo) : MutableLiveData<Resource<List<MediaInfo>>> {
        return albumRepository.deleteMedia(media)
    }

    suspend fun getVideoResolutionFromHttpHeader(media: MediaInfo) : MutableLiveData<Resource<VideoResolutionAndFps>> {
        return albumRepository.getVideoResolutionFromHttpHeader(media)
    }

    suspend fun getVideoResolutionFromLocalFile(file: File): MutableLiveData<Resource<VideoResolutionAndFps>> {
        return albumRepository.getVideoResolutionFromLocalFile(file)
    }

    fun getParameterRangeManager(): AlbumParameterRangeManager {
        return albumRepository.getParameterRangeManager()
    }

    suspend fun getFMCMedia(albumType: AlbumType, start: Int, count: Int) : MutableLiveData<Resource<List<MediaInfo>>> {
        return albumRepository.getFMCMedia(albumType, start, count)
    }

    suspend fun getFMCMedia(start: Int, count: Int) : MutableLiveData<Resource<List<MediaInfo>>> {
        return albumRepository.getFMCMedia(start, count)
    }

    suspend fun deleteFMCMedia(mediaList: List<MediaInfo>) : MutableLiveData<Resource<List<MediaInfo>>> {
        return albumRepository.deleteFMCMedia(mediaList)
    }

    suspend fun deleteFMCMedia(media: MediaInfo) : MutableLiveData<Resource<List<MediaInfo>>> {
        return albumRepository.deleteFMCMedia(media)
    }

    suspend fun getFMCVideoResolutionFromHttpHeader(media: MediaInfo) : MutableLiveData<Resource<VideoResolutionAndFps>> {
        return albumRepository.getFMCVideoResolutionFromHttpHeader(media)
    }

    fun setMediaListToList(mediaList: ArrayList<MediaInfo>) {
        this.mediaList.postValue(mediaList)
    }

    fun addMediaListToList(mediaList: ArrayList<MediaInfo>) {
        var list: ArrayList<MediaInfo> = ArrayList(this.mediaList.value)
        list.addAll(mediaList)
        this.mediaList.postValue(list)
    }

    fun addMediaToList(media: MediaInfo) {
        var list: ArrayList<MediaInfo> = ArrayList(this.mediaList.value)
        list.add(media)
        this.mediaList.postValue(list)
    }

    fun deleteMediaFromList(media: MediaInfo) {
        var list: ArrayList<MediaInfo> = ArrayList(this.mediaList.value)
        list.remove(media)
        this.mediaList.postValue(list)
    }

    fun deleteMediaListFromList(mediaList: ArrayList<MediaInfo>) {
        var list: ArrayList<MediaInfo> = ArrayList(this.mediaList.value)
        list.removeAll(mediaList)
        this.mediaList.postValue(list)
    }


//    // When we will have to Pass the Values to ViewModel, we can use this code
//    class Factory(private val mController: AutelRemoteController?) : ViewModelProvider.Factory {
//        override fun <T : ViewModel> create(modelClass: Class<T>): T {
//            return RemoteControllerViewModel(mController) as T
//        }
//    }
}