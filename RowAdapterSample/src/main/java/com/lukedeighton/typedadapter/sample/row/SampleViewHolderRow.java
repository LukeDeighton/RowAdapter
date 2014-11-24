package com.lukedeighton.typedadapter.sample.row;

import android.view.View;
import android.widget.TextView;

import com.lukedeighton.typedadapter.row.templates.ViewHolderRow;
import com.lukedeighton.typedadapter.sample.R;

public class SampleViewHolderRow extends ViewHolderRow<String, SampleViewHolderRow.ViewHolder> {
    public SampleViewHolderRow(String item) {
        super(item, R.layout.row_header);
    }

    @Override
    public ViewHolder createViewHolder(View view, int position) {
        TextView textView = (TextView) view.findViewById(R.id.textview);
        return new ViewHolder(textView);
    }

    @Override
    public void bindViewHolder(ViewHolder viewHolder, String item, int position) {
        viewHolder.mTextView.setText(item);
    }

    static class ViewHolder {
        TextView mTextView;

        ViewHolder(TextView textView) {
            mTextView = textView;
        }
    }
}
