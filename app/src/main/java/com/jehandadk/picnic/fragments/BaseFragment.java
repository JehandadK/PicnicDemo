package com.jehandadk.picnic.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

import com.jehandadk.picnic.MainComponent;
import com.jehandadk.picnic.PicnicApp;
import com.jehandadk.picnic.activities.BaseActivity;

import butterknife.ButterKnife;

/**
 * Created by jehandad.kamal on 1/24/2016.
 */
public class BaseFragment extends Fragment {

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    protected BaseActivity getBaseActivity() {
        return (BaseActivity) getActivity();
    }

    public PicnicApp getPicnicApp() {
        return getBaseActivity().getPicnicApp();
    }

    public MainComponent getMainComponent() {
        return getPicnicApp().getMainComponent();
    }
}
