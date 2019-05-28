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

import android.content.Context;
import android.content.pm.PackageManager;

public class RequestPermissionUtil {
    
    public static final int PERMISSION_REQUEST_COUNT = 3;
    
    public static interface RequestCode {
        public static final int PERMISSION_REQUEST_CALL_PHONE = 0;
        public static final int PERMISSION_REQUEST_READ_CONTACTS = 1;
        public static final int PERMISSION_REQUEST_READ_EXTERNAL_STORAGE = 2;
    }
    
    public static interface RuntimePermissionHelper {
        void addPendingPermissionTask(int requestCode, PendingPermissionTask task);
        boolean isRequestingPermission(int requestCode);
        void setIsRequestingPermission(int requestCode, boolean isRequesting);
    }
    
    public static abstract class PendingPermissionTask implements Runnable {
        
    };
    
    public static boolean checkRequiredPermission(Context context, String [] requiredPermissions) {
        if (context == null) {
             return false;
        }
        
        if (requiredPermissions != null) {
            for (int i = 0; i < requiredPermissions.length; i++) {
                if (context.checkSelfPermission(requiredPermissions[i]) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }
}
