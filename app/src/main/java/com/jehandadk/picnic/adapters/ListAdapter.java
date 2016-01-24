package com.jehandadk.picnic.adapters;

import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jehandad.kamal on 1/24/2016.
 */
public abstract class ListAdapter<VH extends RecyclerView.ViewHolder, ENTITY> extends RecyclerView.Adapter<VH> {

    protected List<ENTITY> list;

    public ListAdapter(List<ENTITY> list) {
        this.list = list;
    }

    public ListAdapter() {
        this.list = new ArrayList<>();
    }

    @Override
    public void onBindViewHolder(VH holder, int position) {
        onBindViewHolder(holder, list.get(position));
    }

    public abstract void onBindViewHolder(VH holder, ENTITY entity);

    @Override
    public int getItemCount() {
        return list.size();
    }

    public List<ENTITY> getList() {
        return list;
    }

    public void addAll(List<ENTITY> newItems) {
        list.addAll(newItems);
        notifyDataSetChanged();
    }
}
