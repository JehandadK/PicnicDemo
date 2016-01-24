package com.jehandadk.picnic.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.jehandadk.picnic.R;
import com.jehandadk.picnic.data.models.Product;
import com.jehandadk.picnic.helpers.Utils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by jehandad.kamal on 1/24/2016.
 */
public class ProductsAdapter extends ListAdapter<ProductsAdapter.ProductHolder, Product> {

    ProductClickedListener listener;

    public ProductsAdapter(List<Product> list, ProductClickedListener listener) {
        super(list);
        this.listener = listener;
        setHasStableIds(true);
    }

    public ProductsAdapter(ProductClickedListener listener) {
        this(new ArrayList<>(), listener);
    }

    @Override
    public ProductHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ProductHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product, null));
    }

    @Override
    public void onBindViewHolder(ProductHolder holder, Product product) {
        Glide.with(holder.itemView.getContext()).load(product.getImage())
                .diskCacheStrategy(DiskCacheStrategy.ALL).crossFade().into(holder.imgProduct);
        holder.txtProductPrice.setText(Utils.formatCurrency(product.getPrice()));
        holder.txtProductTitle.setText(product.getName());
    }

    public interface ProductClickedListener {
        void onProductClicked(Product product);
    }

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
            itemView.setOnClickListener(v -> {
                Product product = list.get(getAdapterPosition());
                listener.onProductClicked(product);
            });
        }
    }
}
