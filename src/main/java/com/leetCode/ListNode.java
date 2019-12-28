package com.leetCode;

/**
 * 单向链表结构
 */
public class ListNode {
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
