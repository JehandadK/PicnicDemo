package com.jehandadk.picnic.services;

import com.jehandadk.picnic.BuildConfig;
import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.logging.HttpLoggingInterceptor;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

public class ServiceFactory {

    /**
     * Creates a retrofit service from an arbitrary class (clazz)
     *
     * @param clazz Java interface of the retrofit service
     * @return retrofit service with defined endpoint
     */
    public static <T> T createRetrofitService(final Class<T> clazz) {
        final Retrofit restAdapter = new Retrofit.Builder()
                .client(getClient())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BuildConfig.ENDPOINT)
                .build();
        T service = restAdapter.create(clazz);

        return service;
    }

    public static OkHttpClient getClient() {
        OkHttpClient client = new OkHttpClient();
        client.interceptors().add(getLogger());
        return client;
    }

    public static Interceptor getLogger() {
        HttpLoggingInterceptor logger = new HttpLoggingInterceptor();
        logger.setLevel(BuildConfig.DEBUG ? HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.BASIC);
        return logger;
    }
}
