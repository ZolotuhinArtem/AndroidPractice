package com.zolotukhin.picturegame.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Artem Zolotukhin on 7/14/17.
 */

public class SimpleGalleryRepository implements GalleryRepository {

    public static final String PATH = "galery";

    private PictureRepository pictureRepository;

    public SimpleGalleryRepository(PictureRepository repository) {
        this.pictureRepository = repository;

        if (!Gdx.files.local(PATH).exists()) {
            for (Painter i : pictureRepository.getAllPainters()) {
                for (Picture j: i.getPictures()) {
                    Gdx.files.local(PATH).writeString(i.getSystemName() + ":" + j.getSystemName() + "\n", true);
                }
            }
        }

    }

    @Override
    public List<GalleryEntry> get() {

        List<GalleryEntry> entries = new ArrayList<>();
        if (!Gdx.files.local(PATH).exists()) {
            return entries;
        }

        FileHandle fileHandle = Gdx.files.local(PATH);
        LineNumberReader reader = new LineNumberReader(new BufferedReader(fileHandle.reader()));
        String string;
        String values[];
        Painter painter;
        Picture picture;

        try {
            while ( (string = reader.readLine()) != null ) {
                if (string.length() == 0) {
                    continue;
                }
                values = string.split(":");
                painter = pictureRepository.getPainterBySystemName(values[0]);
                picture = pictureRepository.getPictureBySystemName(values[1]);
                entries.add(new GalleryEntry(painter, picture));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return entries;
    }

    @Override
    public void add(GalleryEntry entry) {
        //TODO
    }
}
