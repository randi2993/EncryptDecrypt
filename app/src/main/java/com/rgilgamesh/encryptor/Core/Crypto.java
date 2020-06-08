package com.rgilgamesh.encryptor.Core;

import android.os.Build;

import androidx.annotation.RequiresApi;

public class Crypto {
    //region Attributes
    //region Privates
    //region Vocals
    private final char[] Vocals = new char[]{
            'A', 'E', 'I', 'O', 'U',
            'a', 'e', 'i', 'o', 'u'}; // 10 chars only
    private final char[] VocalsEncode = new char[]{
            '*', '.', '@', ')', '_',
            '~', '^', '$', '(', '-'}; // 10 chars only
    //endregion
    //region Consonants NOT USED
    private final char[] Consonants = new char[] { // NOT USED
            'B', 'C', 'D', 'F', 'G', 'H',
            'J', 'K', 'L', 'M', 'N', 'Ñ',
            'P', 'Q', 'R', 'S', 'T', 'V',
            'W', 'X', 'Y', 'Z', // Upper
            'b', 'c', 'd', 'f', 'g', 'h', // lower
            'j', 'k', 'l', 'm', 'n', 'ñ',
            'p', 'q', 'r', 's', 't', 'v',
            'w', 'x', 'y', 'z'};
    //endregion
    //endregion
    //endregion

    //region Encrypt
    //region Level 1 Base64
    public String encodeBase64(String text){
        byte[] array = java.util.Base64.getEncoder().encode(text.getBytes());
        return new String(array);
    }
    //endregion
    //region Level 2 Vocals
    public String encodeVocals(String text){
        for (int i = 0; i < Vocals.length; i++){
            text = text.replace(Vocals[i], VocalsEncode[i]);
        }

        return text;
    }
    //endregion
    //region Level 3 Consonants
    public String encodeConsonants(String text){
        StringBuilder values = new StringBuilder();
        int ASCIINumber = 0;

        for(int i = 0; i < text.length(); i++){
            ASCIINumber = (int) text.charAt(i) + 1;
            values.append((char)(ASCIINumber));
        }

        return values.toString();
    }
    //endregion
    //endregion

    //region Decrypt
    //region Level 1 Consonants
    public String decodeConsonants(String text){
        StringBuilder values = new StringBuilder();
        int ASCIINumber = 0;

        for(int i = text.length() - 1; i >= 0; i--){
            ASCIINumber = (int) text.charAt(i) - 1;
            values.append((char)(ASCIINumber));
        }

        return values.reverse().toString();
    }
    //endregion
    //region Level 2 Vocals
    public String decodeVocals(String text){
        for (int i = 0; i < Vocals.length; i++){
            text = text.replace(VocalsEncode[i], Vocals[i]);
        }

        return text;
    }
    //endregion
    //region Level 3 Base64
    public String decodeBase64(String text){
        byte[] array = java.util.Base64.getDecoder().decode(text);
        return new String(array);
    }
    //endregion
    //endregion

    // Must be used in Lvl1, lvl2 Encrypt
    // Must be used in Lvl3, lvl2 Decrypt
    public String reverse(String text){
        return new StringBuilder(text).reverse().toString();
    }
}
