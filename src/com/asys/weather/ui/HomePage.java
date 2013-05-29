package com.asys.weather.ui;

import org.andengine.entity.scene.Scene;
import org.andengine.entity.text.Text;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.ui.activity.BaseGameActivity;

import com.asys.weather.model.WeatherInfo;

public class HomePage {

	private Text curTemp, wind, dampness, todayTemp, curState, todayState, ptime, date, lunar;

	public HomePage() {

	}

	public void loadResources() {

	}

	public void loadScene(Scene scene, Font font, VertexBufferObjectManager vertexBufferObjectManager, WeatherInfo weather) {
		curTemp = new Text(50, 30, font, "当前温度：" + weather.getTemp() + " ℃", 40, vertexBufferObjectManager);
		scene.attachChild(curTemp);

		curState = new Text(50, 70, font, "当前天气：" + weather.getCurState(), 40, vertexBufferObjectManager);
		scene.attachChild(curState);

		wind = new Text(50, 110, font, "当前风向：" + weather.getWind(), 40, vertexBufferObjectManager);
		scene.attachChild(wind);
		dampness = new Text(50, 150, font, "当前湿度：" + weather.getDampness(), 40, vertexBufferObjectManager);
		scene.attachChild(dampness);
		todayTemp = new Text(50, 220, font, "今日温度：" + weather.getTodayTemp(), 40, vertexBufferObjectManager);
		scene.attachChild(todayTemp);

		todayState = new Text(50, 260, font, "今日天气：" + weather.getTodayState(), 40, vertexBufferObjectManager);
		scene.attachChild(todayState);

		ptime = new Text(50, 310, font, "更新时间：" + weather.getPtime(), 40, vertexBufferObjectManager);
		scene.attachChild(ptime);

		date = new Text(50, 390, font, "" + weather.getStrDate(), 40, vertexBufferObjectManager);
		scene.attachChild(date);

		lunar = new Text(50, 430, font, "" + weather.getLunarDate(), 40, vertexBufferObjectManager);
		scene.attachChild(lunar);

	}

	public void hide(BaseGameActivity activity) {
		activity.runOnUpdateThread(new Runnable() {
			@Override
			public void run() {
				curTemp.registerEntityModifier(new MoveOutModifier(0));
				curState.registerEntityModifier(new MoveOutModifier(1));
				wind.registerEntityModifier(new MoveOutModifier(2));
				dampness.registerEntityModifier(new MoveOutModifier(3));
				todayTemp.registerEntityModifier(new MoveOutModifier(4));
				todayState.registerEntityModifier(new MoveOutModifier(5));
				ptime.registerEntityModifier(new MoveOutModifier(6));
				date.registerEntityModifier(new MoveOutModifier(7));
				lunar.registerEntityModifier(new MoveOutModifier(8));
			}
		});
	}

	public void show(BaseGameActivity activity, final WeatherInfo weather) {

		activity.runOnUpdateThread(new Runnable() {
			@Override
			public void run() {
				curTemp.registerEntityModifier(new MoveInModifier(0));
				curState.registerEntityModifier(new MoveInModifier(1));
				wind.registerEntityModifier(new MoveInModifier(2));
				dampness.registerEntityModifier(new MoveInModifier(3));
				todayTemp.registerEntityModifier(new MoveInModifier(4));
				todayState.registerEntityModifier(new MoveInModifier(5));
				ptime.registerEntityModifier(new MoveInModifier(6));
				date.registerEntityModifier(new MoveInModifier(7));
				lunar.registerEntityModifier(new MoveInModifier(8));
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
		date.setText("" + weather.getStrDate());
		lunar.setText("" + weather.getLunarDate());
	}

}
