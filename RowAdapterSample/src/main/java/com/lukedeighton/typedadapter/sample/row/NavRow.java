package com.lukedeighton.typedadapter.sample.row;

import android.view.View;
import android.widget.TextView;

import com.lukedeighton.typedadapter.row.templates.InflatedRow;
import com.lukedeighton.typedadapter.sample.MainActivity;
import com.lukedeighton.typedadapter.sample.R;

public class NavRow extends InflatedRow<MainActivity.NavType> {
    public NavRow(MainActivity.NavType item) {
        super(item, R.layout.row_header);
    }

    @Override
    public void bindView(View view, int position) {
        ((TextView) view.findViewById(R.id.textview)).setText(getItem().name());
    }
}
