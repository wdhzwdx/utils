package com.compress.dto;

import lombok.Data;

@Data
public class CompressDTO {
    private String invoiceCode;
    private String invoiceNumber;
    private String validTax;

    public static CompressDTO create(){
        CompressDTO data = new CompressDTO();
        data.setInvoiceCode("3300183130");
        data.setInvoiceNumber("07105067");
        data.setValidTax("0.01");
        return data;
    }
    
}
