/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.bht.fpa.mail.s791660.controller;

import de.bht.fpa.mail.s791660.model.Folder;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

/**
 *
 * @author Dominik
 */
public class FXMLPathHistoryController implements Initializable, EventHandler<ActionEvent> {
    
    private final FXMLDocumentController mainController;
    private Stage thisStage;
    
    @FXML
    private Button OKButton;
    @FXML
    private Button cancelButton;
    @FXML
    private ListView<String> listView;
    
    public FXMLPathHistoryController(FXMLDocumentController mainController){
        this.mainController = mainController;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.OKButton.setOnAction(this);
        this.cancelButton.setOnAction(this);
        if(mainController.getHistoryList().contains(mainController.PATHHISTORY_DEFAULT_MESSAGE)){
            this.OKButton.setDisable(true);
        }
        this.listView.setItems(FXCollections.observableArrayList(mainController.getHistoryList()));
    }

    /***
     * Handles all ActionEvents that occur in the path history window.
     * @param event The ActionEvent that has occured.
     */
    @Override
    public void handle(ActionEvent event) {
        if(event.getSource() == this.OKButton){
            if(this.listView.getSelectionModel().isEmpty()){
                thisStage = (Stage) OKButton.getScene().getWindow();
                thisStage.close();
            }else{
                thisStage = (Stage) OKButton.getScene().getWindow();
                String newPath = this.listView.getSelectionModel().getSelectedItem();
                File file = new File(newPath);
                mainController.getApplicationLogic().changeDirectory(file);
                thisStage.close();
            }
        }
        if(event.getSource() == this.cancelButton){
            thisStage = (Stage) OKButton.getScene().getWindow();
            thisStage.close();
        }
    }
    
}
