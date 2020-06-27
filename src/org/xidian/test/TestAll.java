package org.xidian.test;

import org.xidian.alg.escycle.es.algorithm.RepeatScreen;
import org.junit.Test;
import org.xidian.alg.escycle.util.CollectionUtils;

import java.io.IOException;
import java.util.*;

/**
 * @Author He ymLiu
 * @Date 2020/4/3 19:13
 * @Version 1.0
 */
public class TestAll {
    @Test
    public void test1() throws IOException {
        List<String> s1 = new ArrayList<>();
        List<String> s2 = new ArrayList<>();
        s1.add("1");
        s1.add("1");
        s1.add("2");
        s1.add("2");
        s1.add("3");


        s2.add("1");
        s2.add("2");
        s2.add("3");
        s2.add("3");
        System.out.println(CollectionUtils.reduceRepeat(s1));
        System.out.println(CollectionUtils.isSubset(s1, s2));


    }

    @Test
    public void test2() {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                int n = 0;
                if (i == j) {
                    continue;
                }
                n++;

            }

        }


    }

    @Test
    public void test3() {
        List<String> cycles = new ArrayList<>();
        List<String> cycles2 = new ArrayList<>();
        cycles.add("T1");
        cycles.add("T1");
        cycles2.add("T1");
        cycles2.add("T2");
        cycles.remove("T1");
        System.out.println(cycles);


    }

    @Test
    public void test4() {
        String one = "P16-T4-P5-T6-P14-T10-P16";
        String two = "T3-P4-T5-P6-T7-P16-T8-P8-T9-P9-T11-P11-T13-P12-T14-P13-T12-P14-T10-P15-T3";
        String three = "P1-T4-P5-T6-P17-T10-P16";
        String four = "P16-T4-P5-T6-P11-T10-P16";
        System.out.println(one.startsWith("P"));
        System.out.println(one.startsWith("T"));


    }

    @Test
    public void test5() {
        List<Integer> lis = Arrays.asList(1, 2, 3, 4, 5);
        ArrayList<Integer> list = new ArrayList<>(lis);
        list.remove(0);
        System.out.println(list);
        System.out.println(list.get(0));
    }

    @Test
    public void test6() {
        System.out.println(System.nanoTime());
    }
}
