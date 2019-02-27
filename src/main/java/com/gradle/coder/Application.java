package com.gradle.coder;

import com.gradle.coder.service.EncryptService;

public class Application {

    public static void main(String[] args) {
        EncryptService encryptService = EncryptService.getInstance();
        encryptService.encryption();
    }
}
