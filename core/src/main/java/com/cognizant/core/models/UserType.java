package com.cognizant.core.models;

public enum UserType  {
    BUYER("buyer"),
    SELLER("seller");

    private String name;

    UserType(String buyer) {
        this.name = buyer;
    }
}
