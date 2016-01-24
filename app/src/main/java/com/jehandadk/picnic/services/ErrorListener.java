package com.jehandadk.picnic.services;

import com.jehandadk.picnic.data.models.Err;

/**
 * Created by jehandad.kamal on 1/25/2016.
 */
public interface ErrorListener {

    void handleError(Throwable t);

    void handleError(Err err);

    void handleError(Error error);
}
