package com.asys.weather;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.ui.activity.SimpleBaseGameActivity;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.Signature;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.widget.Toast;

import com.asys.weather.service.DataService;
import com.asys.weather.ui.MainScene;
import com.asys.weather.util.Config;

public class Weather extends SimpleBaseGameActivity {

	private Camera mCamera;

	private MainScene mScene;

	private ProgressDialog mDialog;

	private static final int MSG_START_UPDATE = 0;

	private static final int MSG_END_UPDATE = 1;

	private Handler mHandler = new Handler() {

		@Override
		public void dispatchMessage(Message msg) {
			switch (msg.what) {
			case MSG_START_UPDATE:
				mDialog = ProgressDialog.show(Weather.this, "title", "loading.......");
				sendBroadcast(new Intent(Config.CMD_QUERY));
				break;
			case MSG_END_UPDATE:

				break;
			}

		}
	};

	public void startUpdate() {
		mHandler.sendEmptyMessage(MSG_START_UPDATE);
	}

	private void getAllScene() {
		mScene = new MainScene(this);
	}

	private BroadcastReceiver receiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			mDialog.dismiss();
			int resFlag = intent.getExtras().getInt("RES_FLAG", -1);
			String updateRes = "update failed.....";
			if (resFlag == 1) {
				mScene.update();
				updateRes = "update successfully!";
			}
			Toast.makeText(Weather.this, updateRes, Toast.LENGTH_SHORT).show();
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
