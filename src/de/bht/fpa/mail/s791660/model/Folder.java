package de.bht.fpa.mail.s791660.model;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Simone Strippgen
 *
 */

public class Folder extends Component {

    private final boolean expandable;
    private final ArrayList<Component> content;
    private final List<Email> emails;

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
