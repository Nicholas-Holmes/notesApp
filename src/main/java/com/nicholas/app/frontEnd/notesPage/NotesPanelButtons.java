package com.nicholas.app.frontEnd.notesPage; 
import java.util.Optional;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.nicholas.app.HttpRequestUtility;
import com.nicholas.app.dtoObjects.LoginResponseDto;
import com.nicholas.app.dtoObjects.TokenDto;
import com.nicholas.app.dtoObjects.UpdateTextDto;
import com.nicholas.app.dtoObjects.UpdateTextResponseDto;
import com.google.gson.Gson;
import javax.swing.*;
import java.awt.CardLayout;
import java.awt.Dimension;
public class NotesPanelButtons extends JPanel{
    private NotesDialog dialogBox;
    private Gson gson = new Gson();
    private NotesListPanel notesList;
    private JPanel container;
    private NotesPanel parent;
    private LoginResponseDto response;
    private JTextArea textArea; 
    private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

    public NotesPanelButtons(LoginResponseDto response,NotesListPanel notesList, JTextArea textArea,JPanel container,NotesPanel parent){
       this.setLayout(new BoxLayout(this,BoxLayout.X_AXIS));
       this.dialogBox = new NotesDialog(this,notesList,response );
       this.notesList = notesList;
       this.container = container;
       this.parent = parent;
       this.response = response; 
       this.textArea = textArea; 
       JButton create = new JButton("Create");
       JButton save = new JButton("Save");
       JButton delete = new JButton("Delete");
       JButton logout = new JButton("Logout");
       create.setMaximumSize(new Dimension(150,50));
       save.setMaximumSize(new Dimension(150,50));
       delete.setMaximumSize(new Dimension(150,50));
       logout.setMaximumSize(new Dimension(150,50));
       create.addActionListener(e -> {
        SwingUtilities.invokeLater(() -> dialogBox.setVisible(true));
       });
       delete.addActionListener(e -> deleteNote(notesList.getNoteId()));
       save.addActionListener(e -> updateNote(notesList.getNoteId(),textArea.getText()));
       logout.addActionListener(e -> logout());
       this.add(Box.createRigidArea(new Dimension(100,0)));
       this.add(create);
       this.add(Box.createRigidArea(new Dimension(50,0)));
       this.add(save);
       this.add(Box.createRigidArea(new Dimension(50,0)));
       this.add(delete);
       this.add(Box.createRigidArea(new Dimension(50,0)));
       this.add(logout);
       refreshTokens();

    }

    private void updateNote(long id,String text){
        UpdateTextDto noteText  = new UpdateTextDto(text);
        Optional<UpdateTextResponseDto> optResponseDto = HttpRequestUtility.HttpPutRequest("http://localhost:9090/api/notes/updateNote/"+id,response.getAccessToken(),noteText,UpdateTextResponseDto.class);
        if (!optResponseDto.isEmpty()){
            UpdateTextResponseDto responseDto = optResponseDto.get();
            if (responseDto.getErrorMessage() == null){
                System.out.println(responseDto.getResponseText());
                notesList.populateList(); 
            } else {
                System.out.println(responseDto.getErrorMessage());
            }
        }
    }

    private void deleteNote(long id){
        HttpRequestUtility.HttpDeleteRequest("http://localhost:9090/api/notes/deleteNote/"+id, 
                                            response.getAccessToken());
        notesList.populateList();
        textArea.setText("");
        
    }

    private void logout(){
        scheduler.shutdown();
        CardLayout cl = (CardLayout) container.getLayout();
        cl.show(container,"LoginPanel");
        container.remove(parent);
        container.revalidate();
        container.repaint();
    }

    private void refreshTokens(){
        Runnable refresh = (() -> {
            Optional<TokenDto> optTokens = HttpRequestUtility.httpPostRequest("http://localhost:9090/api/users/refreshTokens",
            TokenDto.class, response.getRefreshToken());
            if(!optTokens.isEmpty()){
                TokenDto tokens = optTokens.get();
                response.setAccessToken(tokens.getAccessToken());
                response.setRefreshToken(tokens.getRefreshToken());
                System.out.println("well that should have been a successful request.");
            } else {
                System.out.println("welp something went wrong somewhere.");
            }
        });
        scheduler.scheduleAtFixedRate(refresh,8,8,TimeUnit.MINUTES);
    }
}