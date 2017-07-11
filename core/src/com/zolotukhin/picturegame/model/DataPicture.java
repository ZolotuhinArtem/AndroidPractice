package com.zolotukhin.picturegame.model;

import java.util.List;

/**
 * Created by Artem Zolotukhin on 7/11/17.
 */

public class DataPicture {

    private List<Picture> pictures;

    public DataPicture() {
    }

    public DataPicture(List<Picture> pictures) {
        this.pictures = pictures;
    }

    public List<Picture> getPictures() {
        return pictures;
    }

    public DataPicture setPictures(List<Picture> pictures) {
        this.pictures = pictures;
        return this;
    }
}
