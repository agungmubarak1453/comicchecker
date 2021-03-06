package comicchecker;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
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
        stage.setTitle("Comic Checker");
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/image/icon.jpg")));
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/HomeView.fxml"))));
        stage.setResizable(false);
        stage.show();
	}
	
	public static void main(String[] args) {
		if(args.length == 0) {
			Application.launch(args);
		}else if(args[0].equals("background")){
			ComicCheckerApplication app = new ComicCheckerApplication();
			app.frequentlyUpdateSubscription(30, false);
		}else {
			Application.launch(args);
		}
	}
	
}