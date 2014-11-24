package com.lukedeighton.typedadapter.sample.row.customview;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lukedeighton.typedadapter.row.templates.customview.ViewDataBinder;
import com.lukedeighton.typedadapter.sample.R;

public class CustomView extends LinearLayout implements ViewDataBinder<CustomData> {
    private ImageView mImageView;
    private TextView mTextView;

    public CustomView(Context context) {
        super(context);
        inflateView(context);
    }

    public void inflateView(Context context) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.row_custom_view, this, true);
        mImageView = (ImageView) findViewById(R.id.row_custom_imageview);
        mTextView = (TextView) findViewById(R.id.row_custom_textview);
    }

    @Override
    public void bindData(CustomData item) {
        mImageView.setImageResource(item.getDrawableRes());
        mTextView.setText(item.getStringRes());
    }
}
