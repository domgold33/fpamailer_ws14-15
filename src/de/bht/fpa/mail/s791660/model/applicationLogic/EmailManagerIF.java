/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.bht.fpa.mail.s791660.model.applicationLogic;

import de.bht.fpa.mail.s791660.model.Folder;
import java.io.File;

/**
 *
 * @author Dominik
 */
public interface EmailManagerIF {
    
    /***
     * Get the folder handled by this EmailManager.
     * @return The handled folder.
     */
    Folder getFolder();
    
    
    /***
     * Loads the emails of the given folder into its email list by calling the private
     * parseXmlFile() method.
     * @param f Folder that shall have its email list filled.
     */
    void loadEmails(Folder f);
    
}
