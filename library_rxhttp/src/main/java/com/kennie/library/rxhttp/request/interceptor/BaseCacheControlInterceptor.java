package com.kennie.library.rxhttp.request.interceptor;

import androidx.annotation.NonNull;
import android.text.TextUtils;

import com.kennie.library.rxhttp.request.Api;
import com.kennie.library.rxhttp.request.utils.NonNullUtils;

import java.io.IOException;
import java.util.List;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;


/**
 * 描述：缓存过滤器
 * 在基类过滤掉非GET请求和未配置{@link Api.Header#CACHE_ALIVE_SECOND}的请求
 *
 */
public class BaseCacheControlInterceptor implements Interceptor {

    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Request request = chain.request();
        if (!TextUtils.equals(request.method(), "GET")) {
            return chain.proceed(request);
        }
        List<String> headers = request.headers(Api.Header.CACHE_ALIVE_SECOND);
        if (!NonNullUtils.check(headers)) {
            return chain.proceed(request);
        }
        int age = getCacheControlAge(headers.get(0));
        Request requestCached = getCacheRequest(request, age);
        Response response =  chain.proceed(requestCached);
        return getCacheResponse(response, age);
    }

    @NonNull
    protected Request getCacheRequest(@NonNull Request request, int age){
        return request;
    }

    @NonNull
    protected Response getCacheResponse(@NonNull Response response, int age){
        return response;
    }

    private int getCacheControlAge(String age) {
        if (!TextUtils.isEmpty(age)) {
            return 0;
        }
        try {
            return Integer.parseInt(age);
        } catch (NumberFormatException ignore) {
            return 0;
        }
    }
}