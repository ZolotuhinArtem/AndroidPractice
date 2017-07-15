package com.zolotukhin.picturegame.model;

import com.badlogic.gdx.Gdx;

/**
 * Created by Artem Zolotukhin on 7/14/17.
 */

public class UserInfoRepositoryImpl implements UserInfoRepository {

    public static final String path = "game_data";


    public UserInfoRepositoryImpl() {

        if (!Gdx.files.local(path).exists()) {
            Gdx.files.local(path).writeString("0", false);
        }

    }

    @Override
    public long getRecord() {

        String value = Gdx.files.local(path).readString();
        long result = 0;
        try {
            result = Long.valueOf(value);
        } catch (ClassCastException ex) {
            ex.printStackTrace();
        }

        return result;
    }

    @Override
    public void writeRecord(long record) {
        String value = Long.toString(record);
        Gdx.files.local(path).writeString(value, false);
    }
}
