package com.jehandadk.picnic.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.jehandadk.picnic.R;
import com.jehandadk.picnic.Utils;
import com.jehandadk.picnic.adapters.holders.ProductHolder;
import com.jehandadk.picnic.data.models.Product;

/**
 * Created by jehandad.kamal on 1/24/2016.
 */
public class ProductsAdapter extends ListAdapter<ProductHolder, Product> {

    @Override
    public ProductHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ProductHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product, null));
    }

    @Override
    public void onBindViewHolder(ProductHolder holder, Product product) {
        Glide.with(holder.itemView.getContext()).load(product.getImage()).into(holder.imgProduct);
        holder.txtProductPrice.setText(Utils.formatCurrency(product.getPrice()));
        holder.txtProductTitle.setText(product.getName());
    }
}