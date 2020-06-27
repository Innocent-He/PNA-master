package org.xidian.alg.escycle.es.algorithm;

import org.xidian.alg.escycle.es.graphFind.Enter;
import org.xidian.alg.escycle.util.EsRelatedUtils;
import org.xidian.alg.ReachabilityGraphAlgorithm;
import org.xidian.model.PetriModel;

import java.util.List;
import java.util.Map;

/**
 * @version 1.0
 * @Author He ymLiu
 * @date 2020/5/15 19:42
 */
public class FindESRealDeadTran {
    static Map<Integer, List<Integer>> allEsDeadTrans = null;

    /**
     * @param reachOrder 每个可达的状态的序号
     * @param esOrder    es环路的序号
     * @return
     */
    public static boolean judgeDead(Integer reachOrder, Integer esOrder) {
        //每个es的环路表达式
        String s = Enter.resourceCycles.get(esOrder);

        //当前状态下每个库所的token
        int[] state = ReachabilityGraphAlgorithm.resu.get(reachOrder).getState();
        Map<Integer, List<Integer>> onlyPlaceMap = EsRelatedUtils.removeTran(EsRelatedUtils.transform(s));
        List<Integer> onlyPlaceList = onlyPlaceMap.get(1);

        Map<Integer, List<Integer>> onlyTranMap = EsRelatedUtils.removePlace(EsRelatedUtils.transform(s));
        List<Integer> onlyTranList = onlyTranMap.get(1);

        if (s.startsWith("P")) {
            for (int i = 0; i < onlyPlaceList.size(); i++) {
                if (state[onlyPlaceList.get(i) - 1] >= PetriModel.posMatrix.getValue(onlyPlaceList.get(i) - 1, onlyTranList.get(i) - 1)) {
                    return false;
                }
            }
            return true;
        }
        if (s.startsWith("T")) {
            for (int i = 0; i < onlyPlaceList.size(); i++) {
                if (i == onlyPlaceList.size() - 1) {
                    if (state[onlyPlaceList.get(i) - 1] >= PetriModel.posMatrix.getValue(onlyPlaceList.get(i) - 1, onlyTranList.get(0) - 1)) {
                        return false;
                    }
                    break;
                }
                if (state[onlyPlaceList.get(i) - 1] >= PetriModel.posMatrix.getValue(onlyPlaceList.get(i) - 1, onlyTranList.get(i + 1) - 1)) {
                    return false;
                }
            }
            return true;
        } else {
            throw new RuntimeException("环路必须以P或者T开始");
        }

    }


}

