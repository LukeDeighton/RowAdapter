package com.lukedeighton.typedadapter.adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;

import com.lukedeighton.typedadapter.row.RowType;

import java.util.ArrayList;
import java.util.List;

public class CursorRowAdapter<U extends RowType, T> extends CursorAdapter {
    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private CursorMapper<U, T> mCursorMapper;
    private List<U> mRows;
    private int mQueriedIndex;

    public CursorRowAdapter(Context context, Cursor c, CursorMapper<U, T> cursorMapper) {
        super(context, c, false);
        mCursorMapper = cursorMapper;
        mLayoutInflater = LayoutInflater.from(context);
        mRows = new ArrayList<U>(c.getCount());
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        int position = cursor.getPosition();
        RowType row = queryRow(context, cursor, position);
        return row.createView(mLayoutInflater, viewGroup, position);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        int position = cursor.getPosition();
        RowType row = queryRow(context, cursor, position);
        row.bindView(view, cursor.getPosition());
    }

    private U queryRow(Context context, Cursor cursor, int position) {
        U row;
        if (position >= mQueriedIndex) {
            row = mCursorMapper.createRow(context, mCursorMapper.map(cursor), position);
            mRows.add(row);
            mQueriedIndex++;
        } else {
            row = mRows.get(position);
        }
        return row;
    }

    //TODO handle dropdown view
    @Override
    public View newDropDownView(Context context, Cursor cursor, ViewGroup parent) {
        return super.newDropDownView(context, cursor, parent);
    }

    public U getItem(int position) {
        Object cursor = super.getItem(position);
        if (!(cursor instanceof Cursor)) {
             return null;
        }
        return queryRow(mContext, (Cursor) cursor, position);
    }
}
