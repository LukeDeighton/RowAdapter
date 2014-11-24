package com.lukedeighton.typedadapter.sample.row;

import com.lukedeighton.typedadapter.sample.R;

public class AnotherHeaderRow extends SimpleTextRow {

    public AnotherHeaderRow(String type) {
        super(type, R.layout.row_header, R.id.textview);
    }
}
