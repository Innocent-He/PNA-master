package org.xidian.alg.escycle.es.algorithm;

import org.xidian.alg.escycle.resources.Constant;
import org.xidian.alg.escycle.util.CollectionUtils;
import org.apache.log4j.Logger;
import org.xidian.utils.LoadModelUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 在所有ES中剔除相似环路
 *
 * @Author He ymLiu
 * @Date 2020/4/4 22:27
 * @Version 1.0
 */
public class SimilarScreen {
    //存放相似环路的集合，两两一组
    static List<String> Similar = new ArrayList<>();

    public static List<String> screen(List<String> cycles) {
        Logger myLog = Logger.getLogger("myLog");

        int resourcePlaces = LoadModelUtil.rp.size();
        List<String> resourceList = new ArrayList<>(resourcePlaces);
        for (int i = 0; i < resourcePlaces; i++) {
            resourceList.add("P" + LoadModelUtil.rp.poll());
        }
        //这个循环筛选资源库所完全一样的环路
        for (int i = 0; i < cycles.size(); i++) {
            //先将第一个作比较的环路的变迁全部去掉，结果集是listI
            String[] cycleI = cycles.get(i).split("-");
            List<String> lisI = CollectionUtils.arrToList(cycleI);
            for (int m = 0; m < lisI.size(); m++) {
                if (lisI.get(m).charAt(0) == 'T') {
                    lisI.remove(m);
                }
            }
            lisI.remove(lisI.size() - 1);
            for (int rei = 0; rei < lisI.size(); rei++) {
                if (!resourceList.contains(lisI.get(rei))) {
                    lisI.remove(rei);
                }
            }
            for (int j = i + 1; j < cycles.size(); j++) {
                //cycleJ是cycleI往后的环路
                String[] cycleJ = cycles.get(j).split("-");
                List<String> lisJ = CollectionUtils.arrToList(cycleJ);
                for (int n = 0; n < lisJ.size(); n++) {
                    if (lisJ.get(n).charAt(0) == 'T') {
                        lisJ.remove(n);
                    }
                }
                lisJ.remove(lisJ.size() - 1);
                for (int rej = 0; rej < lisJ.size(); rej++) {
                    if (!resourceList.contains(lisJ.get(rej))) {
                        lisJ.remove(rej);
                    }
                }
                //此处条件成立则表明cycles[i]和cycles[j]的资源库所相同
                if (lisI.containsAll(lisJ) && lisI.size() == lisJ.size()) {
                    //就在此处判断两个资源库所相同的环路的变迁是否在同一个进程中
                    if (similarProcess(cycles.get(i), cycles.get(j))) {
                        Similar.add(cycles.get(i));
                        Similar.add(cycles.get(j));
                    }

                }

            }
        }
        if (Similar.size() == 0) {
            myLog.info("不存在相似环路" + Similar);
            return cycles;
        }
        cycles.removeAll(Similar);
        String minSize = Similar.get(0);
        for (int i = 1; i < Similar.size(); i++) {
            String temp = Similar.get(i);
            if (temp.length() < minSize.length()) {
                minSize = temp;
            }
        }
        cycles.add(minSize);
        myLog.info("相似环路有：" + Similar);
        return cycles;
    }


    /*此处将判断具有相同资源库所的环路中变迁是否都在同一个进程中*/
    public static boolean similarProcess(String cycle1, String cycle2) {
        List<Integer> flag1 = new ArrayList<>();
        List<Integer> flag2 = new ArrayList<>();

        String[] cycleI = cycle1.split("-");
        String[] cycleJ = cycle2.split("-");

        List<String> lisI = CollectionUtils.arrToList(cycleI);
        List<String> lisJ = CollectionUtils.arrToList(cycleJ);
        for (int m = 0; m < lisI.size(); m++) {
            if (lisI.get(m).charAt(0) == 'P') {
                lisI.remove(m);
            }
        }
        for (int m = 0; m < lisJ.size(); m++) {
            if (lisJ.get(m).charAt(0) == 'P') {
                lisJ.remove(m);
            }
        }
        lisI.remove(lisI.size() - 1);
        lisJ.remove(lisJ.size() - 1);
        for (int i = 0; i < Constant.ALL_PROCESS.length; i++) {
            List<String> processTrans = CollectionUtils.arrToList(Constant.ALL_PROCESS[i]);
            if (CollectionUtils.containOnly(lisI, processTrans)) {
                flag1.add(i);
            }
        }
        for (int i = 0; i < Constant.ALL_PROCESS.length; i++) {
            List<String> processTrans = CollectionUtils.arrToList(Constant.ALL_PROCESS[i]);
            if (CollectionUtils.containOnly(lisJ, processTrans)) {
                flag2.add(i);
            }
        }

        if (flag1.size() == flag2.size() && flag1.containsAll(flag2)) {
            return true;
        } else {
            return false;
        }
    }


}

