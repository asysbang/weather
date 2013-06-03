package com.asys.weather.ui;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

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
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.graphics.Color;

import com.asys.weather.Weather;
import com.asys.weather.model.WeatherInfo;
import com.asys.weather.util.Config;

public class MainScene extends Scene implements OnClickListener {

	private Weather mActivity;

	private static final int PAGE_HOME = 0;
	private static final int PAGE_WEEK = 1;
	private static final int PAGE_LIFE = 2;
	private static final int PAGE_AD = 3;
	private static final int PAGE_SETTING = 4;

	private int curPage = PAGE_HOME;

	private HomePage mHomePage;

	private LifePage mLifePage;

	private WeekPage mWeekPage;

	private AdPage mAdPage;
	private SettingPage mSettingPage;

	public MainScene(Weather activity) {
		mActivity = activity;
		mHomePage = new HomePage();
		mLifePage = new LifePage();
		mWeekPage = new WeekPage();
		mAdPage = new AdPage();
		mSettingPage = new SettingPage();

		mUpdatingDialog = new ProgressDialog(mActivity);
		mUpdatingDialog.setMessage("loading.....");
	}

	private Font mFont;

	private BitmapTextureAtlas tabBgAtlas, contentBgAtlas, titleBgAtlas;

	private TextureRegion tabBgRegion, contentBgRegion, titleBgRegion;

	private Sprite tabBg, contentBg, titleBg;

	private ButtonSprite tab, tab1, tab2, tab3, tab4, refreshSprite;

	private BuildableBitmapTextureAtlas tabAtlsa, refreshAtlas;

	private ITextureRegion tabHomeNormalRegion, tabHomePressedRegion, tabUpdateNormalRegion, tabUpdatePressedRegion, tabWeekNormalRegion,
			tabWeekPressedRegion, tabLifeNormalRegion, tabLifePressedRegion, tabAdNormalRegion, tabAdPressedRegion, refreshRegion,
			refreshPressedRegion;

	private Text appName, strDate, lunarDate;

