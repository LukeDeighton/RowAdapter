package com.lukedeighton.typedadapter.utils;

import android.content.Context;
import android.view.View;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RowUtils {
    public static <T, U> List<U> wrapList(List<T> items, Class<U> clazz) throws NoSuchMethodException,
            IllegalAccessException, InvocationTargetException, InstantiationException {
        if (items == null || items.isEmpty()) {
            throw new IllegalArgumentException("Cannot wrap empty list");
        }
        Constructor<U> constructor = clazz.getConstructor(items.get(0).getClass());
        List<U> rows = new ArrayList<U>(items.size());
        for (T item : items) {
            rows.add(constructor.newInstance(item));
        }
        return rows;
    }

    public static <T, U> List<U> wrapListQuietly(List<T> items, Class<U> clazz) {
        try {
            return wrapList(items, clazz);
        } catch (NoSuchMethodException e) {
            throw new IllegalArgumentException("No constructor with single parameter T exists");
        } catch (Exception e) {
            throw new IllegalArgumentException("Cannot create object: " + e);
        }
    }

    public static <T, U> List<U> wrapList(T[] items, Class<U> clazz) throws InvocationTargetException,
            NoSuchMethodException, InstantiationException, IllegalAccessException {
        return wrapList(Arrays.asList(items), clazz);
    }

    public static <T, U> List<U> wrapListQuietly(T[] items, Class<U> clazz) {
        return wrapListQuietly(Arrays.asList(items), clazz);
    }

    public static <U> U newInstance(Context context, Class<U> clazz) throws IllegalAccessException,
            InvocationTargetException, InstantiationException, NoSuchMethodException {
        Constructor<U> constructor = clazz.getConstructor(Context.class);
        return constructor.newInstance(context);
    }

    public static <U> U newInstance(View view, Class<U> clazz) throws IllegalAccessException,
            InvocationTargetException, InstantiationException, NoSuchMethodException {
        Constructor<U> constructor = clazz.getConstructor(View.class);
        return constructor.newInstance(view);
    }
}
