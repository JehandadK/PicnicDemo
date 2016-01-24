package com.jehandadk.picnic.services;

import com.jehandadk.picnic.data.models.ProductDetail;
import com.jehandadk.picnic.data.responses.ProductsListResponse;

import retrofit2.Result;
import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by jehandad.kamal on 1/24/2016.
 */
public interface IPicnicService {

    @GET("cart/list")
    Observable<Result<ProductsListResponse>> getProducts();

    @GET("cart/{product_id}/detail ")
    Observable<Result<ProductDetail>> getProductDetail(@Path("product_id") String productId);
}
