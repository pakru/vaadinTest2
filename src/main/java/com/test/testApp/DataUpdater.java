package com.test.testApp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.math.RoundingMode;
import java.net.URL;
import java.nio.charset.Charset;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;

import org.json.*;


public class DataUpdater  {	
	
	private String readAll(Reader rd) throws IOException {
	    StringBuilder sb = new StringBuilder();
	    int cp;
	    while ((cp = rd.read()) != -1) {
	      sb.append((char) cp);
	    }
	    return sb.toString();
	  }
	
	private JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
		JSONObject json = null;
		    InputStream inStream = new URL(url).openStream();
		    try {
		      BufferedReader readBuff = new BufferedReader(
		    		  new InputStreamReader(inStream, Charset.forName("UTF-8")));		      
		      String jsonText = readAll(readBuff);
		      json = new JSONObject(jsonText);
		    } catch (Exception e) {
		    	e.printStackTrace();
		    	System.out.println("JSON exception!");
		    }
		    finally {
		    	inStream.close();
		    }
		  return json;
		  }

	public WeatherData getCurrentWeather(String cityKey) {
		JSONObject json = null;
		String apiKey = "a73dbed5bab3457b8c193243172108";
		String weatherForecastURL = "http://api.apixu.com/v1/forecast.json?key="+apiKey+"&q="+cityKey+"&days=2";
		String tommorowTempStr = "";
		String nowTempStr = "";
		
		if (cityKey != null) {
			try {
				json = readJsonFromUrl(weatherForecastURL);
			} catch (JSONException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
				//System.out.println("JSON exception!");
			}
			
		nowTempStr = json.getJSONObject("current").get("temp_c").toString();
		JSONArray weatherArray = json.getJSONObject("forecast").getJSONArray("forecastday");
		
		String timeStamp = LocalDateTime.now().plusDays(1).format(DateTimeFormatter.ISO_LOCAL_DATE);
		
		for (Object weatherDay : weatherArray) {
			JSONObject jsDay= (JSONObject) weatherDay;			
			if (jsDay.get("date").toString().equals(timeStamp)) {
				//System.out.println("--- We found it!!!!");
				tommorowTempStr = jsDay.getJSONObject("day").get("maxtemp_c").toString();
			}
		}
		
		}
	
		return new WeatherData(nowTempStr, tommorowTempStr);
	}
	
	public CurrencyData updateCurrencyData() {
		JSONObject json = null;
		String currenyURL = "https://www.cbr-xml-daily.ru/daily_json.js";
		try {
			json = readJsonFromUrl(currenyURL);
		} catch (JSONException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
			//System.out.println("JSON exception!");
		}
		//System.out.println(json.toString());
		
		String eurCurrent = json.getJSONObject("Valute").getJSONObject("EUR").get("Value").toString();
		String eurPrevious = json.getJSONObject("Valute").getJSONObject("EUR").get("Previous").toString();
		float eurDelta = Float.parseFloat(eurCurrent) - Float.parseFloat(eurPrevious); 		
		
		String usdCurrent = json.getJSONObject("Valute").getJSONObject("USD").get("Value").toString();
		String usdPrevious = json.getJSONObject("Valute").getJSONObject("USD").get("Previous").toString();
		float usdDelta = Float.parseFloat(usdCurrent) - Float.parseFloat(usdPrevious); 
		
		DecimalFormat decFormater = new DecimalFormat("#.####");		
		decFormater.setRoundingMode(RoundingMode.CEILING);		
		decFormater.setPositivePrefix("+");
		
		//setCurrencyData(usdCurrent, decFormater.format(usdDelta), eurCurrent, decFormater.format(eurDelta));
		return new CurrencyData(usdCurrent, usdPrevious, eurCurrent, eurPrevious);
		
	} 



} //class
