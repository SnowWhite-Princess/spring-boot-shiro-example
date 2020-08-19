package com.smart.example;

import org.apache.shiro.crypto.hash.Sha256Hash;
import org.apache.shiro.crypto.hash.SimpleHash;

import java.util.concurrent.ThreadPoolExecutor;

public class ShiroUtils {
    public static void main(String[] args) {
        SimpleHash simpleHash = new SimpleHash(Sha256Hash.ALGORITHM_NAME,"123456",null,1024);
        System.out.println(simpleHash.toHex());
    }
}
