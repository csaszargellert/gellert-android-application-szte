package com.app.nailappointment.utils;

public enum CollectionPaths {

    USERS("users"),

    DATES("dates");

    private final String name;

    CollectionPaths(String path) {
        this.name = path;
    }

    public String getName() {
        return name;
    }
}
