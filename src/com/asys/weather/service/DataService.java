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

	private BroadcastReceiver receiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			new updateTask().execute("start");
		}
	};

	private class updateTask extends AsyncTask<String, Integer, String> {

		@Override
		protected String doInBackground(String... params) {
			updateWeatherDataInfo();
			updateWeatherSkInfo();
			return "ok";
		}

		@Override
		protected void onPostExecute(String result) {
			sendBroadcast(new Intent(Config.CMD_UPDATE));
			Toast.makeText(DataService.this, "update succefully", Toast.LENGTH_SHORT).show();
			super.onPostExecute(result);
		}

	}

	private void updateWeatherSkInfo() {
		BufferedWriter bw = null;
		String info = null;
		try {
			File fileStreamPath = getFileStreamPath(Config.FILE_SK_INFO);
			bw = new BufferedWriter(new FileWriter(fileStreamPath));

			HttpClient mClient = new DefaultHttpClient();
			HttpGet request = new HttpGet("http://www.weather.com.cn/data/sk/101010100.html");
			HttpResponse response = mClient.execute(request);
			BufferedReader br = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
			String line = null;
			while ((line = br.readLine()) != null) {
				info = line;
				bw.write(line);
			}

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
	}

	private void updateWeatherDataInfo() {
		BufferedWriter bw = null;
		try {
			String info = null;
			File fileStreamPath = getFileStreamPath(Config.FILE_DATA_INFO);
			bw = new BufferedWriter(new FileWriter(fileStreamPath));

			HttpClient mClient = new DefaultHttpClient();
			// http://www.weather.com.cn/data/sk/101010100.html
			HttpGet request = new HttpGet("http://m.weather.com.cn/data/101010100.html");
			HttpResponse response = mClient.execute(request);
			BufferedReader br = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
			String line = null;
			while ((line = br.readLine()) != null) {
				info = line;
				bw.write(line);
			}

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
