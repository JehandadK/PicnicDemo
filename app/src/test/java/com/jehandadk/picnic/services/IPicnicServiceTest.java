package com.jehandadk.picnic.services;

import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.jehandadk.picnic.BuildConfig;
import com.jehandadk.picnic.data.models.Product;
import com.jehandadk.picnic.data.responses.ProductsListResponse;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.logging.HttpLoggingInterceptor;

import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;

import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

import static org.hamcrest.CoreMatchers.anyOf;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by jehandad.kamal on 1/24/2016.
 * <p>
 * Class helps in creating models from responses and helps mitigates version changes between apis.
 * V useful when your app has 150+ apis and its swagger doc is outdated. And the team is too busy to keep up. :)
 * <p>
 * This isnt a black box test but to create and parse responses and
 * keep that the api hasnt been revised without revising client api access methods.
 * <p>
 * The basic theory is warning about any variables that are ignored in parsing and crashing
 * when mistmatched types are used
 *
 * @see <a href="http://arandell.royalcyber.com/webapp/wcs/stores/servlet/swagger/index.html#!/product/findProductsBySearchTerm_get_0" >Example Api Docs of my previous app</a>
 */

public class IPicnicServiceTest {
    static IPicnicService service;

    @BeforeClass
    public static void createClient() {
        service = new Retrofit.Builder()
                .client(getClient())
                .addConverterFactory(GsonConverterFactory.create(new Gson()))
                .baseUrl(BuildConfig.ENDPOINT)
                .build()
                .create(IPicnicService.class);
    }

    @NonNull
    private static OkHttpClient getClient() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient();
        client.networkInterceptors().add(logging);
        return client;
    }

    @Test
    public void testGetProducts() throws Exception {
        Response<List<Product>> response = service.getProducts().execute();
        assertThat(response.code(), is(anyOf(equalTo(200), equalTo(304))));
    }

    @Test
    public void testGetProductDetail() throws Exception {
        Response<ProductsListResponse> response = service.getProductDetail("1").execute();
        assertThat(response.code(), is(anyOf(equalTo(200), equalTo(304))));
        assertThat(response.body().getProducts(), is(notNullValue()));

        // Make various assumptions here about the api, though not required in a well defined api.
        // Assumptions like the amount is in string but always parsable through currency parser
        assertThat(response.body().getProducts().size(), org.hamcrest.Matchers.greaterThan(0));

    }
}