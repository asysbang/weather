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
		date1 = new Text(-Config.CAMERA_WIDTH, 80, font, "", 40, vertexBufferObjectManager);
		scene.attachChild(date1);

		date2 = new Text(-Config.CAMERA_WIDTH, 120, font, "", 40, vertexBufferObjectManager);
		scene.attachChild(date2);

		date3 = new Text(-Config.CAMERA_WIDTH, 160, font, "", 40, vertexBufferObjectManager);
		scene.attachChild(date3);
		date4 = new Text(-Config.CAMERA_WIDTH, 200, font, "", 40, vertexBufferObjectManager);
		scene.attachChild(date4);
		date5 = new Text(-Config.CAMERA_WIDTH, 240, font, "", 40, vertexBufferObjectManager);
		scene.attachChild(date5);

		date6 = new Text(-Config.CAMERA_WIDTH, 280, font, "", 40, vertexBufferObjectManager);
		scene.attachChild(date6);

	}

	public void hide(BaseGameActivity activity) {
		activity.runOnUpdateThread(new Runnable() {
			@Override
			public void run() {
				date1.registerEntityModifier(new MoveOutModifier(0, 15));
				date2.registerEntityModifier(new MoveOutModifier(1, 15));
				date3.registerEntityModifier(new MoveOutModifier(2, 15));
				date4.registerEntityModifier(new MoveOutModifier(3, 15));
				date5.registerEntityModifier(new MoveOutModifier(4, 15));
				date6.registerEntityModifier(new MoveOutModifier(5, 15));
			}
		});
	}

	public void show(BaseGameActivity activity, final WeatherInfo weather) {

		activity.runOnUpdateThread(new Runnable() {
			@Override
			public void run() {
				date1.registerEntityModifier(new MoveInModifier(0, 10));
				date2.registerEntityModifier(new MoveInModifier(1, 10));
				date3.registerEntityModifier(new MoveInModifier(2, 10));
				date4.registerEntityModifier(new MoveInModifier(3, 10));
				date5.registerEntityModifier(new MoveInModifier(4, 10));
				date6.registerEntityModifier(new MoveInModifier(5, 10));
				update(weather);
			}
		});
	}

	private void update(WeatherInfo weather) {
		if (weather.getInfo() != null) {
			date1.setText(weather.getInfo()[0]);
			date2.setText(weather.getInfo()[1]);
			date3.setText(weather.getInfo()[2]);
			date4.setText(weather.getInfo()[3]);
			date5.setText(weather.getInfo()[4]);
			date6.setText(weather.getInfo()[5]);
		}
	}

}
