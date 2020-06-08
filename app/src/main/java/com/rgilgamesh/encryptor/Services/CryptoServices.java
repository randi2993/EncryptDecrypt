package com.rgilgamesh.encryptor.Services;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.rgilgamesh.encryptor.Core.Crypto;

public class CryptoServices {
    private Crypto CryptoObj;

    public CryptoServices(){
        CryptoObj = new Crypto();
    }

    public String encodeText(String text){
        // Lvl 1
        String temp = CryptoObj.encodeBase64(text);
        // Lvl 2
        temp = CryptoObj.encodeVocals(temp);
        // Lvl 3
        temp = CryptoObj.reverse(temp);
        // Lvl 4
        temp = CryptoObj.encodeConsonants(temp);
        return temp;
    }

    public String decodeText(String text){
        // Lvl 1
        String temp = CryptoObj.decodeConsonants(text);
        // Lvl 2
        temp = CryptoObj.reverse(temp);
        // Lvl 3
        temp = CryptoObj.decodeVocals(temp);
        // Lvl 4
        temp = CryptoObj.decodeBase64(temp);
        return temp;
    }
}
