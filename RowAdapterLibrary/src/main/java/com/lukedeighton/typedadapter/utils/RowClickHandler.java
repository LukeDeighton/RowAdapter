package com.lukedeighton.typedadapter.utils;

import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.HeaderViewListAdapter;

import com.lukedeighton.typedadapter.adapter.CursorRowAdapter;
import com.lukedeighton.typedadapter.row.Row;
import com.lukedeighton.typedadapter.row.RowType;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;

public class RowClickHandler implements AdapterView.OnItemClickListener {
    private final List<ClickEntry<? extends Row>> mClickHandlers;
    private Events mEvents;

    public RowClickHandler() {
        this(Events.One);
    }

    public RowClickHandler(Events events) {
        mClickHandlers = new ArrayList<ClickEntry<? extends Row>>();
        mEvents = events;
    }

    public <T extends Row> RowClickHandler put(Class<T> clazz, OnRowClickListener<T> listener) {
        mClickHandlers.add(new ClickEntry<T>(clazz, listener));
        return this;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Adapter adapter = parent.getAdapter();
        if (adapter instanceof HeaderViewListAdapter) {
            adapter = ((HeaderViewListAdapter) adapter).getWrappedAdapter();
        }

        RowType rowType;
        if (adapter instanceof CursorRowAdapter) {
            CursorRowAdapter cursorAdapter = (CursorRowAdapter) adapter;
            rowType = cursorAdapter.getItem(position);
        } else {
            rowType = (RowType) adapter.getItem(position);
        }

        onRowClick(rowType, view, position);
    }

    @SuppressWarnings("unchecked")
    public void onRowClick(Row row, View view, int position) {
        Class<?> rowClazz = row.getClass();
        for (ClickEntry<? extends Row> entry : mClickHandlers) {
            ClickEntry<Row> castedEntry = (ClickEntry<Row>) entry;
            if (castedEntry.getKey().isAssignableFrom(rowClazz)) {
                castedEntry.getValue().onRowClick(row, view, position);
                if (mEvents == Events.One) {
                    return;
                }
            }
        }
    }

    private static class ClickEntry<T extends Row>
            extends AbstractMap.SimpleEntry<Class<T>, OnRowClickListener<T>> {
        public ClickEntry(Class<T> theKey, OnRowClickListener<T> theValue) {
            super(theKey, theValue);
        }
    }

    public static enum Events {
        All, One
    }

    public void setEvents(Events events) {
        mEvents = events;
    }

    public Events getEvents() {
        return mEvents;
    }
}
