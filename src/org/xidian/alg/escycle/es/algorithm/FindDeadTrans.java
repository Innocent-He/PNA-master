package org.xidian.alg.escycle.es.algorithm;

import org.xidian.alg.ReachabilityGraphAlgorithm;

import java.util.*;

/**
 * @version 1.0
 * @Author He ymLiu
 * @date 2020/5/3 10:30
 */
public class FindDeadTrans {
    /**
     * 存放所有可达图状态下的状态序号与当前状态变迁是否可发射的数组集合
     */
    public static Map<Integer, boolean[]> allTransStatus = new HashMap<>(100);
    /**
     * 存放的是所有状态可以通过那些变迁发射到达那些状态
     * 数据格式(为了易于理解将，改为-)
     * 1-5-2,1-1-3
     * 2-6-4,2-5-5,2-1-6
     * 3-5-6,3-2-7,3-1-8
     */
    public static List<List<Integer>> lis = ReachabilityGraphAlgorithm.adjlist;

    public static Map<Integer, List<Integer>> findDeadTran() {

        //每个可达图序号以及deadT
        Map<Integer, List<Integer>> hasDeadTranStatus = new HashMap<>();

        //循环判断每个状态下的deadTran
        for (int i = 1; i <= allTransStatus.size(); i++) {
            //存放当前状态下不可以发射的t的序号
            List<Integer> notFireTran = new ArrayList<>(10);

            boolean[] booleans = allTransStatus.get(i);

            //将当前状态不能发射的t放入notFireTran数组中
            for (int j = 0; j < booleans.length; j++) {
                if (booleans[j] == false) {
                    notFireTran.add(j + 1);
                }
            }

            List<Integer> deadTran = hasDeadTran(i, notFireTran);
            if (!deadTran.isEmpty()) {
                hasDeadTranStatus.put(i, deadTran);
            }
        }

        return hasDeadTranStatus;
    }

    public static List<Integer> hasDeadTran(int stateNo, List<Integer> notFireTrans) {
        //存放所有已发生过的状态序号
        List<Integer> allOccuredStatusNo = new ArrayList<>();
        //存放的是每个状态的序号
        LinkedList<Integer> stateQueue = new LinkedList<>();
        stateQueue.add(stateNo);
        while (!stateQueue.isEmpty()) {
            Integer stateNom = stateQueue.pop();
            if (lis.get(stateNom - 1).size() < 3) {
                continue;
            }
            //将当前状态可以到达的状态加入stateQueue中
            for (int i = 2; i < lis.get(stateNom - 1).size(); i = i + 3) {
                //如果此状态已经发生过则无需再加入链表，如果未发生过则加入

                if (!allOccuredStatusNo.contains(lis.get(stateNom - 1).get(i))) {
                    stateQueue.add(lis.get(stateNom - 1).get(i));
                    allOccuredStatusNo.add(lis.get(stateNom - 1).get(i));
                } else {
                    continue;
                }
            }
            //当前状态下变迁的情况
            boolean[] trans = allTransStatus.get(stateNom);
            for (int i = 0; i < notFireTrans.size(); i++) {
                if (trans[notFireTrans.get(i) - 1] == true) {
                    notFireTrans.remove(i);
                }
            }
            if (notFireTrans == null) {
                break;
            }
        }
        return notFireTrans;
    }
}
