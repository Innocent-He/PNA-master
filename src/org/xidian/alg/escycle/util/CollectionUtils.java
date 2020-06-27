package org.xidian.alg.escycle.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @Author He ymLiu
 * @Date 2020/4/4 22:57
 * @Version 1.0
 */
public class CollectionUtils {
    /**
     * 将数组转换为ArrayList
     *
     * @param arr
     * @param <T>
     * @return
     */
    public static <T> List<T> arrToList(T[] arr) {
        List<T> list = new ArrayList<>();
        for (int i = 0; i < arr.length; i++) {
            list.add(arr[i]);
        }
        return list;
    }

    /**
     * 将list转换为数组
     *
     * @param list
     * @return
     */
    public static String[] listToArray(List<String> list) {
        String[] arr = new String[list.size()];
        for (int i = 0; i < list.size(); i++) {
            arr[i] = list.get(i);
        }
        return arr;
    }


    /**
     * 如果lis1中至少有一个lis2中的元素则返回true
     *
     * @param lis1
     * @param lis2
     * @return
     */
    public static <T> boolean containOnly(List<T> lis1, List<T> lis2) {
        for (T lis : lis2) {
            if (lis1.contains(lis)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 如果arr2是arr1的子集则返回true
     *
     * @param arr1
     * @param arr2
     * @return
     */
    public static <T> boolean isSubset(List<T> arr1, List<T> arr2) {
        int m = arr1.size();
        int n = arr2.size();
        if (n >= m) {
            return false;
        }

        if (arr1.containsAll(arr2)) {
            return true;
        } else {
            return false;
        }

    }

    /**
     * 如果arr2是arr1的真子集则返回true
     *
     * @param arr1
     * @param arr2
     * @param <T>
     * @return
     */
    public static <T> boolean isRealSubset(List<T> arr1, List<T> arr2) {
        if (arr2.size() > arr1.size()) {
            return false;
        }
        if (arr1.containsAll(arr2)) {
            return true;
        } else {
            return false;
        }

    }

    /**
     * 去除集合中重复的元素
     *
     * @param a
     * @return
     */
    public static <T> List<T> reduceRepeat(List<T> a) {
        Set<T> set = new HashSet();
        List<T> newList = new ArrayList();
        for (T cd : a) {
            if (set.add(cd)) {
                newList.add(cd);
            }
        }
        return newList;
    }

    /**
     * 将字符串数组转化为数字List
     *
     * @param s
     * @return
     */
    public static ArrayList<Integer> reform(String[] s) {
        ArrayList<Integer> list = new ArrayList<>();
        for (String element : s) {
            list.add(new Integer(element));
        }
        return list;
    }
}
