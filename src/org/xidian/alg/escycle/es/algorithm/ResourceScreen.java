package org.xidian.alg.escycle.es.algorithm;

import org.xidian.alg.escycle.resources.Constant;
import org.xidian.alg.escycle.util.CollectionUtils;
import org.xidian.utils.LoadModelUtil;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @Author He ymLiu
 * @Date 2020/4/3 22:05
 * @Version 1.0
 */
public class ResourceScreen {
    /**
     * 将所有环路中的资源库所筛选出来
     *
     * @param cycles
     * @return
     */
    public static List<String> screen(List<String> cycles) {
        List<String> resourceCycle = new ArrayList<>();

        for (String cycle : cycles) {
            String[] split = cycle.split("-");
            if (isIncludeResource(split)) {
                resourceCycle.add(cycle);
            }
        }
        return resourceCycle;
    }

    //传入所有环路，判断是否是一个ES
    public static boolean isIncludeResource(String[] cycle) {
        List<String> idlePlaces = CollectionUtils.arrToList(Constant.IDLE_PLACES);
        List<String> cycles = CollectionUtils.arrToList(cycle);
        if (CollectionUtils.containOnly(cycles, idlePlaces)) {
            return false;
        }
        int flagResource = 0;
        int resourcePlaceCount = 2;
        List<String> lis = CollectionUtils.arrToList(cycle);
        LinkedList<Integer> resourcePlace = LoadModelUtil.rp;
        for (int i = 0; i < lis.size() - 1; i++) {

            if (lis.get(i).startsWith("T")) {
                continue;
            }
            for (int j = 0; j < resourcePlace.size(); j++) {
                if (lis.get(i).equals("P" + resourcePlace.get(j))) {
                    flagResource++;
                    break;
                }
            }
        }
        if (flagResource >= resourcePlaceCount) {
            return true;
        } else {
            return false;
        }
    }
}
