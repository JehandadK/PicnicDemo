package com.jehandadk.picnic.data.responses;

import com.jehandadk.picnic.data.models.Product;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jehandad.kamal on 1/24/2016.
 */
public class ProductsListResponse {

    private List<Product> products = new ArrayList<>();

    public List<Product> getProducts() {
        return products;
    }
}
