package com.asys.weather.ui;

import org.andengine.entity.scene.Scene;
import org.andengine.entity.text.Text;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.ui.activity.BaseGameActivity;

import com.asys.weather.model.WeatherInfo;
import com.asys.weather.util.Config;

public class AdPage {

	private Text tip;

	public AdPage() {

	}

	public void loadResources() {

	}

	public void loadScene(Scene scene, Font font, VertexBufferObjectManager vertexBufferObjectManager) {

		tip = new Text(-Config.CAMERA_WIDTH, 230, font, "服务器开发中，敬请期待！", 40, vertexBufferObjectManager);
		scene.attachChild(tip);

	}

	public void hide(BaseGameActivity activity) {
		activity.runOnUpdateThread(new Runnable() {
			@Override
			public void run() {
				tip.registerEntityModifier(new MoveOutModifier(0));
			}
		});
	}

	public void show(BaseGameActivity activity, final WeatherInfo weather) {

		activity.runOnUpdateThread(new Runnable() {
			@Override
			public void run() {
				tip.registerEntityModifier(new MoveInModifier(3));
			}
		});
	}

}
