package com.teams_mars.seller_module.service;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProductServiceImplTest {

    @Test
    public void subString() {
        String abc = "/home/next/uploads/folder/";
        System.out.println(abc.substring(abc.indexOf("/uploads/")));
    }
}