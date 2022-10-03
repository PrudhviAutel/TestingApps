package com.android.autelsdk.album

import androidx.lifecycle.MutableLiveData
import com.android.autelsdk.util.Resource
import com.android.autelsdk.util.Utils
import com.autel.common.CallbackWithNoParam
import com.autel.common.CallbackWithOneParam
import com.autel.common.CallbackWithTwoParams
import com.autel.common.album.AlbumParameterRangeManager
import com.autel.common.album.AlbumType
import com.autel.common.album.MediaInfo
import com.autel.common.camera.media.VideoResolutionAndFps
import com.autel.common.dsp.*
import com.autel.common.dsp.evo.*
import com.autel.common.error.AutelError
import com.autel.internal.album.Album10
import com.autel.internal.album.Album20
import com.autel.internal.dsp.cruiser.CruiserDspImpl
import com.autel.sdk.album.AutelAlbum
import com.autel.sdk.dsp.AutelDsp
import com.autel.sdk.dsp.CruiserDsp
import java.io.File

class AlbumRepositoryImpl() : AlbumRepository {

    var mController : AutelAlbum = Album10()
    var autelDspController : AutelDsp = CruiserDspImpl()

    override fun <T> setController(controller: T) {
        if (controller is Album10) {
            mController = controller
        } else if (controller is Album20) {
            mController = controller
        }
    }

    override suspend fun getMedia(albumType: AlbumType, start: Int, count: Int): MutableLiveData<Resource<List<MediaInfo>>> {
        var getMediaResult: MutableLiveData<Resource<List<MediaInfo>>> = MutableLiveData()
        mController.getMedia(albumType, start, count, object : CallbackWithOneParam<List<MediaInfo>> {
            override fun onFailure(rcError: AutelError) {
                val errorMessage =
                    Utils.getFailureShowText("for Album Type = ${albumType.name},Start = ${start}, Count = ${count}.\nReason - ${rcError.description}");
                getMediaResult.postValue(Resource.Companion.error(errorMessage, null))
            }

            override fun onSuccess(data: List<MediaInfo>) {
                val successMessage = Utils.getSuccessShowText("\nFor album type = ${albumType.name},Start = ${start}, Count = ${count}");
                getMediaResult.postValue(Resource.Companion.success(data, successMessage))
            }
        })
        return getMediaResult
    }

    override suspend fun getMedia(start: Int, count: Int): MutableLiveData<Resource<List<MediaInfo>>> {
        var getMediaResult: MutableLiveData<Resource<List<MediaInfo>>> = MutableLiveData()
        mController.getMedia(start, count, object : CallbackWithOneParam<List<MediaInfo>> {
            override fun onFailure(rcError: AutelError) {
                val errorMessage =
                    Utils.getFailureShowText("for Start = ${start}, Count = ${count}.\nReason - ${rcError.description}");
                getMediaResult.postValue(Resource.Companion.error(errorMessage, null))
            }

            override fun onSuccess(data: List<MediaInfo>) {
                val successMessage = Utils.getSuccessShowText("\nFor Start = ${start}, Count = ${count}");
                getMediaResult.postValue(Resource.Companion.success(data, successMessage))
            }
        })
        return getMediaResult
    }

    override suspend fun deleteMedia(mediaList: List<MediaInfo>): MutableLiveData<Resource<List<MediaInfo>>> {
        var getMediaResult: MutableLiveData<Resource<List<MediaInfo>>> = MutableLiveData()
        mController.deleteMedia(mediaList, object : CallbackWithOneParam<List<MediaInfo>> {
            override fun onFailure(rcError: AutelError) {
                val errorMessage =
                    Utils.getFailureShowText("on trying to delete ${mediaList.size} media items.\nReason - ${rcError.description}");
                getMediaResult.postValue(Resource.Companion.error(errorMessage, null))
            }

            override fun onSuccess(data: List<MediaInfo>) {
                val successMessage = Utils.getSuccessShowText("\nDeleted ${mediaList.size} items");
                getMediaResult.postValue(Resource.Companion.success(data, successMessage))
            }
        })
        return getMediaResult
    }

