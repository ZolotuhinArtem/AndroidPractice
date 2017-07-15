package com.zolotukhin.picturegame.model;

import com.google.gson.annotations.SerializedName;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Artem Zolotukhin on 7/11/17.
 */

public class Painter {

    private HashMap<String, String> names;

    @SerializedName("system_name")
    private String systemName;

    private List<Picture> pictures;

    public Painter() {
    }

    public Painter(HashMap<String, String> names, String systemName, List<Picture> pictures) {
        this.names = names;
        this.systemName = systemName;
        this.pictures = pictures;
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

    public List<Picture> getPictures() {
        return pictures;
    }

    public Painter setPictures(List<Picture> pictures) {
        this.pictures = pictures;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Painter painter = (Painter) o;

        return systemName != null ? systemName.equals(painter.systemName) : painter.systemName == null;
    }

    @Override
    public int hashCode() {
        return systemName != null ? systemName.hashCode() : 0;
    }
}
