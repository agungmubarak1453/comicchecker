package comicchecker;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Pagination;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public interface View extends Initializable{
	static ComicCheckerApplication app = new ComicCheckerApplication();
	
	
	@Override
	abstract public void initialize(URL location, ResourceBundle resources);
	abstract public void refresh();
	
	public default void constructorHelper(String fxmlLocation) {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxmlLocation));
		fxmlLoader.setController(this);
		fxmlLoader.setRoot(this);
		try {
			
			fxmlLoader.load();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	default void changeScene(ActionEvent event, String FXMLFile) {
		try {
			Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
			stage.setScene(new Scene(FXMLLoader.load(getClass().getResource(FXMLFile))));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}