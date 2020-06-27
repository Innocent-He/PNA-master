package org.xidian.alg.escycle.es.algorithm;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author He ymLiu
 * @Date 2020/4/3 22:47
 * @Version 1.0
 */
public class RepeatScreen {
    public static List<String> screen(List<String> cycles) {
        List<String> lis1 = new ArrayList<>();
        List<String> lis2 = new ArrayList<>();

        for (int i = 0; i < cycles.size(); i++) {
            String[] contrast1 = cycles.get(i).split("-");
            for (int n = 0; n < contrast1.length - 1; n++) {
                lis1.add(contrast1[n]);
            }
            if (cycles.get(i) == "") {
                continue;
            }
            for (int j = i + 1; j < cycles.size(); j++) {

                String[] contrast2 = cycles.get(j).split("-");
                for (int m = 0; m < contrast2.length - 1; m++) {
                    lis2.add(contrast2[m]);
                }
                if (lis1.containsAll(lis2) && lis1.size() == lis2.size()) {
                    cycles.set(j, "");
                }
                lis2.clear();
            }
            lis1.clear();
        }
        cycles.removeIf(s -> s == "");
        return cycles;
    }
}
