package com.lukedeighton.typedadapter.row.templates;

import android.support.annotation.LayoutRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class InflatedRow<T> extends AbsRowType<T> {
    private int mLayoutResId;

    public InflatedRow(T item, @LayoutRes int layoutResId) {
        this(item, layoutResId, true);
    }

    public InflatedRow(T item, @LayoutRes int layoutResId, boolean isEnabled) {
        super(item, isEnabled);
        mLayoutResId = layoutResId;
    }

    @Override
    public View createView(LayoutInflater inflater, ViewGroup parent, int position) {
        return inflater.inflate(mLayoutResId, parent, false);
    }

    @Override
    public void bindView(View view, int position) {
    }
}
