package de.sbmltab.view;

import java.util.function.Function;

import de.sbmltab.controller.ReactionWrapper;
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

public class SBMLTabMainView extends Application {

	@Override
	public void start(Stage stage) throws Exception {

		BorderPane root = new BorderPane();
		root.setTop(FXMLLoader.load(getClass().getResource("TabModMenu.fxml")));
		TableView<ReactionWrapper> tableView = initializeReactionTableView();
		root.setCenter(tableView);
		root.setLeft(FXMLLoader.load(getClass().getResource("TabModTree.fxml")));

		Scene scene = new Scene(root, 640, 480);

		// Add icons from resources to the Icon-List of this stage.
		stage.getIcons().add(new Image(getClass().getResourceAsStream("Icon_32.png")));
		stage.getIcons().add(new Image(getClass().getResourceAsStream("Icon_16.png")));
		stage.getIcons().add(new Image(getClass().getResourceAsStream("Icon_256.png")));
		stage.getIcons().add(new Image(getClass().getResourceAsStream("Icon_48.ico")));

		stage.setTitle("TabMod 1.0");
		stage.setScene(scene);
		stage.sizeToScene();
		stage.show();

	}

	private TableView<ReactionWrapper> initializeReactionTableView() {
		final ObservableList<ReactionWrapper> data = FXCollections.observableArrayList();

		TableView<ReactionWrapper> tableView = new TableView<ReactionWrapper>();
		tableView.getColumns().add(defineColumn("Name", ReactionWrapper::getReactionName));
		tableView.getColumns().add(defineColumn("Id", ReactionWrapper::getReactionId));
		tableView.getColumns().add(defineColumn("Modifiers", ReactionWrapper::getReactionModifiers));
		tableView.getColumns().add(defineColumn("Products", ReactionWrapper::getReactionProducts));
		tableView.getColumns().add(defineColumn("Reactants", ReactionWrapper::getReactionReactants));
		tableView.getItems().setAll(data);
		tableView.setEditable(true);

		return tableView;
	}

	private <S, T> TableColumn<S, T> defineColumn(String text, Function<S, ObservableValue<T>> property) {
		TableColumn<S, T> col = new TableColumn<>(text);
		col.setCellValueFactory(cellData -> property.apply(cellData.getValue()));
		return col;
	}

}