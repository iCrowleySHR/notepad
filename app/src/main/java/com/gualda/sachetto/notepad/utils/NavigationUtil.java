package com.gualda.sachetto.notepad.utils;

import android.content.Context;
import android.content.Intent;

public class NavigationUtil {

    public static void navigateTo(Context context, Class<?> targetScreen) {
        Intent intent = new Intent(context, targetScreen);
        context.startActivity(intent);
    }
}
