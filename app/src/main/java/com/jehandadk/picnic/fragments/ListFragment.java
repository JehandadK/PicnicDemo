package com.jehandadk.picnic.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.jehandadk.picnic.R;
import com.jehandadk.picnic.services.LoadingListener;

import butterknife.Bind;

/**
 * Created by jehandad.kamal on 1/24/2016.
 */
public abstract class ListFragment extends BaseFragment implements LoadingListener {

    @Bind(R.id.list)
    RecyclerView list;
    @Bind(R.id.list_progress)
    ProgressBar listProgress;
    @Bind(R.id.txt_error)
    TextView txtError;
    @Bind(R.id.btn_retry)
    Button btnRetry;
    @Bind(R.id.layout_list_error)
    LinearLayout layoutListError;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, null);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btnRetry.setOnClickListener((v) -> retry());
    }

    protected abstract void retry();

    protected void showList() {
        list.setVisibility(View.VISIBLE);
        listProgress.setVisibility(View.INVISIBLE);
        layoutListError.setVisibility(View.INVISIBLE);
    }

    protected void showErrorLayout() {
        list.setVisibility(View.INVISIBLE);
        listProgress.setVisibility(View.INVISIBLE);
        layoutListError.setVisibility(View.VISIBLE);
    }

    protected void showLoadingLayout() {
        list.setVisibility(View.INVISIBLE);
        listProgress.setVisibility(View.VISIBLE);
        layoutListError.setVisibility(View.INVISIBLE);
    }

    /**
     * Beware set retry button listener before.
     *
     * @param errMessage
     */
    protected void setErrMessage(@StringRes int errMessage) {
        showErrorLayout();
        txtError.setText(errMessage);
    }

    protected void setErrMessage(String message) {
        showErrorLayout();
        txtError.setText(message);
    }

    @Override
    public void onLoadingFinished() {
        showList();
    }

    @Override
    public void onLoadingStarted() {
        showLoadingLayout();
    }
}

