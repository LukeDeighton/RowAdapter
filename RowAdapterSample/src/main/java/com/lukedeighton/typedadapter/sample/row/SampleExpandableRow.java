package com.lukedeighton.typedadapter.sample.row;

import android.text.Html;
import android.view.View;
import android.widget.TextView;

import com.lukedeighton.typedadapter.row.templates.ExpandableRow;
import com.lukedeighton.typedadapter.sample.R;

public class SampleExpandableRow extends ExpandableRow<String> {
    public SampleExpandableRow(String item) {
        super(item, R.layout.row_expandable);
    }

    @Override
    public void bindView(View view, int position, boolean isExpanded) {
        ((TextView) view.findViewById(R.id.textview1)).setText(getItem());
        CharSequence arrow = Html.fromHtml(isExpanded ? "&#9660;" : "&#9650;");
        ((TextView) view.findViewById(R.id.expanded)).setText(arrow);
    }
}
