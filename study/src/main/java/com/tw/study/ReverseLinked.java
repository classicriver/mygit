package com.tw.study;
/**
 * 
 * @author xiesc
 * @TODO 链表反转
 * @time 2018年7月4日
 * @version 1.0
 */
public class ReverseLinked {

	class ListNode {
	    int val;
	    ListNode next;

	    ListNode(int x) {
	        val = x;
	    }
	}
	
	/*public ListNode reverseList(ListNode head) {
		//上个节点
        ListNode prev = null;
        
        while(head!=null){
        	//取出next节点,放入临时变量
            ListNode tmp = head.next;
            //head的下个节点指向prev上个节点，实现反转
            head.next = prev;
            //当前节点head变成上个节点prev
            prev = head;
            //next节点变当前节点开始下个循环
            head = tmp;
        }
        return prev;
    }*/
	
	
	public ListNode reverseList(ListNode head) {
        if(head==null||head.next ==null)
            return head;
        ListNode prev = reverseList(head.next);
        head.next.next = head;
        head.next = null;
        return prev;
    }
	public static void main(String[] args) {
		ReverseLinked linked = new ReverseLinked();
		ListNode node1 = linked.new ListNode(1);
		ListNode node2 = linked.new ListNode(2);
		ListNode node3 = linked.new ListNode(3);
		ListNode node4 = linked.new ListNode(4);
		node1.next = node2;
		node2.next = node3;
		node3.next = node4;
		linked.reverseList(node1);
	}
}
