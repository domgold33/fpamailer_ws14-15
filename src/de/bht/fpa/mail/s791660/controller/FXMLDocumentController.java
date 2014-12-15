package de.bht.fpa.mail.s791660.controller;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import de.bht.fpa.mail.s791660.model.Component;
import de.bht.fpa.mail.s791660.model.Email;
import de.bht.fpa.mail.s791660.model.Folder;
import de.bht.fpa.mail.s791660.model.applicationLogic.ApplicationLogic;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

/**
 *
 * @author Dominik
 */
public class FXMLDocumentController implements Initializable {
    
    public final String PATHHISTORY_DEFAULT_MESSAGE = "No recent paths in browsing history.";
    
    private final String DEFAULT_ROOT_PATH = "C:/Users/Dominik/Downloads/AUFGABE4/AUFGABE4/Account";
    private final Image FOLDER_CLOSED_ICON = new Image("/images/folder.png", 16, 16, true, false); 
    private final Image FOLDER_OPEN_ICON = new Image("/images/opened_folder.png", 16, 16, true, false);
    
    private ApplicationLogic applicationLogic; 
    private Stage pathHistoryStage;
    private List<String> historyList;
    private ObservableList<Email> tableData; 
    private DirectoryChooser directoryChooser;
    @FXML
    private TreeView<Component> treeView;
    @FXML
    private MenuItem historyMenuItem;
    @FXML
    private MenuItem openFileMenuItem;
    @FXML
    private MenuItem saveMenuItem;
    @FXML
    private TableView<Email> tableView;
    @FXML
    private TableColumn<Email, String> importanceColumn;
    @FXML
    private TableColumn<Email, String> receivedColumn;
    @FXML
    private TableColumn<Email, String> readColumn;
    @FXML
    private TableColumn<Email, String> senderColumn;
    @FXML
    private TableColumn<Email, String> recipientsColumn;
    @FXML
    private TableColumn<Email, String> subjectColumn;
    @FXML
    private TextField searchBar;
    @FXML
    private Label senderLabel;
    @FXML
    private Label subjectLabel;
    @FXML
    private Label receivedLabel;
    @FXML
    private Label receiverLabel;
    @FXML
    private TextArea textDisplay;
    @FXML
    private Label emailsFoundLabel;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Initializing the TreeView.
        Folder f = new Folder(new File(DEFAULT_ROOT_PATH), true);
        applicationLogic = new ApplicationLogic(f, this);
        initializeTree(f);
        //Initializing the Menubar.
        initializeMenuBar();
        //Initializing the TableView and its columns.
        initializeTableView();
        //Initializing browsing history list.
        historyList = new ArrayList<>();
        historyList.add(PATHHISTORY_DEFAULT_MESSAGE);
        //Adding a ChangeListener to the TreeView's selection model.
        treeView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                Folder f1 = (Folder) newValue.getValue();
                if (f1.getEmails().isEmpty()) {
                    applicationLogic.loadEmails(f1);
                }
                //Making the tree item refresh by reassigning the same value again in order to let the 
                //email count display next to the folder name.
                newValue.setValue(null);
                newValue.setValue(f1);
                tableData.setAll(f1.getEmails());
                tableView.setItems(tableData);
                clearDetailedDisplay();
            }
        });
    }  
    
    /***
     * Adds an EventHandler to all the MenuItems in the application.
     */
    private void initializeMenuBar(){
        openFileMenuItem.setOnAction((event) ->{
            try{
                applicationLogic.changeDirectory(openDirectoryChooser());
            }catch(NullPointerException e){
                System.err.println("No directory has been chosen.");
            }
        });
        historyMenuItem.setOnAction((event) -> {
            try{
               openPathHistory();
            }catch(IOException e){
                System.err.println(e.getMessage());
                System.err.println("Failed to open dialog.");
                e.printStackTrace();
            }
        });
        saveMenuItem.setOnAction((event) -> {
            try{
                openSaveDialog();
            }catch(NullPointerException e){
                System.err.println("No directory has been chosen.");
            }
        });
    }
    
    /***
     * Initializes the TableView and its columns aswell as the search bar.
     */
    private void initializeTableView(){
        tableData = FXCollections.observableArrayList();
        searchBar.addEventHandler(KeyEvent.KEY_RELEASED, (Event event) -> {
            Folder f = (Folder) treeView.getSelectionModel().getSelectedItem().getValue();
            tableData.setAll(f.getEmails());
            tableData.retainAll(applicationLogic.search(searchBar.getText()));
            emailsFoundLabel.setText("(" + tableData.size() + ")");
        });
        importanceColumn.setCellValueFactory(new PropertyValueFactory("importance"));
        receivedColumn.setCellValueFactory(new PropertyValueFactory("received"));
        receivedColumn.setComparator((String o1, String o2) -> {
            try{
                DateFormat format = new SimpleDateFormat("EEEE, dd. MMM yyyy HH:mm");
                Date date1 = format.parse(o1);
                Date date2 = format.parse(o2);
                return date1.compareTo(date2);
            }catch(ParseException e){
                System.out.println(e.getMessage());
            }
            return -1;
        });
        receivedColumn.setSortType(TableColumn.SortType.DESCENDING);
        receivedColumn.setSortable(true);
        readColumn.setCellValueFactory(new PropertyValueFactory("read"));
        senderColumn.setCellValueFactory(new PropertyValueFactory("sender"));
        recipientsColumn.setCellValueFactory(new PropertyValueFactory("receiver"));
        subjectColumn.setCellValueFactory(new PropertyValueFactory("subject"));
        tableView.getSortOrder().add(receivedColumn);
        tableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue != null){
                Email mail = (Email) newValue;
                fillDetailedDisplay(mail);
            }
        });
    }
          
    /**
     * Clears the detail display on the lower right of the application.
     */
    private void clearDetailedDisplay(){
        senderLabel.setText("");
        subjectLabel.setText("");
        receivedLabel.setText("");
        receiverLabel.setText("");
        textDisplay.setText("");
    }
    
    /**
     * Fills the detail display on the lower right of the application with the given email's values once
     * an email has been selected in the TableView.
     * @param mail The Email object that will have its values displayed.
     */
    private void fillDetailedDisplay(Email mail){
        senderLabel.setText(mail.getSender());
        subjectLabel.setText(mail.getSubject());
        receivedLabel.setText(mail.getReceived());
        receiverLabel.setText(mail.getReceiver());
        textDisplay.setText(mail.getText());
    }
    
    /***
     * After a new root directory has been chosen, clears the TreeView and initializes it with the new
     * root path.
     * @param f The folder representing the new root path.
     */
    public void initializeNewTree(Folder f){
        if(historyList.contains(PATHHISTORY_DEFAULT_MESSAGE)){
            historyList.clear();
        }
        historyList.add(treeView.getRoot().getValue().getPath());
        initializeTree(f);
    }
    
    /***
     * Initializes the TreeView with the default root path.
     */
    private void initializeTree(Folder f){
        TreeItem<Component> root = new TreeItem<>(f);
        root.addEventHandler(TreeItem.branchExpandedEvent(), (event) -> {
            TreeItem<Component> source = (TreeItem) event.getSource();
            configureTree(source);
        });
        root.addEventHandler(TreeItem.branchCollapsedEvent(), (event) -> {
            TreeItem<Component> source = (TreeItem) event.getSource();
            collapseTreeItem(source);
        });
        root.getChildren().add(new TreeItem());
        root.setExpanded(true);
        treeView.setRoot(root);
    }
    
    /***
     * Loads the content of the given TreeItem's folder, adds icons to the all the children and adds dummy content to non empty child directories 
     * in order to make the expansion arrows next to the children appear. Is called upon initialization with the root TreeItem as 
     * parameter and upon expansion of a TreeItem, with the expanded TreeItem as the parameter.
     * @param ti The TreeItem which shall be expanded.
     */
    private void configureTree(TreeItem<Component> ti){
        //Loading content 
        loadContent(ti);
        //Adding Icons to the TreeView
        //Adding dummy content to expandable children
        ti.setGraphic(new ImageView(FOLDER_OPEN_ICON));
        for(TreeItem<Component> temp : ti.getChildren()){
            addIcons(temp);
            addDummyContent(temp);
        }
    }
    
    /***
     * Loads the content of the given TreeItem, which stores it as its children.
     * @param ti The TreeItem that was expanded and now needs its content loaded.
     */
    private void loadContent(TreeItem<Component> ti){
        ti.getChildren().clear();
        Folder f = (Folder) ti.getValue();
        applicationLogic.loadContent(f);
        for(Component c : f.getComponents()){
            ti.getChildren().add(new TreeItem(c));
        }
    }
    
    /***
     * Called in the configureTree() method, therefore upon expansion of an existing TreeItem.
     * Adds dummy content to the given TreeItem's children in order to make the TreeView display
     * the expansion arrows to the left of the TreeItems.
     * @param ti The TreeItem which was expanded. 
     */
    private void addDummyContent(TreeItem<Component> ti){
        if(ti.getValue().isExpandable()){
            ti.getChildren().add(new TreeItem());
        }
         ti.setExpanded(false); 
    }
    
    /***
     * Adds the folder icons to the given TreeItem's children.
     * @param ti The TreeItem which shall have icons added to its children.
     */
    private void addIcons(TreeItem<Component> ti){
        ti.setGraphic(new ImageView(FOLDER_CLOSED_ICON));         
    }
    
    /***
     * Sets the icon of the given TreeItem back to the closed Folder Icon. Is called upon collapse of a
     * TreeItem.
     * @param ti The TreeItem which was collapsed.
     */
    private void collapseTreeItem(TreeItem<Component> ti){
        ti.setGraphic(new ImageView(FOLDER_CLOSED_ICON));
    }
    
    /***
     * Opens a dialog that allows the user to choose another directory as the root path
     * for the TreeView. Called upon activation of the 'Open File' button in the MenuBar.
     * @return A newly constructed folder with the chosen directory as its root path.
     * @throws NullPointerException When the dialog is closed and no directory has been chosen.
     */
    private File openDirectoryChooser() throws NullPointerException{
        directoryChooser = new DirectoryChooser();
        directoryChooser.setInitialDirectory(new File("C:/Users/Dominik"));
        Stage stage = new Stage();
        File file = directoryChooser.showDialog(stage);
        return file;
    }
    
    /***
     * Opens a new window which displays the history of all directories that have been chosen as a 
     * root path for the TreeView yet. Also allows for choice of an item in the list as the new
     * root path.
     * @throws IOException When the window failed to load.
     */
    private void openPathHistory() throws IOException{
        URL location = getClass().getResource("/de/bht/fpa/mail/s791660/gui/FXMLPathHistoryStage.fxml");
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(location);
        loader.setController(new FXMLPathHistoryController(this));
        AnchorPane root = (AnchorPane) loader.load();
        Scene scene = new Scene(root);
        this.pathHistoryStage = new Stage();
        this.pathHistoryStage.setScene(scene);
        this.pathHistoryStage.setTitle("Path History");
        this.pathHistoryStage.showAndWait();
    }
    
    /**
     * Opens a directory chooser where the user can choose a directory in which the emails of the
     * currently selected TreeItem's folder will be saved. Called upon activation of the save MenuItem.
     * @throws NullPointerException When the dialog is closed and no directory has been chosen.
     */
    private void openSaveDialog() throws NullPointerException{
        directoryChooser = new DirectoryChooser();
        directoryChooser.setInitialDirectory(new File("C:/Users/Dominik"));
        Stage stage = new Stage();
        File file = directoryChooser.showDialog(stage);
        applicationLogic.saveEmails(file);
    }
    
    /***
     * Get the list which contains all paths that have been chosen as the root of the TreeView.
     * @return list which contains all recent root paths.
     */
    public List<String> getHistoryList(){
        return this.historyList;
    }
    
    /**
     * Get the application's TableView.
     * @return The application's TableView.
     */
    public TableView<Email> getTableView(){
        return this.tableView;
    }
    
    /**
     * Get the ApplicationLogic object that handles the model data.
     * @return The ApplicationLogic object of the controller.
     */
    public ApplicationLogic getApplicationLogic() {
        return this.applicationLogic;
    }
    
    /**
     * Get the application's TreeView.
     * @return The application's TreeView.
     */
    public TreeView<Component> getTreeView(){
        return this.treeView;
    }

}
