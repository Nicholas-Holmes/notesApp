package com.nicholas.app.frontEnd;
import java.awt.BorderLayout;
import java.awt.Dimension;


import javax.swing.*;

public class UserInterface extends JFrame{
    private ContainerPanel cp = new ContainerPanel();
    public UserInterface(){
        this.setTitle("userinterface");
        this.setLocationRelativeTo(null);
        this.setMinimumSize(new Dimension(700,700));
        this.setResizable(true);
        this.add(cp, BorderLayout.CENTER);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
    }

    public void showUI(){
        SwingUtilities.invokeLater(() -> this.setVisible(true));
    }



}