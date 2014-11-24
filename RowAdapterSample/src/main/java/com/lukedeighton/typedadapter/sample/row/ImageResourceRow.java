package com.lukedeighton.typedadapter.sample.row;

import android.support.annotation.DrawableRes;
import android.view.View;
import android.widget.ImageView;

import com.lukedeighton.typedadapter.row.templates.InflatedRow;
import com.lukedeighton.typedadapter.sample.R;

public class ImageResourceRow extends InflatedRow<Integer> {
    public ImageResourceRow(@DrawableRes int item) {
        super(item, R.layout.row_image);
    }

    @Override
    public void bindView(View view, int position) {
        ((ImageView) view.findViewById(R.id.row_imageview)).setImageResource(getItem());
    }
}
