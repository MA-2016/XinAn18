package com.mobileai.luncert.model.mysql.entity;

import lombok.Data;

@Data
public class User {

    private int id;
    private String account;
    private String password;
    private boolean isAdmin;

}