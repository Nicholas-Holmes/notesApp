package com.nicholas.app.frontEnd; 
import java.util.Optional;
import com.nicholas.app.HttpRequestUtility;
import java.net.URL;
import java.net.HttpURLConnection;
import java.io.OutputStream;
import com.google.gson.Gson;
import javax.swing.*;

import org.springframework.boot.autoconfigure.graphql.GraphQlProperties.Http;

import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.LayoutManager;
public class NotesPanelButtons extends JPanel{
    private NotesDialog dialogBox;
    private Gson gson = new Gson();
    private NotesListPanel notesList;
    private JPanel container;
    private NotesPanel parent;
    private LoginResponseDto response;
    private JTextArea textArea; 

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
       save.addActionListener(e -> updateNote(notesList.getNoteId(),notesList.getTitle(),textArea.getText()));
       logout.addActionListener(e -> logout());
       this.add(Box.createRigidArea(new Dimension(100,0)));
       this.add(create);
       this.add(Box.createRigidArea(new Dimension(50,0)));
       this.add(save);
       this.add(Box.createRigidArea(new Dimension(50,0)));
       this.add(delete);
       this.add(Box.createRigidArea(new Dimension(50,0)));
       this.add(logout);

    }

    private void updateNote(long id, String title,String text){
        NotesResponseDto requestBody = new NotesResponseDto(id,text,title,null);
        HttpRequestUtility.HttpPutRequest("http://localhost:9090/api/notes/updateNote",response.getToken(),requestBody);
        notesList.populateList(); 
    }

    private void deleteNote(long id){
        HttpRequestUtility.HttpDeleteRequest("http://localhost:9090/api/notes/deleteNote/"+id, 
                                            response.getToken());
        notesList.populateList();
        textArea.setText("");
        
    }
    private void logout(){
        CardLayout cl = (CardLayout) container.getLayout();
        cl.show(container,"LoginPanel");
        container.remove(parent);
        container.revalidate();
        container.repaint();
    }


}