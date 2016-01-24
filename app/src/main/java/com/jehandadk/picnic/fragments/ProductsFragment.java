package com.jehandadk.picnic.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import com.jehandadk.picnic.R;
import com.jehandadk.picnic.adapters.ProductsAdapter;
import com.jehandadk.picnic.data.models.Err;
import com.jehandadk.picnic.data.responses.ProductsListResponse;
import com.jehandadk.picnic.services.IPicnicService;

import java.io.IOException;
import java.lang.annotation.Annotation;

import javax.inject.Inject;

import butterknife.BindInt;
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Result;
import retrofit2.Retrofit;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by jehandad.kamal on 1/24/2016.
 */
public class ProductsFragment extends ListFragment {


    @Inject
    IPicnicService api;
    @Inject
    Retrofit retrofit;
    @BindInt(R.integer.cols_products_grid)
    int coloumns;
    ProductsAdapter adapter;
    Subscription subscribe;
    ProductsListResponse data;
    Action1<Throwable> errorHandle = t -> {
        onLoadingFinished();
        if (t instanceof IOException) {
            setErrMessage(R.string.msg_err_internet);
        } else {
            setErrMessage(R.string.msg_err_unknown); // Cant set any integer, Can only use StringRes courtest of support lib
        }
    };
    Action1<Result<ProductsListResponse>> resultsHandler = result -> {
        onLoadingFinished();
        if (result.isError()) {
            errorHandle.call(result.error());
        } else if (!result.response().isSuccess()) {
            Converter<ResponseBody, Err> convert = retrofit.responseBodyConverter(Err.class, new Annotation[0]);
            try {
                Err err = convert.convert(result.response().errorBody());
                setErrMessage(err.getMessage());
            } catch (IOException e) {
                setErrMessage(R.string.msg_err_unknown);
            }
            return;
        } else {
            data = result.response().body();
            adapter.addAll(data.getProducts());
        }
    };

    /**
     * So that all constants related to extras that need to be set or are required by this fragment
     * are bound to this class only.
     *
     * @return
     */
    public static Fragment newFragment() {
        return new ProductsFragment();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getMainComponent().inject(this);
        adapter = new ProductsAdapter();
        list.setAdapter(adapter);
        list.setLayoutManager(new StaggeredGridLayoutManager(coloumns, StaggeredGridLayoutManager.VERTICAL));
        list.setItemAnimator(new SlideInUpAnimator());
        loadData();
    }

    @Override
    protected void retry() {
        loadData();
    }

    protected void loadData() {

        if (data != null) {
            adapter.addAll(data.getProducts());
            return;
        }

        onLoadingStarted();
        subscribe = api.getProducts().cache()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(resultsHandler, errorHandle);
    }

    @Override
    public void onPause() {
        super.onPause();
        subscribe.unsubscribe();
    }
}
