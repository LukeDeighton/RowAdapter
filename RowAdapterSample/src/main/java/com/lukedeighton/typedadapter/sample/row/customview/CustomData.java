package com.lukedeighton.typedadapter.sample.row.customview;


import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;

public class CustomData {
    private int mDrawableRes;
    private int mStringRes;

    public CustomData(@DrawableRes int drawableRes, @StringRes int stringRes) {
        mDrawableRes = drawableRes;
        mStringRes = stringRes;
    }

    public int getDrawableRes() {
        return mDrawableRes;
    }

    public int getStringRes() {
        return mStringRes;
    }
}
