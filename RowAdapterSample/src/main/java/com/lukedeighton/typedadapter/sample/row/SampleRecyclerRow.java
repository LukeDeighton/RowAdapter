package com.lukedeighton.typedadapter.sample.row;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.lukedeighton.typedadapter.row.templates.ViewHolderRow;
import com.lukedeighton.typedadapter.sample.R;

public class SampleRecyclerRow extends ViewHolderRow<String, SampleRecyclerRow.ViewHolder> {
    public SampleRecyclerRow(String item) {
        super(item, R.layout.row_header);
    }

    @Override
    public ViewHolder createViewHolder(View view, int position) {
        return new ViewHolder(view);
    }

    @Override
    public void bindViewHolder(ViewHolder viewHolder, String item, int position) {
        viewHolder.mTextView.setText(item);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView mTextView;

        ViewHolder(View view) {
            super(view);
            mTextView = (TextView) view.findViewById(R.id.textview);
        }
    }
}
