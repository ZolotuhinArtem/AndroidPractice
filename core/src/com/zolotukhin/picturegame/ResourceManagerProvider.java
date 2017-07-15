package com.zolotukhin.picturegame;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.zolotukhin.picturegame.resource.ResourceManager;

/**
 * Created by Artem Zolotukhin on 7/13/17.
 */

public interface ResourceManagerProvider {

    ResourceManager getResourceManager();
}
