package com.nicholas.app.frontEnd; 
import javax.swing.*;
import java.awt.*;
import java.lang.Thread;
import java.net.HttpURLConnection; 
import java.net.URL;
import java.io.OutputStream; 
import com.google.gson.Gson;
public class NotesDialog extends JDialog{
    private JTextField title;
    private JButton ok;
    private Gson gson;
    public NotesDialog(JPanel parent, NotesListPanel notesList){
        this.gson = new Gson();
        this.setLayout(new BorderLayout());
        this.title = new JTextField(15);
        this.ok = new JButton("Ok");
        JPanel inputPanel = new JPanel(new GridLayout(2,2,5,5));
        inputPanel.add(new JLabel("Title:"));
        inputPanel.add(title);
        ok.addActionListener(e -> {
            String title = this.title.getText();
            createNote(notesList.getUserId(),title,notesList);
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
            try{
                URL url = new URL("http://localhost:9090/api/notes/createNote");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection(); 
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type","application/json; utf-8");
                conn.setDoOutput(true);
                NotesResponseDto note = new NotesResponseDto(id,"",title);
                String json = gson.toJson(note);
                try(OutputStream os = conn.getOutputStream()){
                    os.write(json.getBytes());
                }
                int responseCode = conn.getResponseCode();
                if (responseCode == 200){
                    System.out.println("Success");
                } else {
                    System.out.println("error occured idk");
                }
                conn.disconnect();
                notesList.populateList();
            } catch(Exception e){
                System.out.println(e.getStackTrace());
            }
        }).start();
    }
}