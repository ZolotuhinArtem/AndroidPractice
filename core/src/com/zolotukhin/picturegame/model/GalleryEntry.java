package com.zolotukhin.picturegame.model;

/**
 * Created by Artem Zolotukhin on 7/14/17.
 */

public class GalleryEntry {

    private Painter painter;

    private Picture picture;

    public GalleryEntry() {
    }

    public GalleryEntry(Painter painter, Picture picture) {
        this.painter = painter;
        this.picture = picture;
    }

    public Painter getPainter() {
        return painter;
    }

    public GalleryEntry setPainter(Painter painter) {
        this.painter = painter;
        return this;
    }

    public Picture getPicture() {
        return picture;
    }

    public GalleryEntry setPicture(Picture picture) {
        this.picture = picture;
        return this;
    }

    @Override
    public String toString() {
        return "GalleryEntry{" +
                "painter=" + (painter == null ? "" :painter.getSystemName()) +
                ", picture=" + (picture == null ? "" :picture.getSystemName())  +
                '}';
    }
}
