package com.jehandadk.picnic;

import com.jehandadk.picnic.activities.MainActivity;
import com.jehandadk.picnic.fragments.ProductDetailFragment;
import com.jehandadk.picnic.fragments.ProductsFragment;
import com.jehandadk.picnic.modules.ApiModule;
import com.jehandadk.picnic.modules.AppModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(
        modules = {AppModule.class, ApiModule.class}
)
public interface MainComponent {
    void inject(MainActivity activity);

    void inject(ProductsFragment fragment);

    void inject(ProductDetailFragment fragment);
}