package com.gualda.sachetto.notepad.utils;

import android.content.Context;
import android.content.Intent;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.gualda.sachetto.notepad.R;

public class NavigationUtil {

    // Método para navegar para uma nova Activity
    public static void navigateTo(Context context, Class<?> targetScreen) {
        Intent intent = new Intent(context, targetScreen);
        context.startActivity(intent);
    }

    // Método para navegar entre Fragments
    public static void navigateToFragment(FragmentManager fragmentManager, Fragment fragment) {
        if (fragmentManager == null) {
            throw new IllegalArgumentException("FragmentManager não pode ser nulo");
        }
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.frame_layout, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

}
