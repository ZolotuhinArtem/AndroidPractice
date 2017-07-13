package com.zolotukhin.picturegame.model;


import com.google.gson.annotations.SerializedName;

import java.util.HashMap;

/**
 * Created by Artem Zolotukhin on 7/11/17.
 */

public class Picture {

    private HashMap<String, String> names;

    private String path;

    @SerializedName("system_name")
    private String systemName;

    public Picture() {
    }

    public Picture(HashMap<String, String> names, String path, String systemName) {
        this.names = names;
        this.path = path;
        this.systemName = systemName;
    }

    public HashMap<String, String> getNames() {
        return names;
    }

    public Picture setNames(HashMap<String, String> names) {
        this.names = names;
        return this;
    }

    public String getPath() {
        return path;
    }

    public Picture setPath(String path) {
        this.path = path;
        return this;
    }

    public String getSystemName() {
        return systemName;
    }

    public Picture setSystemName(String systemName) {
        this.systemName = systemName;
        return this;
    }
}
