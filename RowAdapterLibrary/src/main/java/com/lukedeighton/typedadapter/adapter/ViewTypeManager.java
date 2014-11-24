package com.lukedeighton.typedadapter.adapter;

import java.util.HashMap;
import java.util.List;

/**
 * Handles View Type Information for each Row Item
 */
class ViewTypeManager {
    public static final int DYNAMIC_COUNT = -1;

    private HashMap<Class<?>, Integer> mViewTypeMap;
    private int mUsedViewTypeCount;
    private final int mViewTypeCount;

    public ViewTypeManager(List<?> objs) {
        this(objs, DYNAMIC_COUNT);
    }
    public ViewTypeManager(List<?> objs, int viewTypeCount) {
        mViewTypeMap = new HashMap<Class<?>, Integer>();
        int usedViewTypeCount = updateViewTypeInfoInternal(objs);
        if (viewTypeCount < 1) {
            mViewTypeCount = usedViewTypeCount;
        } else {
            checkViewTypeCount(viewTypeCount);
            mViewTypeCount = viewTypeCount;
        }
    }

    //ViewTypeCount is only called once, therefore notifyDataSetChange can't dynamically change
    //the ViewTypeCount - In that case you will have to create a new Adapter or follow the solution
    //from here:
    //http://stackoverflow.com/questions/13000708/dynamically-update-view-types-of-listview
    private void checkViewTypeCount(int viewTypeCount) {
        if (mUsedViewTypeCount > viewTypeCount) {
            throw new IllegalStateException("Cannot dynamically add more ViewTypes: "
                    + viewTypeCount + " current ViewTypeCount, requires " + mUsedViewTypeCount);
        }
    }

    private int updateViewTypeInfoInternal(List<?> objs) {
        int viewTypeCount = mUsedViewTypeCount;
        for (Object obj : objs) {
            Class<?> objClazz = obj.getClass();
            if (mViewTypeMap.get(objClazz) == null) {
                mViewTypeMap.put(objClazz, viewTypeCount++);
            }
        }
        mUsedViewTypeCount = viewTypeCount;
        return viewTypeCount;
    }

    public void updateViewTypeInfo(List<?> objs) {
        updateViewTypeInfoInternal(objs);
        checkViewTypeCount(mViewTypeCount);
    }

    public int getItemViewType(Object obj) {
        return mUsedViewTypeCount < 2 ? 0 : mViewTypeMap.get(obj.getClass());
    }

    public int getViewTypeCount() {
        return mViewTypeCount;
    }
}
