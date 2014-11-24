package com.lukedeighton.typedadapter.sample.row;

import com.lukedeighton.typedadapter.sample.R;

public class HeaderRow extends SimpleTextRow {
    public HeaderRow(String item) {
        super(item, R.layout.row_header, R.id.textview);
    }
}
