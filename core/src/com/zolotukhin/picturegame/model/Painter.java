package com.zolotukhin.picturegame.model;

import com.google.gson.annotations.SerializedName;

import java.util.HashMap;

/**
 * Created by Artem Zolotukhin on 7/11/17.
 */

public class Painter {

    private HashMap<String, String> names;

    @SerializedName("system_name")
    private String systemName;

    public Painter() {
    }

    public Painter(HashMap<String, String> names, String systemName) {
        this.names = names;
        this.systemName = systemName;
    }

    public HashMap<String, String> getNames() {
        return names;
    }

    public Painter setNames(HashMap<String, String> names) {
        this.names = names;
        return this;
    }

    public String getSystemName() {
        return systemName;
    }

    public Painter setSystemName(String systemName) {
        this.systemName = systemName;
        return this;
    }
}
