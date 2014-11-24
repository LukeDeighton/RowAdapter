package com.lukedeighton.typedadapter.row;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public interface ExpandableRowType extends Row {
    View createView(LayoutInflater inflater, ViewGroup parent, int position);

    void bindView(View view, int position, boolean isExpanded);
}
