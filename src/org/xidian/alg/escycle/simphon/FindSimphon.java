package org.xidian.alg.escycle.simphon;

import org.xidian.alg.escycle.util.CollectionUtils;
import org.xidian.model.PetriModel;

import java.util.*;
import java.util.List;

/**
 * @version 1.0
 * @Author He ymLiu
 * @date 2020/3/15 10:30
 */
public class FindSimphon {
    public static ArrayList<ArrayList<Integer>> Simphon() {
        int si;
        MyCombine combine = new MyCombine();

        /**
         * 存放的是每个库所的前置变迁
         */
        ArrayList<ArrayList<Integer>> Post = new ArrayList<>();

        /**
         * 存放的是每个库所的后置变迁
         */
        ArrayList<ArrayList<Integer>> Pre = new ArrayList<>();
        ArrayList<Integer> lisEndPost = new ArrayList<>();
        ArrayList<Integer> lisEndPre = new ArrayList<>();
        ArrayList<ArrayList<Integer>> lisEnd = new ArrayList<>();
        ArrayList<ArrayList<Integer>> relis = new ArrayList<>();
        ArrayList<ArrayList<Integer>> end = new ArrayList<>();


        /*************************************************************/
        int[][] preTranWeight = PetriModel.preMatrix.getMatrix();
        for (int place = 0; place < preTranWeight.length; place++) {
            ArrayList<Integer> preTrans = new ArrayList<>();
            for (int tran = 0; tran < preTranWeight[place].length; tran++) {
                if (preTranWeight[place][tran] != 0) {
                    preTrans.add(tran + 1);
                }
            }
            Post.add(preTrans);
        }

        int[][] postTranWeight = PetriModel.posMatrix.getMatrix();
        for (int place = 0; place < postTranWeight.length; place++) {
            ArrayList<Integer> postTrans = new ArrayList<>();
            for (int tran = 0; tran < postTranWeight[place].length; tran++) {
                if (postTranWeight[place][tran] != 0) {
                    postTrans.add(tran + 1);
                }
            }
            Pre.add(postTrans);
        }


        /**************************************************************/

        int i = postTranWeight.length;
        String[] t = new String[i];
        for (int c = 0; c < i; c++) {
            t[c] = Integer.toString(c);
        }

        for (int n = 1; n <= preTranWeight.length; n++) {

            List<String> lis = combine.combine(t, n);
            int m = combine.getSize();
            for (int m1 = 0; m1 < m; m1++) {
                String[] s = lis.get(m1).split(" ");
                /*
                r存放的是nn组合的单个n组合，一个组合有n个元素
                */

                ArrayList<Integer> r = CollectionUtils.reform(s);

                for (int n1 = 0; n1 < n; n1++) {
                    /*
                    lisEndPost存放的是前置子集的集合[[][][][][]]
                    */
                    lisEndPost.addAll(Post.get(r.get(n1)));
                    lisEndPre.addAll(Pre.get(r.get(n1)));
                }
                List<Integer> Pos = CollectionUtils.reduceRepeat(lisEndPost);
                List<Integer> Pr = CollectionUtils.reduceRepeat(lisEndPre);
                if (CollectionUtils.isRealSubset(Pr, Pos) == true) {
                    lisEnd.add(r);
                }
                lisEndPost.clear();
                lisEndPre.clear();

            }

        }

        si = lisEnd.size();
        for (int zi = 0; zi < si; zi++) {
            for (int ki = 0; ki < si; ki++) {
                if (CollectionUtils.isSubset(lisEnd.get(zi), lisEnd.get(ki))) {
                    break;
                }
                if (ki == si - 1) {
                    relis.add(lisEnd.get(zi));
                }
            }
        }
        for (int r1 = 0; r1 < relis.size(); r1++) {
            for (int r2 = 0; r2 < relis.get(r1).size(); r2++) {
                lisEndPost.addAll(Post.get(relis.get(r1).get(r2)));
                lisEndPre.addAll(Pre.get(relis.get(r1).get(r2)));
            }

            if (lisEndPost.size() == lisEndPre.size()) {
                if (lisEndPost.containsAll(lisEndPre)) {
                    end.add(relis.get(r1));
                }
            }

            lisEndPost.clear();
            lisEndPre.clear();
        }
        relis.removeAll(end);

        for (ArrayList<Integer> list : relis) {
            for (int serial = 0; serial < list.size(); serial++) {
                list.set(serial, list.get(serial) + 1);
            }
        }
        return relis;
    }
}