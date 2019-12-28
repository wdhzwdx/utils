package com.leetCode;

import java.util.Stack;

/**
 *
 * 给定二叉搜索树的根结点 root，返回 L 和 R（含）之间的所有结点的值的和。
 *
 * 二叉搜索树保证具有唯一的值。
 *     即计算 L《 X《R  的值之和
 * 如：root=[10,5,15,3,7,null,18],L=7,R=15  ->32
 *          10
 *        5   15
 *      3  7    18
 *
 *    root=[10,5,15,3,7,13,18,1,null,6] L=6,R=10  ->23
 */
public class LeetCode4 {

    static int count = 0;
    static int sum= 0;
    /**
     * 当前节点为 null 时返回 0
     * 当前节点 X < L 时则返回右子树之和
     * 当前节点 X > R 时则返回左子树之和
     * 当前节点 X=L  时则返回当前节点+右子数之和
     * 当前节点 X=R  时则放回当前节点+左子数之和
     * 当前节点 X >= L 且 X <= R 时则返回：当前节点值 + 左子树之和 + 右子树之和
     *
     * @param root
     * @param L
     * @param R
     * @return
     */
    public static int rangeSumBST(TreeNode root, int L, int R) {
        count++;
        if (root == null) {
            return 0;
        }
        if (root.val < L) {
            return rangeSumBST(root.right, L, R);
        }
        if (root.val==L) {
            return root.val + rangeSumBST(root.right, L, R);
        }
        if (root.val > R) {
            return rangeSumBST(root.left, L, R);
        }
        if (root.val==R) {
            return root.val + rangeSumBST(root.left, L, R);
        }
        return root.val + rangeSumBST(root.left, L, R) + rangeSumBST(root.right, L, R);
    }

    /**
     * 官方
     * @param root
     * @param L
     * @param R
     * @return
     */
    public static int rangeSumBST1(TreeNode root, int L, int R) {
        int ans = 0;
        Stack<TreeNode> stack = new Stack();
        stack.push(root);
        while (!stack.isEmpty()) {
            sum++;
            TreeNode node = stack.pop();
            if (node != null) {
                if (L <= node.val && node.val <= R){
                    ans += node.val;
                }
                if (L < node.val) {
                    stack.push(node.left);
                }
                if (node.val < R) {
                    stack.push(node.right);
                }
            }
        }
        return ans;
    }

    public static void main(String[] args) {
        TreeNode root = new TreeNode(10);
        
        root.addNode(new TreeNode(5));
        root.addNode(new TreeNode(15));
        root.addNode(new TreeNode(3));
        root.addNode(new TreeNode(7));
        root.addNode(new TreeNode(18));
        root.addNode(new TreeNode(9));


        int data = rangeSumBST(root,7,15);
        System.out.println("总共递归调用"+count);
        System.out.println(data);
        int data1 = rangeSumBST1(root,7,15);
        System.out.println("总共递归1调用"+sum);
        System.out.println(data1);

    }
}
