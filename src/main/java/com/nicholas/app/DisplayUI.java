package com.nicholas.app;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import com.nicholas.app.frontEnd.loginPage.UserInterface; 

@Component 
public class DisplayUI implements ApplicationListener<ApplicationReadyEvent>{

    @Override 
    public void onApplicationEvent(ApplicationReadyEvent event){
        System.setProperty("java.awt.headless","false");
        UserInterface ui = new UserInterface();
        ui.showUI();
    }

}