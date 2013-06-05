package com.asys.weather.ui;

import org.andengine.entity.scene.Scene;
import org.andengine.entity.text.Text;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.ui.activity.BaseGameActivity;

import com.asys.weather.model.WeatherInfo;
import com.asys.weather.util.Config;

public class SettingPage {

	private Text city, source, email, tip;

	public SettingPage() {

	}

	public void loadResources() {

	}

	public void loadScene(Scene scene, Font font, VertexBufferObjectManager vertexBufferObjectManager) {

		city = new Text(-Config.CAMERA_WIDTH, 120, font, "当前城市：北京", 40, vertexBufferObjectManager);
		scene.attachChild(city);

		source = new Text(-Config.CAMERA_WIDTH, 160, font, "代码：https://github.com/\r\n         asysbang/weather.git", 60,
				vertexBufferObjectManager);
		scene.attachChild(source);

		email = new Text(-Config.CAMERA_WIDTH, 220, font, "邮件：asysbang@163.com", 40, vertexBufferObjectManager);
		scene.attachChild(email);

		tip = new Text(-Config.CAMERA_WIDTH, 350, font, "aSys天气demo版本 \r\n\r\nhttp://www.asysbang.com", 40, vertexBufferObjectManager);
		scene.attachChild(tip);

	}

	public void hide(BaseGameActivity activity) {
		activity.runOnUpdateThread(new Runnable() {
			@Override
			public void run() {
				city.registerEntityModifier(new MoveOutModifier(0));
				source.registerEntityModifier(new MoveOutModifier(1));
				email.registerEntityModifier(new MoveOutModifier(2));
				tip.registerEntityModifier(new MoveOutModifier(3));
			}
		});
	}

	public void show(BaseGameActivity activity, final WeatherInfo weather) {

		activity.runOnUpdateThread(new Runnable() {
			@Override
			public void run() {
				city.registerEntityModifier(new MoveInModifier(0));
				source.registerEntityModifier(new MoveInModifier(1));
				email.registerEntityModifier(new MoveInModifier(2));
				tip.registerEntityModifier(new MoveInModifier(3));
			}
		});
	}

}
