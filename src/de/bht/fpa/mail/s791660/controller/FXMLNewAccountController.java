/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.bht.fpa.mail.s791660.controller;

import de.bht.fpa.mail.s791660.model.Account;
import de.bht.fpa.mail.s791660.model.applicationLogic.ApplicationLogicIF;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Dominik
 */
public class FXMLNewAccountController implements Initializable {

    private ApplicationLogicIF appLogic;
    
    @FXML
    private TextField accountName;
    @FXML
    private TextField accountHost;
    @FXML
    private TextField accountUsername;
    @FXML
    private TextField accountPassword;
    @FXML
    private Label errorMessage;
    @FXML
    private Button saveButton;
    @FXML
    private Button cancelButton;
    
    public FXMLNewAccountController(ApplicationLogicIF appLogic){
        this.appLogic = appLogic;
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        saveButton.setOnAction((event) ->{
            if(!accountName.getText().isEmpty() && !accountHost.getText().isEmpty() && 
                !accountUsername.getText().isEmpty() && !accountPassword.getText().isEmpty()){
                Account account = new Account(accountName.getText(), accountHost.getText(),
                                                accountUsername.getText(), accountPassword.getText());
                appLogic.saveAccount(account);
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
