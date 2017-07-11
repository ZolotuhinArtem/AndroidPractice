package com.zolotukhin.picturegame.model;

import java.util.HashMap;

/**
 * Created by Artem Zolotukhin on 7/11/17.
 */

public class Painter {

    private HashMap<String, String> names;

    public Painter() {
    }

    public Painter(HashMap<String, String> names) {
        this.names = names;
    }

    public HashMap<String, String> getNames() {
        return names;
    }

    public Painter setNames(HashMap<String, String> names) {
        this.names = names;
        return this;
    }
}
