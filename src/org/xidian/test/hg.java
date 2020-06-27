package org.xidian.test;

//import java.util.ArrayList;
import java.util.*;
//import java.util.List;


public class hg {
	
	public static void main(String[] args) {
        List<Integer> lis = new ArrayList<>();
        lis.add(1);
        lis.add(2);
        lis.add(3);
        lis.add(4);
        lis.add(2, 34);
        lis.add(3, 67);
        System.out.println(lis);
        lis.remove(0);
        lis.remove(2);
        System.out.println(lis);
    }
     
}
