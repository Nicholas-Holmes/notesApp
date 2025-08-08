package com.nicholas.app.frontEnd; 
import javax.swing.*; 

public class NotesPanel extends JPanel {
    private NotesListPanel listPanel;
    public NotesPanel(Long Id,JPanel parent){
        this.add(Box.createHorizontalStrut(5));
        this.setLayout(new BoxLayout(this,BoxLayout.X_AXIS));
        this.listPanel = new NotesListPanel(Id);
        this.add(listPanel);
        listPanel.populateList(); 
        this.add(Box.createHorizontalStrut(5));
        this.add(new NotesEditingPanel(listPanel,parent,this));
        this.add(Box.createHorizontalStrut(2));
    }
}