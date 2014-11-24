package com.lukedeighton.typedadapter.row.templates;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lukedeighton.typedadapter.utils.RowUtils;

public abstract class InstantiatedRow<T, U extends View> extends AbsRowType<T> {
    private Class<U> mClass;
    private float mDensity;

    public InstantiatedRow(Class<U> clazz, T item) {
        this(clazz, item, true);
    }

    public InstantiatedRow(Class<U> clazz, T item, boolean isEnabled) {
        super(item, isEnabled);
        mClass = clazz;
    }

    @Override
    public View createView(LayoutInflater inflater, ViewGroup parent, int position) {
        Context context = parent.getContext();
        mDensity = context.getResources().getDisplayMetrics().density;
        U view;
        try {
            view = RowUtils.newInstance(context, mClass);
        } catch (Exception e) {
            throw new RuntimeException("unable to create view: " + e);
        }
        initView(view, parent);
        return view;
    }

    @Override
    public void bindView(View view, int position) {
        bindView(getItem(), position, mClass.cast(view));
    }

    public float getResourceDensity() {
        return mDensity;
    }

    public abstract void initView(U view, ViewGroup parent);

    public abstract void bindView(T item, int position, U view);
}
