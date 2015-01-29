/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.bht.fpa.mail.s791660.controller;

import de.bht.fpa.mail.s791660.model.Account;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 *
 * @author Dominik
 */
public class FXMLEditAccountController implements Initializable{
    
    private FXMLDocumentController mainController;
    private Account account;
    
    @FXML
    private TextField nameTextfield;
    @FXML
    private TextField hostTextfield;
    @FXML
    private TextField usernameTextfield;
    @FXML
    private TextField passwordTextfield;
    @FXML
    private Label errorMessage;
    @FXML
    private Button saveButton;
    @FXML
    private Button cancelButton;
    
    public FXMLEditAccountController(FXMLDocumentController mainController, String accName){
        this.mainController = mainController;
        this.account = mainController.getApplicationLogic().getAccount(accName);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {       
        nameTextfield.setText(account.getName());
        nameTextfield.setEditable(false);
        hostTextfield.setText(account.getHost());
        usernameTextfield.setText(account.getUsername());
        passwordTextfield.setText(account.getPassword());
        saveButton.setOnAction((event) ->{
            if(!nameTextfield.getText().isEmpty() && !hostTextfield.getText().isEmpty() && 
                !usernameTextfield.getText().isEmpty() && !passwordTextfield.getText().isEmpty()){
                account.setHost(this.hostTextfield.getText());
                account.setUsername(this.usernameTextfield.getText());
                account.setPassword(this.passwordTextfield.getText());
                mainController.getApplicationLogic().updateAccount(account);
                this.mainController.loadAccounts();
                Stage stage = (Stage) this.cancelButton.getScene().getWindow();
                stage.close();
            }else{
                errorMessage.setText("All fields must contain data.");
            }
        });
        cancelButton.setOnAction((event) ->{
            Stage stage = (Stage) this.cancelButton.getScene().getWindow();
            stage.close();
        });
    }
    
}
