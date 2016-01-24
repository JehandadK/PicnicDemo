package com.jehandadk.picnic.activities;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;

import com.jehandadk.picnic.MainComponent;
import com.jehandadk.picnic.PicnicApp;
import com.jehandadk.picnic.R;
import com.jehandadk.picnic.services.LoadingListener;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by jehandad.kamal on 1/24/2016.
 */
public class BaseActivity extends AppCompatActivity implements LoadingListener {
    AtomicInteger loaderCount = new AtomicInteger(0);
    ProgressDialog mProgressDialog;
    private boolean isResumed = false;

    @Override
    protected void onPause() {
        super.onPause();
        isResumed = false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        isResumed = true;
        if (loaderCount.get() > 0) showProgressDialog();
        else hideProgressDialog();
    }


    @Override
    public void onLoadingStarted() {
        int i = loaderCount.incrementAndGet();
        if (i > 0 && isResumed && mProgressDialog == null) showProgressDialog();

    }

    @Override
    public void onLoadingFinished() {
        if (loaderCount.decrementAndGet() == 0)
            hideProgressDialog();
    }

    private void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            try {
                mProgressDialog.dismiss();
            } catch (Exception e) {
            }
        }
        mProgressDialog = null;
    }

    private void showProgressDialog() {
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.setCancelable(true);
        mProgressDialog.setMessage(getString(R.string.msg_loading));
        mProgressDialog.show();
    }


    public PicnicApp getPicnicApp() {
        return (PicnicApp) getApplication();
    }

    public MainComponent getMainComponent() {
        return getPicnicApp().getMainComponent();
    }
}
