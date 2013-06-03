package com.asys.weather.util;

public class Lunar {

	private String mCyclicalYear;

	private String mAnimalsYear;

	private String mLunarMonth;

	private String mLunarDay;

	public Lunar(String cyclicalYear, String animalsYear, String lunarMonth, String lunarDay) {
		mCyclicalYear = cyclicalYear;
		mAnimalsYear = animalsYear;
		mLunarMonth = lunarMonth;
		mLunarDay = lunarDay;
	}
	
	public String getLunarYear(){
		return mCyclicalYear + mAnimalsYear + "年";
	}

	public String toString() {
		return mCyclicalYear + mAnimalsYear + "年" + mLunarMonth + "月" + mLunarDay;
	}

	public String getCyclicalYear() {
		return mCyclicalYear;
	}

	public String getAnimalsYear() {
		return mAnimalsYear;
	}

	public String getLunarMonth() {
		return mLunarMonth;
	}

	public String getLunarDay() {
		return mLunarDay;
	}

}
