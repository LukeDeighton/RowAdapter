package com.lukedeighton.typedadapter.sample.row;

import android.support.annotation.DrawableRes;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;

import com.lukedeighton.typedadapter.row.templates.InstantiatedRow;

public class ImageViewRow extends InstantiatedRow<Integer, ImageView> {
    public ImageViewRow(@DrawableRes int item) {
        super(ImageView.class, item);
    }

    @Override
    public void initView(ImageView view, ViewGroup parent) {
        float density = getResourceDensity();
        view.setLayoutParams(new AbsListView.LayoutParams(
                AbsListView.LayoutParams.MATCH_PARENT, (int) (density * 50)));
        int padding = (int) (10 * density);
        view.setPadding(padding, padding, padding, padding);
    }

    @Override
    public void bindView(Integer item, int position, ImageView view) {
        view.setImageResource(item);
    }
}
