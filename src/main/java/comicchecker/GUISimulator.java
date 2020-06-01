package comicchecker;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.FileInputStream;

/**
 * Class for build GUI of this application
 * 
 * @author Agung Mubarak
 *
 */
public class GUISimulator extends Application{
	private ComicCheckerApplication application;
	
	public GUISimulator() {
		application = new ComicCheckerApplication();
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO: Be creative guys!
		Text ok = new Text("Logo atau gambar comic checker");
    	Text ok2 = new Text("Welcome to comic checker");
    	Button but1 = new Button("Next");
    	Button but2 = new Button("balik");
    	Button but3 = new Button("Add Subscription");
    	String[] komik = {"Naruto","Conan","One Piece","Eyeshield 21"};
    	
    	
    	//scene 1
    	VBox scene1 = new VBox(10,ok,but1);
    	scene1.setAlignment(Pos.CENTER);
    	Scene layout = new Scene(scene1,500,500);
    	
    	//scene2
    	GridPane gp = new GridPane();
        gp.setAlignment(Pos.CENTER);
        gp.setVgap(10);
        gp.setHgap(10);
        gp.setPadding(new Insets(25, 25, 25, 25));
    	//VBox scene2 = new VBox(20);
    	//scene2.getChildren().add(ok2);
    	gp.add(ok2,0,0,2,1);
    	
    	
    	//choice box
    	gp.add(new Label("Nama Komik: "), 0, 1);
    	ChoiceBox genreCB =  new ChoiceBox(FXCollections.observableArrayList(komik));
    	gp.add(genreCB, 1, 1);;
    	HBox tombol = new HBox(20);
    	tombol.getChildren().add(but2);
    	tombol.getChildren().add(but3);
    	tombol.setAlignment(Pos.CENTER);
    	gp.add(tombol,0,2,2,1); 	
    	Scene lay2 = new Scene(gp,500,500);
    	
    	//next scene
    	but1.setOnAction(e -> primaryStage.setScene(lay2));
    	but2.setOnAction(e -> primaryStage.setScene(layout));
    	
    	primaryStage.setScene(layout);
    	primaryStage.setTitle("mantap");
    	primaryStage.show();
	}	
	
	public static void main(String[] args) {
		Application.launch(args);
	}
}