package com.asys.weather.ui;

import org.andengine.entity.modifier.MoveXModifier;

import com.asys.weather.util.Config;

public class MoveInModifier extends MoveXModifier {

	public MoveInModifier(int detaNum) {
		super(Config.HIDE_SHOW_PAGE_ANIMATION_TIME + Config.HIDE_SHOW_PAGE_ANIMATION_DETA * detaNum, -Config.CAMERA_WIDTH, 50);
	}

}
