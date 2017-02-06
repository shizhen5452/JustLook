package com.shizhen5452.justlook.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Create by EminemShi on 2017/2/6
 */

public class ToastUtils {

    private static Toast sToast;

    public static void showShortToast(Context context, String content) {
        if (sToast == null) {
            sToast = Toast.makeText(context.getApplicationContext(), content, Toast.LENGTH_SHORT);
        }
        sToast.setText(content);
        sToast.show();
    }

    public static void showLongToast(Context context, String content) {
        if (sToast == null) {
            sToast = Toast.makeText(context.getApplicationContext(), content, Toast.LENGTH_LONG);
        }
        sToast.setText(content);
        sToast.show();
    }

}
