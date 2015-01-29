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
    
    public IMapEmailManager(Account account){
        this.account = account;
    }
    
    @Override
    public Folder getFolder() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void loadEmails(Folder f) {
        if(!f.getEmails().isEmpty()){
            return;
        }
        Store store = IMapConnectionHelper.connect(account);
        try{
            Message[] messages = store.getFolder(f.getName()).getMessages();
            for(Message message : messages){
                Email mail = IMapEmailConverter.convertMessage(message);
                f.addEmail(mail);
            }
        }catch(MessagingException e){
            System.err.println(e.getMessage());
        }
    }
    
}
