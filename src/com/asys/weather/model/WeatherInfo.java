package com.asys.weather.model;

public class WeatherInfo {



	private String mTemp;
	private String mWind;
	private String mDampness;
	
	private String mPtime;
	
	
	private String todayTemp;
	
	private String todayState;
	
	private String curState;
	

	public String getCurState() {
		return curState;
	}

	public void setCurState(String curState) {
		this.curState = curState;
	}

	public String getTodayState() {
		return todayState;
	}

	public void setTodayState(String todayState) {
		this.todayState = todayState;
	}

	public String getTodayTemp() {
		return todayTemp;
	}

	public void setTodayTemp(String todayTemp) {
		this.todayTemp = todayTemp;
	}

	public WeatherInfo(String temp, String wind, String dampness,String ptime) {
		mTemp = temp;
		mWind = wind;
		mDampness = dampness;
		mPtime = ptime;
	}
	
	public String getTemp() {
		return mTemp;
	}

	public String getWind() {
		return mWind;
	}

	public String getDampness() {
		return mDampness;
	}
	public String getPtime() {
		return mPtime;
	}

}
