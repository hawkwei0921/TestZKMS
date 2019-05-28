package com.hawk.testzkms.permission;

import android.os.Bundle;

import com.hawk.testzkms.CONSTANT;
import com.hawk.testzkms.R;

public class TargetActivity extends BaseActivity {

    private static final String TAG = "TargetActivity";


    @Override
    protected String[] getRequiredPermissions() {
        return new String[] {CONSTANT.PERMISSION_ACCESS_ZION}; // For Zion Developers
/*
        // For Test
        return new String[] {
                // Contacts group
                android.Manifest.permission.READ_CONTACTS, // Contacts display name, EmailAddressAdapter

                // Calendar
                android.Manifest.permission.READ_CALENDAR, // Propose meeting

                // Storage group
                android.Manifest.permission.READ_EXTERNAL_STORAGE, // Show Attachment
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE, // Save Attachment

        };
*/
    }

    @Override
    protected String[] getDesiredPermissions() {
        return new String[] {CONSTANT.PERMISSION_ACCESS_ZION}; // For Zion Developers
/*
        // For Test
        return new String[] {
                // Contacts group
                android.Manifest.permission.READ_CONTACTS,

                // Calendar
                android.Manifest.permission.READ_CALENDAR,

                // Storage group
                android.Manifest.permission.READ_EXTERNAL_STORAGE,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
        };
*/
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);

        setContentView(R.layout.getpermission_activity);
    }
}

