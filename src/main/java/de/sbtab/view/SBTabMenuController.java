package de.sbtab.view;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.StringWriter;
import java.net.URL;
import java.util.Optional;
import java.util.Properties;
import java.util.ResourceBundle;

import org.sbml.jsbml.SBMLDocument;

import de.sbtab.controller.SBTabController;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.Priority;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

public class SBTabMenuController implements Initializable {
	SBTabMainView mainView;
	private boolean unsavedChanges;
	
	public SBTabMenuController() {
		//
	}
	
	public SBTabMenuController(SBTabMainView mainView) {
		this.mainView = mainView;
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		if (mainView.getDoc()==null){
		lockMenu(true);
		}
	}

	// View and Edit Menu as objects:
	@FXML
	private Menu ViewMenu;

	@FXML
	private Menu EditMenu;

	// file MenuItems:

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
	private MenuItem CloseItem;

	@FXML
	private MenuItem ValidateItem;
	
	//TODO: add closeItem and referring methods that close the file but not the stage and blocks the menu again.

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
		if(!mainView.isDocumentLoaded()) {
			handleOpen();
		} else {
			showOnDoubleOpenDialog();
		}
	}

	private void showOnDoubleOpenDialog() {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
		stage.getIcons().add(new Image(this.getClass().getResourceAsStream("Icon_32.png")));
		alert.setGraphic(new ImageView(this.getClass().getResource("AlertIcon_64.png").toString()));
		alert.setTitle("Open another file");
		alert.setHeaderText("To open another file a new Session of TabMod must be started");// TODO: Add appropriate
																							// text/ Implement
																							// abstract dialogs
		alert.setContentText("Do you want to start a new Session to open another file?");

		ButtonType buttonTypeNew = new ButtonType("New session");
		ButtonType buttonTypeClose = new ButtonType("close current file");
		ButtonType buttonTypeCancel = new ButtonType("Cancel");
		

		alert.getButtonTypes().setAll(buttonTypeNew, buttonTypeClose ,buttonTypeCancel);

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == buttonTypeNew) {
			startNewWindow();
		
		}
		if (result.get() == buttonTypeClose) {
			handleClose();
			mainView.clearView("No file specified.");
			lockMenu(true);
			handleOpen();
		}
		else {
		}
	}
	private void startNewWindow(){
		SBMLDocument newDoc = SBTabController.read(chooseFile());
		Stage newStage = new Stage();
		SBTabMainView newGUI = new SBTabMainView();
		newGUI.setDoc(newDoc);
		try {
			newGUI.start(newStage);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	private void lockMenu(boolean bool) {
		ViewMenu.setDisable(bool);
		EditMenu.setDisable(bool);
		SaveItem.setDisable(bool);
		ValidateItem.setDisable(bool);
		ExportItem.setDisable(bool);
		CloseItem.setDisable(bool);
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
		//boolean valid = SBTabController.validate(doc);// TODO: Implement validate
		int errors = SBTabController.numErrors(mainView.getDoc());
		if (errors == 0) {
			Alert alert = new Alert(AlertType.CONFIRMATION);
			Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
			alert.setGraphic(new ImageView(this.getClass().getResource("ApproveIcon_64.png").toString()));
			stage.getIcons().add(new Image(this.getClass().getResourceAsStream("Icon_32.png")));
			alert.setTitle("Validator");
			alert.setHeaderText(null);
			alert.setContentText("Your file is a valid .sbml file");
			alert.showAndWait();

		} else {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Exception Dialog");
			alert.setHeaderText("You have " + errors + "Errors in your Document.");
			alert.setContentText("List of all Errors");

			Exception ex = new FileNotFoundException("Error");

			// Create expandable Exception.
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			ex.printStackTrace(pw);
			String exceptionText = sw.toString();

			Label label = new Label("The exception stacktrace was:");

			TextArea textArea = new TextArea(exceptionText);
			textArea.setEditable(false);

			textArea.setMaxWidth(Double.MAX_VALUE);
			textArea.setMaxHeight(Double.MAX_VALUE);
			GridPane.setVgrow(textArea, Priority.ALWAYS);
			GridPane.setHgrow(textArea, Priority.ALWAYS);

			GridPane expContent = new GridPane();
			expContent.setMaxWidth(Double.MAX_VALUE);
			expContent.add(label, 0, 0);
			expContent.add(textArea, 0, 1);

			// Set expandable Exception into the dialog pane.
			alert.getDialogPane().setExpandableContent(expContent);

			alert.showAndWait();
		}

	}

	@FXML
	void doQuit(ActionEvent event) {
		handleQuit();
	}
	
	
	@FXML
	void doClose(ActionEvent event) {
		handleClose();
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

	public void handleOpen() {
		String filePath = chooseFile();
		Task<Void> task = new Task<Void>() {
		    @Override 
			public Void call() {
				if (filePath != null) {
					mainView.setDoc(SBTabController.read(filePath));
				}
				return null;
			}
		    @Override
		    protected void running() {
		    	if (filePath != null) {
		    	super.running();
		    	mainView.assignStatusBar("Loading: " + filePath, -1D);
		    	}
		    }
		    @Override 
		    public void succeeded() {
		    	super.succeeded();
		    	if (filePath != null) {
					lockMenu(false);
					mainView.reInit();				
				}
		    }};
		Thread thread = new Thread(task);
		thread.setDaemon(true);
		thread.start();
	}

	private void handleSave() {
		SBMLDocument doc = SBTabController.getDoc();
		File filePath = new File(SBTabController.getFilePath());
		String theProjectName = mainView.getTheProjectName();
		String theVersion = mainView.getTheVersion();
		SBTabController.save(doc, filePath, theProjectName, theVersion);
		unsavedChanges=true;
	}
	
	private void handleQuit() {
		if (unsavedChanges) {
			Alert alert = new Alert(AlertType.CONFIRMATION);
			Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
			stage.getIcons().add(new Image(this.getClass().getResourceAsStream("Icon_32.png")));
			alert.setGraphic(new ImageView(this.getClass().getResource("AlertIcon_64.png").toString()));

			alert.setTitle("Unsaved Changes");
			alert.setHeaderText("Your file has unsaved changes");
			alert.setContentText("Do you want to save your changes?");

			ButtonType buttonTypeSave = new ButtonType("Save Changes");
			ButtonType buttonTypeDontSave = new ButtonType("Don't Save Changes");
			ButtonType buttonTypeCancel = new ButtonType("Cancel");

			alert.getButtonTypes().setAll(buttonTypeSave, buttonTypeDontSave, buttonTypeCancel);

			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == buttonTypeSave) {

				// TODO: implement save.
				System.exit(0);
			} else if (result.get() == buttonTypeDontSave) {
				System.exit(0);
			} else {
			}
		}
		else {
			System.exit(0);		
		}
	}
	
	private void handleClose() {
		if (unsavedChanges) {
			Alert alert = new Alert(AlertType.CONFIRMATION);
			Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
			stage.getIcons().add(new Image(this.getClass().getResourceAsStream("Icon_32.png")));
			alert.setGraphic(new ImageView(this.getClass().getResource("AlertIcon_64.png").toString()));

			alert.setTitle("Unsaved Changes");
			alert.setHeaderText("Your file has unsaved changes");
			alert.setContentText("Do you want to save your changes?");

			ButtonType buttonTypeSave = new ButtonType("Save Changes");
			ButtonType buttonTypeDontSave = new ButtonType("Don't Save Changes");
			ButtonType buttonTypeCancel = new ButtonType("Cancel");

			alert.getButtonTypes().setAll(buttonTypeSave, buttonTypeDontSave, buttonTypeCancel);

			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == buttonTypeSave) {

				// TODO: implement save.
				mainView.setDoc(null);
				mainView.clearView("No file specified.");
				lockMenu(true);
			} else if (result.get() == buttonTypeDontSave) {
				mainView.setDoc(null);
				mainView.clearView("No file specified.");
				lockMenu(true);
			} else {
			}
		}
		else{
			mainView.setDoc(null);
			mainView.clearView("No file specified.");
			lockMenu(true);
		}
	}
	
	

	/*
	 * Choose file from file dialog and get the file path.
	 */
	private String chooseFile() {
		Reader reader = null;
		Properties theProperties = new Properties();
		try {
			if (!(new File(".properties").exists())) {
				SBTabController.setProperties();
			}
			reader = new FileReader(".properties");
			theProperties.load(reader);
			theProperties.list(System.out);
		} catch (IOException e) {
			e.printStackTrace();
		}
		final FileChooser fileChooser = new FileChooser();
		String filePath = "";
		fileChooser.getExtensionFilters().addAll(new ExtensionFilter("XML Files", "*.xml"),
				new ExtensionFilter("GZip Files", "*.gz"));
		fileChooser.setTitle("Choose SBML or XML File.");
		fileChooser.setInitialDirectory(new File(theProperties.getProperty("FilePath")));
		File file = fileChooser.showOpenDialog(null);
		if (file != null) {
			filePath = file.getAbsolutePath();
			return filePath;
		}
		return null;
	}
}
