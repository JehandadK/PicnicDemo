package com.jehandadk.picnic.modules;

import com.jehandadk.picnic.PicnicApp;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {

    PicnicApp mApplication;

    public AppModule(PicnicApp application) {
        mApplication = application;
    }

    @Provides
    @Singleton
    PicnicApp providesApplication() {
        return mApplication;
    }
}