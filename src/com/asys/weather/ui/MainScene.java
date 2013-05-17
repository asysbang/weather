package com.asys.weather.ui;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.andengine.entity.scene.IOnAreaTouchListener;
import org.andengine.entity.scene.ITouchArea;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.ButtonSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.input.touch.TouchEvent;
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

public class MainScene extends Scene implements IOnAreaTouchListener {

	private BaseGameActivity mActivity;

	public MainScene(BaseGameActivity activity) {
		mActivity = activity;
	}

	private Font mFont;

	private BitmapTextureAtlas mRefreshAtlas,  tabBgAtlas, contentBgAtlas;

	private TextureRegion mRefreshRegion, tabBgRegion, contentBgRegion;

	private Sprite mRefresh,  tabBg, contentBg;

	private ButtonSprite tab, tab1, tab2, tab3, tab4 ;

	private Text temp, wind, dampness, ptime;
	
	private BuildableBitmapTextureAtlas dockBarAtlsa;
	
	private ITextureRegion dockBarNormalRegion,dockBarPressedRegion;

	public void loadResources() {
		
		//!!!!!     sample use BuildableBitmapTextureAtlas  not BitmapTextureAtlas    !!!!!
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("img/");
		mRefreshAtlas = new BitmapTextureAtlas(mActivity.getTextureManager(), 128, 128, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		mRefreshRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(mRefreshAtlas, mActivity, "refresh.png", 0, 0);
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
		temp.setText("温度：" + mWeatherInfo.getTemp() + " ℃");
		wind.setText("风向：" + mWeatherInfo.getWind());
		dampness.setText("湿度：" + mWeatherInfo.getDampness());
		ptime.setText("更新时间：" + mWeatherInfo.getPtime());
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
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void addDataInfo(String dataInfo) {

	}

	private WeatherInfo mWeatherInfo = new WeatherInfo("xxx", "xxx", "xxx", "xxx");

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
			e.printStackTrace();
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
		tab = new ButtonSprite(10, 470, dockBarNormalRegion,dockBarPressedRegion, mActivity.getVertexBufferObjectManager()){

			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
				System.out.println("====aaaaaaaaaaaa============="+tab);
				return true;
			}
			
		};
		tab1 = new ButtonSprite(70, 470, dockBarNormalRegion,dockBarPressedRegion, mActivity.getVertexBufferObjectManager());
		tab2 = new ButtonSprite(130, 470, dockBarNormalRegion,dockBarPressedRegion, mActivity.getVertexBufferObjectManager());
		tab3 = new ButtonSprite(190, 470, dockBarNormalRegion,dockBarPressedRegion, mActivity.getVertexBufferObjectManager());
		tab4 = new ButtonSprite(250, 470, dockBarNormalRegion,dockBarPressedRegion, mActivity.getVertexBufferObjectManager());
		
		tabBg = new Sprite(0, 460, tabBgRegion, mActivity.getVertexBufferObjectManager());
		contentBg = new Sprite(0, 0, contentBgRegion, mActivity.getVertexBufferObjectManager());

		attachChild(contentBg);
		attachChild(tabBg);
		attachChild(tab);
		attachChild(tab1);
		attachChild(tab2);
		attachChild(tab3);
		attachChild(tab4);

		temp = new Text(40, 30, mFont, "温度：" + mWeatherInfo.getTemp() + " ℃", 40, mActivity.getVertexBufferObjectManager());
		attachChild(temp);
		wind = new Text(40, 70, mFont, "风向：" + mWeatherInfo.getWind(), 40, mActivity.getVertexBufferObjectManager());
		attachChild(wind);
		dampness = new Text(40, 110, mFont, "湿度：" + mWeatherInfo.getDampness(), 40, mActivity.getVertexBufferObjectManager());
		attachChild(dampness);
		ptime = new Text(40, 150, mFont, "更新时间：" + mWeatherInfo.getPtime(), 40, mActivity.getVertexBufferObjectManager());
		attachChild(ptime);

		mRefresh = new Sprite(100, 300, mRefreshRegion, mActivity.getVertexBufferObjectManager());
		attachChild(mRefresh);
		
		//register touch areas
		registerTouchArea(mRefresh);
		registerTouchArea(tab);
		registerTouchArea(tab1);
		registerTouchArea(tab2);
		registerTouchArea(tab3);
		registerTouchArea(tab4);
		setOnAreaTouchListener(this);
		setTouchAreaBindingOnActionDownEnabled(true);

	}

	@Override
	public boolean onAreaTouched(TouchEvent event, ITouchArea area, float arg2, float arg3) {
		System.out.println("====================="+area);
		if (TouchEvent.ACTION_UP == event.getAction()){
			mActivity.sendBroadcast(new Intent(Config.CMD_QUERY));
		}
		return false;
	}

}
