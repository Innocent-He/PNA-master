package org.xidian.alg.escycle.util;

import org.xidian.model.PetriModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * @Author He ymLiu
 * @Date 2020/4/3 18:15
 * @Version 1.0
 */
public class LoopUtil {
    /**
     * 限制node最大数
     */
    private static int MAX_NODE_COUNT = 100;
    /**
     * node集合
     */
    private static List<String> nodes = new ArrayList<String>();
    /**
     * 有向图的邻接矩阵
     */
    private static int[][] adjacencyMatrix = new int[MAX_NODE_COUNT][MAX_NODE_COUNT];

    /**
     * @param nodeName
     * @return
     * @Title addNode
     * @Description 添加节点
     * @date 2018年5月17日
     */
    private static int addNode(String nodeName) {
        if (!nodes.contains(nodeName)) {
            if (nodes.size() >= MAX_NODE_COUNT) {
                System.out.println("nodes超长:" + nodeName);
                return -1;
            }
            nodes.add(nodeName);
            return nodes.size() - 1;
        }
        return nodes.indexOf(nodeName);
    }

    /**
     * @param startNode
     * @param endNode
     * @Title addLine
     * @Description 添加线，初始化邻接矩阵
     * @date 2018年5月17日
     */
    public static void addLine(String startNode, String endNode) {
        int startIndex = addNode(startNode);
        int endIndex = addNode(endNode);
        if (startIndex >= 0 && endIndex >= 0) {
            adjacencyMatrix[startIndex][endIndex] = 1;
        }
    }

    /**
     * @return
     * @Title find
     * @Description 寻找闭环
     * @date 2018年5月17日
     */
    public static List<String> find() {
        // 从出发节点到当前节点的轨迹
        List<Integer> trace = new ArrayList<Integer>();
        //返回值
        List<String> reslut = new ArrayList<>();
        if (adjacencyMatrix.length > 0) {
            findCycle(0, trace, reslut);
        }
        if (reslut.size() == 0) {
            reslut.add("no cycle!");
        }
        return reslut;
    }

    /**
     * @param v
     * @param trace
     * @param reslut
     * @Title findCycle
     * @Description dfs
     * @date 2018年5月17日
     */

    private static void findCycle(int v, List<Integer> trace, List<String> reslut) {
        int j;
        //添加闭环信息
        if ((j = trace.indexOf(v)) != -1) {
            StringBuffer sb = new StringBuffer();
            String startNode = nodes.get(trace.get(j));
            while (j < trace.size()) {
                sb.append(nodes.get(trace.get(j)) + "-");
                j++;
            }
            reslut.add(sb.toString() + startNode);
            return;
        }
        trace.add(v);
        for (int i = 0; i < nodes.size(); i++) {
            if (adjacencyMatrix[v][i] == 1) {
                findCycle(i, trace, reslut);
            }
        }
        trace.remove(trace.size() - 1);
    }

    public static void loadData(Properties prop, String flag) {
        for (int i = 1; i <= prop.size(); i++) {
            //k代表每个库所或者变迁的序号
            String k = String.valueOf(i);
            //value代表变迁/库所的后置内容
            String value = prop.getProperty(k);
            //后置内容不唯一，进行分割
            String[] Values = value.split(",");
            switch (flag) {
                case ("P"): {
                    for (int n = 0; n < Values.length; n++) {
                        String p = "P" + k;
                        String t = "T" + Values[n];
                        addLine(p, t);
                    }
                }
                break;
                case ("T"): {
                    for (int m = 0; m < Values.length; m++) {
                        for (int n = 0; n < Values.length; n++) {
                            String t = "T" + k;
                            String p = "P" + Values[n];
                            addLine(t, p);
                        }
                    }
                }
                break;
                default: {
                    throw new RuntimeException("输入格式不正确，请重新输入");
                }
            }
        }
    }

    public static void newLoadData() {

        //库所到变迁的弧权值
        int[][] postArcWeight = PetriModel.posMatrix.getMatrix();


        //变迁到库所的弧权值
        int[][] preArcWeight = PetriModel.preMatrix.getMatrix();

        for (int place = 0; place < postArcWeight.length; place++) {
            for (int tran = 0; tran < postArcWeight[place].length; tran++) {
                if (postArcWeight[place][tran] > 0) {
                    addLine("P" + (place + 1), "T" + (tran + 1));
                }
            }
        }

        for (int place = 0; place < preArcWeight.length; place++) {
            for (int tran = 0; tran < preArcWeight[place].length; tran++) {
                if (preArcWeight[place][tran] > 0) {
                    addLine("T" + (tran + 1), "P" + (place + 1));
                }
            }
        }


    }
}
