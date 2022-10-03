package com.android.autelsdk.album

import androidx.lifecycle.MutableLiveData
import com.android.autelsdk.util.Resource
import com.autel.common.CallbackWithOneParam
import com.autel.common.album.AlbumParameterRangeManager
import com.autel.common.album.AlbumType
import com.autel.common.album.MediaInfo
import com.autel.common.camera.media.VideoResolutionAndFps
import java.io.File

interface AlbumRepository {

    fun <T> setController(controller : T)

    suspend fun getMedia(albumType: AlbumType, start: Int, count: Int) : MutableLiveData<Resource<List<MediaInfo>>>

    suspend fun getMedia(start: Int, count: Int) : MutableLiveData<Resource<List<MediaInfo>>>

    suspend fun deleteMedia(mediaList: List<MediaInfo>) : MutableLiveData<Resource<List<MediaInfo>>>

    suspend fun deleteMedia(media: MediaInfo) : MutableLiveData<Resource<List<MediaInfo>>>

    suspend fun getVideoResolutionFromHttpHeader(media: MediaInfo) : MutableLiveData<Resource<VideoResolutionAndFps>>

    suspend fun getVideoResolutionFromLocalFile(file: File): MutableLiveData<Resource<VideoResolutionAndFps>>

    fun getParameterRangeManager(): AlbumParameterRangeManager

    suspend fun getFMCMedia(albumType: AlbumType, start: Int, count: Int) : MutableLiveData<Resource<List<MediaInfo>>>

    suspend fun getFMCMedia(start: Int, count: Int) : MutableLiveData<Resource<List<MediaInfo>>>

    suspend fun deleteFMCMedia(mediaList: List<MediaInfo>) : MutableLiveData<Resource<List<MediaInfo>>>

    suspend fun deleteFMCMedia(media: MediaInfo) : MutableLiveData<Resource<List<MediaInfo>>>

    suspend fun getFMCVideoResolutionFromHttpHeader(media: MediaInfo) : MutableLiveData<Resource<VideoResolutionAndFps>>

}