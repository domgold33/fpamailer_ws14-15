/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.bht.fpa.mail.s791660.model.applicationLogic.account;

import de.bht.fpa.mail.s791660.model.Account;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

/**
 *
 * @author Dominik
 */
public class AccountDBDAO implements AccountDAOIF{

    private static final String DATA_PU = "fpamailer";
    
    private static final String GET_ALL_QUERY = "SELECT a FROM Account a";
    
    private static EntityManagerFactory emf;
    private EntityManager em;
    private EntityTransaction et;
    
    public AccountDBDAO(){
        try{
            TestDBDataProvider.createAccounts();
            emf = Persistence.createEntityManagerFactory(DATA_PU);
            em = emf.createEntityManager();
            et = em.getTransaction();
        }catch(PersistenceException e){
            System.err.println(e.getMessage());
        }
    }
    
    @Override
    public List<Account> getAllAccounts() {
        try{
            Query query = em.createQuery(GET_ALL_QUERY);
            List<Account> list = (List<Account>) query.getResultList();
            return list;
        }catch(PersistenceException e){
            System.err.println(e.getMessage());
        }
        return new ArrayList<Account>();
    }

    @Override
    public Account saveAccount(Account acc) {
        try{
            et.begin();
            em.merge(acc);
            et.commit();
            return acc;
        }catch(PersistenceException e){
            System.out.println(e.getMessage());
        }
        return acc;
    }

    @Override
    public boolean updateAccount(Account acc) {
        try{
            et.begin();
            em.merge(acc);
            et.commit();
            return true;
        }catch(PersistenceException e){
            System.out.println(e.getMessage());
        }
        return false;
    }
    
}
