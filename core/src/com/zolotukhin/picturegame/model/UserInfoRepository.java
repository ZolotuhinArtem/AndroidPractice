package com.zolotukhin.picturegame.model;

/**
 * Created by Artem Zolotukhin on 7/14/17.
 */

public interface UserInfoRepository {

    long getRecord();

    void writeRecord(long record);

}
