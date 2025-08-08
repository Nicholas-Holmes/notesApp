package com.nicholas.app.frontEnd;
import java.awt.Dimension;
import java.io.OutputStream;
import java.io.InputStream;
import java.util.stream.Collectors;
import java.util.Map;
import java.lang.reflect.Type;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.net.HttpURLConnection;
import java.net.URL; 
import javax.swing.*;
import java.awt.CardLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent; 
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class ButtonPanel extends JPanel{
    private TextFieldPanel username;
    private PasswordFieldPanel password; 
    private CardLayout cl; 
    private Gson gson;
    private JPanel parent; 
    private JLabel errorLabel;
    public ButtonPanel(TextFieldPanel username, PasswordFieldPanel password, CardLayout cl, JPanel parent,JLabel jl){
        this.gson = new Gson();
        this.username = username;
        this.password = password; 
        this.cl = cl;
        this.parent = parent; 
        this.errorLabel = jl;
        this.setLayout(new BoxLayout(this,BoxLayout.X_AXIS));
        this.setMinimumSize(new Dimension(250,150));
        this.add(Box.createHorizontalGlue());
        JButton register = new JButton("Register");
        JButton login = new JButton("Login");
        register.setMinimumSize(new Dimension(150,300));
        login.setMinimumSize(new Dimension(150,300));
        this.add(register);
        this.add(Box.createHorizontalStrut(10));
        this.add(login);
        this.add(Box.createHorizontalStrut(10));
        this.add(Box.createHorizontalGlue());

        register.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                registerUser();

            } 
        });

        login.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                login();
            }
        });
    }
    private void registerUser(){
        try {
            String username = this.username.getText();
            String password = new String(this.password.getText());
            
            String jsonInputString = String.format("{\"username\":\"%s\",\"password\":\"%s\"}", username, password);
            URL url = new URL("http://localhost:9090/api/users/register");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type","application/json; utf-8");
            con.setDoOutput(true);
            try(OutputStream os = con.getOutputStream()){
                os.write(jsonInputString.getBytes());
            }
            int responseCode = con.getResponseCode();
            InputStream is = responseCode == 200 ? con.getInputStream():con.getErrorStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String responseBody = reader.lines().collect(Collectors.joining("\n"));
            reader.close();
            if (responseCode == 200){
                System.out.println("Successful");
                System.out.println(responseBody);
            } else {
                Type type = new TypeToken<Map<String,String>>(){}.getType();
                Map<String,String> errorMap = gson.fromJson(responseBody,type);
                errorLabel.setText(errorMap.get("error"));
                errorLabel.setVisible(true);
            } 
            con.disconnect();
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    public void login(){
        try{ 
            String username = this.username.getText();
            String password = new String(this.password.getText());
            String jsonInputString = String.format("{\"username\":\"%s\",\"password\":\"%s\"}", username, password);
            URL url = new URL("http://localhost:9090/api/users/login");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type","application/json; utf-8");
            con.setDoOutput(true);
            try(OutputStream os = con.getOutputStream()){
                os.write(jsonInputString.getBytes());
            }
            int responseCode = con.getResponseCode();
            InputStream is = responseCode == 200 ? con.getInputStream():con.getErrorStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String responseBody = reader.lines().collect(Collectors.joining("\n"));
            reader.close();
            if (responseCode == 200){
                LoginResponseDto loginResponse = gson.fromJson(responseBody,LoginResponseDto.class);
                System.out.println("Logged in");
                System.out.println("Id: " + loginResponse.getId() + " " + loginResponse.getUsername());
                SwingUtilities.invokeLater(() -> {
                    parent.add(new NotesPanel(loginResponse.getId(),parent),"NotesPanel");
                    cl.show(parent,"NotesPanel");
                    this.password.setText("");
                });
            } else {
                Type type = new TypeToken<Map<String,String>>(){}.getType();
                Map<String,String> errorMap = gson.fromJson(responseBody,type);
                errorLabel.setText(errorMap.get("error"));
                errorLabel.setVisible(true);
            }
            con.disconnect();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}