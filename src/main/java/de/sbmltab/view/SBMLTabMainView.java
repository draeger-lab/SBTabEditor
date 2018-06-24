package de.sbmltab.view;


import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;

public class SBMLTabMainView extends Application {

	@Override
	public void start(Stage stage) throws Exception {

		BorderPane root = new BorderPane();
		root.setTop(FXMLLoader.load(getClass().getResource("TabModMenu.fxml")));
		TableView<ItemSet> tableView = initializeTableView();
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

	@SuppressWarnings("unchecked")
	private TableView<ItemSet> initializeTableView() {
		final ObservableList<ItemSet> data = FXCollections.observableArrayList();
		for (int i = 0; i < 4; i++) {
			data.add(new ItemSet(1, 2, 3, 4, 5, 6, 7, 8, 9));
		}

		TableColumn<ItemSet, Integer> tcRow1 = new TableColumn<>("tcRow1");
		tcRow1.setCellValueFactory(new PropertyValueFactory<>("item1"));

		TableColumn<ItemSet, Integer> tcRow2 = new TableColumn<>("tcRow2");
		tcRow2.setCellValueFactory(new PropertyValueFactory<>("item2"));

		TableColumn<ItemSet, Integer> tcRow3 = new TableColumn<>("tcRow3");
		tcRow3.setCellValueFactory(new PropertyValueFactory<>("item3"));

		TableColumn<ItemSet, Integer> tcRow4 = new TableColumn<>("tcRow4");
		tcRow4.setCellValueFactory(new PropertyValueFactory<>("item4"));

		TableColumn<ItemSet, Integer> tcRow5 = new TableColumn<>("tcRow5");
		tcRow5.setCellValueFactory(new PropertyValueFactory<>("item5"));

		TableColumn<ItemSet, Integer> tcRow6 = new TableColumn<>("tcRow6");
		tcRow6.setCellValueFactory(new PropertyValueFactory<>("item6"));

		TableColumn<ItemSet, Integer> tcRow7 = new TableColumn<>("tcRow7");
		tcRow7.setCellValueFactory(new PropertyValueFactory<>("item7"));

		TableColumn<ItemSet, Integer> tcRow8 = new TableColumn<>("tcRow8");
		tcRow8.setCellValueFactory(new PropertyValueFactory<>("item8"));

		TableColumn<ItemSet, Integer> tcRow9 = new TableColumn<>("tcRow9");
		tcRow9.setCellValueFactory(new PropertyValueFactory<>("item9"));
		TableView<ItemSet> tableView = new TableView();
		tableView.getColumns().addAll(tcRow1, tcRow2, tcRow3, tcRow4, tcRow5, tcRow6, tcRow7, tcRow8, tcRow9);
		tableView.getItems().setAll(data);
		tableView.setEditable(true);

		return tableView;
	}

	public static class ItemSet {
		int[] items = new int[9];

		public ItemSet(int... args) {
			for (int i = 0; i < args.length; i++) {
				items[i] = args[i];
			}
		}

		public int getItem1() {
			return items[0];
		}

		public int getItem2() {
			return items[1];
		}

		public int getItem3() {
			return items[2];
		}

		public int getItem4() {
			return items[3];
		}

		public int getItem5() {
			return items[4];
		}

		public int getItem6() {
			return items[5];
		}

		public int getItem7() {
			return items[6];
		}

		public int getItem8() {
			return items[7];
		}

		public int getItem9() {
			return items[8];
		}
	}
}
