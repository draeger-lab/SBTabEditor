package de.sbtab.view;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;
import java.awt.Desktop;
import java.net.URI;
import java.net.URISyntaxException;

import org.sbml.jsbml.SBMLDocument;

import de.sbtab.containers.SBTabReactionWrapper;
import de.sbtab.controller.SBTabController;
import de.sbtab.services.TableType;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.TextArea;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class SBTabMenuController implements Initializable {
	private SBTabMainView mainView;
	private SBTabController controller;
	private boolean newFile;

	private String[] checkBoxNames = { "Name", "Id", "SBO Term", "Compartment" };

	public SBTabMenuController() {
		//
	}

	public SBTabMenuController(SBTabMainView mainView, SBTabController controller) {
		this.mainView = mainView;
		this.controller = controller;
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		if (mainView.getDoc() == null) {
			lockMenu(true);
		}
		// TODO: prevent closing of stage
		mainView.getStage().setOnCloseRequest(event -> {
			event.consume();
			handleClose();
		});
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
	private MenuItem SaveAsItem;

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
		if (!mainView.isDocumentLoaded()) {
			handleNew();
		} else {
			showOnDoubleOpenDialog(true);
		}
	}

	@FXML
	void doOpen(ActionEvent event) {
		if (!mainView.isDocumentLoaded()) {
			handleOpen();
		} else {
			showOnDoubleOpenDialog(false);
		}
	}

	private void showOnDoubleOpenDialog(boolean newClicked) {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
		stage.getIcons().add(new Image(this.getClass().getResourceAsStream("Icon_32.png")));
		alert.setGraphic(new ImageView(this.getClass().getResource("AlertIcon_64.png").toString()));
		alert.setTitle("Open another file");
		alert.setHeaderText("To open another file a new Session of TabMod must be started");// TODO:
																							// Add
																							// appropriate
																							// text/
																							// Implement
																							// abstract
																							// dialogs
		alert.setContentText("Do you want to start a new Session to open another file?");

		ButtonType buttonTypeNew = new ButtonType("New session");
		ButtonType buttonTypeClose = new ButtonType("close current file");
		ButtonType buttonTypeCancel = new ButtonType("Cancel");

		alert.getButtonTypes().setAll(buttonTypeNew, buttonTypeClose, buttonTypeCancel);

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == buttonTypeNew) {
			if (newClicked) {
				startNewWindowNew();
			} else {
				startNewWindowOpen();
			}
		}
		if (result.get() == buttonTypeClose) {
			handleClose();
			mainView.clearView("No file specified.");
			lockMenu(true);
			if (newClicked) {
				handleNew();
			} else {
				handleOpen();
			}
		} else {
		}
	}

	private void startNewWindowOpen() {
		String filePath = chooseFile();
		SBTabMainView newGUI = new SBTabMainView();
		Task<Void> task = new Task<Void>() {
			@Override
			public Void call() {
				if (filePath != null) {
					newGUI.setDoc(controller.read(filePath));
					newGUI.setFilePath(filePath);
				}
				return null;
			}

			@Override
			public void succeeded() {
				super.succeeded();
				if (filePath != null) {
					Stage newStage = new Stage();
					try {
						newGUI.start(newStage);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		};
		Thread thread = new Thread(task);
		thread.setDaemon(true);
		thread.start();
	}

	private void startNewWindowNew() {
		SBTabMainView newGUI = new SBTabMainView();
		Task<Void> task = new Task<Void>() {
			@Override
			public Void call() {
				SBMLDocument newDoc = new SBMLDocument(3, 1);
				newDoc.setName("new document");
				newGUI.setDoc(newDoc);
				return null;
			}

			@Override
			public void succeeded() {
				super.succeeded();
				Stage newStage = new Stage();
				try {
					newGUI.start(newStage);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};
		Thread thread = new Thread(task);
		thread.setDaemon(true);
		thread.start();
	}

	private void lockMenu(boolean bool) {
		ViewMenu.setDisable(bool);
		EditMenu.setDisable(bool);
		SaveItem.setDisable(bool);
		ValidateItem.setDisable(bool);
		ExportItem.setDisable(bool);
		CloseItem.setDisable(bool);
		SaveAsItem.setDisable(bool);
	}

	@FXML
	void doSave(ActionEvent event) {
		handleSave();
	}

	@FXML
	void doSaveAs(ActionEvent event) {
		handleSaveAs();
	}

	@FXML
	void doImport(ActionEvent event) {
	}

	@FXML
	void doExport(ActionEvent event) {
	}

	@FXML
	void doValidate(ActionEvent event) {
		// TODO: Implement validate
		int errors = controller.numErrors(mainView.getDoc());
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
			Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
			stage.getIcons().add(new Image(this.getClass().getResourceAsStream("Icon_32.png")));
			alert.setGraphic(new ImageView(this.getClass().getResource("DisapproveIcon_64.png").toString()));

			Label label = new Label("The exception stacktrace was:");

			TextArea textArea = new TextArea(controller.stringValidator(mainView.getDoc()));
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

	private boolean[] checked = { true, true, true, true };

	@FXML
	void doHideColumns(ActionEvent event) {
		handleHideColumns();
	}

	@FXML
	void doShowHiddenColumns(ActionEvent event) {
		for (int i = 0; i < checked.length; i++) {
			checked[i] = true;
		}
		handleChecked();
	}
	// Help menu action methods:

	@FXML
	void doDocumentation(ActionEvent event) throws IOException, URISyntaxException {
		handleDocumentation();
	}

	@FXML
	void doWebSearch(ActionEvent event) throws IOException, URISyntaxException {
		Desktop d = Desktop.getDesktop();
		d.browse(new URI("http://bigg.ucsd.edu/"));
	}

	// Handler methods:

	private void handleNew() {
		Task<Void> task = new Task<Void>() {
			@Override
			public Void call() {
				try {
					SBMLDocument newDoc = new SBMLDocument(3, 1);
					newDoc.setName("new document");
					mainView.setDoc(newDoc);
				} catch (Exception e) {
					e.printStackTrace();
				}
				return null;
			}

			@Override
			protected void running() {
				super.running();
				mainView.assignStatusBar("Creating a new .SBML-file", -1D);
			}

			@Override
			public void succeeded() {
				super.succeeded();
				lockMenu(false);
				mainView.updateTitle();
				mainView.reInit();
				newFile = true;
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
					mainView.setDoc(controller.read(filePath));
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
					controller.setFilePath(filePath);
					mainView.updateTitle();
					mainView.reInit();
					newFile = false;
				}
			}
		};
		Thread thread = new Thread(task);
		thread.setDaemon(true);
		thread.start();
	}

	private void handleSave() {
		if (!newFile) {
			SBMLDocument doc = mainView.getDoc();
			File filePath = new File(controller.getFilePath());
			String theProjectName = mainView.getTheProjectName();
			String theVersion = mainView.getTheVersion();
			if (Objects.equals(controller.getFileExtension(filePath), ".xml")) {
				controller.save(doc, filePath, theProjectName, theVersion);
				setDocUnchanged();
			} else {
				handleSaveAs();
			}
		} else {
			handleSaveAs();
		}
	}

	private void handleQuit() {
		if (isDocChanged()) {
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
		} else {
			System.exit(0);
		}
	}

	private void handleClose() {
		if (isDocChanged()) {
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
				mainView.updateTitle();
				mainView.clearView("No file specified.");
				lockMenu(true);
			} else if (result.get() == buttonTypeDontSave) {
				mainView.setDoc(null);
				mainView.updateTitle();
				mainView.clearView("No file specified.");
				lockMenu(true);

			} else {
			}
		} else {
			mainView.setDoc(null);
			mainView.updateTitle();
			mainView.clearView("No file specified.");
			lockMenu(true);
		}
	}

	private void handleSaveAs() {
		String filePath = chooseSaveLocation();
		if (filePath != null) {
			controller.setFilePath(filePath);
			newFile = false;
			handleSave();
			mainView.getDoc().setName(new File(filePath).getName());
			mainView.updateTitle();
		}
	}

	private String chooseSaveLocation() {
		Preferences thePreferences = Preferences.userNodeForPackage(SBTabController.class);
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Specify a directory and a name to save as");
		fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("XML Document", "*.xml"));
		fileChooser.setInitialFileName(mainView.getDoc().getName());
		fileChooser.setInitialDirectory(new File(controller.getFilePath()).getParentFile());
		String filePath = "";
		File file = fileChooser.showSaveDialog(mainView.getStage());
		String lastOutputDir = thePreferences.get("last_output_dir", System.getProperty("user.home"));
		fileChooser.setInitialDirectory(new File(lastOutputDir));
		if (file != null) {
			filePath = file.getAbsolutePath();
			return filePath;
		} else {
			return null;
		}
	}

	/*
	 * Choose file from file dialog and get the file path.
	 */
	private String chooseFile() {
		Preferences thePreferences = Preferences.userNodeForPackage(SBTabController.class);
		final FileChooser fileChooser = new FileChooser();
		String filePath = "";
		fileChooser.getExtensionFilters().addAll(new ExtensionFilter("XML Files", "*.xml"),
				new ExtensionFilter("GZip Files", "*.gz"));
		fileChooser.setTitle("Choose SBML or XML File.");
		String lastOutputDir = thePreferences.get("last_output_dir", System.getProperty("user.home"));
		fileChooser.setInitialDirectory(new File(lastOutputDir));
		File file = fileChooser.showOpenDialog(mainView.getStage());
		if (file != null) {
			filePath = file.getAbsolutePath();
			if (thePreferences.get("last_output_dir", "") == "") {
				controller.setPreferences(filePath);
			}
			return filePath;
		}
		return null;
	}

	private void handleHideColumns() {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
		stage.getIcons().add(new Image(this.getClass().getResourceAsStream("Icon_32.png")));
		stage.setTitle("Hide columns");
		stage.setWidth(250);
		Scene scene = new Scene(new Group());

		Label text = new Label("Select checkbox to show column.");
		text.setPadding(new Insets(10));

		VBox vBox = new VBox();
		vBox.setSpacing(5);
		vBox.setPadding(new Insets(10));

		for (int i = 0; i < checkBoxNames.length; i++) {
			CheckBox cb = new CheckBox(checkBoxNames[i]);
			cb.setSelected(checked[i]);
			final int x = i;
			cb.selectedProperty().addListener(new ChangeListener<Boolean>() {
				public void changed(ObservableValue<? extends Boolean> ov, Boolean old_val, Boolean new_val) {
					checked[x] = new_val;
				}
			});
			vBox.getChildren().add(cb);
		}

		HBox hBox = new HBox(5);
		hBox.setPadding(new Insets(10));

		Button buttonOk = new Button("Ok");
		buttonOk.setPrefWidth(110);
		Button buttonCancel = new Button("Cancel");
		buttonCancel.setPrefWidth(110);

		hBox.getChildren().addAll(buttonOk, buttonCancel);

		buttonOk.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				handleChecked();
				stage.hide();
			}
		});

		buttonCancel.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				stage.hide();
			}
		});

		BorderPane root = new BorderPane();
		root.setTop(text);
		root.setCenter(vBox);
		root.setBottom(hBox);

		((Group) scene.getRoot()).getChildren().addAll(root);
		stage.setScene(scene);
		stage.show();
	}

	private void handleDocumentation() {
		String localDocumentationPath = System.getProperty("user.dir") + "/docs/.html";
		String theDocumentationURL = "https://draeger-lab.github.io/SBTabEditor/";
		URL url = controller.getDocumentation(theDocumentationURL, localDocumentationPath);
		Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
		Stage stage = new Stage();
		WebView browser = new WebView();
		WebEngine webEngine = browser.getEngine();
		webEngine.load(url.toString());

		StackPane root = new StackPane();
		root.getChildren().add(browser);

		Scene scene = new Scene(root);

		stage.setTitle("SBTabEditor Documentation");
		stage.getIcons().add(new Image(this.getClass().getResourceAsStream("Icon_32.png")));
		stage.setScene(scene);
		stage.setWidth(0.4 * primaryScreenBounds.getWidth());
		stage.setHeight(0.4 * primaryScreenBounds.getHeight());
		stage.show();
	}

	private void handleChecked() {
		TableType[] TableNames = TableType.values();
		for (int k = 0; k < TableNames.length; k++) {
			TableView<SBTabReactionWrapper> tableView = (TableView<SBTabReactionWrapper>) mainView.getTableProducer()
					.getTableView(TableNames[k]);

			int size = tableView.getColumns().size();
			for (int j = 0; j < checkBoxNames.length; j++) {
				for (int i = 0; i < size; i++) {
					TableColumn current = tableView.getColumns().get(i);
					
					if (current.getText() == checkBoxNames[j]) {
						tableView.getColumns().get(i).setVisible(checked[j]);
						break;
					}
				}
			}
		}
	}

	private boolean isDocChanged() {
		TableType[] TableNames = TableType.values();
		for (int k = 0; k < TableNames.length; k++) {
			TableView<SBTabReactionWrapper> tableView = (TableView<SBTabReactionWrapper>) mainView.getTableProducer()
					.getTableView(TableNames[k]);
			ObservableList<TableColumn<SBTabReactionWrapper, ?>> currentColumns = tableView.getColumns();
			for (int i = 0; i < currentColumns.size(); i++) {
				if (tableView.getColumns().get(i).getId() == "changed") {
					return true;
				}
			}
		}
		return false;
	}

	private void setDocUnchanged() {
		TableType[] TableNames = TableType.values();
		for (int k = 0; k < TableNames.length; k++) {
			TableView<SBTabReactionWrapper> tableView = (TableView<SBTabReactionWrapper>) mainView.getTableProducer()
					.getTableView(TableNames[k]);
			ObservableList<TableColumn<SBTabReactionWrapper, ?>> currentColumns = tableView.getColumns();
			for (int i = 0; i < currentColumns.size(); i++) {
				currentColumns.get(i).setId("noChanges");
			}
		}
	}
}
