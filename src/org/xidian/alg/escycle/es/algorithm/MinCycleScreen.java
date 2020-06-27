package org.xidian.alg.escycle.es.algorithm;

import org.xidian.alg.escycle.util.EsRelatedUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author He ymLiu
 * @Date 2020/4/3 19:41
 * @Version 1.0
 */
public class MinCycleScreen {
    public static List<String> screen(List<String> cycles) {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < cycles.size(); i++) {
            int flag = 0;
            for (int j = 0; j < cycles.size(); j++) {
                if (i == j) {
                    continue;
                }
                if (EsRelatedUtils.screenUtil(cycles.get(i), cycles.get(j))) {
                    cycles.remove(i);
                    i--;
                    break;
                }
                flag++;
            }
            if (flag == cycles.size() - 1) {
                list.add(cycles.get(i));
            }
        }
        return list;
    }
}
