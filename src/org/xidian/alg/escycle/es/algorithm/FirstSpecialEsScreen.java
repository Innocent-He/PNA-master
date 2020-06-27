package org.xidian.alg.escycle.es.algorithm;

import org.xidian.alg.escycle.util.EsRelatedUtils;
import org.xidian.model.PetriModel;
import org.xidian.utils.LoadModelUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @version 1.0
 * @Author He ymLiu
 * @date 2020/5/26 21:19
 */
public class FirstSpecialEsScreen {
    public static List<String> minOneSpecialES(List<String> dfsResult) {
        int tranCounts = PetriModel.preMatrix.getMatrix()[0].length;
        String[] pr = new String[10];
        //导入所需Petri Net数据
        for (int i = 0; i < LoadModelUtil.rp.size(); i++) {
            pr[i] = "P" + LoadModelUtil.rp.get(i);
        }
        //变迁最大
        String[] transition = new String[tranCounts];
        for (int i = 0; i < tranCounts; i++) {
            transition[i] = "T" + (i + 1);
        }

        //数据集合
        List<String[]> generalES = new ArrayList<>();//用来存放资源数目为1，变迁数目为2或者3的回路
        List<String[]> generalES1 = new ArrayList<>();//用来存放资源数目为1，变迁数目为3的回路
        List<String[]> generalES2 = new ArrayList<>();//用来存放资源数目为1，变迁数目为2的回路
        List<String[]> oneSpecialES = new ArrayList<>();//用来存放第一种Specail ES
        List<String[]> noRepeatLoop = new ArrayList<>();//用来存放去除重复之后的所有回路
        List<String> tool = new ArrayList<>();//判断工具，每次用完之后进行清空
        List<String> result = new ArrayList<>();

        //计数器
        int sumr = 0;//存放资源库所数目
        int sumt = 0;//存放变迁数目

        noRepeatLoop = EsRelatedUtils.reRepeatLoop(dfsResult);//去除重复回路

        for (int s = 2; s < 100; s++) {
            //用来计算存放资源数目为1，变迁数目为2或者3的回路，并将其放入对应的回路
            for (int i2 = 0; i2 < noRepeatLoop.size(); i2++) {
                String[] string = noRepeatLoop.get(i2);

                //将回路中的每个节点放入List<String>当中，已经去除掉了StartNode
                for (int i1 = 0; i1 < string.length - 1; i1++) {
                    tool.add(string[i1]);
                }

                //计算资源库所以及变迁数目
                for (int i1 = 0; i1 < pr.length; i1++) {
                    if (tool.contains(pr[i1])) {
                        sumr++;
                    }
                }
                for (int i1 = 0; i1 < transition.length; i1++) {
                    if (tool.contains(transition[i1])) {
                        sumt++;
                    }
                }
                //将回路放到对应的集合当中
                if (sumr == 1 && sumt == (s + 1)) {
                    generalES1.add(string);
                    generalES.add(string);
                }
                if (sumr == 1 && sumt == s) {
                    generalES2.add(string);
                    generalES.add(string);
                }

                tool.clear();
                sumr = sumt = 0;
            }

            //利用集合的包含关系计算第一种Special ES
            for (int i = 0; i < generalES1.size(); i++) {
                for (int i1 = 0; i1 < generalES2.size(); i1++) {
                    if (EsRelatedUtils.getMin(generalES1.get(i), generalES2.get(i1))) {
                        oneSpecialES.add(generalES2.get(i1));
                        oneSpecialES.add(generalES1.get(i));
                    }
                }
            }


            //oneSpecialES.clear();
            generalES.clear();
            generalES1.clear();
            generalES2.clear();
        }
        int sign = 0;
        int j = 1;
        //System.out.println("第一种Special ES的数目为："+(oneSpecialES.size()/2));
        for (String[] string : oneSpecialES) {
            for (int i1 = 0; i1 < string.length; i1++) {
                tool.add(string[i1]);
            }
            if (sign % 2 == 0) {
                StringBuilder cycle = new StringBuilder();
                String[] specialCycle = new String[oneSpecialES.get(sign).length];
                for (int i = 0; i < specialCycle.length; i++) {
                    cycle.append(oneSpecialES.get(sign)[i] + "-");
                }

                cycle.deleteCharAt(cycle.length() - 1);
                result.add(cycle.toString());
            }
            if (sign % 2 == 1) {
                //System.out.print("   ");
                //System.out.println(tool);
            }

            sign++;
        }
        return result;
    }
}
