/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.bht.fpa.mail.s791660.model.applicationLogic.imap;

import de.bht.fpa.mail.s791660.model.Account;
import de.bht.fpa.mail.s791660.model.Folder;
import de.bht.fpa.mail.s791660.model.applicationLogic.FolderManagerIF;
import javax.mail.MessagingException;
import javax.mail.Store;

/**
 *
 * @author Dominik
 */
public class IMapFolderManager implements FolderManagerIF{
    
    private Account account;
    private Store store;
    
    public IMapFolderManager(Account account){
        this.account = account;
        this.store = IMapConnectionHelper.connect(account);
    }

    @Override
    public Folder getTopFolder() {
        if(store != null){
            try{
                javax.mail.Folder topFolder = store.getDefaultFolder();  
                Folder top = new Folder();
                System.out.println(account.getName());
                top.setName(account.getName());
                top.setPath(topFolder.getFullName());
                return top;
            }catch(MessagingException e){
                System.err.println(e.getMessage());
            }
        }
        System.err.println("Es konnte keine Verbindung mit dem Mailserver des ausgewählten Accounts hergestellt werden.");
        return null;
    }

    @Override
    public void loadContent(Folder f) {
        System.out.println(f.getName());
        if(!f.getComponents().isEmpty()){
            return;
        }
        try{
            for(javax.mail.Folder folder : store.getFolder(f.getPath()).list()){
                Folder subFolder = new Folder();
                subFolder.setName(folder.getName());
                subFolder.setPath(folder.getFullName());
                subFolder.setExpandable(folder.list().length > 0);
                f.addComponent(subFolder);
            }
        }catch(MessagingException e){
            System.err.println(e.getMessage());
        }
    }
    
}
