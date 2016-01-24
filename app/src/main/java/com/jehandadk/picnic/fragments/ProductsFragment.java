package com.jehandadk.picnic.fragments;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import com.jehandadk.picnic.R;
import com.jehandadk.picnic.adapters.ProductsAdapter;
import com.jehandadk.picnic.data.models.Err;
import com.jehandadk.picnic.data.models.Product;
import com.jehandadk.picnic.data.responses.ProductsListResponse;
import com.jehandadk.picnic.services.IPicnicService;

import org.parceler.Parcels;

import java.io.IOException;
import java.lang.annotation.Annotation;

import javax.inject.Inject;

import butterknife.BindInt;
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Result;
import retrofit2.Retrofit;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by jehandad.kamal on 1/24/2016.
 */
public class ProductsFragment extends ListFragment implements ProductsAdapter.ProductClickedListener {


    private static final String KEY_PRODUCTS = "products";
    @Inject
    IPicnicService api;
    @Inject
    Retrofit retrofit;
    @BindInt(R.integer.cols_products_grid)
    int coloumns;
    ProductsAdapter adapter;
    Subscription subscribe;
    ProductsListResponse data;
    Observable<Result<ProductsListResponse>> request;


    Action1<Throwable> errorHandle = t -> {
        onLoadingFinished();
        if (t instanceof IOException) {
            setErrMessage(R.string.msg_err_internet);
        } else {
            setErrMessage(R.string.msg_err_unknown); // Cant set any integer, Can only use StringRes courtesy of support lib
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
            setData();
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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getMainComponent().inject(this);
        request = api.getProducts().cache();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        adapter = new ProductsAdapter(this);
        list.setAdapter(adapter);
        list.setLayoutManager(new StaggeredGridLayoutManager(coloumns, StaggeredGridLayoutManager.VERTICAL));
        list.setItemAnimator(new SlideInUpAnimator());
    }

    @Override
    public void onStart() {
        super.onStart();
        loadData();
    }

    @Override
    protected void retry() {
        loadData();
    }

    protected void loadData() {
        if (data != null) {
            setData();
            return;
        }
        onLoadingStarted();
        subscribe = request
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(resultsHandler, errorHandle);
    }

    private void setData() {
        onLoadingFinished();
        adapter.setAll(data.getProducts());
    }

    @Override
    public void onPause() {
        super.onPause();
        if (subscribe != null)
            subscribe.unsubscribe();
    }

    @Override
    public void onProductClicked(Product product) {
        getFragmentManager()
                .beginTransaction()
                .addToBackStack(null)
                .replace(R.id.frame_fragment, ProductDetailFragment.newFragment(product))
                .commit();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (data != null)
            outState.putParcelable(KEY_PRODUCTS, Parcels.wrap(data));
    }

    @Override
    public void onViewStateRestored(Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if (savedInstanceState == null) return;
        Parcelable parcelable = savedInstanceState.getParcelable(KEY_PRODUCTS);
        if (parcelable != null) {
            data = Parcels.unwrap(parcelable);
            setData();
        }
    }
}
