package com.lukedeighton.typedadapter.row.templates;

import android.support.annotation.LayoutRes;
import android.view.View;

import com.lukedeighton.typedadapter.utils.RowUtils;

//TODO this doesn't seem to work... just remove it?
/*public*/ abstract class InstantiatedVHRow<T, U> extends ViewHolderRow<T, U> {
    private Class<U> mClass;

    public InstantiatedVHRow(Class<U> clazz, T item, @LayoutRes int layoutResId) {
        super(item, layoutResId);
        mClass = clazz;
    }

    public InstantiatedVHRow(Class<U> clazz, T item, @LayoutRes int layoutResId, boolean isEnabled) {
        super(item, layoutResId, isEnabled);
        mClass = clazz;
    }

    @Override
    public U createViewHolder(View view, int position) {
        U viewHolder;
        try {
            viewHolder = RowUtils.newInstance(view, mClass);
        } catch (Exception e) {
            throw new RuntimeException("unable to create viewHolder without single arg constructor: " + e);
        }
        return viewHolder;
    }
}
