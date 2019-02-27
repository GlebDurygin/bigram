package com.gradle.coder.utils;

import com.gradle.coder.entity.Key;

import java.io.*;
import java.nio.CharBuffer;
import java.util.Map;

public class CoderUtils {

    private CharBuffer buffer =CharBuffer.allocate(2);
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private Key key;

    public void CodingTxt(File inputTxt, File cipherTxt, Key key) {
        try {
            initValues(inputTxt, cipherTxt);
            Map<String, String> keyMap = key.getKey();
            this.key = key;
            int symbol = bufferedReader.read();
            int index = 0;
            StringBuilder stringBuilder = new StringBuilder();
            while (symbol != -1) {
                if (!checkLetterInAlphabet((char) symbol)) {
                    stringBuilder.append((char) symbol);
                } else {
                    buffer.append((char) symbol);
                    if (buffer.length() == 1) {
                        index = stringBuilder.length();
                    } else {
                        buffer.compact();
                        buffer.put((keyMap.get(buffer.toString())));
                        buffer.compact();
                        stringBuilder.append(buffer.get(1));
                        stringBuilder.insert(index, buffer.get(0));
                        bufferedWriter.write(stringBuilder.toString());
                        stringBuilder.delete(0, stringBuilder.length());
                    }
                }
                symbol = bufferedReader.read();
            }
            if (buffer.length() == 1 || stringBuilder.length() != 0) {
                if (buffer.length() == 1) {
                    buffer.append(key.getSpecialCharacter());
                    buffer.compact();
                    buffer.put((keyMap.get(buffer.toString())));
                    buffer.compact();
                    stringBuilder.append(buffer.get(1));
                    stringBuilder.insert(index, buffer.get(0));
                }
                bufferedWriter.write(stringBuilder.toString());
                stringBuilder.delete(0, stringBuilder.length());
            }
            bufferedReader.close();
            bufferedWriter.flush();
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initValues(File inputTxt, File cipherTxt) {
        try {
            bufferedReader = new BufferedReader(new FileReader(inputTxt));
            bufferedWriter = new BufferedWriter(new FileWriter(cipherTxt));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean checkLetterInAlphabet(char letter) {
        if (key.getAlphabet().indexOf(letter) != -1) return true;
        else return false;
    }
}
