package com.jehandadk.picnic.fragments;

import android.os.Bundle;
import android.view.View;

import com.jehandadk.picnic.data.models.Product;

import org.parceler.Parcels;

/**
 * Created by jehandad.kamal on 1/24/2016.
 */
public class ProductDetailFragment extends BaseFragment {

    private static final String KEY_PRODUDCT = "product";
    private Product product;

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
        product = Parcels.unwrap(getArguments().getParcelable(KEY_PRODUDCT));
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }
}
