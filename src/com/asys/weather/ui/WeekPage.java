package com.asys.weather.ui;

import org.andengine.entity.scene.Scene;
import org.andengine.entity.text.Text;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.ui.activity.BaseGameActivity;

import com.asys.weather.model.WeatherInfo;
import com.asys.weather.util.Config;

public class WeekPage {

	private Text date1, date2, date3, date4, date5, date6;

	private Text temp1, temp2, temp3, temp4, temp5, temp6;

	private Text weather1, weather2, weather3, weather4, weather5, weather6;

	private Text wind1, wind2, wind3, wind4, wind5, wind6;

	public WeekPage() {

	}

	public void loadResources() {

	}

	public void loadScene(Scene scene, Font font, VertexBufferObjectManager vertexBufferObjectManager, WeatherInfo weather) {
		date1 = new Text(-Config.CAMERA_WIDTH, 30, font, "今天" + weather.getTemp(), 40, vertexBufferObjectManager);
		scene.attachChild(date1);

		date2 = new Text(-Config.CAMERA_WIDTH, 70, font, "明天" + weather.getCurState(), 40, vertexBufferObjectManager);
		scene.attachChild(date2);

		date3 = new Text(-Config.CAMERA_WIDTH, 110, font, "22日" + weather.getWind(), 40, vertexBufferObjectManager);
		scene.attachChild(date3);
		date4 = new Text(-Config.CAMERA_WIDTH, 150, font, "23日" + weather.getDampness(), 40, vertexBufferObjectManager);
		scene.attachChild(date4);
		date5 = new Text(-Config.CAMERA_WIDTH, 220, font, "24日" + weather.getTodayTemp(), 40, vertexBufferObjectManager);
		scene.attachChild(date5);

		date6 = new Text(-Config.CAMERA_WIDTH, 260, font, "25日" + weather.getTodayState(), 40, vertexBufferObjectManager);
		scene.attachChild(date6);

	}

	public void hide(BaseGameActivity activity) {
		activity.runOnUpdateThread(new Runnable() {
			@Override
			public void run() {
				date1.registerEntityModifier(new MoveOutModifier(0));
				date2.registerEntityModifier(new MoveOutModifier(1));
				date3.registerEntityModifier(new MoveOutModifier(2));
				date4.registerEntityModifier(new MoveOutModifier(3));
				date5.registerEntityModifier(new MoveOutModifier(4));
				date6.registerEntityModifier(new MoveOutModifier(5));
			}
		});
	}

	public void show(BaseGameActivity activity, final WeatherInfo weather) {

		activity.runOnUpdateThread(new Runnable() {
			@Override
			public void run() {
				date1.registerEntityModifier(new MoveInModifier(0));
				date2.registerEntityModifier(new MoveInModifier(1));
				date3.registerEntityModifier(new MoveInModifier(2));
				date4.registerEntityModifier(new MoveInModifier(3));
				date5.registerEntityModifier(new MoveInModifier(4));
				date6.registerEntityModifier(new MoveInModifier(5));
				update(weather);
			}
		});
	}

	private void update(WeatherInfo weather) {
		date1.setText("今天：" + weather.getTemp());
		date2.setText("明天：" + weather.getCurState());
		date3.setText("22日：" + weather.getWind());
		date4.setText("23日：" + weather.getDampness());
		date5.setText("24日：" + weather.getTodayTemp());
		date6.setText("25日：" + weather.getTodayState());
	}

}
