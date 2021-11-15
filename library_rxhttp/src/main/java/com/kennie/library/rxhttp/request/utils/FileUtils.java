package com.kennie.library.rxhttp.request.utils;

import android.text.TextUtils;
import android.webkit.MimeTypeMap;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.File;
import java.util.Locale;

/**
 * 描述：
 */
class FileUtils {

    @NonNull
    private static String getSuffix(@Nullable File file) {
        if (file == null || !file.exists() || file.isDirectory()) {
            return "";
        }
        String fileName = file.getName();
        if (fileName.endsWith(".")) {
            return "";
        }
        int index = fileName.lastIndexOf(".");
        if (index < 0) {
            return "";
        }
        return fileName.substring(index + 1).toLowerCase(Locale.US);
    }

    @Nullable
    static String getMimeType(@Nullable File file){
        String suffix = getSuffix(file);
        String mimeType = null;
        if (!TextUtils.isEmpty(suffix)) {
            mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(suffix);
        }
        if (TextUtils.isEmpty(mimeType)) {
            mimeType = "file/*";
        }
        return mimeType;
    }
}
