package com.zolotukhin.picturegame.model;

import java.util.List;

/**
 * Created by Artem Zolotukhin on 7/14/17.
 */

public interface GalleryRepository {

    List<GalleryEntry> get();

    void add(GalleryEntry entry);

    void clear();
}
