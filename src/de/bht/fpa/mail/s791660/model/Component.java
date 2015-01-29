/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.bht.fpa.mail.s791660.model;

import java.io.File;
import java.io.Serializable;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;

/**
 *
 * @author Dominik
 */
@Entity
@Inheritance
public abstract class Component implements Serializable{
    
    @Id
    @GeneratedValue
    private long id;
    
    private String path;
    private String name;
    
    public Component(){
        this.path = "";
        this.name = "";
    }
    
    public Component(File f){
        this.path = f.getAbsolutePath();
        this.name = f.getName();
    }
    
    public abstract boolean isExpandable();

    public String getPath(){
        return path;
    }
    
    public void setPath(String path){
        this.path = path;
    }
    
    public String getName(){
        return name;
    }
    
    public void setName(String name){
        this.name = name;
    }
    
    public List<Component> getComponents(){
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    @Override
    public String toString(){
        return name;
    }
    
}
