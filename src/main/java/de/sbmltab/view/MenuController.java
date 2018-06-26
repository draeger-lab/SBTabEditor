package de.sbmltab.view;

import java.io.File;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import org.sbml.jsbml.SBMLDocument;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Menu;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import de.sbmltab.controller.*;
import de.sbmltab.main.SBMLTab;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;

public class MenuController implements Initializable {
  @FXML
  void initialize() {

  }
  // Generates a MenuBar as discussed in the GUI-Concept
  /*
   * public static MenuBar generateMenuBar (){
   * 
   * MenuBar menuBar = new MenuBar();
   * 
   * // Menus needed Menu fileMenu = new Menu("File"); Menu editMenu = new
   * Menu("Edit"); Menu viewMenu = new Menu("View"); Menu helpMenu = new
   * Menu("Help");
   * 
   * // Menu-Items needed //file MenuItem newItem = new MenuItem("New");
   * MenuItem openItem = new MenuItem("Open"); MenuItem saveItem = new
   * MenuItem("Save"); MenuItem exitItem = new MenuItem("Exit"); MenuItem
   * exportItem = new MenuItem("Export"); MenuItem importItem = new
   * MenuItem("Import"); //edit MenuItem copyItem = new MenuItem("Copy");
   * MenuItem cutItem = new MenuItem("Cut"); MenuItem pasteItem = new
   * MenuItem("Paste"); MenuItem undoItem = new MenuItem("Undo"); MenuItem
   * redoItem = new MenuItem("Redo"); //View MenuItem columnsShownItem = new
   * MenuItem("Columns shown"); MenuItem hideColumnsItem = new
   * MenuItem("Hide Columns"); MenuItem showHiddenColumnsItem = new
   * MenuItem("Show hidden columns");
   * 
   * // Arranging Menu-Items in the right order
   * fileMenu.getItems().addAll(newItem, openItem, importItem, exportItem,
   * saveItem, exitItem ); editMenu.getItems().addAll(undoItem, redoItem,
   * copyItem, cutItem, pasteItem);
   * viewMenu.getItems().addAll(columnsShownItem, hideColumnsItem,
   * showHiddenColumnsItem);
   * 
   * menuBar.getMenus().addAll(fileMenu, editMenu, viewMenu, helpMenu);
   * 
   * //define Keyboard shortcuts
   * newItem.setAccelerator(KeyCombination.keyCombination("Ctrl+N"));
   * openItem.setAccelerator(KeyCombination.keyCombination("Ctrl+O"));
   * saveItem.setAccelerator(KeyCombination.keyCombination("Ctrl+S"));
   * undoItem.setAccelerator(KeyCombination.keyCombination("Ctrl+Z"));
   * redoItem.setAccelerator(KeyCombination.keyCombination("Ctrl+Y"));
   * copyItem.setAccelerator(KeyCombination.keyCombination("Ctrl+C"));
   * cutItem.setAccelerator(KeyCombination.keyCombination("Ctrl+X"));
   * pasteItem.setAccelerator(KeyCombination.keyCombination("Ctrl+V"));
   * 
   * return menuBar; }
   */

  @Override
  public void initialize(URL arg0, ResourceBundle arg1) {
	  if (!SBMLTabMainView.fileLoaded){
		  ViewMenu.setDisable(true);
		  EditMenu.setDisable(true);//Disable unnecessary Menus while no file is loaded
	  }

  }
  // View and Edit Menu as objects:
  @FXML
  private Menu ViewMenu;
  
  @FXML
  private Menu EditMenu;
  
  
  //file MenuItems:

  @FXML
  private MenuItem NewItem;

  @FXML
  private MenuItem OpenItem;

  @FXML
  private MenuItem SaveItem;

  @FXML
  private MenuItem QuitItem;

  @FXML
  private MenuItem ImportItem;

  @FXML
  private MenuItem ExportItem;

  @FXML
  private MenuItem ValidateItem;
  

  // edit MenuItems:
  @FXML
  private MenuItem UndoItem;

  @FXML
  private MenuItem RedoItem;

  @FXML
  private MenuItem CopyItem;

  @FXML
  private MenuItem PasteItem;

  @FXML
  private MenuItem CutItem;

  // view MenuItems:
  @FXML
  private MenuItem FieldSizeItem;

  @FXML
  private MenuItem HideColumnsItem;

  @FXML
  private MenuItem ShowHiddenColumnsItem;
  
