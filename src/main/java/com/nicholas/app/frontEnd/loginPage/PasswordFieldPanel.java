package com.nicholas.app.frontEnd.loginPage;
import java.awt.Dimension;

import javax.swing.*;

public class PasswordFieldPanel extends JPanel{
    private JPasswordField jpf;
    public PasswordFieldPanel(String labelText){
        this.setLayout(new BoxLayout(this,BoxLayout.X_AXIS));
        this.setMaximumSize(new Dimension(250,100));
        this.add(Box.createHorizontalGlue());
        this.add(new JLabel(labelText));
        JPasswordField jpf = new JPasswordField();
        this.jpf = jpf;
        jpf.setMinimumSize(new Dimension(350,25));
        jpf.setEchoChar('*');
        this.add(jpf);
        this.add(Box.createHorizontalStrut(100));
        this.setVisible(true); 
    }
    public char[] getText(){
        return jpf.getPassword();
    }
    public void setText(String text){
        jpf.setText(text);
    }
    
}