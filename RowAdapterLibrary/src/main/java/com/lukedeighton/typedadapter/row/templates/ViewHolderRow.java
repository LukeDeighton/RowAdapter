package com.lukedeighton.typedadapter.row.templates;

import android.support.annotation.LayoutRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lukedeighton.typedadapter.row.ViewHolderRowType;

public abstract class ViewHolderRow<T, U> extends InflatedRow<T> implements ViewHolderRowType<U> {
    public ViewHolderRow(T item, @LayoutRes int layoutResId) {
        super(item, layoutResId);
    }

    public ViewHolderRow(T item, @LayoutRes int layoutResId, boolean isEnabled) {
        super(item, layoutResId, isEnabled);
    }

    @Override
    public View createView(LayoutInflater inflater, ViewGroup parent, int position) {
        View view = super.createView(inflater, parent, position);
        view.setTag(createViewHolder(view, position));
        return view;
    }

    @Override
    public U createViewHolder(LayoutInflater inflater, ViewGroup viewGroup, int position) {
        View view = super.createView(inflater, viewGroup, position);
        return createViewHolder(view, position);
    }

    public abstract U createViewHolder(View view, int position);

    @Override
    @SuppressWarnings("unchecked")
    public void bindView(View view, int position) {
        bindViewHolder((U) view.getTag(), position);
    }

    @Override
    public void bindViewHolder(U viewHolder, int position) {
        bindViewHolder(viewHolder, getItem(), position);
    }

    public abstract void bindViewHolder(U viewHolder, T item, int position);
}