  @FXML
  private MenuItem ZoomInItem;
  
  @FXML
  private MenuItem ZoomOutItem;
  
  @FXML
  private MenuItem SetToItem;

  // Help MenuItems:
  @FXML
  private MenuItem DocumentationItem;

  @FXML
  private MenuItem WebSearchItem;

  // file menu action Methods:

  @FXML
  void doNew(ActionEvent event) {
    handleNew();
  }

  @FXML
  void doOpen(ActionEvent event) {
    SBMLTabMainView.doc = handleOpen();
    // TODO: change when tree and more views are implemented
    if (SBMLTabMainView.doc!=null) {
      SBMLTabMainView.initializeReactionTableView();
      SBMLTabMainView.fileLoaded=true;
    }
  }

  @FXML
  void doSave(ActionEvent event) {
    handleSave();
  }

  @FXML
  void doImport(ActionEvent event) {
  }

  @FXML
  void doExport(ActionEvent event) {

  }

  @FXML
  void doValidate(ActionEvent event) {
	  boolean valid=true;//TODO: Implement validate
	  if (valid){
		  Alert alert = new Alert(AlertType.INFORMATION);
		  alert.setTitle("Validator");
		  alert.setHeaderText(null);
		  alert.setContentText("Your file is a valid .sbml file");
		  alert.showAndWait();
	  }
	  else{
		  
	  }
	  

  }

  @FXML
  void doQuit(ActionEvent event) {
	//TODO: check for unsaved changes
	boolean unsavedChanges= true;
	if (unsavedChanges){
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Unsaved Changes");
		alert.setHeaderText("Your file has unsaved changes");//TODO: Add appropriate text
		alert.setContentText("Do you want to save your changes?");

		ButtonType buttonTypeSave = new ButtonType("Save Changes");
		ButtonType buttonTypeDontSave = new ButtonType("Don't Save Changes");
		ButtonType buttonTypeCancel = new ButtonType("Cancel");

		alert.getButtonTypes().setAll(buttonTypeSave, buttonTypeDontSave, buttonTypeCancel);

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == buttonTypeSave){
			
		    //TODO: implement save.
			System.exit(0);
		} else if (result.get() == buttonTypeDontSave) {
			System.exit(0);
		} else {
		}
	}
  }

  // edit menu action methods
  @FXML
  void doUndo(ActionEvent event) {

  }

  @FXML
  void doRedo(ActionEvent event) {

  }

  @FXML
  void doCopy(ActionEvent event) {

  }

  @FXML
  void doPaste(ActionEvent event) {
  }

  @FXML
  void doCut(ActionEvent event) {
  }

  // View menu action methods:
  @FXML
  void doZoomIn(ActionEvent event) {

  }
  @FXML
  void doZoomOut(ActionEvent event) {

  }
  @FXML
  void doSetTo(ActionEvent event) {

  }

  @FXML
  void doHideColumns(ActionEvent event) {
  }

  @FXML
  void doShowHiddenColumns(ActionEvent event) {
  }
  // Help menu action methods:

  @FXML
  void doDocumentation(ActionEvent event) {
  }

  @FXML
  void doWebSearch(ActionEvent event) {
  }

  // Handler methods:

  private void handleNew() {
    Task<Void> task = new Task<Void>() {
      @Override
      public Void call() {
        try {
          System.out.println("new");
          // TODO new
        } catch (Exception e) {
          e.printStackTrace();
        }

        return null;
      }
    };
    Thread th = new Thread(task);
    th.setDaemon(true);
    th.start();
  }

  public static SBMLDocument handleOpen() {
    String filePath = chooseFile();
    if (filePath!=null) {
      SBMLDocument doc = SBMLTabController.read(filePath);
      return doc;
    }
    return null;
  }
  private void handleSave() {
    
  }

  private static String chooseFile(){
    final FileChooser fileChooser = new FileChooser();
    String filePath = "";
    fileChooser.getExtensionFilters().addAll(
      new ExtensionFilter("XML Files", "*.xml"),
      new ExtensionFilter("SBML Files", "*.SBML"));
    fileChooser.setTitle("Choose SBML or XML File.");
    fileChooser.setInitialDirectory(new File(System.getProperty("user.home"))) ;
    File file = fileChooser.showOpenDialog(null);
    if (file != null) {
      filePath = file.getAbsolutePath();
      return filePath;
    }
    return null;
  }

}
