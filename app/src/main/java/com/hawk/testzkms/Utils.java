package com.hawk.testzkms;

import android.content.Context;
import android.content.pm.FeatureInfo;
import android.content.pm.PackageManager;
import android.provider.Settings;
import android.util.Base64;
import android.util.Log;

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

    public final static boolean isDeviceSupportHardwareWallet(Context context) {
        final String feature = "com.htc.hardware.wallet";
        final PackageManager packageManager = context.getPackageManager();
        final FeatureInfo[] featuresList = packageManager.getSystemAvailableFeatures();
        for (FeatureInfo f : featuresList) {
            if (f.name != null && f.name.equals(feature)) {
                Log.i("Utils", "isDeviceSupportHardwareWallet  true , " + f.name);
                return true;
            }
        }

        return false;
    }
}
