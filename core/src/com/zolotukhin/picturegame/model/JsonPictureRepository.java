package com.zolotukhin.picturegame.model;

import com.badlogic.gdx.Gdx;
import com.google.gson.Gson;

import java.io.Reader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Artem Zolotukhin on 7/11/17.
 */

public class JsonPictureRepository implements PictureRepository {

    public static final String FILE_PATH = "data/data_picture.json";

    private static DataPicture dataPicture;

    public JsonPictureRepository() {

        if (dataPicture == null) {
            Gson gson = new Gson();
            Reader reader = Gdx.files.internal(FILE_PATH).reader();
            dataPicture = gson.fromJson(reader, DataPicture.class);
        }
    }

    @Override
    public List<Picture> getAll() {
        List<Picture> list = new ArrayList<>(dataPicture.getPictures().size());
        for (Picture i : dataPicture.getPictures()) {
            list.add(i);
        }
        return list;
    }

    @Override
    public List<Picture> getByPainter(Painter painter) {
        List<Picture> list = new ArrayList<>(dataPicture.getPictures().size());

        for (Picture i : dataPicture.getPictures()) {
            if (i.getPainter().equals(painter)) {
                list.add(i);
            }
        }

        return list;
    }

    @Override
    public List<Picture> getByName(String name) {
        List<Picture> list = new ArrayList<>(dataPicture.getPictures().size());

        for (Picture i : dataPicture.getPictures()) {
            if (i.getNames().values().contains(name)) {
                list.add(i);
            }
        }
        return null;
    }

    @Override
    public List<Painter> getAllPainters() {
        Set<Painter> painters = new HashSet<>();
        for (Picture i : dataPicture.getPictures()) {
            painters.add(i.getPainter());
        }
        List<Painter> list = new ArrayList<>(painters.size());
        for (Painter i : painters) {
            list.add(i);
        }
        return list;
    }

    @Override
    public Painter getBySystemName(String systemName) {
        List<Painter> list = new ArrayList<>();
        for (Painter i : getAllPainters()) {
            if (i.getSystemName().equals(systemName)) {
                return i;
            }
        }
        return null;
    }
}
