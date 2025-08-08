package com.nicholas.app.frontEnd;

import javax.swing.*;

public class NotesEditingPanel extends JPanel{
    private JTextArea textArea;

    public NotesEditingPanel(NotesListPanel notesList,JPanel container,NotesPanel parent){
       this.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
       this.textArea = new JTextArea(); 
       this.textArea.setLineWrap(true);
       this.textArea.setWrapStyleWord(true);
       notesList.setTextArea(this.textArea);
       this.add(new NotesPanelButtons(notesList, textArea,container,parent));
       this.add(Box.createVerticalStrut(5));
       this.add(new JScrollPane(textArea));
    }
}