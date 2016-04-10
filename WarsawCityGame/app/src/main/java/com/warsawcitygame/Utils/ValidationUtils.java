package com.warsawcitygame.Utils;

import android.text.TextUtils;

/**
 * Created by Dell on 4/10/2016.
 */
public class ValidationUtils {
    public final static boolean isValidEmail(CharSequence target) {
        if (TextUtils.isEmpty(target)) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }
}
