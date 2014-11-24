package com.lukedeighton.typedadapter.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;

import com.lukedeighton.typedadapter.row.ExpandableRowType;
import com.lukedeighton.typedadapter.row.RowType;

import java.util.ArrayList;
import java.util.List;

public class ExpandableRowAdapter extends BaseExpandableListAdapter {
    public static class GroupRow {
        ExpandableRowType mRow;
        List<? extends RowType> mChildRows;

        public GroupRow(ExpandableRowType expandableRow) {
            this(expandableRow, null);
        }

        public GroupRow(ExpandableRowType expandableRow, List<? extends RowType> childRows) {
            mRow = expandableRow;
            mChildRows = childRows == null ? new ArrayList<RowType>(0) : childRows;
        }
    }

    private List<? extends GroupRow> mGroupRows;
    private LayoutInflater mInflater;
    private ViewTypeManager mGroupVTManager;
    private ViewTypeManager mChildVTManager;

    public ExpandableRowAdapter(Context context, List<? extends GroupRow> groupRows) {
        this(context, groupRows, ViewTypeManager.DYNAMIC_COUNT, ViewTypeManager.DYNAMIC_COUNT);
    }

    public ExpandableRowAdapter(Context context, List<? extends GroupRow> groupRows,
                                int childViewTypeCount, int groupViewTypeCount) {
    	mInflater = LayoutInflater.from(context);
        mGroupRows = groupRows;
        mGroupVTManager = new ViewTypeManager(getAllGroupRows(groupRows), groupViewTypeCount);
        mChildVTManager = new ViewTypeManager(getAllChildRows(groupRows), childViewTypeCount);
    }

    private List<ExpandableRowType> getAllGroupRows(List<? extends GroupRow> groupRows) {
        List<ExpandableRowType> expandableRows = new ArrayList<ExpandableRowType>();
        for (GroupRow groupRow : groupRows) {
            expandableRows.add(groupRow.mRow);
        }
        return expandableRows;
    }

    private List<RowType> getAllChildRows(List<? extends GroupRow> groupRows) {
        List<RowType> childRows = new ArrayList<RowType>();
        for (GroupRow groupRow : groupRows) {
            childRows.addAll(groupRow.mChildRows);
        }
        return childRows;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return mGroupRows.get(groupPosition).mChildRows.get(childPosition);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild,
                             View convertView, ViewGroup parent) {
        RowType childRow = mGroupRows.get(groupPosition).mChildRows.get(childPosition);
        if (convertView == null) {
        	convertView = childRow.createView(mInflater, parent, childPosition);
        }
        childRow.bindView(convertView, childPosition);
        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return mGroupRows.get(groupPosition).mChildRows.size();
    }

    @Override
    public int getChildType(int groupPosition, int childPosition) {
    	return mChildVTManager.getItemViewType(mGroupRows.get(groupPosition).mChildRows.get(childPosition));
    }
    
    @Override
    public int getChildTypeCount() {
    	return mChildVTManager.getViewTypeCount();
    }
    
    @Override
    public Object getGroup(int groupPosition) {
        return mGroupRows.get(groupPosition).mRow;
    }

    @Override
    public int getGroupCount() {
        return mGroupRows.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        ExpandableRowType expandableRow = mGroupRows.get(groupPosition).mRow;
        if (convertView == null) {
        	convertView = expandableRow.createView(mInflater, parent, groupPosition);
        }
        expandableRow.bindView(convertView, groupPosition, isExpanded);
        return convertView;
    }
    
    @Override
    public int getGroupType(int groupPosition) {
    	return mGroupVTManager.getItemViewType(mGroupRows.get(groupPosition).mRow);
    }

    @Override
    public int getGroupTypeCount() {
    	return mGroupVTManager.getViewTypeCount();
    }
    
    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return mGroupRows.get(groupPosition).mChildRows.get(childPosition).isEnabled();
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public void notifyDataSetChanged() {
        mGroupVTManager.updateViewTypeInfo(getAllGroupRows(mGroupRows));
        mChildVTManager.updateViewTypeInfo(getAllChildRows(mGroupRows));
        super.notifyDataSetChanged();
    }
}
