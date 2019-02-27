package com.gradle.coder.service;

import com.gradle.coder.entity.Key;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class KeyService {

    private Key key;
    private static KeyService instance;
    private Map<String, String> keyMapCode;
    private Map<String, String> keyMapEncode;
    private final Random random = new Random();
    private ArrayList<String> supportList;

    private KeyService() {
        key = new Key();
    }

    public static synchronized KeyService getInstance() {
        if (instance == null) {
            instance = new KeyService();
        }
        return instance;
    }

    public void generateKey(File keyFile) {
        try(FileWriter keyWriter= new FileWriter(keyFile)) {
            keyMapCode = new HashMap<>();
            keyMapEncode = new HashMap<>();
            supportList = key.getSupportKey();

            for (char s1 : key.getAlphabet().toCharArray()) {
                StringBuilder stringBuilder = new StringBuilder();
                for (char s2 : key.getAlphabet().toCharArray()) {
                    int index = random.nextInt(supportList.size());
                    String codeString = supportList.get(index);
                    keyMapCode.put(String.valueOf(new char[] {s1,s2}), codeString);
                    keyMapEncode.put(codeString, String.valueOf(new char[] {s1,s2}));
                    supportList.remove(index);
                    stringBuilder.append(codeString + " ");
                }
                stringBuilder.delete(stringBuilder.length()-1,stringBuilder.length());
                if (supportList.size() != 0) stringBuilder.insert(stringBuilder.length(),"\r\n");
                keyWriter.write(stringBuilder.toString());
            }
            keyWriter.flush();
            key.setKeyCode(keyMapCode);
            key.setKeyEncode(keyMapEncode);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Key getKey() {
        return key;
    }
}
