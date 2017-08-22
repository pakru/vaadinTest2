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

import com.test.testApp.MyUI;
import com.vaadin.data.converter.StringToFloatConverter;

import org.json.*;


public class weatherDataGetter extends MyUI {  	
	private static String readAll(Reader rd) throws IOException {
		    StringBuilder sb = new StringBuilder();
		    int cp;
		    while ((cp = rd.read()) != -1) {
		      sb.append((char) cp);
		    }
		    return sb.toString();
		  }

	public static JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
		JSONObject json = null;
		    InputStream inStream = new URL(url).openStream();
		    try {
		      BufferedReader readBuff = new BufferedReader(
		    		  new InputStreamReader(inStream, Charset.forName("UTF-8")));		      
		      String jsonText = readAll(readBuff);
		      //System.out.println(jsonText);
		      //jsonText = jsonText.replace("[", "");  /// remake it with regexp!!!
		      //jsonText = jsonText.replace("]", "");
		      //jsonText = jsonText.replaceAll("//", replacement)
		      //System.out.println("After filter exp:");
		      //System.out.println(jsonText);
		      json = new JSONObject(jsonText);
		      //return json;
		    } catch (Exception e) {
		    	e.printStackTrace();
		    	System.out.println("JSON exception!");
		    }
		    finally {
		    	inStream.close();
		    }
		  return json;
		  }
	
	public static void doRequest() {
		JSONObject json = null;
		try {
			json = readJsonFromUrl(
					"http://apidev.accuweather.com/currentconditions/v1/294459.json?language=en&apikey=hoArfRosT1215");
		} catch (JSONException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("JSON exception!");
		}
		System.out.println(json.toString());
		
	    
		setTodayTemp(json.getJSONObject("Temperature").getJSONObject("Metric").get("Value").toString());
		
		System.out.println(json.getJSONObject("Temperature").getJSONObject("Metric").get("Value"));
		
		
	}
	
	public static void updateCurrentWeather(String city) {
		JSONObject json = null;
		String cityKey = getCityKey(city);		
		String apiKey = "a73dbed5bab3457b8c193243172108";
		//String weatherTodayURL = "http://api.apixu.com/v1/current.json?key="+apiKey+"&q=" + cityKey;
		//String weatherTommorowURL = "http://api.apixu.com/v1/forecast.json?key="+apiKey+"&q=" + cityKey;
		String weatherForecastURL = "http://api.apixu.com/v1/forecast.json?key="+apiKey+"&q="+cityKey+"&days=2";
		if (cityKey != null) {
			//String weatherURL = "http://apidev.accuweather.com/currentconditions/v1/" + String.valueOf(cityKey) + ".json?language=en&apikey=hoArfRosT1215";
			//String weatherForTommorowURL = "http://api.accuweather.com/forecasts/v1/daily/1day/335315?apikey=apiKey";
			try {
				json = readJsonFromUrl(weatherForecastURL);
			} catch (JSONException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("JSON exception!");
			}
			System.out.println(json.toString());
		setTodayTemp(json.getJSONObject("current").get("temp_c").toString());			
		}
				
	}
	
	public static void updateCurrencyData() {
		JSONObject json = null;
		String currenyURL = "https://www.cbr-xml-daily.ru/daily_json.js";
		try {
			json = readJsonFromUrl(currenyURL);
		} catch (JSONException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("JSON exception!");
		}
		System.out.println(json.toString());
		
		String eurCurrent = json.getJSONObject("Valute").getJSONObject("EUR").get("Value").toString();
		String eurPrevious = json.getJSONObject("Valute").getJSONObject("EUR").get("Previous").toString();
		float eurDelta = Float.parseFloat(eurCurrent) - Float.parseFloat(eurPrevious); 		
		
		String usdCurrent = json.getJSONObject("Valute").getJSONObject("USD").get("Value").toString();
		String usdPrevious = json.getJSONObject("Valute").getJSONObject("USD").get("Previous").toString();
		float usdDelta = Float.parseFloat(usdCurrent) - Float.parseFloat(usdPrevious); 
		
		DecimalFormat decFormater = new DecimalFormat("#.####");
		decFormater.setRoundingMode(RoundingMode.CEILING);				
		
		setCurrencyData(usdCurrent, decFormater.format(usdDelta), eurCurrent, decFormater.format(eurDelta));				
		
	}

}
