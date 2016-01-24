package com.jehandadk.picnic.services;

import retrofit2.Response;
import retrofit2.Result;
import rx.Subscriber;

/**
 * Created by jehandad.kamal on 1/25/2016.
 */
public abstract class HandlerSubscriber<S extends Result<T>, T> extends Subscriber<S> {

    LoadingListener loadingListener;
    ErrorListener errorListener;
//    T data;

    public HandlerSubscriber(LoadingListener loadingListener, ErrorListener errorListener) {
        super();
        this.loadingListener = loadingListener;
        this.errorListener = errorListener;
        if (loadingListener != null)
            loadingListener.onLoadingStarted();
    }

    @Override
    public void onCompleted() {
        if (loadingListener != null)
            loadingListener.onLoadingFinished();
        loadingListener = null;
    }

    @Override
    public void onError(Throwable e) {
        if (errorListener != null)
            errorListener.handleError(e);
    }

    @Override
    public void onNext(S result) {
        if (result.isError()) {
            onError(result.error());
            return;
        }
        Response<T> response = result.response();
        int code = response.code();
        if (code >= 200 && code < 300) {
            success(result.response());
        } else if (code == 401) {
            unauthenticated(response);
        } else if (code >= 400 && code < 500) {
            clientError(response);
        } else if (code >= 500 && code < 600) {
            serverError(response);
        } else {
            unexpectedError(new RuntimeException("Unexpected response " + response));
        }
    }

    protected void clientError(Response<T> response) {
        //TODO: Parse Error Body
    }

    public void unexpectedError(RuntimeException e) {
        if (errorListener != null)
            errorListener.handleError(e);
    }

    public void serverError(Response<T> response) {
        //TODO: Parse Error Body
    }

    public void unauthenticated(Response<T> response) {
        //TODO: Parse Error Body
    }

    public abstract void success(Response<T> response);
}
