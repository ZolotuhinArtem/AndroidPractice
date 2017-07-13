package com.zolotukhin.picturegame.model;

import java.util.List;

/**
 * Created by Artem Zolotukhin on 7/11/17.
 */

public interface PictureRepository {

    List<Picture> getAll();

    List<Picture> getByPainter(Painter painter);

    List<Painter> getAllPainters();

    Painter getPainterBySystemName(String systemName);

    Picture getPictureBySystemName(String systemName);

    Painter getPainterByPicture(Picture picture);
}
