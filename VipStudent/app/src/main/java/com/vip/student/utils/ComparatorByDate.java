package com.vip.student.utils;


import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;

/**
 * Created by Administrator on 2015/12/25.
 */
class ComparatorByDate<T> implements Comparator<T> {
    public int compare(T o1, T o2) {
        int i = Integer.parseInt(String.valueOf(o1));
        int j = Integer.parseInt(String.valueOf(o2));
        if (i > j) return 1;
        if (i < j) return -1;
        return 0;
    }
}