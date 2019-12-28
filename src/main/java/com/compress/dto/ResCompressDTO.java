package com.compress.dto;

import lombok.Data;

@Data
public class ResCompressDTO {
    private String invoiceCode;
    private String invoiceNumber;
    private String billingDate;
    private String deductiblePeriod;
    private String validTax;
    private String totalAmount;
    private String totalTax;
    private String checkStatus;
    private String checkDate;
    private String deductibleMode;
    private String deductibleResult;
    private String deductibleResultMsg;

    public static ResCompressDTO create(){
        ResCompressDTO resCompressDTO = new ResCompressDTO();
        resCompressDTO.setInvoiceCode("3300183130");
        resCompressDTO.setInvoiceNumber("07105067");
        resCompressDTO.setBillingDate("2019-01-23");
        resCompressDTO.setDeductiblePeriod("201907");
        resCompressDTO.setValidTax("0.01");
        resCompressDTO.setTotalAmount("1103.77");
        resCompressDTO.setTotalTax("1103.77");
        resCompressDTO.setCheckStatus("1");
        resCompressDTO.setCheckDate("2019-07-26");
        resCompressDTO.setDeductibleMode("1");
        resCompressDTO.setDeductibleResult("2");
        resCompressDTO.setDeductibleResultMsg("查无此票");
        return resCompressDTO;
    }
}
