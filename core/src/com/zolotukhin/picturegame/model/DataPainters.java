package com.zolotukhin.picturegame.model;

import java.util.List;

/**
 * Created by Artem Zolotukhin on 7/11/17.
 */

public class DataPainters {

    private List<Painter> painters;

    public DataPainters() {
    }

    public DataPainters(List<Painter> painters) {
        this.painters = painters;
    }

    public List<Painter> getPainters() {
        return painters;
    }

    public DataPainters setPainters(List<Painter> painters) {
        this.painters = painters;
        return this;
    }
}
