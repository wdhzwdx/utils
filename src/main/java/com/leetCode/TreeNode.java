package com.leetCode;

/**
 * 二叉树
 */
public class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;
    TreeNode(int val){
        this.val = val;
    }
    
    public static void putNode(TreeNode root,TreeNode next){
        TreeNode left = root.left;
        TreeNode right = root.right;
        if(root.val>next.val){
            if (left==null) {
                root.left = next;
            }else {
                putNode(left,next);
            }
        }
        if(root.val<next.val){
            if(right==null){
                root.right = next;
            }else {
                putNode(right,next);
            }
        }
    }

    public void addNode(TreeNode next){
        TreeNode left = this.left;
        if(this.val>next.val){
            if (left==null) {
                this.left = next;
            }else {
                left.addNode(next);
            }
        }
        TreeNode right = this.right;
        if(this.val<next.val){
            if(right==null){
                this.right = next;
            }else {
                right.addNode(next);
            }
        }
    }


}
