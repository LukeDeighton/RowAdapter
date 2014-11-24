package com.lukedeighton.typedadapter.row;

import android.view.LayoutInflater;
import android.view.ViewGroup;

public interface ViewHolderRowType<T> extends Row {
    T createViewHolder(LayoutInflater inflater, ViewGroup viewGroup, int position);

    void bindViewHolder(T viewHolder, int position);

    boolean isEnabled();
}
