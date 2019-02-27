package com.gradle.coder.entity;

import java.util.ArrayList;
import java.util.Map;

public class Key {
    private String alphabet = "abcdefghijklmnopqrstuvwxyz";

    private Map<String,String> key;

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

    public Map<String, String> getKey() {
        return key;
    }

    public void setKey(Map<String, String> key) {
        this.key = key;
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
