package com.hawk.testzkms;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Service;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.hawk.testzkms.permission.TargetActivity;
import com.htc.wallet.server.IZKMS;
import com.htc.wallet.server.PublicKeyHolderParcel;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "MainActivity";

    private static Activity sActivity;
    private IZKMS mZKMS;
    public int mPID;
    int intValue = RESULT.UNKNOWN;
    String mZKMS_version;
    String mStrApiVersion;
    long uid;
    String wallet_name = "MyWallet";
    String sha256 = "123456789";
    Handler mHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sActivity = this;
        setContentView(R.layout.activity_main);
    }

    public void getPermissions(View v){
        Intent intent = new Intent();
        intent.setClass(sActivity, TargetActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    ZKMSConnection mZKMSConnection = new ZKMSConnection();
    private class ZKMSConnection implements ServiceConnection
    {
        public void onServiceConnected(ComponentName name, IBinder binder)
        {
            Log.i(TAG,"onServiceConnected");
            try
            {
                mZKMS = IZKMS.Stub.asInterface(binder);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName)
        {
            Log.i(TAG,"onServiceDisconnected");
        }
    }

    public void bindService(View v){
        Intent intent = new Intent();
        intent.setAction("com.htc.wallet.server.ZKMS");
        intent.setPackage("com.htc.wallet");
        boolean bBind = MainActivity.this.bindService(intent, mZKMSConnection, Service.BIND_AUTO_CREATE);
        if(bBind == true)
            Log.d(TAG, "bind ZKMS success!");
        else
            showUpdateDialog(sActivity, "ROM not support ZKMS.");
    }

    public void demoAPIs(View v){
        mHandler = new Handler();
        mHandler.post(apiRunnable);
    }

    final Runnable apiRunnable = new Runnable() {
        @Override
        public void run() {
            try {
                if ((ActivityCompat.checkSelfPermission(sActivity,  "com.htc.wallet.permission.ACCESS_ZION") != PackageManager.PERMISSION_GRANTED) ||
                   (mZKMS == null)) {
                    Toast.makeText(sActivity,"No ACCESS_ZION permission or bind ZKMS", Toast.LENGTH_LONG).show();
                    return;
                }
                // call ZKMS APIs in background thread
                // 1. init
                mPID = android.os.Process.myPid();
                mZKMS_version = com.htc.wallet.server.BuildConfig.VERSION_NAME;
                intValue = mZKMS.init(mPID, com.htc.wallet.server.BuildConfig.VERSION_NAME);
                // 2. getApiVersion
                mStrApiVersion = mZKMS.getApiVersion();
                // 3. register
                uid = mZKMS.register(wallet_name, sha256);
                // 4. create Seed
                // intValue = mZKMS.createSeed(uid);
                intValue = mZKMS.restoreSeed(uid);
                PublicKeyHolderParcel accountxPubKey = mZKMS.getAccountExtPublicKey(uid, 44, 145, 0);
                PublicKeyHolderParcel bip32xPubKey = mZKMS.getBipExtPublicKey(uid, 44, 145, 0, 0,0);
                Log.d(TAG, "test end!");
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    };

    public int init(View v){
        mPID = android.os.Process.myPid();
        mZKMS_version = com.htc.wallet.server.BuildConfig.VERSION_NAME;

        try {
            intValue = mZKMS.init(mPID, com.htc.wallet.server.BuildConfig.VERSION_NAME);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        switch (intValue) {
            case RESULT.E_ZKMA_TOO_OLD: // ZKMS only
                showUpdateDialog(sActivity, "PLEASE UPDATE Zion Wallet APP or SYSTEM");
                break;
            case RESULT.E_SDK_ROM_SERVICE_TOO_OLD:
            case RESULT.E_SDK_ROM_TZAPI_TOO_OLD:
                showUpdateDialog(sActivity, "PLEASE UPDATE YOUR SYSTEM");
                break;
            case RESULT.E_TEEKM_TAMPERED:
                showUpdateDialog(sActivity, "SDK can't support Rooted device");
                break;
            default:
                Log.d(TAG, "init("+mPID+","+mZKMS_version+")  result=" + intValue);
        }

        return intValue;
    }

    public void getApiVersion(View v){
        try {
            mStrApiVersion = mZKMS.getApiVersion();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void register(View v){

        try {
            uid = mZKMS.register(wallet_name, sha256);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void createSeed(View v){
        try {
            intValue = mZKMS.createSeed(uid);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void clearSeed(View v){
        try {
            intValue = mZKMS.clearSeed(uid);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public static void showUpdateDialog(final Activity activity, final String prompt) {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                // Use the Builder class for convenient dialog construction
                AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                builder.setMessage(prompt)
                        .setPositiveButton("Update Now!", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Log.d(TAG, "clicked the Update button");
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Log.d(TAG, "clicked the Cancel button");
                            }
                        });
                builder.create();
                builder.show();
            }
        });
    }

    public void unbindService(View v){
        try {
            MainActivity.this.unbindService(mZKMSConnection);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
