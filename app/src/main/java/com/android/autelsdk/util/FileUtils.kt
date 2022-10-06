package com.android.autelsdk.util

import com.autel.internal.sdk.util.AutelDirPathUtils

class FileUtils {

    companion object {
        public fun getMissionFilePath(): String? {
            return AutelDirPathUtils.getAppDir() + "/Mission/"
        }
    }
}