package org.xidian.alg.escycle.es.algorithm;

/**
 * @Author He ymLiu
 * @Date 2020/4/5 19:28
 * @Version 1.0
 */
public class QuerySpecialCycle {
 /*   public static String[] screen(Properties prop, List<String> cycles) {
//        该集合存储只有一个资源库所的环路
        List<String> onlyResourcePlaceCycle = new ArrayList<>();
        //筛选只有一个资源库所的环路
        for (int i = 0; i < cycles.size(); i++) {
            int flag = 0;
            String[] cycle = cycles.get(i).split("-");
            List<String> placeList = CollectionUtils.arrToList(Constant.RESOURCE_PLACE);
            for (int j = 0; j < cycle.length - 1; j++) {
                if (placeList.contains(cycle[j])) {
                    flag++;
                }
            }
            if (flag == 1) {
                onlyResourcePlaceCycle.add(cycles.get(i));
            }
        }


//        筛选掉重复的环路
        List<String> noRepeatOnlyResource = RepeatScreen.screen(onlyResourcePlaceCycle);
        List<String> maybeSpecialCycle = new ArrayList<>();
        //分别对每个环路中连续发射的变迁进行判断，是否是当前资源库所的后置变迁
        for (int i = 0; i < noRepeatOnlyResource.size(); i++) {
            String[] cycle = noRepeatOnlyResource.get(i).split("-");
//            遍历连续发射变迁
            for (int j = 0; j < Constant.CONTINUOUS_LAUNCH_TRANSITION.length; j++) {
                String[] cycleContinue = Constant.CONTINUOUS_LAUNCH_TRANSITION[j].split("-");
//                筛选环路是否包含连续发射的两个变迁
                if (CollectionUtils.arrToList(cycle).containsAll(CollectionUtils.arrToList(cycleContinue))) {
                    String[] postTransition = Constant.POST_TRANSITION[j].split("-");
//                    检查环路中是否至少包含一个连续发射变迁的后置变迁
                    if (CollectionUtils.containOnly(CollectionUtils.arrToList(cycle),CollectionUtils.arrToList(postTransition))) {
//                        该集合保存具有一个资源库所，两个连续发射变迁，一个后置变迁的环路集合
                        maybeSpecialCycle.add(noRepeatOnlyResource.get(i));
                    }
                }
            }
        }



        List<String> realSpecialCycle=new ArrayList<>();
        String resource=null;
        for (int i = 0; i < maybeSpecialCycle.size(); i++) {
            int flag=0;
//            环路的节点集
            String[] cycle = maybeSpecialCycle.get(i).split("-");
            List<String> cycleLis = CollectionUtils.arrToList(cycle);
            for (String Node:cycle){
                switch (Node){
                    case("P13") : resource="13";break;
                    case("P14") : resource="14";break;
                    case("P15") : resource="15";break;
                    case("P16") : resource="16";break;
                    default:continue;
                }
            }
//            库所的后置变迁集
            String[] postTransition = prop.getProperty(resource).split(",");
            for (String node:postTransition){
                if (cycleLis.indexOf("T"+node)>-1){
                    flag++;
                }
            }



            if (flag>1){
                realSpecialCycle.add(maybeSpecialCycle.get(i));
            }
        }

        return CollectionUtils.listToArray(realSpecialCycle);
    }*/
    /*该算法主要是判断资源库所的后置变迁数目至少有两个存在于环路中，不是很准确，最好的方法是判断资源库所的前置变迁是否是两个连续发射变迁的后置变迁*/
}

