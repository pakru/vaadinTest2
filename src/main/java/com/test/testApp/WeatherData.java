package com.test.testApp;

public class WeatherData {
	private float nowTemp;
	private float tommorowTemp;
	//private String nowTempStr = new String();
	//private String tommorowTempStr = new String();
	
	public WeatherData(float nowTemp, float tommorowTemp){
		setNowTemp(nowTemp);
		setTommorowTemp(tommorowTemp);
	}
	public WeatherData(String nowTempStr, String tommorowTempStr) {
		setNowTemp(nowTempStr);
		setTommorowTemp(tommorowTempStr);
	}
	
	public float getNowTemp() {
		return nowTemp;
	}
	public String getNowTempStr() {
		return String.valueOf(nowTemp);
	}
	public void setNowTemp(float nowTemp) {
		this.nowTemp = nowTemp;
		//this.nowTempStr = String.valueOf(nowTemp);
	}	
	public void setNowTemp(String nowTempStr) {
		//this.nowTempStr = nowTempStr;
		this.nowTemp = Float.parseFloat(nowTempStr);
	}	
	public float getTommorowTemp() {
		return tommorowTemp;
	}
	
	public String getTommorowTempStr() {
		return String.valueOf(tommorowTemp);
	}	
	public void setTommorowTemp(float tommorowTemp) {
		this.tommorowTemp = tommorowTemp;
		//this.tommorowTempStr = String.valueOf(nowTemp);
	}
	
	public void setTommorowTemp(String tommorowTempStr) {
		//this.tommorowTempStr = tommorowTempStr;
		this.tommorowTemp = Float.parseFloat(tommorowTempStr);
	}
	/*
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return super.toString();
	}
	*/
	
	

}
