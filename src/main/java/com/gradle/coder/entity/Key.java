package com.gradle.coder.entity;

import java.util.ArrayList;
import java.util.Map;

public class Key {
    private String alphabet = "abcdefghijklmnopqrstuvwxyz";

    private Map<String,String> keyCode;

    public Map<String, String> getKeyEncode() {
        return keyEncode;
    }

    public void setKeyEncode(Map<String, String> keyEncode) {
        this.keyEncode = keyEncode;
    }

    private Map<String,String> keyEncode;

    private ArrayList<String> supportKey = new ArrayList<>();

    public ArrayList<String> getSupportKey() {
        return supportKey;
    }

    public Key() {
        for (char s1 : alphabet.toCharArray()) {
            for (char s2 : alphabet.toCharArray()) {
                supportKey.add(String.valueOf(new char[] {s1,s2}));
            }
        }
    }

    public Map<String, String> getKeyCode() {
        return keyCode;
    }

    public void setKeyCode(Map<String, String> keyCode) {
        this.keyCode = keyCode;
    }

    public String getAlphabet() {
        return alphabet;
    }

    public void setAlphabet(String alphabet) {
        this.alphabet = alphabet;
    }

    public char getSpecialCharacter() {
        return alphabet.charAt(alphabet.length()-1);
    }
}
