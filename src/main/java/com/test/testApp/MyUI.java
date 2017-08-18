package com.test.testApp;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.annotation.WebServlet;

import org.json.JSONException;

import com.test.testApp.weatherDataGetter;

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
	private VerticalLayout weatherLayout = new VerticalLayout();
	private VerticalLayout currencyLayout = new VerticalLayout();
	private VerticalLayout visitsLyout = new VerticalLayout();
	private static HashMap<String, Integer> citiesMap = new HashMap<>();
	
	
	private static Label currentWeatherText = new Label("Now: ");    	
	private static Label tommorowWeatherText = new Label("Tommorow: ");
	
	
	
	
	private static String todayTemp = new String();
	private static String tommorowTemp = new String();
	

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

        
        //layout.setSizeFull();
        //layout.setSpacing(true);             
        
        
        layout.addComponents(name, button);
                
        layout.setComponentAlignment(name, Alignment.TOP_CENTER);
        layout.setComponentAlignment(button, Alignment.TOP_CENTER);
        //layout.setComponentAlignment(button, Alignment.);
       
       //constructWeatherLayout();
       
       //setContent(weatherLayout);
        constructUI();
        
    }
    
    void constructUI() {
    	HorizontalLayout mainLayout = new HorizontalLayout();
    	constructWeatherLayout();
    	constructCurrencyLayout();
    	constructVisitsLayout();
    	mainLayout.addComponents(weatherLayout,currencyLayout,visitsLyout);
    	setContent(mainLayout);
    	
    }
    
    void constructWeatherLayout() {
    	/*
    	citiesMap.put(294459, "Novosibirsk");
    	citiesMap.put(294021, "Moscow");
    	citiesMap.put(295212, "S Petersburg");
    	*/
    	citiesMap.put("Novosibirsk", 294459);
    	citiesMap.put("Moscow", 294021);
    	citiesMap.put("S Petersburg", 295212);

    	
    	ComboBox<String> cityChoose = new ComboBox<>("Choose city");    	
    	cityChoose.setItems(citiesMap.keySet());
    	//cityChoose.setItemCaptionGenerator(cap -> citiesMap.values());
    	//cityChoose.setReadOnly(true);
    	cityChoose.setTextInputAllowed(true);
    	cityChoose.setValue("Novosibirsk");
    	cityChoose.setEmptySelectionAllowed(false);
    	
    	Label layoutTitle = new Label("Weather");
    	Button updateBtn = new Button("Update");
    	
    	updateBtn.addClickListener(clickEvent -> {
    		Notification notify = new Notification("Updating weather for " + cityChoose.getValue());
    		    		
    		//weatherDataGetter.doRequest();
    		weatherDataGetter.updateCurrentWeather(cityChoose.getValue());
    		
    		notify.setDelayMsec(1000);
    		notify.setPosition(Position.BOTTOM_RIGHT);
    		notify.show(Page.getCurrent());
    		cityChoose.getSelectedItem();
    		
    		System.out.println("Updating weather for " + cityChoose.getValue());
    		
    		    		
    	});
    	   	
    	weatherLayout.addComponents(layoutTitle, cityChoose,currentWeatherText, tommorowWeatherText, updateBtn);    	
    }
    
    void constructCurrencyLayout() {
    	Label layoutTitle = new Label("Currency");
    	Label usdText = new Label("USD:");
    	Label eurText = new Label("EUR:");
    	Button updateBtn = new Button("Update");
    	
    	currencyLayout.addComponents(layoutTitle,usdText,eurText,updateBtn);
    	
    }
    
    void constructVisitsLayout() {
    	Label layoutTitle = new Label("Visits");
    	Label unicVisits = new Label("Unic Visits:");
    	Label totalVisits = new Label("Total Visits:");
    	
    	visitsLyout.addComponents(layoutTitle,unicVisits,totalVisits);
    }
    
    

	public static void setTodayTemp(String todayTemp) {
		MyUI.todayTemp = todayTemp;
		currentWeatherText.setValue("Now: " + todayTemp + " °C");
		
	}
	
	public static int getCityKey(String city) {
		int cityKey = 0;
		cityKey = citiesMap.get(city);
		
		return cityKey;
	}



	@WebServlet(urlPatterns = "/*", name = "MyTestApp", asyncSupported = true)
    @VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }
}
