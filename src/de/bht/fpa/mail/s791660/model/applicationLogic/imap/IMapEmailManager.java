/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.bht.fpa.mail.s791660.model.applicationLogic.imap;

import de.bht.fpa.mail.s791660.model.Account;
import de.bht.fpa.mail.s791660.model.Email;
import de.bht.fpa.mail.s791660.model.Folder;
import de.bht.fpa.mail.s791660.model.applicationLogic.EmailManagerIF;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Store;

/**
 *
 * @author Dominik
 */
public class IMapEmailManager implements EmailManagerIF{

    private Account account;
    private Store store;
    
    public IMapEmailManager(Account account){
        this.account = account;
        this.store = IMapConnectionHelper.connect(account);
    }
    
    @Override
    public Folder getFolder() {
        try{
            javax.mail.Folder topFolder = store.getDefaultFolder();  
            Folder top = new Folder();
            top.setName(topFolder.getName());
            top.setPath(topFolder.getFullName());
            return top;
        }catch(MessagingException e){
            System.err.println(e.getMessage());
        }
        return null;
    }

    @Override
    public void loadEmails(Folder f) {
        if(!f.getEmails().isEmpty()){
            return;
        }
        try{
            javax.mail.Folder folder = store.getFolder(f.getPath());
            if(!folder.isOpen()){
                folder.open(javax.mail.Folder.READ_ONLY);
            }
            Message[] messages = folder.getMessages();
            for(Message message : messages){
                Email mail = IMapEmailConverter.convertMessage(message);
                f.addEmail(mail);
            }
            folder.close(true);
        }catch(MessagingException e){
            System.err.println(e.getMessage());
        }
    }
    
}
