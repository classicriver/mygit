/**   
*/ 
package com.tw.study;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @Description: TODO
* @author  xiesc
* @date 2018年1月16日 
* @version V1.0   
 */
public class Immutable {
	
	private final char value;
	
	Immutable(char value){
		this.value = value;
	}
	
	public Immutable append(char value) {
		return new Immutable(value);
	}
	
	public static void main(String[] args) {
		
		Set<Integer> result = new HashSet<Integer>();
        Set<Integer> set1 = new HashSet<Integer>(){{
            add(1);
            add(3);
            add(5);
        }};

        Set<Integer> set2 = new HashSet<Integer>(){{
            add(1);
            add(2);
            add(3);
        }};

        result.clear();
        result.addAll(set1);
        System.out.println("去重复交集前1："+set1);
        System.out.println("去重复交集前2："+set2);
        result.retainAll(set2);
        System.out.println("set1与set2的交集是："+result);

        result.clear();
        result.addAll(set2);
        System.out.println("差集前的1："+set1);
        System.out.println("差集前的2："+set2);
        result.removeAll(set1);
        System.out.println("set2与set1的差集是："+result);

        result.clear();
        result.addAll(set1);
        result.addAll(set2);
       
        System.out.print("set1和set2的并集："+result);
        System.err.print("set1集合并集：是去重复"+"\n");
        
        
        
        List<Integer> list = new ArrayList<Integer>();
        List<Integer> list1 = new ArrayList<Integer>(){{
            add(1);
            add(3);
            add(5);
        }};

        List<Integer> list2 = new ArrayList<Integer>(){
        {
            add(1);
            add(2);
            add(3);
        }};

        list.clear();
        list.addAll(list1);
        System.out.println("去重复交集前1："+list1);
        System.out.println("去重复交集前2："+list2);
        list.retainAll(list2);
        System.out.println("list1与list2的交集是："+list);

        list.clear();
        list.addAll(list2);
        System.out.println("差集前的1："+list1);
        System.out.println("差集前的2："+list2);
        list.removeAll(list1);
        System.out.println("list2与list1的差集是："+list);

        list.clear();
        list.addAll(list1);
        list.addAll(list2);
        
        System.out.print("list1和set2的并集："+list);
        System.err.print("List集合并集：是不会去重复");
		
	}
}
