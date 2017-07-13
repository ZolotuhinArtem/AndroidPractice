package com.zolotukhin.picturegame.model;

import com.badlogic.gdx.Gdx;
import com.google.gson.Gson;

import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Artem Zolotukhin on 7/11/17.
 */

public class JsonPictureRepository implements PictureRepository {

    public static final String FILE_PATH = "data/data_picture.json";

    private static DataPainters dataPainters;

    public JsonPictureRepository() {

        if (dataPainters == null) {
            Gson gson = new Gson();
            Reader reader = Gdx.files.internal(FILE_PATH).reader();
            dataPainters = gson.fromJson(reader, DataPainters.class);
        }
    }

    @Override
    public List<Picture> getAll() {
        List<Picture> list = new ArrayList<>();
        for (Painter i : dataPainters.getPainters()) {
            list.addAll(i.getPictures());
        }
        return list;
    }

    @Override
    public List<Picture> getByPainter(Painter painter) {
        List<Picture> list = new ArrayList<>(dataPainters.getPainters().size());

        for (Painter i : dataPainters.getPainters()) {
            if (i.equals(painter)) {
                list.addAll(painter.getPictures());
                break;
            }
        }

        return list;
    }

    @Override
    public List<Painter> getAllPainters() {
        List<Painter> src = dataPainters.getPainters();
        List<Painter> list = new ArrayList<>(src.size());
        list.addAll(src);
        return list;
    }

    @Override
    public Painter getPainterBySystemName(String systemName) {
        for (Painter i : getAllPainters()) {
            if (i.getSystemName().equals(systemName)) {
                return i;
            }
        }
        return null;
    }

    @Override
    public Picture getPictureBySystemName(String systemName) {

        for (Picture i : getAll()) {
            if (i.getSystemName().equals(systemName)) {
                return i;
            }
        }

        return null;
    }
}
