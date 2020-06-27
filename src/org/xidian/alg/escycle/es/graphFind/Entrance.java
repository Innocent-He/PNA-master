package org.xidian.alg.escycle.es.graphFind;

import org.xidian.alg.escycle.es.algorithm.FindESRealDeadTran;
import org.xidian.alg.escycle.util.CollectionUtils;
import org.xidian.alg.escycle.util.LoadDataUtil;
import org.xidian.alg.escycle.es.algorithm.FindDeadTrans;
import org.xidian.alg.escycle.simphon.SiphonPostTrans;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.Map;


/**
 * 切入可达图算法，计算ES与信标相关内容
 *
 * @version 1.0
 * @Author He ymLiu
 * @date 2020/5/15 19:37
 */
public class Entrance {
    public static void go() {
        Logger myLog = Logger.getLogger("myLog");
        myLog.setLevel(Level.INFO);
        //存放每个信标及其所有后置变迁
        Map<Integer, List<Integer>> siphonPostTrans = SiphonPostTrans.getSiphonPostTrans();
        myLog.info("极小信标序号与其后置变迁:\n" + siphonPostTrans);

        //每个可达图的序号以及他当前的deadT集合，deadT序号从1开始
        Map<Integer, List<Integer>> deadTran = FindDeadTrans.findDeadTran();
        myLog.info("可达图序号与当前状态的deadT集合:\n" + deadTran);
        StringBuilder str = new StringBuilder();
        //每个ES的序号以及它的变迁集合
        Map<Integer, List<Integer>> esTrans = Enter.getESTrans();
        myLog.info("极小ES序号与其变迁集合:\n" + esTrans);
        int emptyEs = 0;
        int emptySimphon = 0;
        for (Map.Entry<Integer, List<Integer>> entries : deadTran.entrySet()) {
            for (int j = 1; j <= esTrans.size(); j++) {
                if (entries.getValue().containsAll(esTrans.get(j)) && FindESRealDeadTran.judgeDead(entries.getKey(), j)) {
                    str.append("ES" + j + "在状态" + entries.getKey() + "下是可清空的\n");
                    emptyEs++;
                }
            }
            for (int m = 1; m <= siphonPostTrans.size(); m++) {
                if (CollectionUtils.containOnly(entries.getValue(), siphonPostTrans.get(m))) {
                    str.append("信标" + m + "在状态" + entries.getKey() + "下是可清空的\n");
                    emptySimphon++;
                }
            }
            str.append("状态" + entries.getKey() + "下的deadD:" + entries.getValue() + "\n");
        }
        str.append("可清空的es共有" + emptyEs + "\n");
        str.append("可清空的信标共有" + emptySimphon);

        LoadDataUtil load = new LoadDataUtil();
        load.outFile(str.toString());

    }


}
