package com.leetCode;

/**
 * 给你一个有效的 IPv4 地址 address，返回这个 IP 地址的无效化版本。
 *
 * 所谓无效化 IP 地址，其实就是用 "[.]" 代替了每个 "."。
 */
public class LeetCode2 {
    
    public static void defangIPaddr(String address) {
        StringBuilder res = new StringBuilder();
        for (char item : address.toCharArray()){
            if (item == '.') {
                res.append("[.]");
            } else {
                res.append(item);
            }
        }
        System.out.println("转化后的地址为："+res.toString());
    }

    public static void main(String[] args) {
        String ip = "1.0.0.1";
        defangIPaddr(ip);
    }
}
