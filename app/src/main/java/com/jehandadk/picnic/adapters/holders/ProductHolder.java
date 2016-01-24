package com.jehandadk.picnic.adapters.holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jehandadk.picnic.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by jehandad.kamal on 1/24/2016.
 */
public class ProductHolder extends RecyclerView.ViewHolder {

    @Bind(R.id.img_product)
    public ImageView imgProduct;
    @Bind(R.id.txt_product_title)
    public TextView txtProductTitle;
    @Bind(R.id.txt_product_price)
    public TextView txtProductPrice;

    public ProductHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
