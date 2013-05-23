package com.asys.weather.ui;

import org.andengine.entity.modifier.MoveModifier;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.text.Text;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.ui.activity.BaseGameActivity;

import com.asys.weather.model.WeatherInfo;
import com.asys.weather.util.Config;

public class HomePage {

	private Text curTemp, wind, dampness, todayTemp, curState, todayState, ptime;

	public HomePage() {

	}

	public void loadResources() {

	}

	public void loadScene(Scene scene, Font font, VertexBufferObjectManager vertexBufferObjectManager, WeatherInfo weather) {
		curTemp = new Text(40, 30, font, "当前温度：" + weather.getTemp() + " ℃", 40, vertexBufferObjectManager);
		scene.attachChild(curTemp);

		curState = new Text(40, 70, font, "当前天气：" + weather.getCurState(), 40, vertexBufferObjectManager);
		scene.attachChild(curState);

		wind = new Text(40, 110, font, "当前风向：" + weather.getWind(), 40, vertexBufferObjectManager);
		scene.attachChild(wind);
		dampness = new Text(40, 150, font, "当前湿度：" + weather.getDampness(), 40, vertexBufferObjectManager);
		scene.attachChild(dampness);
		todayTemp = new Text(40, 220, font, "今日温度：" + weather.getTodayTemp(), 40, vertexBufferObjectManager);
		scene.attachChild(todayTemp);

		todayState = new Text(40, 260, font, "今日天气：" + weather.getTodayState(), 40, vertexBufferObjectManager);
		scene.attachChild(todayState);

		ptime = new Text(40, 310, font, "更新时间：" + weather.getPtime(), 40, vertexBufferObjectManager);
		scene.attachChild(ptime);

	}

	public void hide(BaseGameActivity activity) {
		activity.runOnUpdateThread(new Runnable() {
			@Override
			public void run() {
				float deta = 0.1f;
				curTemp.registerEntityModifier(new MoveModifier(Config.HIDE_SHOW_PAGE_ANIMATION_TIME, curTemp.getX(),
						-Config.CAMERA_WIDTH, curTemp.getY(), curTemp.getY()));
				curState.registerEntityModifier(new MoveModifier(Config.HIDE_SHOW_PAGE_ANIMATION_TIME + deta, curState.getX(),
						-Config.CAMERA_WIDTH, curState.getY(), curState.getY()));
				wind.registerEntityModifier(new MoveModifier(Config.HIDE_SHOW_PAGE_ANIMATION_TIME + deta * 2, wind.getX(),
						-Config.CAMERA_WIDTH, wind.getY(), wind.getY()));
				dampness.registerEntityModifier(new MoveModifier(Config.HIDE_SHOW_PAGE_ANIMATION_TIME + deta * 3,
						dampness.getX(), -Config.CAMERA_WIDTH, dampness.getY(), dampness.getY()));
				todayTemp.registerEntityModifier(new MoveModifier(Config.HIDE_SHOW_PAGE_ANIMATION_TIME + deta * 4, todayTemp
						.getX(), -Config.CAMERA_WIDTH, todayTemp.getY(), todayTemp.getY()));
				todayState.registerEntityModifier(new MoveModifier(Config.HIDE_SHOW_PAGE_ANIMATION_TIME + deta * 5, todayState
						.getX(), -Config.CAMERA_WIDTH, todayState.getY(), todayState.getY()));
				ptime.registerEntityModifier(new MoveModifier(Config.HIDE_SHOW_PAGE_ANIMATION_TIME + deta * 6, ptime.getX(),
						-Config.CAMERA_WIDTH, ptime.getY(), ptime.getY()));
			}
		});
	}

	public void show(BaseGameActivity activity, final WeatherInfo weather) {

		activity.runOnUpdateThread(new Runnable() {
			@Override
			public void run() {
				float deta = 0.1f;
				curTemp.registerEntityModifier(new MoveModifier(Config.HIDE_SHOW_PAGE_ANIMATION_TIME, -Config.CAMERA_WIDTH, 40,
						curTemp.getY(), curTemp.getY()));
				curState.registerEntityModifier(new MoveModifier(Config.HIDE_SHOW_PAGE_ANIMATION_TIME + deta,
						-Config.CAMERA_WIDTH, 40, curState.getY(), curState.getY()));
				wind.registerEntityModifier(new MoveModifier(Config.HIDE_SHOW_PAGE_ANIMATION_TIME + deta * 2,
						-Config.CAMERA_WIDTH, 40, wind.getY(), wind.getY()));
				dampness.registerEntityModifier(new MoveModifier(Config.HIDE_SHOW_PAGE_ANIMATION_TIME + deta * 3,
						-Config.CAMERA_WIDTH, 40, dampness.getY(), dampness.getY()));
				todayTemp.registerEntityModifier(new MoveModifier(Config.HIDE_SHOW_PAGE_ANIMATION_TIME + deta * 4,
						-Config.CAMERA_WIDTH, 40, todayTemp.getY(), todayTemp.getY()));
				todayState.registerEntityModifier(new MoveModifier(Config.HIDE_SHOW_PAGE_ANIMATION_TIME + deta * 5,
						-Config.CAMERA_WIDTH, 40, todayState.getY(), todayState.getY()));
				ptime.registerEntityModifier(new MoveModifier(Config.HIDE_SHOW_PAGE_ANIMATION_TIME + deta * 6,
						-Config.CAMERA_WIDTH, 40, ptime.getY(), ptime.getY()));
				update(weather);
			}
		});
	}

	private void update(WeatherInfo weather) {
		curTemp.setText("当前温度：" + weather.getTemp() + " ℃");
		curState.setText("当前天气：" + weather.getCurState());
		wind.setText("当前风向：" + weather.getWind());
		dampness.setText("当前湿度：" + weather.getDampness());
		todayTemp.setText("今日温度：" + weather.getTodayTemp());
		todayState.setText("今日天气：" + weather.getTodayState());
		ptime.setText("更新时间：" + weather.getPtime());
	}

}
