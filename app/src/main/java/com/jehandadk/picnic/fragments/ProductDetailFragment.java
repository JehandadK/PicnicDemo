package com.jehandadk.picnic.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jehandadk.picnic.R;
import com.jehandadk.picnic.data.models.Product;
import com.jehandadk.picnic.data.models.ProductDetail;
import com.jehandadk.picnic.helpers.Utils;
import com.jehandadk.picnic.services.IPicnicService;

import org.parceler.Parcels;

import javax.inject.Inject;

import butterknife.Bind;
import retrofit2.Result;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by jehandad.kamal on 1/24/2016.
 */
public class ProductDetailFragment extends BaseFragment {

    private static final String KEY_PRODUDCT = "product";
    private static final String KEY_PRODUCT_DESC = "product_desc";
    @Bind(R.id.txt_product_title)
    TextView txtProductTitle;
    @Bind(R.id.txt_product_price)
    TextView txtProductPrice;
    @Bind(R.id.txt_product_desc)
    TextView txtProductDesc;
    @Bind(R.id.img_product_detail)
    ImageView imgProductDetail;
    @Inject
    IPicnicService api;
    Observable<Result<ProductDetail>> request;
    private Product product;
    private ProductDetail data;
    Action1<Result<ProductDetail>> resultsHandler = result -> {
        if (result.isError()) {
            //TODO: Handle Error
        } else if (!result.response().isSuccess()) {
            // TODO: Handle other issues
        } else {
            data = result.response().body();
            setData();
        }
    };
    private Subscription subscribe;

    public static ProductDetailFragment newFragment(Product product) {
        ProductDetailFragment fragment = new ProductDetailFragment();
        Bundle args = new Bundle();
        args.putParcelable(KEY_PRODUDCT, Parcels.wrap(product));
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getMainComponent().inject(this);
        product = Parcels.unwrap(getArguments().getParcelable(KEY_PRODUDCT));
        request = api.getProductDetail(product.getProductId()).cache();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product_detail, null);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setData();
    }

    @Override
    public void onStart() {
        super.onStart();
        loadData();
    }

    private void setData() {
        txtProductPrice.setText(Utils.formatCurrency(product.getPrice()));
        txtProductTitle.setText(product.getName());
        Glide.with(this).load(product.getImage()).placeholder(R.drawable.ic_picnic_logo).error(R.drawable.ic_info).crossFade().into(imgProductDetail);
        if (data != null)
            txtProductDesc.setText(data.getDescription());
    }

    private void loadData() {
        if (data != null) {
            setData();
            return;
        }
        subscribe = request
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(resultsHandler);
    }


    @Override
    public void onPause() {
        super.onPause();
        if (subscribe != null)
            subscribe.unsubscribe();
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(KEY_PRODUCT_DESC, data.getDescription());
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if (savedInstanceState == null) return;
        data = ProductDetail.with(product);
        data.setDescription(savedInstanceState.getString(KEY_PRODUCT_DESC));
    }
}
