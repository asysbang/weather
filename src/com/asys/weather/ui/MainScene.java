package com.asys.weather.ui;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.ButtonSprite;
import org.andengine.entity.sprite.ButtonSprite.OnClickListener;
import org.andengine.entity.sprite.Sprite;
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

	private int curPage = 0;
	
	private HomePage mHomePage;

	public MainScene(BaseGameActivity activity) {
		mActivity = activity;
		mHomePage = new HomePage();
	}

	private Font mFont;

	private BitmapTextureAtlas mRefreshAtlas, tabBgAtlas, contentBgAtlas;

	private TextureRegion tabBgRegion, contentBgRegion;

	private Sprite tabBg, contentBg;

	private ButtonSprite tab, tab1, tab2, tab3, tab4;



	private BuildableBitmapTextureAtlas tabAtlsa;

	private ITextureRegion   tabHomeNormalRegion, tabHomePressedRegion,
			tabUpdateNormalRegion, tabUpdatePressedRegion, tabWeekNormalRegion, tabWeekPressedRegion, tabLifeNormalRegion,
			tabLifePressedRegion, tabAdNormalRegion, tabAdPressedRegion;

	public void loadResources() {
		mHomePage.loadResources();

		// !!!!! sample use BuildableBitmapTextureAtlas not BitmapTextureAtlas
		// !!!!!
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("img/");
		mRefreshAtlas = new BitmapTextureAtlas(mActivity.getTextureManager(), 128, 128, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		mRefreshAtlas.load();

		tabBgAtlas = new BitmapTextureAtlas(mActivity.getTextureManager(), 512, 512, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		tabBgRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(tabBgAtlas, mActivity, "tab_bg.png", 0, 0);
		tabBgAtlas.load();

		contentBgAtlas = new BitmapTextureAtlas(mActivity.getTextureManager(), 512, 512, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		contentBgRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(contentBgAtlas, mActivity, "content_bg.jpg", 0,
				0);
		contentBgAtlas.load();

		tabAtlsa = new BuildableBitmapTextureAtlas(mActivity.getTextureManager(), 256, 256);

		tabUpdateNormalRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(tabAtlsa, mActivity,
				"tab_update_nromal.png");
		tabUpdatePressedRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(tabAtlsa, mActivity,
				"tab_update_pressed.png");

		tabHomeNormalRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(tabAtlsa, mActivity,
				"tab_home_nromal.png");
		tabHomePressedRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(tabAtlsa, mActivity,
				"tab_home_pressed.png");

		tabWeekNormalRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(tabAtlsa, mActivity,
				"tab_week_nromal.png");
		tabWeekPressedRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(tabAtlsa, mActivity,
				"tab_week_pressed.png");

		tabLifeNormalRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(tabAtlsa, mActivity,
				"tab_life_nromal.png");
		tabLifePressedRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(tabAtlsa, mActivity,
				"tab_life_pressed.png");

		tabAdNormalRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(tabAtlsa, mActivity, "tab_ad_nromal.png");
		tabAdPressedRegion = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(tabAtlsa, mActivity, "tab_ad_pressed.png");
		try {
			tabAtlsa.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 0, 0));
			tabAtlsa.load();
		} catch (TextureAtlasBuilderException e) {
			e.printStackTrace();
		}

		BitmapTextureAtlas pBitmapTextureAtlas = new BitmapTextureAtlas(mActivity.getTextureManager(), 256, 256,
				TextureOptions.BILINEAR_PREMULTIPLYALPHA);

		FontFactory.setAssetBasePath("fonts/");

		mFont = FontFactory.createFromAsset(mActivity.getFontManager(), pBitmapTextureAtlas, mActivity.getAssets(),
				"weather.ttf", 22, true, Color.DKGRAY);
		mFont.load();

		loadSkInfoFile();
		loadDataInfoFile();

	}

	public void update() {
		loadSkInfoFile();
		loadDataInfoFile();
		mHomePage.update(mWeatherInfo);
		mHomePage.show(mActivity);
		
	}

	private void loadDataInfoFile() {
		File info = mActivity.getFileStreamPath(Config.FILE_DATA_INFO);
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(info));
			String line = null;
			while ((line = br.readLine()) != null) {
				addDataInfo(line);
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
			mWeatherInfo.setCurState(info.getString("img_title1"));
			mWeatherInfo.setTodayState(info.getString("weather1"));
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
			WeatherInfo res = new WeatherInfo(info.getString("temp"), info.getString("WD") + info.getString("WS"),
					info.getString("SD"), info.getString("time"));
			return res;
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}

	public void loadScene() {
		float tabLeft = 10;
		float tabWidth = tabHomeNormalRegion.getWidth();
		tab = new ButtonSprite(tabLeft, 470, tabHomeNormalRegion, tabHomePressedRegion, mActivity.getVertexBufferObjectManager());
		tab1 = new ButtonSprite(tabLeft + tabWidth, 470, tabWeekNormalRegion, tabWeekPressedRegion,
				mActivity.getVertexBufferObjectManager());
		tab2 = new ButtonSprite(tabLeft + tabWidth * 2, 470, tabLifeNormalRegion, tabLifePressedRegion,
				mActivity.getVertexBufferObjectManager());
		tab3 = new ButtonSprite(tabLeft + tabWidth * 3, 470, tabAdNormalRegion, tabAdPressedRegion,
				mActivity.getVertexBufferObjectManager());
		tab4 = new ButtonSprite(tabLeft + tabWidth * 4, 470, tabUpdateNormalRegion, tabUpdatePressedRegion,
				mActivity.getVertexBufferObjectManager());

		tabBg = new Sprite(0, 460, tabBgRegion, mActivity.getVertexBufferObjectManager());
		contentBg = new Sprite(0, 0, contentBgRegion, mActivity.getVertexBufferObjectManager());

		attachChild(contentBg);
		attachChild(tabBg);
		attachChild(tab);
		attachChild(tab1);
		attachChild(tab2);
		attachChild(tab3);
		attachChild(tab4);

		mHomePage.loadScene(this,mFont,mActivity.getVertexBufferObjectManager(),mWeatherInfo);

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

	private void switchToHomePage() {
		if (curPage == 0){
			return;
		}

	}
	private void switchToWeekPage() {
		if (curPage == 0){
			return;
		}
		
	}
	private void switchToLifePage() {
		
	}
	private void switchToAdPage() {
		if (curPage == 0){
			return;
		}
		
	}
	private void switchToUpdatePage() {
		
	}

	@Override
	public void onClick(ButtonSprite btnSprite, float arg1, float arg2) {
		if (btnSprite == tab4) {
			mActivity.sendBroadcast(new Intent(Config.CMD_QUERY));
			// should animation

		} else if (btnSprite == tab3) {
			update();
		} else if (btnSprite == tab) {
			switchToHomePage();
		} else {
			// should animation
			// temp.setVisible(false);
			mHomePage.hide(mActivity);
		}

	}

}