	public void loadResources() {
		mHomePage.loadResources();

		// !!!!! sample use BuildableBitmapTextureAtlas not BitmapTextureAtlas
		// !!!!!
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("img/");

		tabBgAtlas = new BitmapTextureAtlas(mActivity.getTextureManager(), 512, 512, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		tabBgRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(tabBgAtlas, mActivity, "tab_bg.png", 0, 0);
		tabBgAtlas.load();

		titleBgAtlas = new BitmapTextureAtlas(mActivity.getTextureManager(), 512, 512, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		titleBgRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(titleBgAtlas, mActivity, "title_bg.png", 0, 0);
		titleBgAtlas.load();

		refreshAtlas = new BuildableBitmapTextureAtlas(mActivity.getTextureManager(), 128, 128, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		refreshRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(refreshAtlas, mActivity, "refresh_normal.png");
		refreshPressedRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(refreshAtlas, mActivity, "refresh_pressed.png");
		try {
			refreshAtlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(1, 1, 1));
			refreshAtlas.load();
		} catch (TextureAtlasBuilderException e) {
			e.printStackTrace();
		}

		contentBgAtlas = new BitmapTextureAtlas(mActivity.getTextureManager(), 512, 512, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		contentBgRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(contentBgAtlas, mActivity, "content_bg.jpg", 0, 0);
		contentBgAtlas.load();

		tabAtlsa = new BuildableBitmapTextureAtlas(mActivity.getTextureManager(), 512, 512, TextureOptions.BILINEAR_PREMULTIPLYALPHA);

		tabUpdateNormalRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(tabAtlsa, mActivity, "tab_update_normal.png");
		tabUpdatePressedRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(tabAtlsa, mActivity, "tab_update_pressed.png");

		tabHomeNormalRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(tabAtlsa, mActivity, "tab_home_normal.png");
		tabHomePressedRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(tabAtlsa, mActivity, "tab_home_pressed.png");

		tabWeekNormalRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(tabAtlsa, mActivity, "tab_week_normal.png");
		tabWeekPressedRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(tabAtlsa, mActivity, "tab_week_pressed.png");

		tabLifeNormalRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(tabAtlsa, mActivity, "tab_life_normal.png");
		tabLifePressedRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(tabAtlsa, mActivity, "tab_life_pressed.png");

		tabAdNormalRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(tabAtlsa, mActivity, "tab_ad_normal.png");
		tabAdPressedRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(tabAtlsa, mActivity, "tab_ad_pressed.png");
		try {
			tabAtlsa.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(1, 1, 1));
			tabAtlsa.load();
		} catch (TextureAtlasBuilderException e) {
			e.printStackTrace();
		}

		BitmapTextureAtlas pBitmapTextureAtlas = new BitmapTextureAtlas(mActivity.getTextureManager(), 256, 256,
				TextureOptions.BILINEAR_PREMULTIPLYALPHA);

		FontFactory.setAssetBasePath("fonts/");

		mFont = FontFactory.createFromAsset(mActivity.getFontManager(), pBitmapTextureAtlas, mActivity.getAssets(), "weather.ttf", 18,
				true, Color.DKGRAY);
		mFont.load();

		loadSkInfoFile();
		loadDataInfoFile();

	}

	public void update() {
		loadSkInfoFile();
		loadDataInfoFile();
		strDate.setText("" + mWeatherInfo.getStrDate());
		lunarDate.setText("" + mWeatherInfo.getLunarDate());
		mUpdatingDialog.dismiss();
		switchToPage(curPage, true);

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
			mWeatherInfo.setDress(info.getString("index"));
			mWeatherInfo.setDressTip(info.getString("index_d"));
			mWeatherInfo.setUvRays(info.getString("index_uv"));
			mWeatherInfo.setCar(info.getString("index_xc"));
			mWeatherInfo.setTravel(info.getString("index_tr"));
			mWeatherInfo.setExe(info.getString("index_cl"));
			mWeatherInfo.setSun(info.getString("index_ls"));
			// set info[]
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
			Date date = sdf.parse(info.getString("date_y"));

			mWeatherInfo.setDate(date);

			String[] data = new String[6];
			for (int i = 0; i < data.length; i++) {
				data[i] = formatData(date, i, info.getString("temp" + (i + 1)), info.getString("weather" + (i + 1)));
			}
			mWeatherInfo.setInfo(data);

		} catch (JSONException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private String[] weeks = new String[] { "(周日)", "(周一)", "(周二)", "(周三)", "(周四)", "(周五)", "(周六)" };

	private String formatData(Date date, int i, String string, String string2) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		int j = c.get(Calendar.DAY_OF_MONTH);
		c.set(Calendar.DAY_OF_MONTH, j + i);
		int index = c.get((Calendar.DAY_OF_WEEK));
		return c.get(Calendar.DATE) + "日" + (weeks[index - 1]) + ":" + string + " : " + string2;
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
			WeatherInfo res = new WeatherInfo(info.getString("temp"), info.getString("WD") + info.getString("WS"), info.getString("SD"),
					info.getString("time"));
			return res;
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}

	public void loadScene() {
		float tabLeft = 10;
		float tabWidth = tabHomeNormalRegion.getWidth();
		VertexBufferObjectManager vertexBufferObjectManager = mActivity.getVertexBufferObjectManager();
		tab = new ButtonSprite(tabLeft, 470, tabHomeNormalRegion, tabHomePressedRegion, vertexBufferObjectManager);
		tab1 = new ButtonSprite(tabLeft + tabWidth, 470, tabWeekNormalRegion, tabWeekPressedRegion, vertexBufferObjectManager);
		tab2 = new ButtonSprite(tabLeft + tabWidth * 2, 470, tabLifeNormalRegion, tabLifePressedRegion, vertexBufferObjectManager);
		tab3 = new ButtonSprite(tabLeft + tabWidth * 3, 470, tabAdNormalRegion, tabAdPressedRegion, vertexBufferObjectManager);
		tab4 = new ButtonSprite(tabLeft + tabWidth * 4, 470, tabUpdateNormalRegion, tabUpdatePressedRegion, vertexBufferObjectManager);

		tabBg = new Sprite(0, 460, tabBgRegion, vertexBufferObjectManager);
		contentBg = new Sprite(0, 0, contentBgRegion, vertexBufferObjectManager);

		titleBg = new Sprite(5, 2, titleBgRegion, vertexBufferObjectManager);

		attachChild(contentBg);
		attachChild(titleBg);
		attachChild(tabBg);
		attachChild(tab);
		attachChild(tab1);
		attachChild(tab2);
		attachChild(tab3);
		attachChild(tab4);

		refreshSprite = new ButtonSprite(255, 9, refreshRegion, refreshPressedRegion, vertexBufferObjectManager);
		attachChild(refreshSprite);

		appName = new Text(15, 27, mFont, "aSys天气", vertexBufferObjectManager);
		attachChild(appName);

		strDate = new Text(105, 17, mFont, "" + mWeatherInfo.getStrDate(), 40, vertexBufferObjectManager);
		attachChild(strDate);

		lunarDate = new Text(105, 37, mFont, "" + mWeatherInfo.getLunarDate(), 40, vertexBufferObjectManager);
		attachChild(lunarDate);

		mHomePage.loadScene(this, mFont, vertexBufferObjectManager, mWeatherInfo);
		mLifePage.loadScene(this, mFont, vertexBufferObjectManager, mWeatherInfo);
		mWeekPage.loadScene(this, mFont, vertexBufferObjectManager, mWeatherInfo);
		mAdPage.loadScene(this, mFont, vertexBufferObjectManager);
		mSettingPage.loadScene(this, mFont, vertexBufferObjectManager);

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

		refreshSprite.setOnClickListener(this);
		registerTouchArea(refreshSprite);
		setTouchAreaBindingOnActionDownEnabled(true);
	}

	private void hideCurPage() {
		switch (curPage) {
		case PAGE_HOME:
			mHomePage.hide(mActivity);
			break;
		case PAGE_WEEK:
			mWeekPage.hide(mActivity);
			break;
		case PAGE_LIFE:
			mLifePage.hide(mActivity);

			break;
		case PAGE_AD:
			mAdPage.hide(mActivity);

			break;
		case PAGE_SETTING:
			mSettingPage.hide(mActivity);

			break;
		}
	}

	private void switchToPage(int tagPage, boolean selfToSelf) {
		if (!selfToSelf && curPage == tagPage) {
			return;
		}
		hideCurPage();
		switch (tagPage) {
		case PAGE_HOME:
			mHomePage.show(mActivity, mWeatherInfo);
			curPage = PAGE_HOME;
			break;
		case PAGE_WEEK:
			mWeekPage.show(mActivity, mWeatherInfo);
			curPage = PAGE_WEEK;

			break;
		case PAGE_LIFE:
			mLifePage.show(mActivity, mWeatherInfo);
			curPage = PAGE_LIFE;
			break;
		case PAGE_AD:
			mAdPage.show(mActivity, mWeatherInfo);
			curPage = PAGE_AD;

			break;
		case PAGE_SETTING:
			mSettingPage.show(mActivity, mWeatherInfo);
			curPage = PAGE_SETTING;
			break;
		}

	}

	@Override
	public void onClick(ButtonSprite btnSprite, float arg1, float arg2) {
		int tagPage = PAGE_HOME;
		if (btnSprite == tab) {
			// default is home page
		} else if (btnSprite == tab1) {
			tagPage = PAGE_WEEK;
		} else if (btnSprite == tab2) {
			tagPage = PAGE_LIFE;
		} else if (btnSprite == tab3) {
			tagPage = PAGE_AD;
		} else if (btnSprite == tab4) {
			tagPage = PAGE_SETTING;
		} else if (btnSprite == refreshSprite) {
			mActivity.startUpdate();
			return;
		}
		switchToPage(tagPage, false);
	}

	private ProgressDialog mUpdatingDialog;

}
