package com.kennie.library.rxhttp.request.interceptor;

import androidx.annotation.NonNull;

import com.kennie.library.rxhttp.request.utils.NetUtils;

import java.util.concurrent.TimeUnit;

import okhttp3.CacheControl;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * 描述：缓存过滤器
 * 用于为Request配置缓存策略
 *
 */
public class CacheControlInterceptor extends BaseCacheControlInterceptor {

    public static void addTo(@NonNull OkHttpClient.Builder builder) {
        builder.addInterceptor(new CacheControlInterceptor());
    }

    private CacheControlInterceptor() {
    }

    @NonNull
    @Override
    protected Request getCacheRequest(@io.reactivex.annotations.NonNull Request request, int age) {
        if (NetUtils.isConnected()) {
            if (age <= 0) {
                return request.newBuilder()
                        .cacheControl(CacheControl.FORCE_NETWORK)
                        .build();
            } else {
                return request.newBuilder()
                        .cacheControl(new CacheControl.Builder().maxAge(age, TimeUnit.SECONDS).build())
                        .build();
            }
        } else {
            return request.newBuilder()
                    .cacheControl(CacheControl.FORCE_CACHE)
                    .build();
        }
    }
}