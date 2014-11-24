package com.lukedeighton.typedadapter.sample.row;

import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.view.View;
import android.widget.TextView;

import com.lukedeighton.typedadapter.row.templates.InflatedRow;

public class SimpleTextRow extends InflatedRow<String> {
    private int mTextViewResId;

    public SimpleTextRow(String item, @LayoutRes int layoutResId, @IdRes int textViewResId) {
        super(item, layoutResId);
        mTextViewResId = textViewResId;
    }

    @Override
    public void bindView(View view, int position) {
        ((TextView) view.findViewById(mTextViewResId)).setText(getItem());
    }
}
