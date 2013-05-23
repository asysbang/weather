package com.asys.weather.model;

public class WeatherInfo {

	// for home page
	private String mTemp;
	private String mWind;
	private String mDampness;
	private String mPtime;
	private String todayTemp;
	private String todayState;
	private String curState;

	// for life page
	private String dress, dressTip, uvRays, car, travel, exe, sun;

	public WeatherInfo(String temp, String wind, String dampness, String ptime) {
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

	public String getDress() {
		return dress;
	}

	public void setDress(String dress) {
		this.dress = dress;
	}

	public String getDressTip() {
		return dressTip;
	}

	public void setDressTip(String dressTip) {
		this.dressTip = dressTip;
	}

	public String getUvRays() {
		return uvRays;
	}

	public void setUvRays(String uvRays) {
		this.uvRays = uvRays;
	}

	public String getCar() {
		return car;
	}

	public void setCar(String car) {
		this.car = car;
	}

	public String getTravel() {
		return travel;
	}

	public void setTravel(String travel) {
		this.travel = travel;
	}

	public String getExe() {
		return exe;
	}

	public void setExe(String exe) {
		this.exe = exe;
	}

	public String getSun() {
		return sun;
	}

	public void setSun(String sun) {
		this.sun = sun;
	}

}
