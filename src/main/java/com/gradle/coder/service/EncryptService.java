package com.gradle.coder.service;

import com.gradle.coder.utils.CoderUtils;
import com.gradle.coder.utils.EncryptConstants;

import java.io.File;

public class EncryptService {

    private static EncryptService instance;
    private KeyService keyService;
    private CoderUtils coderUtils;

    private File keyFile;
    private File codeFile;
    private File encodeFile;
    private File cipherFile;

    private EncryptService() {
        keyFile = new File(EncryptConstants.KEY_TXT);
        codeFile = new File(EncryptConstants.CODE_TXT);
        cipherFile = new File(EncryptConstants.SHIPHER_TEXT);
        keyService = KeyService.getInstance();
        coderUtils = new CoderUtils();
    }

    public static synchronized EncryptService getInstance() {
        if (instance == null) instance = new EncryptService();
        return instance;
    }

    public void encryption() {
        //Step 1 - Generate key
        keyService.generateKey(keyFile);

        //Step 2 - get cipher text
        coderUtils.CodingTxt(codeFile,cipherFile,keyService.getKey());
    }

}
