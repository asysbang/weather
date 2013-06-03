package com.asys.weather.ui;

import org.andengine.entity.scene.Scene;
import org.andengine.entity.text.Text;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.ui.activity.BaseGameActivity;

import com.asys.weather.model.WeatherInfo;
import com.asys.weather.util.Config;

public class LifePage {
	// index 穿衣
	private Text dress ;
	
	// index_d穿衣提示   太长暂时不显示
	private Text dressTip;

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

		dress = new Text(-Config.CAMERA_WIDTH, 110, font, "穿衣指数：" + weather.getDress(), 40, vertexBufferObjectManager);
		scene.attachChild(dress);

		// dressTip = new Text(-Config.CAMERA_WIDTH, 70, font, "穿衣指南：" +
		// weather.getDressTip(), 40, vertexBufferObjectManager);
		// scene.attachChild(dressTip);

		uvRays = new Text(-Config.CAMERA_WIDTH, 150, font, "紫外线指数：" + weather.getUvRays(), 40, vertexBufferObjectManager);
		scene.attachChild(uvRays);

		car = new Text(-Config.CAMERA_WIDTH, 190, font, "洗车指数：" + weather.getCar(), 40, vertexBufferObjectManager);
		scene.attachChild(car);

		travel = new Text(-Config.CAMERA_WIDTH, 230, font, "出行指数：" + weather.getTravel(), 40, vertexBufferObjectManager);
		scene.attachChild(travel);

		exe = new Text(-Config.CAMERA_WIDTH, 270, font, "晨练指数：" + weather.getExe(), 40, vertexBufferObjectManager);
		scene.attachChild(exe);

		sun = new Text(-Config.CAMERA_WIDTH, 310, font, "晾衣指数：" + weather.getSun(), 40, vertexBufferObjectManager);
		scene.attachChild(sun);

	}

	public void hide(BaseGameActivity activity) {
		activity.runOnUpdateThread(new Runnable() {
			@Override
			public void run() {
				dress.registerEntityModifier(new MoveOutModifier(0));
				uvRays.registerEntityModifier(new MoveOutModifier(1));
				car.registerEntityModifier(new MoveOutModifier(2));
				travel.registerEntityModifier(new MoveOutModifier(3));
				exe.registerEntityModifier(new MoveOutModifier(4));
				sun.registerEntityModifier(new MoveOutModifier(5));
			}
		});
	}

	public void show(BaseGameActivity activity, final WeatherInfo weather) {
		activity.runOnUpdateThread(new Runnable() {
			@Override
			public void run() {
				dress.registerEntityModifier(new MoveInModifier(0));
				uvRays.registerEntityModifier(new MoveInModifier(1));
				car.registerEntityModifier(new MoveInModifier(2));
				travel.registerEntityModifier(new MoveInModifier(3));
				exe.registerEntityModifier(new MoveInModifier(4));
				sun.registerEntityModifier(new MoveInModifier(5));
				update(weather);
			}
		});
	}

	private void update(WeatherInfo weather) {
		dress.setText("穿衣指数：" + weather.getDress());
		uvRays.setText("紫外线指数：" + weather.getUvRays());
		car.setText("洗车指数：" + weather.getCar());
		travel.setText("出行指数：" + weather.getTravel());
		exe.setText("晨练指数：" + weather.getExe());
		sun.setText("晾衣指数：" + weather.getSun());
	}

}
