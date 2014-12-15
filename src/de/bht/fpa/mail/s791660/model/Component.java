/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.bht.fpa.mail.s791660.model;

import java.io.File;
import java.util.List;

/**
 *
 * @author Dominik
 */
public abstract class Component {
    
    private String path;
    private String name;
    
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
