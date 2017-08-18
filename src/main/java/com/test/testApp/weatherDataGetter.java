package com.test.testApp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;

import com.test.testApp.MyUI;
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
		      jsonText = jsonText.replace("[", "");  /// remake it with regexp!!!
		      jsonText = jsonText.replace("]", "");
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
		String nskLocationKey = "294459";
		String mskLocationKey = "294021";
		String spbLocationKey = "295212";
				
		String curTemp = new String("");
		
		
	}

}
