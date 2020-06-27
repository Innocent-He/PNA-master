package org.xidian.alg.escycle.simphon;

import org.apache.log4j.Logger;
import org.xidian.model.PetriModel;

import java.util.*;


/**
 * 获取每个信标的所有后置变迁集，用以求可清空的信标
 *
 * @version 1.0
 * @Author He ymLiu
 * @date 2020/5/7 13:27
 */
public class SiphonPostTrans {


    public static Map<Integer, List<Integer>> getSiphonPostTrans() {
        Logger myLog = Logger.getLogger("myLog");
        ArrayList<ArrayList<Integer>> siphons = FindSimphon.Simphon();
        myLog.info("所有的极小信标:\n" + siphons);
        Map<Integer, List<Integer>> siphonPostTrans = new HashMap<>();

        for (int i = 0; i < siphons.size(); i++) {
            //存放每一个信标的所有库所
            List<Integer> siphonArrays = siphons.get(i);

            List<Integer> postTrans = new ArrayList<>();
            for (Integer place : siphonArrays) {
                //某个库所的所有后置变迁
                int[] trans = PetriModel.posMatrix.getValues(place - 1);
                for (int j = 0; j < trans.length; j++) {
                    if (trans[j] > 0 && !postTrans.contains(j + 1)) {
                        postTrans.add(j + 1);
                    }
                }
            }
            siphonPostTrans.put(i + 1, postTrans);
        }
        return siphonPostTrans;
    }

}
