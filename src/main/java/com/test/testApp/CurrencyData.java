package com.test.testApp;

import java.math.RoundingMode;
import java.text.DecimalFormat;

public class CurrencyData {
	private float usdCurrent;
	private float eurCurrent;
	private float usdPrevious;
	private float eurPrevious;
	private float usdDelta;
	private float eurDelta;
	
	private DecimalFormat decFormater = new DecimalFormat("#.####");		
	
	
	CurrencyData(float usdCurrent, float usdPrevious, float eurCurrent, float eurPrevious){
		decFormater.setRoundingMode(RoundingMode.CEILING);		
		decFormater.setPositivePrefix("+");
		
		setUsdCurrent(usdCurrent);
		setUsdPrevious(usdPrevious);
		setEurCurrent(eurCurrent);
		setEurPrevious(eurPrevious);			
	}
	CurrencyData(String usdCurrent, String usdPrevious, String eurCurrent, String eurPrevious){
		decFormater.setRoundingMode(RoundingMode.CEILING);		
		decFormater.setPositivePrefix("+");
		
		setUsdCurrent(usdCurrent);
		setUsdPrevious(usdPrevious);
		setEurCurrent(eurCurrent);
		setEurPrevious(eurPrevious);			
	}
	
	
	public float getUsdCurrent() {		
		return usdCurrent;
	}	
	public String getUsdCurrentStr() {
		//public S
		return String.valueOf(usdCurrent);
	}
	public float getUsdPrevious() {
		return usdPrevious;
	}
	public void setUsdPrevious(float usdPrevious) {
		this.usdPrevious = usdPrevious;
		this.usdDelta = this.usdPrevious - this.usdCurrent;
	}
	public void setUsdPrevious(String usdPreviousStr) {
		setUsdPrevious(Float.parseFloat(usdPreviousStr));
	}
	public void setUsdCurrent(float usdCurrent) {		
		this.usdCurrent = usdCurrent;
		this.usdDelta = this.usdPrevious - this.usdCurrent;
	}
	public void setUsdCurrent(String usdCurrent) {
		setUsdCurrent(Float.parseFloat(usdCurrent));
	}
	public String getUsdDeltaStr() {
		return decFormater.format(usdDelta);		
	}
	public float getEurCurrent() {
		return eurCurrent;
	}
	public String getEurCurrentStr() {
		return String.valueOf(eurCurrent);
	}
	public void setEurCurrent(float eurCurrent) {
		this.eurCurrent = eurCurrent;
		this.eurDelta = this.eurPrevious - this.eurCurrent;
	}
	public void setEurCurrent(String eurCurrentStr) {
		setEurCurrent(Float.parseFloat(eurCurrentStr));
	}
	public float getEurPrevious() {
		return eurPrevious;
	}
	public String getEurPreviousStr() {
		return String.valueOf(eurPrevious);
	}
	public void setEurPrevious(float eurPrevious) {
		this.eurPrevious = eurPrevious;
		this.eurDelta = this.eurPrevious - this.eurCurrent;
	}	
	public void setEurPrevious(String eurPrevious) {
		setEurPrevious(Float.parseFloat(eurPrevious));
	}
	public String getEurDeltaStr() {
		return decFormater.format(eurDelta);
		
	}

}
