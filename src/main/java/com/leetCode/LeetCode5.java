package com.leetCode;

/**
 * 合并二叉树
 * Tree 1                     Tree 2
 *           1                         2
 *          / \                       / \
 *         3   2                     1   3
 *        /                           \   \
 *       5                             4   7
 * 输出:
 * 合并后的树:
 * 	     3
 * 	    / \
 * 	   4   5
 * 	  / \   \
 * 	 5   4   7
 */
public class LeetCode5 {
    public static TreeNode mergeTrees(TreeNode t1, TreeNode t2) {
        if(t1==null){
           return t2;
        }
        if (t2==null) {
            return t1;
        }
        t1.val+=t2.val;
        t1.left = mergeTrees(t1.left,t2.left);
        t1.right = mergeTrees(t1.right,t2.right);
        return t1;
    }

    public static void main(String[] args) {
        TreeNode root = new TreeNode(10);
        root.addNode(new TreeNode(5));
        root.addNode(new TreeNode(15));
        root.addNode(new TreeNode(3));
        root.addNode(new TreeNode(7));
        root.addNode(new TreeNode(18));
        root.addNode(new TreeNode(9));

        TreeNode root1 = new TreeNode(10);
        root1.addNode(new TreeNode(3));
        root1.addNode(new TreeNode(2));
        root1.addNode(new TreeNode(11));
        root1.addNode(new TreeNode(13));
        root1.addNode(new TreeNode(18));
        root1.addNode(new TreeNode(9));

        mergeTrees(root,root1);
        System.out.println("合并完成");
    }

    
}
