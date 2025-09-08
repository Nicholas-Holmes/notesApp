package com.nicholas.app.frontEnd.loginPage;
import javax.swing.*;

import java.awt.*;

public class LoginPanel extends JPanel{
    private CardLayout cl;
    private JPanel parent;
    public LoginPanel(CardLayout cl, JPanel parent){
        PasswordFieldPanel password = new PasswordFieldPanel("password");
        TextFieldPanel username = new TextFieldPanel("username");
        JLabel errorLabel = new JLabel();
        errorLabel.setVisible(false);
        this.parent = parent; 
        this.cl = cl;
        this.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
        this.add(Box.createRigidArea(new Dimension(this.getWidth(),150)));
        this.add(Box.createVerticalGlue());
        this.add(errorLabel);
        this.add(username);
        this.add(Box.createVerticalStrut(50));
        this.add(password);
        this.add(Box.createRigidArea(new Dimension(this.getWidth(),50)));
        this.add(Box.createVerticalGlue());
        this.add(new ButtonPanel(username, password,cl,parent,errorLabel));
        this.add(Box.createVerticalStrut((10)));
        this.add(Box.createVerticalGlue());
        this.setVisible(true);
    }

}