package de.bht.fpa.mail.s791660.model.applicationLogic;

import de.bht.fpa.mail.s791660.model.Folder;
import java.io.File;

/*
 * This class manages a hierarchy of folders and their content which is loaded 
 * from a given directory path.
 * 
 * @author Simone Strippgen
 */
public final class FileManager implements FolderManagerIF {

    //top Folder of the managed hierarchy
    private final Folder topFolder;

    /**
     * Constructs a new FileManager object which manages a folder hierarchy, 
     * where file contains the path to the top directory. 
     * The contents of the  directory file are loaded into the top folder
     * @param file File which points to the top directory
     */
    public FileManager(File file) {
       // hier kommt Ihr Code hin.
        this.topFolder = new Folder(file, true);
        loadContent(topFolder);
    }
    
    /**
     * Loads all relevant content in the directory path of a folder
     * object into the folder.
     * @param f the folder into which the content of the corresponding 
     *          directory should be loaded
     */
    @Override
    public void loadContent(Folder f) {
        // hier kommt Ihr Code hin.
        f.getComponents().clear();
        File file = new File(f.getPath());
        for(File tempFile : file.listFiles()){
            if(tempFile.isDirectory()){
                if(tempFile.listFiles().length <= 0){
                    f.addComponent(new Folder(tempFile, false));
                }else{
                    boolean hasSubFolders = false;
                    for(File tempFile2 : tempFile.listFiles()){
                        if(tempFile2.isDirectory()){
                            hasSubFolders = true;
                            break;
                        }
                    }
                    f.addComponent(new Folder(tempFile, hasSubFolders));
                }
            }
        }
    }

    @Override
    public Folder getTopFolder() {
        return topFolder;
    }
}
