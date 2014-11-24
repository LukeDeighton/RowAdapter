package com.lukedeighton.typedadapter.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lukedeighton.typedadapter.row.Row;
import com.lukedeighton.typedadapter.row.ViewHolderRowType;
import com.lukedeighton.typedadapter.utils.OnRowClickListener;
import com.lukedeighton.typedadapter.utils.RowClickHandler;

import org.lucasr.twowayview.ItemClickSupport;

import java.util.List;

public class RecyclerRowAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private LayoutInflater mLayoutInflater;
    private List<? extends ViewHolderRowType<? extends RecyclerView.ViewHolder>> mRows;
    private ViewTypeManager mViewTypeManager;

    public RecyclerRowAdapter(Context context,
                              List<? extends ViewHolderRowType<? extends RecyclerView.ViewHolder>> rows) {
        mLayoutInflater = LayoutInflater.from(context);
        mRows = rows;
        mViewTypeManager = new ViewTypeManager(mRows);
    }

    public static <T extends Row> void setOnRowClickListener(RecyclerView recyclerView,
                                                             Class<T> clazz, OnRowClickListener<T> listener) {
        RowClickHandler handler = new RowClickHandler();
        handler.put(clazz, listener);
        setRowClickHandler(recyclerView, handler);
    }

    public static void setRowClickHandler(RecyclerView recyclerView, final RowClickHandler handler) {
        final ItemClickSupport itemClick = ItemClickSupport.addTo(recyclerView);
        itemClick.setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerView parent, View child, int position, long id) {
                RecyclerView.Adapter<?> adapter = parent.getAdapter();
                if (!(adapter instanceof RecyclerRowAdapter)) return;
                List<? extends ViewHolderRowType<? extends RecyclerView.ViewHolder>> rows = ((RecyclerRowAdapter) adapter).getRows();
                if (position < 0 || position >= rows.size()) return;
                ViewHolderRowType<? extends RecyclerView.ViewHolder> row = rows.get(position);
                if (!row.isEnabled()) return;
                handler.onRowClick(row, child, position);
            }
        });
    }

    public List<? extends ViewHolderRowType<? extends RecyclerView.ViewHolder>> getRows() {
        return mRows;
    }

    public ViewHolderRowType getItem(int position) {
        return mRows.get(position);
    }

    @Override
    public int getItemViewType(int position) {
        return mViewTypeManager.getItemViewType(mRows.get(position));
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, final int i) {
        final ViewHolderRowType<? extends RecyclerView.ViewHolder> row = mRows.get(i);
        RecyclerView.ViewHolder viewHolder = row.createViewHolder(mLayoutInflater, viewGroup, i);
        return viewHolder;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder t, int i) {
        ViewHolderRowType<RecyclerView.ViewHolder> row =
                (ViewHolderRowType<RecyclerView.ViewHolder>) mRows.get(i);
        row.bindViewHolder(t, i);
    }

    @Override
    public int getItemCount() {
        return mRows.size();
    }
}
