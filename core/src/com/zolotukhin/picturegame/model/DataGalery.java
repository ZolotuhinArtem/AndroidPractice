package com.zolotukhin.picturegame.model;

import java.util.List;

/**
 * Created by Artem Zolotukhin on 7/14/17.
 */

public class DataGalery {

    private List<GaleryJson> pictures;

    public DataGalery() {
    }

    public DataGalery(List<GaleryJson> pictures) {
        this.pictures = pictures;
    }

    public List<GaleryJson> getPictures() {
        return pictures;
    }

    public DataGalery setPictures(List<GaleryJson> pictures) {
        this.pictures = pictures;
        return this;
    }
}
