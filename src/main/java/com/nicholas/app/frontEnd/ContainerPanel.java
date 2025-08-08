package com.nicholas.app.frontEnd;
import javax.swing.*;
import java.awt.*;

public class ContainerPanel extends JPanel{
    private LayoutManager layout = new CardLayout();
    private CardLayout cl = (CardLayout)layout;

    public ContainerPanel(){
       //this.setBackground(Color.RED);
       this.setLayout(layout);
       this.add(new LoginPanel(cl,this), "LoginPanel");
       this.cl.show(this,"LoginPanel");
       this.setVisible(true);

    }      
}