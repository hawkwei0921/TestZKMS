/*
 * Copyright (C) 2015 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.hawk.testzkms.permission;

import android.app.Activity;

/**
 * Activity that requests permissions needed for activities exported from Contacts.
 */
public class RequestPermissionsActivity extends RequestPermissionsActivityBase {
    
    private static String[] mRequiredPermissions = new String[] {};
    private static String[] mDesiredPermissions = new String[] {};
    
    @Override
    protected String[] getRequiredPermissions() {
        return mRequiredPermissions;
    }

    @Override
    protected String[] getDesiredPermissions() {
        return mDesiredPermissions;
    }
    
    public static boolean startPermissionActivity(Activity activity, String[] requiredPermissions, String[] desiredPermissions) {
        boolean isRequired = (requiredPermissions != null && requiredPermissions.length > 0) ? true : false;
        boolean isDesired = (desiredPermissions != null && desiredPermissions.length > 0) ? true : false;
        if (!isRequired && !isDesired) return false;
        
        mRequiredPermissions = requiredPermissions;
        mDesiredPermissions = desiredPermissions;
        return startPermissionActivity(activity, requiredPermissions,
                RequestPermissionsActivity.class);
    }
}
