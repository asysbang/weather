package com.asys.weather.ui;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.andengine.entity.modifier.MoveModifier;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.ButtonSprite;
import org.andengine.entity.sprite.ButtonSprite.OnClickListener;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.atlas.bitmap.BuildableBitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.source.IBitmapTextureAtlasSource;
import org.andengine.opengl.texture.atlas.buildable.builder.BlackPawnTextureAtlasBuilder;
import org.andengine.opengl.texture.atlas.buildable.builder.ITextureAtlasBuilder.TextureAtlasBuilderException;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.ui.activity.BaseGameActivity;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.graphics.Color;

import com.asys.weather.model.WeatherInfo;
import com.asys.weather.util.Config;

public class MainScene extends Scene implements OnClickListener {

	private BaseGameActivity mActivity;

	public MainScene(BaseGameActivity activity) {
		mActivity = activity;
	}

	private Font mFont;

	private BitmapTextureAtlas mRefreshAtlas, tabBgAtlas, contentBgAtlas;

	private TextureRegion tabBgRegion, contentBgRegion;

	private Sprite tabBg, contentBg;

	private ButtonSprite tab, tab1, tab2, tab3, tab4;

	private Text temp, wind, dampness, todayTemp, todayState, ptime;

	private BuildableBitmapTextureAtlas dockBarAtlsa;

	private ITextureRegion dockBarNormalRegion, dockBarPressedRegion;

