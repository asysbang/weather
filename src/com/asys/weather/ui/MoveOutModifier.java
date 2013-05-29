package com.asys.weather.ui;

import org.andengine.entity.modifier.MoveXModifier;

import com.asys.weather.util.Config;

public class MoveOutModifier extends MoveXModifier {

	public MoveOutModifier(int detaNum) {
		super(Config.HIDE_SHOW_PAGE_ANIMATION_TIME + Config.HIDE_SHOW_PAGE_ANIMATION_DETA * detaNum, 50, -Config.CAMERA_WIDTH);
	}

	public MoveOutModifier(int detaNum, float left) {
		super(Config.HIDE_SHOW_PAGE_ANIMATION_TIME + Config.HIDE_SHOW_PAGE_ANIMATION_DETA * detaNum, left, -Config.CAMERA_WIDTH);
	}

}