    override suspend fun deleteMedia(media: MediaInfo): MutableLiveData<Resource<List<MediaInfo>>> {
        var getMediaResult: MutableLiveData<Resource<List<MediaInfo>>> = MutableLiveData()
        mController.deleteMedia(media, object : CallbackWithOneParam<List<MediaInfo>> {
            override fun onFailure(rcError: AutelError) {
                val errorMessage =
                    Utils.getFailureShowText("on trying to delete ${media.originalMedia}.\nReason - ${rcError.description}");
                getMediaResult.postValue(Resource.Companion.error(errorMessage, null))
            }

            override fun onSuccess(data: List<MediaInfo>) {
                val successMessage = Utils.getSuccessShowText("\nDeleted ${media.originalMedia} items");
                getMediaResult.postValue(Resource.Companion.success(data, successMessage))
            }
        })
        return getMediaResult
    }

    override suspend fun getVideoResolutionFromHttpHeader(media: MediaInfo): MutableLiveData<Resource<VideoResolutionAndFps>> {
        var getVideoResolutionFromHttpHeaderResult: MutableLiveData<Resource<VideoResolutionAndFps>> = MutableLiveData()
        mController.getVideoResolutionFromHttpHeader(media, object : CallbackWithOneParam<VideoResolutionAndFps> {
            override fun onFailure(rcError: AutelError) {
                val errorMessage =
                    Utils.getFailureShowText("for media : ${media.originalMedia}.\nReason - ${rcError.description}");
                getVideoResolutionFromHttpHeaderResult.postValue(Resource.Companion.error(errorMessage, null))
            }

            override fun onSuccess(data: VideoResolutionAndFps) {
                val successMessage = Utils.getSuccessShowText("resolution = ${data.resolution}, fps = ${data.fps}.\nFor media = ${media.originalMedia}");
                getVideoResolutionFromHttpHeaderResult.postValue(Resource.Companion.success(data, successMessage))
            }
        })
        return getVideoResolutionFromHttpHeaderResult
    }

    override suspend fun getVideoResolutionFromLocalFile(file: File): MutableLiveData<Resource<VideoResolutionAndFps>> {
        var getVideoResolutionFromLocalFileResult: MutableLiveData<Resource<VideoResolutionAndFps>> = MutableLiveData()
        val videoResolutionAndFps = mController.getVideoResolutionFromLocalFile(file)
        val successMessage = Utils.getSuccessShowText("for file = ${file.name}\n${videoResolutionAndFps}")
        getVideoResolutionFromLocalFileResult.postValue(Resource.Companion.success(videoResolutionAndFps, successMessage))
        return getVideoResolutionFromLocalFileResult
    }

    override fun getParameterRangeManager(): AlbumParameterRangeManager {
        return mController.parameterRangeManager
    }

    override suspend fun getFMCMedia(albumType: AlbumType, start: Int, count: Int): MutableLiveData<Resource<List<MediaInfo>>> {
        var getMediaResult: MutableLiveData<Resource<List<MediaInfo>>> = MutableLiveData()
        mController.getFMCMedia(albumType, start, count, object : CallbackWithOneParam<List<MediaInfo>> {
            override fun onFailure(rcError: AutelError) {
                val errorMessage =
                    Utils.getFailureShowText("for Album Type = ${albumType.name},Start = ${start}, Count = ${count}.\nReason - ${rcError.description}");
                getMediaResult.postValue(Resource.Companion.error(errorMessage, null))
            }

            override fun onSuccess(data: List<MediaInfo>) {
                val successMessage = Utils.getSuccessShowText("\nFor album type = ${albumType.name},Start = ${start}, Count = ${count}");
                getMediaResult.postValue(Resource.Companion.success(data, successMessage))
            }
        })
        return getMediaResult
    }