	public void loadResources() {

		// !!!!! sample use BuildableBitmapTextureAtlas not BitmapTextureAtlas
		// !!!!!
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("img/");
		mRefreshAtlas = new BitmapTextureAtlas(mActivity.getTextureManager(), 128, 128, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		mRefreshAtlas.load();

		tabBgAtlas = new BitmapTextureAtlas(mActivity.getTextureManager(), 512, 512, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		tabBgRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(tabBgAtlas, mActivity, "tab_bg.png", 0, 0);
		tabBgAtlas.load();

		contentBgAtlas = new BitmapTextureAtlas(mActivity.getTextureManager(), 512, 512, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		contentBgRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(contentBgAtlas, mActivity, "content_bg.jpg", 0, 0);
		contentBgAtlas.load();

		dockBarAtlsa = new BuildableBitmapTextureAtlas(mActivity.getTextureManager(), 128, 128);
		dockBarNormalRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(dockBarAtlsa, mActivity, "tab_nromal.png");
		dockBarPressedRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(dockBarAtlsa, mActivity, "tab_pressed.png");
		try {
			dockBarAtlsa.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 0, 0));
			dockBarAtlsa.load();
		} catch (TextureAtlasBuilderException e) {
			e.printStackTrace();
		}

		BitmapTextureAtlas pBitmapTextureAtlas = new BitmapTextureAtlas(mActivity.getTextureManager(), 256, 256,
				TextureOptions.BILINEAR_PREMULTIPLYALPHA);

		FontFactory.setAssetBasePath("fonts/");

		mFont = FontFactory.createFromAsset(mActivity.getFontManager(), pBitmapTextureAtlas, mActivity.getAssets(), "weather.ttf", 24,
				true, Color.DKGRAY);
		mFont.load();

		loadSkInfoFile();
		loadDataInfoFile();

	}

	public void update() {
		loadSkInfoFile();
		loadDataInfoFile();
		temp.setText("当前温度：" + mWeatherInfo.getTemp() + " ℃");
		wind.setText("风向：" + mWeatherInfo.getWind());
		dampness.setText("湿度：" + mWeatherInfo.getDampness());
		todayTemp.setText("今日温度：" + mWeatherInfo.getTodayTemp());
		todayState.setText("今日天气：" + mWeatherInfo.getTodayState());
		ptime.setText("更新时间：" + mWeatherInfo.getPtime());
		mActivity.runOnUpdateThread(new Runnable() {
			@Override
			public void run() {
				temp.registerEntityModifier(new MoveModifier(0.8f, -temp.getWidth(), 40, temp.getY(), temp.getY()));
				wind.registerEntityModifier(new MoveModifier(1, -wind.getWidth(), 40, wind.getY(), wind.getY()));
				dampness.registerEntityModifier(new MoveModifier(1.2f, -dampness.getWidth(), 40, dampness.getY(), dampness.getY()));
				todayTemp.registerEntityModifier(new MoveModifier(1.4f, -todayTemp.getWidth(), 40, todayTemp.getY(), todayTemp.getY()));
				todayState.registerEntityModifier(new MoveModifier(1.4f, -todayState.getWidth(), 40, todayState.getY(), todayState.getY()));
				ptime.registerEntityModifier(new MoveModifier(1.6f, -ptime.getWidth(), 40, ptime.getY(), ptime.getY()));
			}
		});
	}

	private void loadDataInfoFile() {
		File info = mActivity.getFileStreamPath(Config.FILE_DATA_INFO);
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(info));
			String line = null;
			while ((line = br.readLine()) != null) {
				addDataInfo(line);
				System.out.println("=====line==" + line);
			}
		} catch (FileNotFoundException e) {
			// ignore first start file not exist
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void addDataInfo(String dataInfo) {
		try {
			JSONObject info = new JSONObject(dataInfo).getJSONObject("weatherinfo");
			mWeatherInfo.setTodayTemp(info.getString("temp1"));
			mWeatherInfo.setTodayState(info.getString("img_title_single"));
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	private WeatherInfo mWeatherInfo = new WeatherInfo("", "", "", "");

	private void loadSkInfoFile() {
		File info = mActivity.getFileStreamPath(Config.FILE_SK_INFO);
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(info));
			String line = null;
			while ((line = br.readLine()) != null) {
				mWeatherInfo = parserSkInfoFile(line);
				System.out.println("" + mWeatherInfo.getTemp());
				System.out.println("" + mWeatherInfo.getWind());
				System.out.println("" + mWeatherInfo.getDampness());
				System.out.println("=====111111111111111line==" + line);
			}
		} catch (FileNotFoundException e) {
			// ignore first start file not exist
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private WeatherInfo parserSkInfoFile(String jsonStr) {
		try {
			JSONObject info = new JSONObject(jsonStr).getJSONObject("weatherinfo");
			WeatherInfo res = new WeatherInfo(info.getString("temp"), info.getString("WD") + info.getString("WS"), info.getString("SD"),
					info.getString("time"));
			return res;
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}

	public void loadScene() {
		tab = new ButtonSprite(10, 470, dockBarNormalRegion, dockBarPressedRegion, mActivity.getVertexBufferObjectManager());
		tab1 = new ButtonSprite(70, 470, dockBarNormalRegion, dockBarPressedRegion, mActivity.getVertexBufferObjectManager());
		tab2 = new ButtonSprite(130, 470, dockBarNormalRegion, dockBarPressedRegion, mActivity.getVertexBufferObjectManager());
		tab3 = new ButtonSprite(190, 470, dockBarNormalRegion, dockBarPressedRegion, mActivity.getVertexBufferObjectManager());
		tab4 = new ButtonSprite(250, 470, dockBarNormalRegion, dockBarPressedRegion, mActivity.getVertexBufferObjectManager());

		tabBg = new Sprite(0, 460, tabBgRegion, mActivity.getVertexBufferObjectManager());
		contentBg = new Sprite(0, 0, contentBgRegion, mActivity.getVertexBufferObjectManager());

		attachChild(contentBg);
		attachChild(tabBg);
		attachChild(tab);
		attachChild(tab1);
		attachChild(tab2);
		attachChild(tab3);
		attachChild(tab4);

		temp = new Text(40, 30, mFont, "当前温度：" + mWeatherInfo.getTemp() + " ℃", 40, mActivity.getVertexBufferObjectManager());
		attachChild(temp);
		wind = new Text(40, 70, mFont, "风向：" + mWeatherInfo.getWind(), 40, mActivity.getVertexBufferObjectManager());
		attachChild(wind);
		dampness = new Text(40, 110, mFont, "湿度：" + mWeatherInfo.getDampness(), 40, mActivity.getVertexBufferObjectManager());
		attachChild(dampness);
		todayTemp = new Text(40, 150, mFont, "今日温度：" + mWeatherInfo.getTodayTemp(), 40, mActivity.getVertexBufferObjectManager());
		attachChild(todayTemp);
		todayState = new Text(40, 190, mFont, "今日天气：" + mWeatherInfo.getTodayState(), 40, mActivity.getVertexBufferObjectManager());
		attachChild(todayState);
		ptime = new Text(40, 250, mFont, "更新时间：" + mWeatherInfo.getPtime(), 40, mActivity.getVertexBufferObjectManager());
		attachChild(ptime);

		// register touch areas
		tab.setOnClickListener(this);
		registerTouchArea(tab);

		tab1.setOnClickListener(this);
		registerTouchArea(tab1);

		tab2.setOnClickListener(this);
		registerTouchArea(tab2);
		tab3.setOnClickListener(this);
		registerTouchArea(tab3);
		tab4.setOnClickListener(this);
		registerTouchArea(tab4);
		setTouchAreaBindingOnActionDownEnabled(true);

	}

	@Override
	public void onClick(ButtonSprite btnSprite, float arg1, float arg2) {
		if (btnSprite == tab4) {
			mActivity.sendBroadcast(new Intent(Config.CMD_QUERY));
			// should animation

		} else {
			// should animation
			// temp.setVisible(false);
			mActivity.runOnUpdateThread(new Runnable() {
				@Override
				public void run() {
					temp.registerEntityModifier(new MoveModifier(0.8f, temp.getX(), -temp.getWidth(), temp.getY(), temp.getY()));
					wind.registerEntityModifier(new MoveModifier(1, wind.getX(), -wind.getWidth(), wind.getY(), wind.getY()));
					dampness.registerEntityModifier(new MoveModifier(1.2f, dampness.getX(), -dampness.getWidth(), dampness.getY(), dampness
							.getY()));
					todayTemp.registerEntityModifier(new MoveModifier(1.4f, todayTemp.getX(), -todayTemp.getWidth(), todayTemp.getY(),
							todayTemp.getY()));
					todayState.registerEntityModifier(new MoveModifier(1.4f, todayState.getX(), -todayState.getWidth(), todayState.getY(),
							todayState.getY()));
					ptime.registerEntityModifier(new MoveModifier(1.6f, ptime.getX(), -ptime.getWidth(), ptime.getY(), ptime.getY()));
				}
			});
		}

	}

}
