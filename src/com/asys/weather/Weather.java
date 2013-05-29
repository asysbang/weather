package com.asys.weather;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.ui.activity.SimpleBaseGameActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.Signature;
import android.os.Bundle;
import android.util.DisplayMetrics;

import com.asys.weather.service.DataService;
import com.asys.weather.ui.MainScene;
import com.asys.weather.util.Config;

public class Weather extends SimpleBaseGameActivity {

	private Camera mCamera;

	private MainScene mScene;

	private void getAllScene() {
		mScene = new MainScene(this);
	}

	private BroadcastReceiver receiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			mScene.update();
		}
	};

	@Override
	protected void onCreate(Bundle pSavedInstanceState) {
		super.onCreate(pSavedInstanceState);

		try {
			PackageInfo info = getPackageManager().getPackageInfo("com.asys.weather", PackageManager.GET_SIGNATURES);
			Signature[] ss = info.signatures;
			System.out.println("=======" + ss.hashCode());
			System.out.println("=======" + ss.length);
			System.out.println("=======" + ss[0].toCharsString());
			if (ss[0].toCharsString().startsWith("308201e53082014ea00")) {
				System.out.println("=============okokokokokookokok========");
			}
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}

		IntentFilter filter = new IntentFilter(Config.CMD_UPDATE);
		registerReceiver(receiver, filter);
	}

	@Override
	protected void onDestroy() {
		unregisterReceiver(receiver);
		super.onDestroy();
	}

	@Override
	public EngineOptions onCreateEngineOptions() {
		DisplayMetrics outMetrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(outMetrics);
		startService(new Intent(this, DataService.class));
		getAllScene();
		mCamera = new Camera(0, 0, Config.CAMERA_WIDTH, Config.CAMERA_HEIGHT);
		return new EngineOptions(true, ScreenOrientation.PORTRAIT_FIXED, new RatioResolutionPolicy(outMetrics.widthPixels,
				outMetrics.heightPixels), mCamera);
	}

	@Override
	protected void onCreateResources() {
		mScene.loadResources();
	}

	@Override
	protected Scene onCreateScene() {
		mScene.loadScene();
		return mScene;
	}

}
