package de.sbtab.view;

import java.util.function.Function;

import org.sbml.jsbml.ListOf;
import org.sbml.jsbml.Reaction;
import org.sbml.jsbml.SBMLDocument;

import de.sbtab.controller.SBTabReactionWrapper;
import javafx.application.Application;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;

public class SBTabMainView extends Application {
	public static SBMLDocument doc;
	private static BorderPane root = new BorderPane();
	public static boolean fileLoaded=true;// relevant information if a file is loaded or not.

	@Override
	public void start(Stage stage) throws Exception {
		if (fileLoaded) {
		root.setTop(FXMLLoader.load(getClass().getResource("SBTabMenu.fxml")));
		root.setLeft(FXMLLoader.load(getClass().getResource("SBTabTree.fxml")));
		}
		else{
			root.setTop(FXMLLoader.load(getClass().getResource("SBTabMenu.fxml")));	
		}

		Scene scene = new Scene(root, 640, 480);

		// Add icons from resources to the Icon-List of this stage.
		stage.getIcons().add(new Image(getClass().getResourceAsStream("Icon_32.png")));
		stage.getIcons().add(new Image(getClass().getResourceAsStream("Icon_16.png")));
		stage.getIcons().add(new Image(getClass().getResourceAsStream("Icon_256.png")));
		stage.getIcons().add(new Image(getClass().getResourceAsStream("Icon_48.ico")));

		stage.setTitle("TabMod 1.1");
		stage.setScene(scene);
		stage.sizeToScene();
		stage.show();

	}

	public static TableView<SBTabReactionWrapper> initializeReactionTableView() {
		final ObservableList<SBTabReactionWrapper> data = FXCollections.observableArrayList();
		if (doc != null) {
			ListOf<Reaction> listOfReactions = doc.getModel().getListOfReactions();
			for (Reaction reaction : listOfReactions) {
				data.add(new SBTabReactionWrapper(reaction));
			}
		}

		TableView<SBTabReactionWrapper> tableView = new TableView<SBTabReactionWrapper>();
		// TODO: figure out what fields do we need to work with
		tableView.getColumns().add(defineColumn("Name", SBTabReactionWrapper::getReactionName));
		tableView.getColumns().add(defineColumn("Id", SBTabReactionWrapper::getReactionId));
		tableView.getColumns().add(defineColumn("SBO Term", SBTabReactionWrapper::getSBOTerm));
		tableView.getItems().setAll(data);
		tableView.setEditable(true);
		root.setCenter(tableView);
		return tableView;
	}

	private static <S, T> TableColumn<S, T> defineColumn(String text, Function<S, ObservableValue<T>> property) {
		TableColumn<S, T> col = new TableColumn<>(text);
		col.setCellValueFactory(cellData -> property.apply(cellData.getValue()));
		return col;
	}

}