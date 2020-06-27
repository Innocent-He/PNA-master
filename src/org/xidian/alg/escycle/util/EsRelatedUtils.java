package org.xidian.alg.escycle.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @version 1.0
 * @Author He ymLiu
 * @date 2020/5/15 19:44
 */
public class EsRelatedUtils {
    /**
     * s1中的变迁包含s2的变迁时则返回true s1/s2="T1-P3-T1-P6……"
     *
     * @param s1
     * @param s2
     * @return
     */
    public static boolean screenUtil(String s1, String s2) {
        List<String> lis1 = new ArrayList<>();
        List<String> lis2 = new ArrayList<>();
        String c1 = s1;
        String c2 = s2;
        String[] split1 = c1.split("-");
        String[] split2 = c2.split("-");
        //分别去除两个字符串数组中的库所以及多余的变迁
        if (split1[0].charAt(0) == 'T') {
            for (int i = 1; i < split1.length; i = i + 2) {
                split1[i] = null;
            }
            split1[split1.length - 1] = null;

        } else {
            for (int i = 0; i < split1.length; i = i + 2) {
                split1[i] = null;
            }
        }
        if (split2[0].charAt(0) == 'T') {
            for (int i = 1; i < split2.length; i = i + 2) {
                split2[i] = null;
            }
            split2[split2.length - 1] = null;
        } else {
            for (int i = 0; i < split2.length; i = i + 2) {
                split2[i] = null;
            }
        }

        for (int i = 0; i < split1.length; i++) {
            if (split1[i] != null) {
                lis1.add(split1[i]);
            }
        }
        for (int i = 0; i < split2.length; i++) {
            if (split2[i] != null) {
                lis2.add(split2[i]);
            }
        }
        if (lis1.containsAll(lis2)) {
            lis1.clear();
            lis2.clear();
            return true;
        }
        lis1.clear();
        lis2.clear();
        return false;
    }

    /**
     * 将ES环路中所有库所去掉，只剩变迁序号
     *
     * @param cycles
     * @return
     */
    public static Map<Integer, List<Integer>> removePlace(Map<Integer, String> cycles) {
        Map<Integer, List<Integer>> map = new HashMap<>(20);
        for (int i = 1; i <= cycles.size(); i++) {
            List<String> trash = new ArrayList<>();
            String[] split = cycles.get(i).split("-");
            List<String> strings = CollectionUtils.arrToList(split);
            List<Integer> trans = new ArrayList<>();
            strings.remove(strings.size() - 1);
            for (int j = 0; j < strings.size(); j++) {
                if (strings.get(j).contains("T")) {
                    String substring = strings.get(j).substring(1);
                    strings.set(j, substring);
                    continue;
                }

            }
            for (int j = 0; j < strings.size(); j++) {
                if (strings.get(j).contains("P")) {
                    trash.add(strings.get(j));
                }
            }
            strings.removeAll(trash);
            for (int m = 0; m < strings.size(); m++) {
                int num = Integer.parseInt(strings.get(m));
                trans.add(num);
            }
            map.put(i, trans);
        }
        return map;
    }

    /**
     * 将ES环路中所有变迁去掉，只剩库所序号
     *
     * @param cycles
     * @return
     */
    public static Map<Integer, List<Integer>> removeTran(Map<Integer, String> cycles) {
        Map<Integer, List<Integer>> map = new HashMap<>(20);
        for (int i = 1; i <= cycles.size(); i++) {
            List<String> trash = new ArrayList<>();
            String[] split = cycles.get(i).split("-");
            List<String> strings = CollectionUtils.arrToList(split);
            List<Integer> places = new ArrayList<>();
            strings.remove(strings.size() - 1);
            for (int j = 0; j < strings.size(); j++) {
                if (strings.get(j).contains("P")) {
                    String substring = strings.get(j).substring(1);
                    strings.set(j, substring);
                }

            }
            for (int j = 0; j < strings.size(); j++) {
                if (strings.get(j).contains("T")) {
                    trash.add(strings.get(j));
                }
            }
            strings.removeAll(trash);
            for (int m = 0; m < strings.size(); m++) {
                int num = Integer.parseInt(strings.get(m));
                places.add(num);
            }
            map.put(i, places);
        }
        return map;
    }


    public static Map<Integer, String> transform(String s) {
        Map<Integer, String> map = new HashMap<>(1);
        map.put(1, s);
        return map;
    }

    /*********************刘宇铭工具类***********************/
    //去除相等的ES
    public static boolean reRepeat(String[] a, String[] b) {
        List<String> aa = new ArrayList<>();
        List<String> bb = new ArrayList<>();
        for (int i3 = 0; i3 < a.length; i3++) {
            aa.add(a[i3]);
        }
        for (int i3 = 0; i3 < b.length; i3++) {
            bb.add(b[i3]);
        }
        if (aa.equals(bb)) {
            return true;
        }
        return false;
    }

    //求严格极小（去除有包含关系的ES，用所有节点包含去计算）
    public static boolean getMin(String[] a, String[] b) {
        List<String> aa = new ArrayList<>();
        List<String> bb = new ArrayList<>();
        for (int i3 = 0; i3 < a.length; i3++) {
            aa.add(a[i3]);
        }
        for (int i3 = 0; i3 < b.length; i3++) {
            bb.add(b[i3]);
        }
        if (aa.containsAll(bb)) {
            return true;
        }
        return false;
    }

    //利用dfsResult计算不重复回路
    public static List<String[]> reRepeatLoop(List<String> dfsResult) {
        List<String[]> sumOfLoop = new ArrayList<>();
        List<String[]> noRepeatLoop = new ArrayList<>();
        for (String string : dfsResult) {
            String[] split = string.split("-");
            sumOfLoop.add(split);
        }

        //去除重复回路
        for (int i1 = 0; i1 < sumOfLoop.size(); i1++) {
            for (int i2 = 0; i2 < sumOfLoop.size(); i2++) {
                if (i1 == i2 && i2 != sumOfLoop.size() - 1) {
                    i2++;
                }
                if (reRepeat(sumOfLoop.get(i1), sumOfLoop.get(i2))) {
                    break;
                }
                if (i2 == (sumOfLoop.size() - 1)) {
                    noRepeatLoop.add(sumOfLoop.get(i1));
                }
            }
        }
        return noRepeatLoop;
    }
}
