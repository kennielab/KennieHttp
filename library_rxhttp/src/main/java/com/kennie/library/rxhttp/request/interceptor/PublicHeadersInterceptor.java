package com.kennie.library.rxhttp.request.interceptor;

import androidx.annotation.NonNull;

import com.kennie.library.rxhttp.core.RxHttp;
import com.kennie.library.rxhttp.request.setting.ParameterGetter;
import com.kennie.library.rxhttp.request.utils.NonNullUtils;

import java.io.IOException;
import java.util.Map;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


/**
 * 描述：添加公共请求头
 */
public class PublicHeadersInterceptor implements Interceptor {

    public static void addTo(@NonNull OkHttpClient.Builder builder) {
        Map<String, String> staticParameters = RxHttp.getRequestSetting().getStaticHeaderParameter();
        Map<String, ParameterGetter> dynamicParameters = RxHttp.getRequestSetting().getDynamicHeaderParameter();
        if (NonNullUtils.check(staticParameters, dynamicParameters)) {
            builder.addInterceptor(new PublicHeadersInterceptor());
        }
    }

    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Request request = chain.request();
        Request.Builder builder = request.newBuilder();
        Map<String, String> staticParameters = RxHttp.getRequestSetting().getStaticHeaderParameter();
        if (NonNullUtils.check(staticParameters)) {
            for (Map.Entry<String, String> entry : staticParameters.entrySet()) {
                builder.header(entry.getKey(), entry.getValue());
            }
        }
        Map<String, ParameterGetter> dynamicParameters = RxHttp.getRequestSetting().getDynamicHeaderParameter();
        if (NonNullUtils.check(dynamicParameters)) {
            for (Map.Entry<String, ParameterGetter> entry : dynamicParameters.entrySet()) {
                builder.header(entry.getKey(), entry.getValue().get());
            }
        }
        return chain.proceed(builder.build());
    }
}
