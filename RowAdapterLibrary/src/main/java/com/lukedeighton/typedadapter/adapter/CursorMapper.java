package com.lukedeighton.typedadapter.adapter;

import android.content.Context;
import android.database.Cursor;

import com.lukedeighton.typedadapter.row.RowType;

public interface CursorMapper<U extends RowType, T> {
    T map(Cursor c);

    U createRow(Context context, T item, int position);
}
