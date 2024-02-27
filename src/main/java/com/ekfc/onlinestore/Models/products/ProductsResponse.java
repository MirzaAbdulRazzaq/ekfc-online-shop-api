package com.ekfc.onlinestore.Models.products;

import java.util.List;

public class ProductsResponse {

    private int status;
    private String message;
    private List<products> data;

    public ProductsResponse(int status, String message, List<products> data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public List<products> getProduct() {
        return data;
    }

}
