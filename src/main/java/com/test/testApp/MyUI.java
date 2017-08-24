package com.test.testApp;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.annotation.WebServlet;

import org.json.JSONException;

//import com.test.testApp.weatherDataGetter;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.shared.Position;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.ComboBox;

/**
 * This UI is the application entry point. A UI may either represent a browser window 
 * (or tab) or some part of a html page where a Vaadin application is embedded.
 * <p>
 * The UI is initialized using {@link #init(VaadinRequest)}. This method is intended to be 
 * overridden to add component to the user interface and initialize non-component functionality.
 */
@Theme("mytheme")
public class MyUI extends UI {
	/*
	private VerticalLayout weatherLayout = new VerticalLayout();
	private VerticalLayout currencyLayout = new VerticalLayout();
	private VerticalLayout visitsLyout = new VerticalLayout();
	private static HashMap<String, String> citiesMap = new HashMap<>();
	private Label ipAddrText = new Label();

	
	
	private static Label currentWeatherText = new Label("Now: ");    	
	private static Label tommorowWeatherText = new Label("Tommorow: ");
	
	private static Label usdText = new Label("$:");
	private static Label eurText = new Label("€:");

	private static String todayTemp = new String();
	private static String tommorowTemp = new String();
	*/
	

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        final VerticalLayout layout = new VerticalLayout();
        
        final TextField name = new TextField();
        name.setCaption("Type your name here:");

        Button button = new Button("Click Me");
        button.addClickListener( e -> {
            layout.addComponent(new Label("Thanks " + name.getValue() 
                    + ", it works!"));
        });
        
        UIConstructor ifaceWeb = new UIConstructor();
        //ifaceWeb.constructIface(vaadinRequest);
        setContent(ifaceWeb.constructIface(vaadinRequest));
        //setContent(name);
        
        
       // ipAddrText.setValue("Your IP: " + vaadinRequest.getRemoteAddr());
        
        
        //layout.setSizeFull();
        //layout.setSpacing(true);             
        
        
       // layout.addComponents(name, button);
                
        //layout.setComponentAlignment(name, Alignment.TOP_CENTER);
        //layout.setComponentAlignment(button, Alignment.TOP_CENTER);
        //layout.setComponentAlignment(button, Alignment.);
       
       //constructWeatherLayout();
       
       //setContent(weatherLayout);
        //constructUI();
        
    }
    /*
    void constructUI() {
    	HorizontalLayout mainLayout = new HorizontalLayout();
    	   	
    	constructWeatherLayout();
    	constructCurrencyLayout();
    	constructVisitsLayout();
    	mainLayout.addComponents(weatherLayout,currencyLayout,visitsLyout,ipAddrText);
    	setContent(mainLayout);
    	
    } */
    /*
    void constructWeatherLayout() {
    	/*
    	citiesMap.put(294459, "Novosibirsk");
    	citiesMap.put(294021, "Moscow");
    	citiesMap.put(295212, "S Petersburg");
    	*/
 /*   	citiesMap.put("Novosibirsk", "Novosibirsk");
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
    		weatherDataGetter.updateCurrentWeather(cityChoose.getValue());
    		
    		System.out.println("Updating weather for " + cityChoose.getValue());
    		
    		    		
    	});
    	   	
    	weatherLayout.addComponents(layoutTitle, cityChoose,currentWeatherText, tommorowWeatherText, updateBtn);  
    	updateBtn.click();
    }
    
    void constructCurrencyLayout() {
    	Label layoutTitle = new Label("Currency");
    	Button updateBtn = new Button("Update");
    	
    	updateBtn.addClickListener(clickEvent -> {
    		Notification notify = new Notification("Updating currency data");
    		notify.setDelayMsec(1000);
    		notify.setPosition(Position.BOTTOM_RIGHT);
    		notify.show(Page.getCurrent());
    		
    		weatherDataGetter.updateCurrencyData();
    		
    	});
    	
    	currencyLayout.addComponents(layoutTitle,usdText,eurText,updateBtn);
    	updateBtn.click();
    	
    }
    
    void constructVisitsLayout() {
    	Label layoutTitle = new Label("Visits");
    	Label unicVisits = new Label("Unic Visits:");
    	Label totalVisits = new Label("Total Visits:");
    	
    	visitsLyout.addComponents(layoutTitle,unicVisits,totalVisits);
    } 
    
    
	public static void setTodayTemp(String todayTemp, String tommorowTemp) {
		MyUI.todayTemp = todayTemp;
		currentWeatherText.setValue("Now: " + todayTemp + " °C");	
		tommorowWeatherText.setValue("Tommorow: " + tommorowTemp + " °C");
	}
	
	public static void setCurrencyData(String usdData, String usdDelta, String eurData, String eurDelta) {
		usdText.setValue("$: " + usdData + " ( " + usdDelta + " )");
		eurText.setValue("€: " + eurData + " ( " + eurDelta + " )");
	}
	
	
	public static String getCityKey(String city) {
		//int cityKey = 0;
		String cityKey = citiesMap.get(city);
		
		return cityKey;
	} */



	@WebServlet(urlPatterns = "/*", name = "MyTestApp", asyncSupported = true)
    @VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }
}
