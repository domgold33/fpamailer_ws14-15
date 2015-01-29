package de.bht.fpa.mail.s791660.model;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;

/**
 *
 * @author Simone Strippgen
 *
 */

@Entity
public class Folder extends Component implements Serializable{

    @Id
    @GeneratedValue
    private long id;
    
    private boolean expandable;
    @Transient
    private ArrayList<Component> content;
    @Transient
    private List<Email> emails;

    public Folder(){
        super();
        this.expandable = true;
        this.content = new ArrayList<>();
        this.emails = new ArrayList<>();
    }
    
    public Folder(File path, boolean expandable) {
        super(path);
        this.expandable = expandable;
        content = new ArrayList<>();
        emails = new ArrayList<>();
    }

    @Override
    public boolean isExpandable() {
        return expandable;
    }
    
    public void setExpandable(boolean expandable){
        this.expandable = expandable;
    }

    public void addComponent(Component comp) {
        content.add(comp);
    }

    @Override
    public List<Component> getComponents() {
        return content;
    }

    public List<Email> getEmails() {
        return emails;
    }

    public void addEmail(Email message) {
        emails.add(message);
    }

    @Override
    public String toString() {
        File file = new File(this.getPath());
        if(emails.isEmpty()){
            return file.getName();
        }
        return file.getName() + "  (" + emails.size() + ")";
    }
    
 }
