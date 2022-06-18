package com.cognizant.product.cmd.api.dto;


import com.cognizant.core.dto.BaseResponse;

public class NewProductResponse extends BaseResponse {
    private String id;

    public NewProductResponse(String id, String message) {
        super(message);
        this.id = id;
    }
}
