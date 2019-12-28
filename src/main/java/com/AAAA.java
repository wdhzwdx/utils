package com;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AAAA {
    public String tes;

    public AAAA(String tes){
        this.tes = tes;
    }
    void tet(){
        System.out.println("aa");
    }
    @Override
    public  int hashCode(){
        return 1;
    }

    public static void main(String[] args) {
        String t = "74@qq.co";
        String regex = "^[a-zA-Z0-9_.·•\\-]+@[a-zA-Z0-9_.·•\\-]+(\\.[a-zA-Z0-9_.·•-]+)+$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(t);
        while (m.find()) {
            System.out.println(m.group().replace("/",""));
            break;
        }
        String t1 = "1";


    }


}
