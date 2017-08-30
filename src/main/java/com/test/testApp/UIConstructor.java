package com.test.testApp;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Enumeration;
import java.util.HashMap;

import org.json.Cookie;

import com.test.testApp.MyUI;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinSession;
import com.vaadin.shared.Position;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Layout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

public class UIConstructor extends MyUI {
	private VerticalLayout weatherLayout = new VerticalLayout();
	private VerticalLayout currencyLayout = new VerticalLayout();
	private VerticalLayout visitsLyout = new VerticalLayout();
	private VerticalLayout mainLayout = new VerticalLayout();
	private HashMap<String, String> citiesMap = new HashMap<>();
	private Label ipAddrText = new Label();

	private Label currentWeatherText = new Label("Сейчас: ");    	
	private Label tommorowWeatherText = new Label("Завтра: ");
	
	private Label unicVisits = new Label("Уникальныйх посещений:");
	private Label totalVisits = new Label("Всего посещений:");

	
	private Label usdText = new Label("$:");
	private Label eurText = new Label("€:");
	
	//private String todayTemp = new String();
	//private String tommorowTemp = new String();
	private String userIP = new String();
	
	private DataUpdater dataUpdater = new DataUpdater();
	private WeatherData weatherData = null;
	private CurrencyData currencyData = null;
	private dbDataAccess dbVisitsCounter = new dbDataAccess();


	
	public Layout constructIface (VaadinRequest vdRequest) {
		userIP = vdRequest.getRemoteAddr();
		String sessionId = VaadinSession.getCurrent().getSession().getId();
		
		dbVisitsCounter.addVisitorData(userIP, sessionId);
		
		System.out.println("Session id: " + sessionId + " " + userIP);
		Page.getCurrent().setTitle("Тестовое приложение");
		/*
		javax.servlet.http.Cookie[] cookies = vdRequest.getCookies();
		
		for (javax.servlet.http.Cookie cookie : cookies) {
			System.out.println("Cookie: " + cookie.getName() + " : " + cookie.getValue());
		} */

		return constructUI();
	}
	
	
	private Layout constructUI() {
		HorizontalLayout infoLayout = new HorizontalLayout(); // weather, currency, visits
		HorizontalLayout personalDataLayout = new HorizontalLayout(); // date, ip
		Label dateTime = new Label("Запрос произведен: " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss dd-MM-yyyy")));
		//String currentDateTime = LocalDateTime.now().format(DateTimeFormatter.RFC_1123_DATE_TIME);
		//Label heaterText = new Label("This is the app");
		Panel testPanel = new Panel();
		
		//heaterText.heaterText.setStyleName("headertext");
		//heaterText.setWidth("50%");
		ipAddrText.setValue("Ваш IP: " + userIP);	
		dateTime.setWidth("250px");
		dateTime.setStyleName("v-label-datetime");
		
		constructWeatherLayout();
	    constructCurrencyLayout();
	    constructVisitsLayout();	    	    
	    
	    infoLayout.addComponents(weatherLayout,currencyLayout,visitsLyout);
		personalDataLayout.addComponents(dateTime,ipAddrText);
		personalDataLayout.setComponentAlignment(dateTime, Alignment.MIDDLE_CENTER);
		personalDataLayout.setComponentAlignment(ipAddrText, Alignment.MIDDLE_CENTER);
		personalDataLayout.setHeight("100px");
		personalDataLayout.setWidth("100%");
	    
	    //mainLayout.addComponents(heaterText,infoLayout,personalDataLayout);
		
		//testPanel.setWidth("800px");
		//testPanel.setHeight("600px");
		
		FormLayout content = new FormLayout();
		content.addComponent(infoLayout);		
		testPanel.setContent(content);
		testPanel.setCaption("Тестовое приложение");
		testPanel.setWidth("850px");
		testPanel.setHeight("100%");
		//testPanel.setStyleName("headerpanel");
		mainLayout.addComponents(testPanel,personalDataLayout);
		
	    //mainLayout.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
	    //mainLayout.setComponentAlignment(heaterText, Alignment.TOP_CENTER);
	    //mainLayout.setHeight("80%");
	    //mainLayout.setWidth("850px");
	    mainLayout.setComponentAlignment(testPanel, Alignment.MIDDLE_CENTER);
	    //mainLayout.setHeight("100%");
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
    	citiesMap.put("Новосибриск", "Novosibirsk");
    	citiesMap.put("Москва", "Moscow");
    	citiesMap.put("Санкт-Петербург", "Saint-Petersburg");

    	
    	ComboBox<String> cityChoose = new ComboBox<>("Город");    	
    	cityChoose.setItems(citiesMap.keySet());
    	//cityChoose.setItemCaptionGenerator(cap -> citiesMap.values());
    	//cityChoose.setReadOnly(true);
    	cityChoose.setTextInputAllowed(false);
    	cityChoose.setValue("Новосибриск");
    	cityChoose.setEmptySelectionAllowed(false);
    	    	
    	Label layoutTitle = new Label("Погода");
    	layoutTitle.setStyleName("miniheader");
    	Button updateBtn = new Button("Обновить");
    	updateBtn.setIcon(VaadinIcons.REFRESH);
    	
    	
    	cityChoose.addSelectionListener(changeListener -> {
    		updateBtn.click();
    	});
    	updateBtn.addClickListener(clickEvent -> {
    		Notification notify = new Notification("Получаем погоду для " + cityChoose.getValue());
    		Notification failureNotification = new Notification("Не удалось получить данные погоды");
    		
    		//weatherDataGetter.doRequest();
    		
    		notify.setDelayMsec(1000);
    		notify.setPosition(Position.BOTTOM_RIGHT);
    		//notify.show(Page.getCurrent());
    		failureNotification.setDelayMsec(2000);
    		failureNotification.setStyleName(ValoTheme.NOTIFICATION_ERROR);
    		failureNotification.setPosition(Position.BOTTOM_RIGHT);
    		//cityChoose.getSelectedItem();
    		
    		currentWeatherText.setValue("");
    		currentWeatherText.setStyleName(ValoTheme.LABEL_SPINNER);
			
    		
    		weatherData = dataUpdater.getCurrentWeather(getCityKey(cityChoose.getValue()));
    		
    		
    		if (weatherData != null) {
    			setTodayTemp(weatherData.getNowTempStr(), weatherData.getTommorowTempStr());
    		} else {
    			failureNotification.show(Page.getCurrent());
    		}
    		
    		
    		
    		//System.out.println("Updating weather for " + cityChoose.getValue());    		    		
    	});
    	   	
    	weatherLayout.addComponents(layoutTitle, cityChoose,currentWeatherText, tommorowWeatherText, updateBtn);
    	weatherLayout.setMargin(false);
    	weatherLayout.setWidth("250px");
    	weatherLayout.setComponentAlignment(updateBtn, Alignment.BOTTOM_CENTER);
    	weatherLayout.setStyleName("layout-with-border");
    	updateBtn.click();
    }
    
    private void constructCurrencyLayout() {
    	Label layoutTitle = new Label("Валюта");
    	Button updateBtn = new Button("Обновить");
    	layoutTitle.setStyleName("miniheader");
    	updateBtn.setIcon(VaadinIcons.REFRESH);
    	
    	    	
    	updateBtn.addClickListener(clickEvent -> {
    		Notification notify = new Notification("Обновляем данные по валюте...");
    		Notification failureNotification = new Notification("Не удалось получить данные валюты");
    		
    		notify.setDelayMsec(1000);
    		notify.setPosition(Position.BOTTOM_RIGHT);
    		failureNotification.setDelayMsec(2000);
    		failureNotification.setStyleName(ValoTheme.NOTIFICATION_ERROR);
    		failureNotification.setPosition(Position.BOTTOM_RIGHT);
    		//notify.show(Page.getCurrent());
    		//updateBtn.setStyleName(ValoTheme.LABEL_SPINNER);
    		
    		currencyData = dataUpdater.updateCurrencyData();
    		if (currencyData != null) {
    			setCurrencyData(currencyData.getUsdCurrentStr(),currencyData.getUsdDeltaStr(),currencyData.getEurCurrentStr(),currencyData.getEurDeltaStr());        		
    		} else {
    			failureNotification.show(Page.getCurrent());
    		}
    		
    		    		
    	});
    	
    	currencyLayout.addComponents(layoutTitle,usdText,eurText,updateBtn);
    	currencyLayout.setWidth("250px");
    	currencyLayout.setMargin(false);
    	currencyLayout.setComponentAlignment(updateBtn, Alignment.BOTTOM_CENTER);
    	currencyLayout.setStyleName("layout-with-border");
    	updateBtn.click();
    	
    }
    
    private void constructVisitsLayout() {
    	Label layoutTitle = new Label("Посещения");
    	layoutTitle.setStyleName("miniheader");
    	visitsLyout.addComponents(layoutTitle,unicVisits,totalVisits);
    	visitsLyout.setWidth("250px");
    	visitsLyout.setMargin(false);
    	setVisitsData();
    }

    
	public String getCityKey(String city) {
		String cityKey = citiesMap.get(city);		
		return cityKey;
	}
	
	public void setTodayTemp(String todayTemp, String tommorowTemp) {
		//this.todayTemp = todayTemp;
		
		currentWeatherText.setStyleName("v-label");
		currentWeatherText.setValue("Сейчас: " + todayTemp + " °C");	
		tommorowWeatherText.setValue("Завтра: " + tommorowTemp + " °C");
	}

	public void setCurrencyData(String usdData, String usdDelta, String eurData, String eurDelta) {
		usdText.setValue("$: " + usdData + " ( " + usdDelta + " )");
		eurText.setValue("€: " + eurData + " ( " + eurDelta + " )");
	}
	
	public void setVisitsData() {
		String totalVisitsCount = dbVisitsCounter.getTotalVisits();
		String uniqueVisitsCount = dbVisitsCounter.getUniqueVisits();
		totalVisits.setValue("Всего посещений: " + totalVisitsCount);
		unicVisits.setValue("Уникальных посещений: " + uniqueVisitsCount);
		
	}


	/**
	 * 
	 */
	
}
