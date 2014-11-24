package com.lukedeighton.typedadapter.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.lukedeighton.typedadapter.row.RowType;

import java.util.List;

public class RowAdapter extends BaseAdapter {
    private LayoutInflater mLayoutInflater;
    private List<? extends RowType> mRows;
    private ViewTypeManager mViewTypeManager;

    public RowAdapter(Context context, List<? extends RowType> rows) {
        this(context, rows, ViewTypeManager.DYNAMIC_COUNT);
    }

    /**
     * @param viewTypeCount is optional and is only required if you intend to add more ViewType's
     *                      later using notifyDataSetChange - else ignore this option
     */
    public RowAdapter(Context context, List<? extends RowType> rows, int viewTypeCount) {
        mLayoutInflater = LayoutInflater.from(context);
        mRows = rows;
        mViewTypeManager = new ViewTypeManager(rows, viewTypeCount);
    }

    @SuppressWarnings("unchecked")
    public List<RowType> getRows() {
        return (List<RowType>) mRows;
    }

    @Override
    public int getCount() {
        return mRows.size();
    }

    @Override
    public Object getItem(int position) {
        return mRows.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return mViewTypeManager.getItemViewType(mRows.get(position));
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        RowType row = mRows.get(position);

        if (convertView == null) {
            convertView = row.createView(mLayoutInflater, parent, position);
        }
        row.bindView(convertView, position);

        return convertView;
    }

    @Override
    @SuppressWarnings("unchecked")
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        RowType row = mRows.get(position);

        if (convertView == null) {
            convertView = row.createDropDownView(mLayoutInflater, parent, position);
        }
        row.bindDropDownView(convertView, position);

        return convertView;
    }

    @Override
    public int getViewTypeCount() {
        return mViewTypeManager.getViewTypeCount();
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public boolean isEmpty() {
        return mRows.isEmpty();
    }

    public boolean areAllItemsEnabled() {
        return true;
    }

    public boolean isEnabled(int position) {
        return mRows.get(position).isEnabled();
    }

    @Override
    public void notifyDataSetChanged() {
        mViewTypeManager.updateViewTypeInfo(mRows);
        super.notifyDataSetChanged();
    }
}
