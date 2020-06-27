package org.xidian.alg.escycle.es.graphFind;


import org.xidian.alg.escycle.es.algorithm.*;
import org.xidian.alg.escycle.util.EsRelatedUtils;
import org.xidian.alg.escycle.util.LoopUtil;
import org.apache.log4j.Logger;

import java.util.*;

/**
 * @Author He ymLiu
 * @Date 2020/4/3 19:11
 * @Version 1.0
 */
public class Enter {
    /**
     * 存放ES与他们序号的键值对
     */
    public static Map<Integer, String> resourceCycles = new HashMap<>(10);
    /**
     * 只保留相似环路中单个的极小es集合(包含第一种特殊es)
     */
    public static List<String> noSimilarRealCycle;

    public static Map<Integer, List<Integer>> run() {
        Logger myLoger = Logger.getLogger("myLog");
        LoopUtil.newLoadData();
        //得到所有的环路
        List<String> cycles = LoopUtil.find();

        //筛选资源库所数目大于2且不包括闲置库所的环路（√）
        List<String> resourceCycle = ResourceScreen.screen(cycles);

        List<String> noRepeatSourceCycle = RepeatScreen.screen(resourceCycle);
        myLoger.info("所有的es:\n" + noRepeatSourceCycle);
        //筛选掉包含其它环路变迁的环路(√)
        List<String> firstSpecialEs = FirstSpecialEsScreen.minOneSpecialES(cycles);
        myLoger.info("第一种特殊ES:\n" + firstSpecialEs);
        //筛选极小的时候需要把特殊es也加进来
        List<String> allES = new ArrayList<>();
        allES.addAll(noRepeatSourceCycle);
        allES.addAll(firstSpecialEs);
        List<String> realResourceCycle = MinCycleScreen.screen(allES);
        //如果将特殊es筛选掉了重新加回来
        for (String cycle : firstSpecialEs) {
            if (!realResourceCycle.contains(cycle)) {
                realResourceCycle.add(cycle);
            }
        }


        //筛选掉相似环路,保留长度最小的相似环路(√)
        noSimilarRealCycle = SimilarScreen.screen(realResourceCycle);

        for (int i = 1; i <= noSimilarRealCycle.size(); i++) {
            resourceCycles.put(i, noSimilarRealCycle.get(i - 1));
        }
        //将传入的每个ES中的T提取出来
        return EsRelatedUtils.removePlace(resourceCycles);
    }

}
