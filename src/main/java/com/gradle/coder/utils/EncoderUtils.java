package com.gradle.coder.utils;

import com.gradle.coder.entity.Key;

import java.io.*;
import java.nio.CharBuffer;

public class EncoderUtils {

    private CharBuffer buffer = CharBuffer.allocate(2);
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;


    public void EncodingTxt(File cipherTxt, File outputTxt, Key key) {
        try {
            initValues(outputTxt, cipherTxt);
            int symbol = bufferedReader.read();
            int index = 0;
            StringBuilder stringBuilder = new StringBuilder();
            while (symbol != -1) {
                if (!checkLetterInAlphabet((char) symbol, key)) {
                    stringBuilder.append((char) symbol);
                } else {
                    buffer.append((char) symbol);
                    if (buffer.length() == 1) {
                        index = stringBuilder.length();
                    } else {
                        buffer.compact();
                        buffer.put((key.getKeyEncode().get(buffer.toString())));
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
                    buffer.put((key.getKeyCode().get(buffer.toString())));
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

    private void initValues(File outputTxt, File cipherTxt) {
        try {
            bufferedReader = new BufferedReader(new FileReader(cipherTxt));
            bufferedWriter = new BufferedWriter(new FileWriter(outputTxt));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean checkLetterInAlphabet(char letter, Key key) {
        return key.getAlphabet().indexOf(letter) != -1;
    }
}
