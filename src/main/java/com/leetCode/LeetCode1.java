package com.leetCode;

/**
 * 宝石和石头 https://leetcode-cn.com/problems/jewels-and-stones
 *  给定字符串J 代表石头中宝石的类型，和字符串 S代表你拥有的石头。 S 中每个字符代表了一种你拥有的石头的类型，
 *      你想知道你拥有的石头中有多少是宝石。
 * J 中的字母不重复，J 和 S中的所有字符都是字母。字母区分大小写，因此"a"和"A"是不同类型的石头。
 * 示例1：
 *      输入: J = "aA", S = "aAAbbbb"
 *      输出: 3
 * 示例2：
 *       输入: J = "z", S = "ZZ"
 *      输出: 0
 * 注意 ：
 * S 和 J 最多含有50个字母。
 *  J 中的字符不重复。
 */
public class LeetCode1 {
    /**
     * 考虑更高的空间性能，使用byte数组。ASCII码中字母的跨度为65~122，所以定义数组长度为58最节省。
     * @param j
     * @param s
     * @return
     */
    public static int numJewelsInStones(String j,String s){
        int count = 0;
        if (j==null||j.isEmpty()) {
            return count;
        }
        if (s==null || s.isEmpty()) {
            return count;
        }
        byte[] ch = new byte[58];
        int ascii = 65;
        for (char item : j.toCharArray()){
           ch[item-ascii] = 1;
        }
        for (char item : s.toCharArray()){
            if (ch[item-ascii]==1) {
                count++;
            }
        }
        return count;
    }

    public static void main(String[] args) {
        String j = "aAb";
        String s = "asdaqweqAdfsb";                                 
        int count = numJewelsInStones(j,s);
        System.out.println("这堆石头中有"+count+"个是宝石");


    }

}
