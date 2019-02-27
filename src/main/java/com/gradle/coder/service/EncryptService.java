package com.gradle.coder.service;

import com.gradle.coder.utils.EncryptConstants;

import java.io.*;
import java.nio.CharBuffer;

public class EncryptService {

    private static EncryptService instance;
    private KeyService keyService;

    private File keyFile;
    private File codeFile;
    private File encodeFile;
    private File cipherFile;

    private CharBuffer buffer = CharBuffer.allocate(2);
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;

    private EncryptService() {
        keyFile = new File(EncryptConstants.KEY_TXT);
        codeFile = new File(EncryptConstants.CODE_TXT);
        cipherFile = new File(EncryptConstants.SHIPHER_TEXT);
        encodeFile = new File(EncryptConstants.ENCODE_TXT);

        keyService = KeyService.getInstance();
    }

    public static synchronized EncryptService getInstance() {
        if (instance == null) instance = new EncryptService();
        return instance;
    }

    public void fullEncryptionProcess() {
        //Step 1 - Generate key
        keyService.generateKey(keyFile);

        //Step 2 - get cipher text
        encryption(true);

        //Step 3 - get output text
        encryption(false);
    }

    private void encryption(boolean code) {
        try {
            if (code) initValues(codeFile, cipherFile);
            else initValues(cipherFile, encodeFile);
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
                        if (code) buffer.put((keyService.getKey().getKeyCode().get(buffer.toString())));
                        else buffer.put((keyService.getKey().getKeyEncode().get(buffer.toString())));
                        buffer.compact();
                        if (bufferedReader.ready()) stringBuilder.append(buffer.get(1));
                        stringBuilder.insert(index, buffer.get(0));
                        bufferedWriter.write(stringBuilder.toString());
                        stringBuilder.delete(0, stringBuilder.length());
                    }
                }
                symbol = bufferedReader.read();
            }
            if (buffer.length() == 1 || stringBuilder.length() != 0) {
                if (buffer.length() == 1) {
                    buffer.append(keyService.getKey().getSpecialCharacter());
                    buffer.compact();
                    if (code) buffer.put((keyService.getKey().getKeyCode().get(buffer.toString())));
                    else buffer.put((keyService.getKey().getKeyEncode().get(buffer.toString())));
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

    private void initValues(File readTxt, File writeTxt) {
        try {
            bufferedReader = new BufferedReader(new FileReader(readTxt));
            bufferedWriter = new BufferedWriter(new FileWriter(writeTxt));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean checkLetterInAlphabet(char letter) {
        return keyService.getKey().getAlphabet().indexOf(letter) != -1;
    }
}
