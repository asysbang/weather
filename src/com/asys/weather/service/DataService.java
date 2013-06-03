package com.asys.weather.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;

import com.asys.weather.util.Config;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.IBinder;
import android.widget.Toast;

public class DataService extends Service {

	private int resFlag = -1;

	private BroadcastReceiver receiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			new updateTask().execute("start");
		}
	};

	private class updateTask extends AsyncTask<String, Integer, String> {

		@Override
		protected String doInBackground(String... params) {
			resFlag = updateWeatherDataInfo();
			if (resFlag == 1) {
				updateWeatherSkInfo();
			}
			return "ok";
		}

		@Override
		protected void onPostExecute(String result) {
			Intent intent = new Intent(Config.CMD_UPDATE);
			intent.putExtra("RES_FLAG", resFlag);
			sendBroadcast(intent);
			super.onPostExecute(result);
		}

	}

	private int updateWeatherSkInfo() {
		BufferedWriter bw = null;
		try {
			File fileStreamPath = getFileStreamPath(Config.FILE_SK_INFO);
			bw = new BufferedWriter(new FileWriter(fileStreamPath));

			HttpClient mClient = new DefaultHttpClient();
			mClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, Config.HTTP_TIMEOUT);
			mClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, Config.HTTP_TIMEOUT);
			HttpGet request = new HttpGet("http://www.weather.com.cn/data/sk/101010100.html");
			HttpResponse response = mClient.execute(request);
			BufferedReader br = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
			String line = null;
			while ((line = br.readLine()) != null) {
				bw.write(line);
			}
			return 1;
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (bw != null) {
				try {
					bw.flush();
					bw.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return -1;
	}

	private int updateWeatherDataInfo() {
		BufferedWriter bw = null;
		try {
			File fileStreamPath = getFileStreamPath(Config.FILE_DATA_INFO);
			bw = new BufferedWriter(new FileWriter(fileStreamPath));
			HttpClient mClient = new DefaultHttpClient();
			mClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, Config.HTTP_TIMEOUT);
			mClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, Config.HTTP_TIMEOUT);
			HttpGet request = new HttpGet("http://m.weather.com.cn/data/101010100.html");
			HttpResponse response = mClient.execute(request);
			BufferedReader br = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
			String line = null;
			while ((line = br.readLine()) != null) {
				bw.write(line);
			}
			return 1;
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (bw != null) {
				try {
					bw.flush();
					bw.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return -1;
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onCreate() {
		IntentFilter filter = new IntentFilter(Config.CMD_QUERY);
		registerReceiver(receiver, filter);
	};

	@Override
	public void onDestroy() {
		unregisterReceiver(receiver);
		super.onDestroy();
	}

	@Override
	public void onStart(Intent intent, int startId) {
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public boolean onUnbind(Intent intent) {
		return super.onUnbind(intent);
	}

	@Override
	public void onRebind(Intent intent) {
		super.onRebind(intent);
	};

}
