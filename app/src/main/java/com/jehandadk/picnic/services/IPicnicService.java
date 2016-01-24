package com.jehandadk.picnic.services;

import com.jehandadk.picnic.data.models.Product;
import com.jehandadk.picnic.data.responses.ProductsListResponse;

import java.util.List;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Path;

/**
 * Created by jehandad.kamal on 1/24/2016.
 */
public interface IPicnicService {

    @GET("cart/list")
    Call<List<Product>> getProducts();

    @GET("cart/{product_id}/detail ")
    Call<ProductsListResponse> getProductDetail(@Path("product_id") String productId);
}
