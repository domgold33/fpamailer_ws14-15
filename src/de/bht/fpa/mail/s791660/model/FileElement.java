/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.bht.fpa.mail.s791660.model;

import java.io.File;

/**
 *
 * @author Dominik
 */
public class FileElement extends Component{
    
    private String path;
    private String name;
    
    public FileElement(File f){
        super(f);
    }
    
    @Override
    public boolean isExpandable(){
        return false;
    }
    
}
