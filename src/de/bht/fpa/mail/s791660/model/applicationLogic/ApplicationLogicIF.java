package de.bht.fpa.mail.s791660.model.applicationLogic;

import de.bht.fpa.mail.s791660.model.Account;
import de.bht.fpa.mail.s791660.model.Email;
import de.bht.fpa.mail.s791660.model.Folder;
import java.io.File;
import java.util.List;

/**
 * This Interface defines the methods which are needed 
 * to manage emails and folders.
 * 
 * @author Simone Strippgen
 */

public interface ApplicationLogicIF {

    /**
     * Get current root folder.
     * @return current root folder.
     */
    Folder getTopFolder();

    /**
     * Loads all relevant content in the directory path of a folder
     * into the folder.
     * @param folder the folder into which the content of the corresponding 
     *          directory should be loaded
     */
    void loadContent(Folder folder);

    /**
     * Searches for all emails in the selected folder that contain the 
     * given pattern.
     * @param pattern contains a string for comparison with email content
     * @return A list containing all the email objects that match the search pattern.
     */
    List<Email> search(String pattern);

    /**
     * Loads all emails in the directory path of the given folder
     * as objects of the class Email into the folder.
     * @param folder    the folder into which the emails of the corresponding 
     *                  directory should be loaded
     */
    void loadEmails(Folder folder);

    /**
     * Changes the root directory of the application, and initializes
     * the folder manager with the new root directory.
     * @param file  the path to the directory which was selected as 
     *              the new root directory of the application.
     */
    void changeDirectory(File file);

    /**
     * Saves the email objects of the selected folder into the given
     * directory.
     * @param file  the path to the directory in which the email objects
     *              should be saved.
     */
    void saveEmails(File file);
    
    void openAccount(String name);
    
    Account getAccount(String name);
    
    void saveAccount(Account acc);
    
    void updateAccount(Account acc);
    
    List<String> getAllAccounts();
    
}
