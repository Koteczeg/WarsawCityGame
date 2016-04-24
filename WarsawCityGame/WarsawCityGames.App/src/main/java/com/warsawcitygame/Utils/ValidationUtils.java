package com.warsawcitygame.Utils;

import android.text.TextUtils;


public class ValidationUtils {
    public static boolean isValidEmail(CharSequence target)
    {
        return !TextUtils.isEmpty(target) && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }
}
