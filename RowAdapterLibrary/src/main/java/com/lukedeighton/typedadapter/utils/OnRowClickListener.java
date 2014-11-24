package com.lukedeighton.typedadapter.utils;

import android.view.View;

import com.lukedeighton.typedadapter.row.Row;

public interface OnRowClickListener<T extends Row> {
    void onRowClick(T typedRow, View view, int position);
}
