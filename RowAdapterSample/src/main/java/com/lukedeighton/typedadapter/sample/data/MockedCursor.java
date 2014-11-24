package com.lukedeighton.typedadapter.sample.data;

import android.database.MatrixCursor;

public class MockedCursor extends MatrixCursor {
    public MockedCursor(int rowCount) {
        super(new String[] { "_id", "Name" }, rowCount);
        for (int i = 0; i < rowCount; i++) {
            addRow(new Object[] { i + 1, "Item " + i });
        }
    }
}
