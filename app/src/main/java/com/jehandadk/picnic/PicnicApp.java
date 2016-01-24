package com.jehandadk.picnic;

import android.app.Application;

import com.bumptech.glide.Glide;
import com.bumptech.glide.MemoryCategory;
import com.jehandadk.picnic.modules.ApiModule;
import com.jehandadk.picnic.modules.AppModule;

/**
 * Created by jehandad.kamal on 1/24/2016.
 */
public class PicnicApp extends Application {

    MainComponent mainComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        Glide.get(this).setMemoryCategory(MemoryCategory.HIGH);
        mainComponent = DaggerMainComponent.builder()
                .appModule(new AppModule(this))
                .apiModule(new ApiModule())
                .build();
    }

    public MainComponent getMainComponent() {
        return mainComponent;
    }
}
