package com.compress.dto;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class ResCompressDTO1 {
    private String je;
    private String se;
    private String mxid;
    private String glzt;
    private String kprq;
    private String xxly;
    private String code;
    private String yxse;
    private String modifySe;
    private String number;
    private String mc;
    private String gxsj;
    private String fplx;
    private String gx;
    private String zt;

    public static ResCompressDTO1 create(){
        ResCompressDTO1 resCompressDTO = new ResCompressDTO1();
        resCompressDTO.setJe("18707.96");
        resCompressDTO.setSe("18707.96");
        resCompressDTO.setMxid("302191130,06172105,2019-08-02,01,ME0Md3Y6UJv7LvSPt%2FpBd9hyMq9vKN%2FNG5KKgoiYjV8%3D,宁波金山文体用品有限公司,0");
        resCompressDTO.setGlzt("正常");
        resCompressDTO.setKprq("2019-08-02");
        resCompressDTO.setXxly("消息来源");
        resCompressDTO.setCode("3302191130");
        resCompressDTO.setYxse("1132.04");
        resCompressDTO.setModifySe("y");
        resCompressDTO.setNumber("06172105");
        resCompressDTO.setMc("宁波金山文体用品有限公司宁波金山文体用品有限公司宁波金山文体用品有限公司宁波金山文体用品有限公司宁波金山文体用品有限公司");
        resCompressDTO.setGxsj("2019-08-02");
        resCompressDTO.setFplx("增值税专用发票");
        resCompressDTO.setGx("n");
        resCompressDTO.setZt("正常");
        return resCompressDTO;
    }

    public static void main(String[] args) {
        Map<String, ResCompressDTO1> t = new HashMap<>();
        t.put("1",create());
    }
}
