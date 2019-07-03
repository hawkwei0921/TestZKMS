package com.hawk.testzkms;

import android.provider.Settings;
import android.util.Base64;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Utils {
    public static String StringToSha256String(String inputStr) {
        if(inputStr == null)
            return null;
        try {
            byte[] inputByte = inputStr.getBytes();
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(inputByte);
            return new String(Base64.encode(hash, Base64.DEFAULT));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }
}
