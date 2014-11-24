package com.lukedeighton.typedadapter.utils;

import com.lukedeighton.typedadapter.row.RowType;

import java.util.ArrayList;
import java.util.List;

public class RowBuilder {
    private List<RowType> mList;

    public RowBuilder() {
        mList = new ArrayList<RowType>();
    }

    public RowBuilder add(RowType row) {
        mList.add(row);
        return this;
    }

    public RowBuilder add(List<? extends RowType> collection) {
        mList.addAll(collection);
        return this;
    }

    public RowBuilder add(List<?> items, Class<? extends RowType> clazz) {
        mList.addAll(RowUtils.wrapListQuietly(items, clazz));
        return this;
    }

    public RowBuilder add(Object[] items, Class<? extends RowType> clazz) {
        mList.addAll(RowUtils.wrapListQuietly(items, clazz));
        return this;
    }

    public List<RowType> build() {
        return mList;
    }
}
