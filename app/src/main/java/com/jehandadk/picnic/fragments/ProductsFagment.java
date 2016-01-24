package com.jehandadk.picnic.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

/**
 * Created by jehandad.kamal on 1/24/2016.
 */
public class ProductsFagment extends ListFragment {


    /**
     * So that all constants related to extras that need to be set or are required by this fragment
     * are bound to this class only.
     *
     * @return
     */
    public static Fragment newFragment() {
        return new ProductsFagment();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loadData();
    }

    @Override
    protected void loadData() {

    }
}
