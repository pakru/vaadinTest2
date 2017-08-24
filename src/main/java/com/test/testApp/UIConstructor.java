package com.test.testApp;

import java.util.HashMap;

import com.test.testApp.MyUI;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinRequest;
import com.vaadin.shared.Position;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Layout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.VerticalLayout;

public class UIConstructor extends MyUI {
	private VerticalLayout weatherLayout = new VerticalLayout();
	private VerticalLayout currencyLayout = new VerticalLayout();
	private VerticalLayout visitsLyout = new VerticalLayout();
	private HorizontalLayout mainLayout = new HorizontalLayout();
	private HashMap<String, String> citiesMap = new HashMap<>();
	private Label ipAddrText = new Label();

	private Label currentWeatherText = new Label("Now: ");    	
	private Label tommorowWeatherText = new Label("Tommorow: ");
	
	private Label usdText = new Label("$:");
	private Label eurText = new Label("€:");
	
	//private String todayTemp = new String();
	//private String tommorowTemp = new String();
	
	private DataUpdater dataUpdater = new DataUpdater();
	private WeatherData weatherData = null;
	private CurrencyData currencyData = null;


	
	public Layout constructIface (VaadinRequest vdRequest) {
		return constructUI();
	}
	
	
	private Layout constructUI() {
	    	constructWeatherLayout();
	    	constructCurrencyLayout();
	    	constructVisitsLayout();
	    	mainLayout.addComponents(weatherLayout,currencyLayout,visitsLyout,ipAddrText);
	    	//setContent(mainLayout);
	    	//setContent(currentWeatherText);
	    	return mainLayout;
	    	
	}
	
    private void constructWeatherLayout() {
    	/*
    	citiesMap.put(294459, "Novosibirsk");
    	citiesMap.put(294021, "Moscow");
    	citiesMap.put(295212, "S Petersburg");
    	*/
    	citiesMap.put("Novosibirsk", "Novosibirsk");
    	citiesMap.put("Moscow", "Moscow");
    	citiesMap.put("S Petersburg", "Saint-Petersburg");

    	
    	ComboBox<String> cityChoose = new ComboBox<>("Choose city");    	
    	cityChoose.setItems(citiesMap.keySet());
    	//cityChoose.setItemCaptionGenerator(cap -> citiesMap.values());
    	//cityChoose.setReadOnly(true);
    	cityChoose.setTextInputAllowed(true);
    	cityChoose.setValue("Novosibirsk");
    	cityChoose.setEmptySelectionAllowed(false);
    	    	
    	Label layoutTitle = new Label("Weather");
    	Button updateBtn = new Button("Update");
    	
    	cityChoose.addSelectionListener(changeListener -> {
    		updateBtn.click();
    	});
    	updateBtn.addClickListener(clickEvent -> {
    		Notification notify = new Notification("Updating weather for " + cityChoose.getValue());
    		    		
    		//weatherDataGetter.doRequest();
    		
    		notify.setDelayMsec(1000);
    		notify.setPosition(Position.BOTTOM_RIGHT);
    		notify.show(Page.getCurrent());
    		cityChoose.getSelectedItem();
    		
    		//DataUpdater.up
    		//dataUpdater.updateCurrentWeather(cityChoose.getValue());    	
    		weatherData = dataUpdater.getCurrentWeather(getCityKey(cityChoose.getValue()));
    		setTodayTemp(weatherData.getNowTempStr(), weatherData.getTommorowTempStr());
    		
    		System.out.println("Updating weather for " + cityChoose.getValue());    		    		
    	});
    	   	
    	weatherLayout.addComponents(layoutTitle, cityChoose,currentWeatherText, tommorowWeatherText, updateBtn);  
    	updateBtn.click();
    }
    
    private void constructCurrencyLayout() {
    	Label layoutTitle = new Label("Currency");
    	Button updateBtn = new Button("Update");
    	
    	updateBtn.addClickListener(clickEvent -> {
    		Notification notify = new Notification("Updating currency data");
    		notify.setDelayMsec(1000);
    		notify.setPosition(Position.BOTTOM_RIGHT);
    		notify.show(Page.getCurrent());
    		
    		currencyData = dataUpdater.updateCurrencyData();
    		setCurrencyData(currencyData.getUsdCurrentStr(),currencyData.getUsdDeltaStr(),currencyData.getEurCurrentStr(),currencyData.getEurDeltaStr());    		
    		
    	});
    	
    	currencyLayout.addComponents(layoutTitle,usdText,eurText,updateBtn);
    	updateBtn.click();
    	
    }
    
    private void constructVisitsLayout() {
    	Label layoutTitle = new Label("Visits");
    	Label unicVisits = new Label("Unic Visits:");
    	Label totalVisits = new Label("Total Visits:");
    	
    	visitsLyout.addComponents(layoutTitle,unicVisits,totalVisits);
    }

    
	public String getCityKey(String city) {
		//int cityKey = 0;
		String cityKey = citiesMap.get(city);
		
		return cityKey;
		
	}
	
	public void setTodayTemp(String todayTemp, String tommorowTemp) {
		//this.todayTemp = todayTemp;
		currentWeatherText.setValue("Now: " + todayTemp + " °C");	
		tommorowWeatherText.setValue("Tommorow: " + tommorowTemp + " °C");
	}

	public void setCurrencyData(String usdData, String usdDelta, String eurData, String eurDelta) {
		usdText.setValue("$: " + usdData + " ( " + usdDelta + " )");
		eurText.setValue("€: " + eurData + " ( " + eurDelta + " )");
	}


	/**
	 * 
	 */
	
}
