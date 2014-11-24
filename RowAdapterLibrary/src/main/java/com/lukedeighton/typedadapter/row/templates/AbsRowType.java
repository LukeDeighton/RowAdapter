package com.lukedeighton.typedadapter.row.templates;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lukedeighton.typedadapter.row.RowType;

public abstract class AbsRowType<T> implements RowType {
    private boolean mIsEnabled;
    private T mItem;

    public AbsRowType(T item, boolean isEnabled) {
        mItem = item;
        mIsEnabled = isEnabled;
    }

    public T getItem() {
        return mItem;
    }

    @Override
    public boolean isEnabled() {
        return mIsEnabled;
    }

    @Override
    public View createDropDownView(LayoutInflater inflater, ViewGroup parent, int position) {
        return createView(inflater, parent, position);
    }

    @Override
    public void bindDropDownView(View view, int position) {
        bindView(view, position);
    }
}
