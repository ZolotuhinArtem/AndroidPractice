package com.zolotukhin.picturegame;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.zolotukhin.picturegame.PictureGame;

public class AndroidLauncher extends AndroidApplication {

	public static final float SCREEN_WIDTH = 229;

	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();

		int width = getResources().getDisplayMetrics().widthPixels;
		int height = getResources().getDisplayMetrics().heightPixels;

		config.useGyroscope = false;c
		config.useCompass = false;
		config.useAccelerometer = false;

		initialize(new PictureGame(width, height), config);
	}
}
