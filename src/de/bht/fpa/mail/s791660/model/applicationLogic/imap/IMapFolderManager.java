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
    
    public IMapFolderManager(Account account){
        this.account = account;
    }

    @Override
    public Folder getTopFolder() {
        Store store = IMapConnectionHelper.connect(account);
        try{
            javax.mail.Folder topFolder = store.getDefaultFolder();  
            Folder top = new Folder();
            top.setName(account.getName());
            top.setPath(topFolder.getFullName());
            return top;
        }catch(MessagingException e){
            System.err.println(e.getMessage());
        }
        return null;
    }

    @Override
    public void loadContent(Folder f) {
        if(!f.getComponents().isEmpty()){
            return;
        }
        Store store = IMapConnectionHelper.connect(account);
        try{
            for(javax.mail.Folder folder : store.getFolder(f.getName()).list()){
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
