package com.lukedeighton.typedadapter.row;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public interface RowType extends Row {
    View createView(LayoutInflater inflater, ViewGroup parent, int position);

    void bindView(View view, int position);

    View createDropDownView(LayoutInflater inflater, ViewGroup parent, int position);

    void bindDropDownView(View view, int position);

    boolean isEnabled();
}