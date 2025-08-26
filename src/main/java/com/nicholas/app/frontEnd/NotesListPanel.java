package com.nicholas.app.frontEnd;
import javax.swing.*;
import java.util.List;
import java.util.Optional;
import java.lang.Thread; 
import java.net.HttpURLConnection;
import java.net.URL; 
import java.awt.Dimension;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.stream.Collectors;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nicholas.app.HttpRequestUtility;

import java.lang.reflect.Type;

public class NotesListPanel extends JPanel{
    private JScrollPane pane;
    private JList<NotesResponseDto> list;
    private LoginResponseDto response;
    private Gson gson;
    private String title = "";
    private Long noteId = 0L;
    private JTextArea textArea;

    public NotesListPanel(LoginResponseDto response){
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.response = response;
        this.list = new JList<>(new NotesResponseDto[0]);
        list.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()){
                NotesResponseDto note = list.getSelectedValue();
                if (note != null){
                    this.noteId = note.getId();
                    this.title = note.getTitle();
                    textArea.setText(note.getText());
                }
            }
        });
        this.pane = new JScrollPane(list);
        pane.setMinimumSize(new Dimension(100,this.getHeight()));
        this.add(pane);
        this.gson = new Gson();
    }

    public void populateList(){
        new Thread(() -> {
            try{
                Type type = new TypeToken<NotesResponseDto>(){}.getType();
                Optional<NotesResponseDto> optResponseList = 
                    HttpRequestUtility.httpGetRequest("http://localhost:9090/api/notes/getNotes",type,response.getAccessToken());
                if (!optResponseList.isEmpty() ){
                    NotesResponseDto responseList = optResponseList.get();
                    SwingUtilities.invokeLater(() -> list.setListData(responseList.getNotesList().toArray(new NotesResponseDto[0])));
                } else {
                    System.out.println("error occured");
                } 
                
            } catch (Exception e){
                e.printStackTrace();
            }
        }).start();
    }
    public long getNoteId(){
        return this.noteId;
    }
    public String getTitle(){
        return this.title;
    }
    public void setTextArea(JTextArea jta){
        this.textArea = jta;
    }
}
