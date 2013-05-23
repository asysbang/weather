package com.asys.weather.ui;

import org.andengine.entity.modifier.MoveModifier;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.text.Text;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.ui.activity.BaseGameActivity;

import com.asys.weather.model.WeatherInfo;
import com.asys.weather.util.Config;

public class LifePage {
	// index 穿衣
	private Text dress, dressTip;

	// index_uv 紫外线
	private Text uvRays;

	// index_xc 洗车
	private Text car;

	// index_tr 旅游
	private Text travel;

	// index_cl 晨练
	private Text exe;

	// index_ls 晾晒
	private Text sun;

	public LifePage() {

	}

	public void loadScene(Scene scene, Font font, VertexBufferObjectManager vertexBufferObjectManager, WeatherInfo weather) {

		dress = new Text(-Config.CAMERA_WIDTH, 70, font, "穿衣指数：" + weather.getDress(), 40, vertexBufferObjectManager);
		scene.attachChild(dress);

		dressTip = new Text(-Config.CAMERA_WIDTH, 70, font, "穿衣指南：" + weather.getDressTip(), 40, vertexBufferObjectManager);
		//too long to show
		//scene.attachChild(dressTip);

		uvRays = new Text(-Config.CAMERA_WIDTH, 110, font, "紫外线指数：" + weather.getUvRays(), 40, vertexBufferObjectManager);
		scene.attachChild(uvRays);

		car = new Text(-Config.CAMERA_WIDTH, 150, font, "洗车指数：" + weather.getCar(), 40, vertexBufferObjectManager);
		scene.attachChild(car);

		travel = new Text(-Config.CAMERA_WIDTH, 190, font, "出行指数：" + weather.getTravel(), 40, vertexBufferObjectManager);
		scene.attachChild(travel);

		exe = new Text(-Config.CAMERA_WIDTH, 230, font, "晨练指数：" + weather.getExe(), 40, vertexBufferObjectManager);
		scene.attachChild(exe);

		sun = new Text(-Config.CAMERA_WIDTH, 270, font, "晾衣指数：" + weather.getSun(), 40, vertexBufferObjectManager);
		scene.attachChild(sun);

	}
	


	public void hide(BaseGameActivity activity) {
		activity.runOnUpdateThread(new Runnable() {
			@Override
			public void run() {
				float deta = 0.1f;
				dress.registerEntityModifier(new MoveModifier(Config.HIDE_SHOW_PAGE_ANIMATION_TIME, dress.getX(), -Config.CAMERA_WIDTH, dress.getY(), dress
						.getY()));
				dressTip.registerEntityModifier(new MoveModifier(Config.HIDE_SHOW_PAGE_ANIMATION_TIME + deta, dressTip.getX(), -Config.CAMERA_WIDTH, dressTip
						.getY(), dressTip.getY()));
				uvRays.registerEntityModifier(new MoveModifier(Config.HIDE_SHOW_PAGE_ANIMATION_TIME + deta * 2, uvRays.getX(), -Config.CAMERA_WIDTH, uvRays
						.getY(), uvRays.getY()));
				car.registerEntityModifier(new MoveModifier(Config.HIDE_SHOW_PAGE_ANIMATION_TIME + deta * 3, car.getX(), -Config.CAMERA_WIDTH, car.getY(), car
						.getY()));
				travel.registerEntityModifier(new MoveModifier(Config.HIDE_SHOW_PAGE_ANIMATION_TIME + deta * 4, travel.getX(), -Config.CAMERA_WIDTH, travel
						.getY(), travel.getY()));
				exe.registerEntityModifier(new MoveModifier(Config.HIDE_SHOW_PAGE_ANIMATION_TIME + deta * 5, exe.getX(), -Config.CAMERA_WIDTH, exe.getY(), exe
						.getY()));
				sun.registerEntityModifier(new MoveModifier(Config.HIDE_SHOW_PAGE_ANIMATION_TIME + deta * 6, sun.getX(), -Config.CAMERA_WIDTH, sun.getY(), sun
						.getY()));
			}
		});
	}

	public void show(BaseGameActivity activity,final WeatherInfo weather) {
		activity.runOnUpdateThread(new Runnable() {
			@Override
			public void run() {
				float deta = 0.1f;
				dress.registerEntityModifier(new MoveModifier(Config.HIDE_SHOW_PAGE_ANIMATION_TIME, -Config.CAMERA_WIDTH, 40, dress.getY(), dress.getY()));
				dressTip.registerEntityModifier(new MoveModifier(Config.HIDE_SHOW_PAGE_ANIMATION_TIME + deta, -Config.CAMERA_WIDTH, 40, dressTip.getY(), dressTip
						.getY()));
				uvRays.registerEntityModifier(new MoveModifier(Config.HIDE_SHOW_PAGE_ANIMATION_TIME + deta * 2, -Config.CAMERA_WIDTH, 40, uvRays.getY(), uvRays
						.getY()));
				car.registerEntityModifier(new MoveModifier(Config.HIDE_SHOW_PAGE_ANIMATION_TIME + deta * 3, -Config.CAMERA_WIDTH, 40, car.getY(), car.getY()));
				travel.registerEntityModifier(new MoveModifier(Config.HIDE_SHOW_PAGE_ANIMATION_TIME + deta * 4, -Config.CAMERA_WIDTH, 40, travel.getY(), travel
						.getY()));
				exe.registerEntityModifier(new MoveModifier(Config.HIDE_SHOW_PAGE_ANIMATION_TIME + deta * 5, -Config.CAMERA_WIDTH, 40, exe.getY(), exe.getY()));
				sun.registerEntityModifier(new MoveModifier(Config.HIDE_SHOW_PAGE_ANIMATION_TIME + deta * 6, -Config.CAMERA_WIDTH, 40, sun.getY(), sun.getY()));
				update(weather);
			}
		});
	}
	
	private void update(WeatherInfo weather) {
		dress.setText("穿衣指数：" + weather.getDress());
		dressTip.setText("穿衣指南：" + weather.getDressTip());
		uvRays.setText("紫外线指数：" + weather.getUvRays());
		car.setText("洗车指数：" + weather.getCar());
		travel.setText("出行指数：" + weather.getTravel());
		exe.setText("晨练指数：" + weather.getExe());
		sun.setText("晾衣指数：" + weather.getSun());
	}

}
