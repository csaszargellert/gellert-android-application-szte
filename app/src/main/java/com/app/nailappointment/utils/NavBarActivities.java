package com.app.nailappointment.utils;

import androidx.annotation.IdRes;

import com.app.nailappointment.R;

public enum NavBarActivities {
    MAIN("MainActivity", R.id.bottom_menu_home),

    LOGIN("LoginActivity", R.id.bottom_menu_login),

    SETTINGS("SettingsActivity", R.id.bottom_menu_date),

    DATE("DateActivity", R.id.bottom_menu_settings),

    NONE("NONE", -1);

    private String name;

    @IdRes
    private int itemId;

    NavBarActivities(String name, @IdRes int itemId) {
        this.name = name;
        this.itemId = itemId;
    }

    public String getActivityName() {
        return name;
    }

    public @IdRes int getItemId() {
        return itemId;
    }
}
