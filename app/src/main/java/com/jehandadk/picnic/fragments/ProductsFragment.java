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
import com.jehandadk.picnic.services.ErrorListener;
import com.jehandadk.picnic.services.HandlerSubscriber;
import com.jehandadk.picnic.services.IPicnicService;
import com.jehandadk.picnic.services.LoadingListener;

import org.parceler.Parcels;

import java.io.IOException;

import javax.inject.Inject;

import butterknife.BindInt;
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;
import retrofit2.Response;
import retrofit2.Result;
import retrofit2.Retrofit;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by jehandad.kamal on 1/24/2016.
 */
public class ProductsFragment extends ListFragment implements ProductsAdapter.ProductClickedListener, ErrorListener {


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
                .subscribe(new ProductsSubscriber(this, this));
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

    @Override
    public void handleError(Throwable t) {
        if (t instanceof IOException) {
            setErrMessage(R.string.msg_err_internet);
        } else {
            setErrMessage(R.string.msg_err_unknown); // Cant set any integer, Can only use StringRes courtesy of support lib
        }
    }

    @Override
    public void handleError(Err err) {
        setErrMessage(err.getMessage());
    }

    @Override
    public void handleError(Error error) {
        // TODO: Handle Error, perhaps auto retry in some cases
    }

    public class ProductsSubscriber extends HandlerSubscriber<Result<ProductsListResponse>, ProductsListResponse> {

        public ProductsSubscriber(LoadingListener loadingListener, ErrorListener errorListener) {
            super(loadingListener, errorListener);
        }

        @Override
        public void success(Response<ProductsListResponse> response) {
            data = response.body();
            setData();
        }
    }
}
