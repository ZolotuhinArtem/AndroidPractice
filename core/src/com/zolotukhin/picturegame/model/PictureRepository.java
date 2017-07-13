package com.zolotukhin.picturegame.model;

import java.util.List;

/**
 * Created by Artem Zolotukhin on 7/11/17.
 */

public interface PictureRepository {

    List<Picture> getAll();

    List<Picture> getByPainter(Painter painter);

    List<Picture> getByName(String name);

    List<Painter> getAllPainters();

    Painter getBySystemName(String systemName);
}
