package com.lukedeighton.typedadapter.row.templates.customview;

import android.view.View;
import android.view.ViewGroup;

import com.lukedeighton.typedadapter.row.templates.InstantiatedRow;

public class CustomViewRow<T, U extends View & ViewDataBinder<T>> extends InstantiatedRow<T, U> {
    public CustomViewRow(Class<U> clazz, T item) {
        super(clazz, item);
    }

    public CustomViewRow(Class<U> clazz, T item, boolean isEnabled) {
        super(clazz, item, isEnabled);
    }

    @Override
    public void initView(U view, ViewGroup parent) {
    }

    @Override
    public void bindView(T item, int position, U view) {
        view.bindData(item);
    }
}
