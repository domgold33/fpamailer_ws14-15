/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.bht.fpa.mail.s791660.model.applicationLogic.account;

import de.bht.fpa.mail.s791660.model.Account;
import java.util.List;

/**
 *
 * @author Dominik
 */
public class AccountManager implements AccountManagerIF{

    private AccountDAOIF accountDAO;
    
    public AccountManager(){
        this.accountDAO = new AccountDBDAO();
    }
    
    @Override
    public Account getAccount(String name) {
        List<Account> accounts = this.getAllAccounts();
        for(Account acc : accounts){
            if(acc.getName().equalsIgnoreCase(name)){
                return acc;
            }
        }
        return null;
    }

    @Override
    public List<Account> getAllAccounts() {
        return accountDAO.getAllAccounts();
    }

    @Override
    public boolean saveAccount(Account acc) {
        boolean exists = false;
        if(acc == null){
            return exists;
        }
        for(Account tempAcc : this.getAllAccounts()){
            if(tempAcc.getName().equalsIgnoreCase(acc.getName())){
                exists = true;
                break;
            }
        }
        if(!exists){
            accountDAO.saveAccount(acc);
        }
        return exists;
    }

    @Override
    public boolean updateAccount(Account account) {
        return accountDAO.updateAccount(account);
    }
    
}
