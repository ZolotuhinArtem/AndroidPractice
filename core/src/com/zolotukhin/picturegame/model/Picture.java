package com.zolotukhin.picturegame.model;


import java.util.HashMap;

/**
 * Created by Artem Zolotukhin on 7/11/17.
 */

public class Picture {

    private HashMap<String, String> names;

    private Painter painter;

    private String path;

    public Picture() {
    }

    public Picture(HashMap<String, String> names, Painter painter, String path) {
        this.names = names;
        this.painter = painter;
        this.path = path;
    }

    public HashMap<String, String> getNames() {
        return names;
    }

    public Picture setNames(HashMap<String, String> names) {
        this.names = names;
        return this;
    }

    public Painter getPainter() {
        return painter;
    }

    public Picture setPainter(Painter painter) {
        this.painter = painter;
        return this;
    }

    public String getPath() {
        return path;
    }

    public Picture setPath(String path) {
        this.path = path;
        return this;
    }
}
