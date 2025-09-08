package com.nicholas.app.frontEnd.notesPage; 
import javax.swing.*;
import java.awt.*;
import java.lang.Thread;
import java.net.HttpURLConnection; 
import java.net.URL;
import java.util.Optional;
import java.io.OutputStream; 
import com.google.gson.Gson;
import com.nicholas.app.HttpRequestUtility;
import com.nicholas.app.dtoObjects.LoginResponseDto;
import com.nicholas.app.dtoObjects.NotesResponseDto;
import com.nicholas.app.dtoObjects.TextResponseDto;
public class NotesDialog extends JDialog{
    private JTextField title;
    private JButton ok;
    private Gson gson;
    private LoginResponseDto response;
    public NotesDialog(JPanel parent, NotesListPanel notesList, LoginResponseDto response){
        this.gson = new Gson();
        this.setLayout(new BorderLayout());
        this.title = new JTextField(15);
        this.ok = new JButton("Ok");
        this.response = response;
        JPanel inputPanel = new JPanel(new GridLayout(2,2,5,5));
        inputPanel.add(new JLabel("Title:"));
        inputPanel.add(title);
        ok.addActionListener(e -> {
            String title = this.title.getText();
            createNote(response.getId(),title,notesList);
            this.title.setText("");
            dispose();

        });
        this.add(inputPanel,BorderLayout.CENTER);
        this.add(ok,BorderLayout.SOUTH);
        this.pack();
        this.setLocationRelativeTo(parent);
    }
    public void createNote(long id,String title,NotesListPanel notesList){
        new Thread(() ->{

            NotesResponseDto note = new NotesResponseDto(id,"",title,null);
            Optional<TextResponseDto> optTextResponse = HttpRequestUtility.httpPostRequest("http://localhost:9090/api/notes/createNote",note,
            TextResponseDto.class,response.getAccessToken());
            if(!optTextResponse.isEmpty()){
                TextResponseDto textResponse = optTextResponse.get();
                System.out.println(textResponse.getTextResponse());
                notesList.populateList();
            }
        }).start();
    }
}