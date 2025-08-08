package com.nicholas.app.frontEnd;
import java.awt.Dimension;
import javax.swing.*;

public class TextFieldPanel extends JPanel{
    private JTextField jtf; 
    public TextFieldPanel(String labelText){
        this.setLayout(new BoxLayout(this,BoxLayout.X_AXIS));
        this.setMaximumSize(new Dimension(250,100));
        this.add(Box.createHorizontalGlue());
        this.add(new JLabel(labelText));
        JTextField jtf = new JTextField();
        this.jtf = jtf; 
        jtf.setMinimumSize(new Dimension(350,25));
        this.add(jtf);
        this.add(Box.createHorizontalStrut(100));
        this.setVisible(true);    
    }
    public String getText(){
        return jtf.getText();
    }
}