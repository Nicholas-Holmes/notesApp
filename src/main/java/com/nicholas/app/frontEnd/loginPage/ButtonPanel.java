package com.nicholas.app.frontEnd.loginPage;
import java.awt.Dimension;
import java.util.Optional;
import javax.swing.*;
import java.awt.CardLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent; 
import com.google.gson.Gson;
import com.nicholas.app.HttpRequestUtility;
import com.nicholas.app.dtoObjects.LoginResponseDto;
import com.nicholas.app.dtoObjects.RegisterDto;
import com.nicholas.app.dtoObjects.RegisterResponseDto;
import com.nicholas.app.dtoObjects.UserDto;
import com.nicholas.app.frontEnd.notesPage.NotesPanel;

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
            RegisterDto registerInfo = new RegisterDto(username,password);
            Optional<RegisterResponseDto> optRegisterResponse = HttpRequestUtility.httpPostRequest("http://localhost:9090/api/users/register",registerInfo,RegisterResponseDto.class);
            if(!optRegisterResponse.isEmpty()){
                RegisterResponseDto registerResponse = optRegisterResponse.get();
                if(registerResponse.getErrorMessage().equals(null)){
                    errorLabel.setText(registerResponse.getSuccessfulResponse());
                    errorLabel.setVisible(true);
                }else{
                    errorLabel.setText(registerResponse.getErrorMessage());
                    errorLabel.setVisible(true);
                }
            }else{
               System.out.println("Unkown error occured"); 
            }
            
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    public void login(){
        UserDto requestBody = new UserDto(username.getText(),new String(password.getText()));
        Optional<LoginResponseDto> optResponse = HttpRequestUtility.httpPostRequest("http://localhost:9090/api/users/login",requestBody,LoginResponseDto.class);
        if (!optResponse.isEmpty()){
            LoginResponseDto response = optResponse.get();
            if (response.getErrorMessage() == null){
                System.out.println("Value was '' ");
                parent.add(new NotesPanel(response,parent),"NotesPanel");
                cl.show(parent,"NotesPanel");
                this.password.setText("");
            } else {
                errorLabel.setText(response.getErrorMessage());
                errorLabel.setVisible(true);
            }
        } else {
            System.out.println("error occured will add functionality to get error message on the frontend in due time");
        }
    }
}