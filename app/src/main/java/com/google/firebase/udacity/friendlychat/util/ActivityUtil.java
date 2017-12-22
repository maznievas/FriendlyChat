package com.google.firebase.udacity.friendlychat.util;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;


public final class ActivityUtil {
    public static void addFragmentToActivity(FragmentManager fragmentManager,
                                             Fragment fragment,
                                             int frameId) {
        fragmentManager
                .beginTransaction()
                .add(frameId, fragment)
                .commit();
    }
}
