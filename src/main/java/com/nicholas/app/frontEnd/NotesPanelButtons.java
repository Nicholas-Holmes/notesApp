package com.nicholas.app.frontEnd; 
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
    public NotesPanelButtons(NotesListPanel notesList, JTextArea textArea,JPanel container,NotesPanel parent){
       this.setLayout(new BoxLayout(this,BoxLayout.X_AXIS));
       this.dialogBox = new NotesDialog(this,notesList);
       this.notesList = notesList;
       this.container = container;
       this.parent = parent;
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
        try{
            URL url = new URL("http://localhost:9090/api/notes/updateNote");
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            conn.setRequestMethod("PUT");
            conn.setRequestProperty("Content-Type","application/json; utf-8");
            conn.setDoOutput(true);
            NotesResponseDto note = new NotesResponseDto(id,text,title);
            String json = gson.toJson(note);
            try(OutputStream os = conn.getOutputStream()){
                os.write(json.getBytes());
            }

            int responseCode = conn.getResponseCode();
            if (responseCode == 200){
                System.out.println("Success");
                notesList.populateList();
                
                
            } else {
                System.out.println("something went wrong");
            }
            conn.disconnect();

        } catch(Exception e){
            e.printStackTrace();
        }
    }
    private void deleteNote(long id){
        try{
            URL url = new URL("http://localhost:9090/api/notes/deleteNote/"+id);
            var conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("DELETE");
            int responseCode = conn.getResponseCode();
            if (responseCode != 204){
                System.out.println("something went wrong");
            } else {
                notesList.populateList();
            }
            conn.disconnect();

        } catch (Exception e){
            e.printStackTrace();
        }
    }
    private void logout(){
        CardLayout cl = (CardLayout) container.getLayout();
        cl.show(container,"LoginPanel");
        container.remove(parent);
        container.revalidate();
        container.repaint();
    }


}