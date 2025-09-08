package com.nicholas.app.frontEnd.notesPage;

import javax.swing.*;

import com.nicholas.app.dtoObjects.LoginResponseDto;

public class NotesEditingPanel extends JPanel{
    private JTextArea textArea;

    public NotesEditingPanel(NotesListPanel notesList,JPanel container,NotesPanel parent, LoginResponseDto response){
       this.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
       this.textArea = new JTextArea(); 
       this.textArea.setLineWrap(true);
       this.textArea.setWrapStyleWord(true);
       notesList.setTextArea(this.textArea);
       this.add(new NotesPanelButtons(response,notesList, textArea,container,parent));
       this.add(Box.createVerticalStrut(5));
       this.add(new JScrollPane(textArea));
    }
}