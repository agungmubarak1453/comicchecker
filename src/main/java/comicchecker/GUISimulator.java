package comicchecker;

import java.net.URL;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 * Class for build GUI of this application
 * 
 * @author Agung Mubarak
 *
 */
public class GUISimulator extends Application{
	
	public GUISimulator() {

	}
	
	@Override
	public void start(Stage stage) throws Exception {
        stage.setTitle("Welcome ojii-san");
        stage.getIcons().add(new Image("file:image/icon.jpg"));
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/HomeView.fxml"))));
        stage.setResizable(false);
        stage.show();
	}
	
	public static void main(String[] args) {
		Application.launch(args);
	}
	
}