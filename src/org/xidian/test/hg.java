package org.xidian.test;

//import java.util.ArrayList;
import java.util.HashSet;
//import java.util.List;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

public class hg {
	
	public static void main(String[] args) {
		Queue<Integer> lis=new LinkedList();
		lis.add(1);
		lis.add(2);
		lis.add(3);
		System.out.println(lis);
		Integer poll = lis.poll();
		System.out.println(poll);
		System.out.println(lis);
	}
     
}
