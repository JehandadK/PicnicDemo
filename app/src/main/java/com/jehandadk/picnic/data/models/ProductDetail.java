package com.jehandadk.picnic.data.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class ProductDetail extends Product {

    @SerializedName("description")
    @Expose
    private String description;


    public ProductDetail(Product product) {
        super(product);
    }

    public static ProductDetail with(Product product) {
        return new ProductDetail(product);
    }

    /**
     * @return The description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description The description
     */
    public void setDescription(String description) {
        this.description = description;
    }
}