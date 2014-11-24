package com.lukedeighton.typedadapter.row.templates;

import android.support.annotation.LayoutRes;
import android.view.View;

import com.lukedeighton.typedadapter.row.ExpandableRowType;

public abstract class ExpandableRow<T> extends InflatedRow<T> implements ExpandableRowType {
    public ExpandableRow(T item, @LayoutRes int layoutResId) {
        super(item, layoutResId);
    }

    public ExpandableRow(T item, @LayoutRes int layoutResId, boolean isEnabled) {
        super(item, layoutResId, isEnabled);
    }

    @Override
    public void bindView(View view, int position) {
        throw new UnsupportedOperationException("To be used only in a ExpandableRowAdapter");
    }

    public abstract void bindView(View view, int position, boolean isExpanded);

    @Override
    public boolean isEnabled() {
        throw new UnsupportedOperationException("Cannot disable click events on group row");
    }
}
