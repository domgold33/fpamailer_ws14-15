/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.bht.fpa.mail.s791660.model.applicationLogic.xml;

import de.bht.fpa.mail.s791660.model.Email;
import de.bht.fpa.mail.s791660.model.Folder;
import de.bht.fpa.mail.s791660.model.applicationLogic.EmailManagerIF;
import java.io.File;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

/**
 *
 * @author Dominik
 */
public final class EmailManager implements EmailManagerIF {
    
    private final Folder folder;
    
    public EmailManager(Folder f){
        this.folder = f;
//        loadEmails(f);
    }

    @Override
    public Folder getFolder() {
        return folder;
    }
    
    @Override
    public void loadEmails(Folder f){
        File file = new File(f.getPath());
        for(File xmlFile : file.listFiles((File pathname) -> {
            int lastIndexOf = pathname.getName().lastIndexOf(".");
            if(lastIndexOf >= 0){
                String extension = pathname.getName().substring(lastIndexOf);
                return extension.equalsIgnoreCase(".xml");
            }
            return false;
        })){
            parseXmlFile(xmlFile, f);
        }
    }
    
    /***
     * Parses the given file into an email object and adds it to the email list of this EmailManager's
     * folder.
     * @param file An Xml file that will be parsed into an object of the Email class.
     */
    private void parseXmlFile(File file, Folder f){
        try{
            JAXBContext context = JAXBContext.newInstance(Email.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            Email email = (Email) unmarshaller.unmarshal(file);
            f.addEmail(email);
        }catch(JAXBException e){
            System.err.println(e.getMessage());
        }
    }
    
}
