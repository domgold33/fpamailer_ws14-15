/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.bht.fpa.mail.s791660.model.applicationLogic;

import de.bht.fpa.mail.s791660.controller.FXMLDocumentController;
import de.bht.fpa.mail.s791660.model.Email;
import de.bht.fpa.mail.s791660.model.Folder;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

/**
 *
 * @author Dominik
 */
public class ApplicationLogic implements ApplicationLogicIF{

    private Folder topFolder;
    private FolderManagerIF fileManager;
    private EmailManager mailManager;
    private final FXMLDocumentController mainController;
    
    public ApplicationLogic(Folder f, FXMLDocumentController c){
        this.topFolder = f;
        this.fileManager = new FileManager(new File(f.getPath()));
        this.mailManager = new EmailManager(f);
        this.mainController = c;
    }
    
    @Override
    public Folder getTopFolder() {
        return this.topFolder;
    }

    @Override
    public void loadContent(Folder folder) {
        this.fileManager.loadContent(folder);
    }
    
    /**
     * Searches through the content of the emails in the currently selected directory by the given pattern,
     * which is the current text in the search bar above the table view.
     * @param pattern The pattern that is searched for.
     */
    @Override
    public List<Email> search(String pattern) {
        Folder f = (Folder) mainController.getTreeView().getSelectionModel().getSelectedItem().getValue();
        List<Email> list = new ArrayList<>();
        for(Email mail: f.getEmails()){
            String subject = mail.getSubject();
            String text = mail.getText();
            String received = mail.getReceived();
            String sent = mail.getSent();
            String receiver = mail.getReceiver();
            String sender = mail.getSender();
            String[] strings = {subject, text, received, sent, receiver, sender};
            for(String string : strings){
                if(search(pattern, string)){
                    list.add(mail);
                    break;
                }
            }
        }
        return list;
    }
    
    /**
     * Called in the public search() method, searches through a single part of content of an email.
     * @param pattern The pattern that is searched for.
     * @param searchText The content in which the search will be done.
     * @return True if the pattern has been found in the given content.
     */
    private boolean search(String pattern, String searchText){
        if(pattern.length() > searchText.length()){
            return false;
        }else if(pattern.length() == searchText.length() && !pattern.equalsIgnoreCase(searchText)){
            return false;
        }else{
            boolean found = false;
            for(int i = 0; i < searchText.length() - pattern.length(); i++){
                String substring = searchText.substring(i, i + pattern.length());
                found = substring.equalsIgnoreCase(pattern);
                if(found){
                    break;
                }
            }
            return found;
        }
    }

    @Override
    public void loadEmails(Folder folder) {
        this.mailManager.loadEmails(folder);
    }

    @Override
    public void changeDirectory(File file) {
        Folder newFolder = new Folder(file, true);
        this.topFolder = newFolder;
        this.fileManager = new FileManager(file);
        this.mailManager = new EmailManager(newFolder);
        this.mainController.initializeNewTree(newFolder);
    }

    @Override
    public void saveEmails(File file) {
        try{          
            JAXBContext context = JAXBContext.newInstance(Email.class);
            Folder selectedFolder = (Folder) mainController.getTreeView().getSelectionModel().getSelectedItem().getValue();
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            for(int i = 0; i < selectedFolder.getEmails().size(); i++){
                if(i < 9){
                    File newMail = new File(file.getAbsolutePath() + "/mail00" + (i+1) + ".xml");
                    marshaller.marshal(selectedFolder.getEmails().get(i), newMail);
                }else if(i < 99){
                    File newMail = new File(file.getAbsolutePath() + "/mail0" + (i+1) + ".xml");
                    marshaller.marshal(selectedFolder.getEmails().get(i), newMail);
                }else{
                    File newMail = new File(file.getAbsolutePath() + "/mail" + (i+1) + ".xml");
                    marshaller.marshal(selectedFolder.getEmails().get(i), newMail);
                }
            }
        }catch(JAXBException e){
            System.err.println(e.getMessage());
        }
    }
    
}
