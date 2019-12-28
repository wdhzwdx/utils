package com.leetCode;

/**
 *  237. 删除链表中的节点
 * https://leetcode-cn.com/problems/delete-node-in-a-linked-list
 * 请编写一个函数，使其可以删除某个链表中给定的（非末尾）节点，你将只被给定要求被删除的节点。
 *
 * 现有一个链表 -- head = [4,5,1,9]，它可以表示为:
 *      4->5->1->9
 *
 *
 */
public class LeetCode3 {

    public static class ListNode{
        Object val;
        ListNode next;
        ListNode(Object val){
            this.val = val;
        }

        public String getVal(ListNode node){
            StringBuilder res = new StringBuilder();
            res.append(node.val+",");
            if(node.next!=null){
                res.append(getVal(node.next));
            }
            return res.toString();
        }

        @Override
        public String toString(){
            return getVal(this);
        }
    }

    /**
     * 要删除3
     * 1->2->3->4->5
     *   1->2->4->4->5  由于不能访问到前面的节点。 复制后面节点值到该节点   t
     *   1->2->4->5    复制完后再指向节点的下下个节点
     * @param node
     */
    public static ListNode deleteNode(ListNode node){
        ListNode next =  node.next;
        node.val = node.next.val;
        node.next = node.next.next;

        next.val = null;
        next.next = null;
        next = null;
        return next;
    }

    public static void main(String[] args) {
        ListNode node1 = new ListNode("1");
        ListNode node2 = new ListNode("2");
        ListNode node3 = new ListNode("3");
        ListNode node4 = new ListNode("4");
        ListNode node5 = new ListNode("5");

        node1.next = node2;
        node2.next = node3;
        node3.next = node4;
        node4.next = node5;

        System.out.println(node1);
        deleteNode(node3);
//        node4 = deleteNode(node3); 主动回收，清理多余节点
        System.out.println(node1);
        System.out.println(node4);
        System.out.println(node4==null);
    }
    
}