    override suspend fun getFMCMedia(start: Int, count: Int): MutableLiveData<Resource<List<MediaInfo>>> {
        var getMediaResult: MutableLiveData<Resource<List<MediaInfo>>> = MutableLiveData()
        mController.getMedia(start, count, object : CallbackWithOneParam<List<MediaInfo>> {
            override fun onFailure(rcError: AutelError) {
                val errorMessage =
                    Utils.getFailureShowText("for Start = ${start}, Count = ${count}.\nReason - ${rcError.description}");
                getMediaResult.postValue(Resource.Companion.error(errorMessage, null))
            }

            override fun onSuccess(data: List<MediaInfo>) {
                val successMessage = Utils.getSuccessShowText("\nFor Start = ${start}, Count = ${count}");
                getMediaResult.postValue(Resource.Companion.success(data, successMessage))
            }
        })
        return getMediaResult
    }

    override suspend fun deleteFMCMedia(mediaList: List<MediaInfo>): MutableLiveData<Resource<List<MediaInfo>>> {
        var getMediaResult: MutableLiveData<Resource<List<MediaInfo>>> = MutableLiveData()
        mController.deleteFMCMedia(mediaList, object : CallbackWithOneParam<List<MediaInfo>> {
            override fun onFailure(rcError: AutelError) {
                val errorMessage =
                    Utils.getFailureShowText("on trying to delete ${mediaList.size} items.\nReason - ${rcError.description}");
                getMediaResult.postValue(Resource.Companion.error(errorMessage, null))
            }

            override fun onSuccess(data: List<MediaInfo>) {
                val successMessage = Utils.getSuccessShowText("\nDeleted ${data.size} items.");
                getMediaResult.postValue(Resource.Companion.success(data, successMessage))
            }
        })
        return getMediaResult
    }

    override suspend fun deleteFMCMedia(media: MediaInfo): MutableLiveData<Resource<List<MediaInfo>>> {
        var getMediaResult: MutableLiveData<Resource<List<MediaInfo>>> = MutableLiveData()
        mController.deleteFMCMedia(media, object : CallbackWithOneParam<List<MediaInfo>> {
            override fun onFailure(rcError: AutelError) {
                val errorMessage =
                    Utils.getFailureShowText("on trying to delete ${media.originalMedia}.\nReason - ${rcError.description}");
                getMediaResult.postValue(Resource.Companion.error(errorMessage, null))
            }

            override fun onSuccess(data: List<MediaInfo>) {
                val successMessage = Utils.getSuccessShowText("\nDeleted ${media.originalMedia}");
                getMediaResult.postValue(Resource.Companion.success(data, successMessage))
            }
        })
        return getMediaResult
    }

    override suspend fun getFMCVideoResolutionFromHttpHeader(media: MediaInfo): MutableLiveData<Resource<VideoResolutionAndFps>> {
        var getFMCVideoResolutionFromHttpHeaderResult: MutableLiveData<Resource<VideoResolutionAndFps>> = MutableLiveData()
        mController.getVideoResolutionFromHttpHeader(media, object : CallbackWithOneParam<VideoResolutionAndFps> {
            override fun onFailure(rcError: AutelError) {
                val errorMessage =
                    Utils.getFailureShowText("for media : ${media.originalMedia}.\nReason - ${rcError.description}");
                getFMCVideoResolutionFromHttpHeaderResult.postValue(Resource.Companion.error(errorMessage, null))
            }

            override fun onSuccess(data: VideoResolutionAndFps) {
                val successMessage = Utils.getSuccessShowText("resolution = ${data.resolution}, fps = ${data.fps}.\nFor media = ${media.originalMedia}");
                getFMCVideoResolutionFromHttpHeaderResult.postValue(Resource.Companion.success(data, successMessage))
            }
        })
        return getFMCVideoResolutionFromHttpHeaderResult
    }


}
