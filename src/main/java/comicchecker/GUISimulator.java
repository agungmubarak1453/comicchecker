package src.main.java.comicchecker;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.CheckBox;
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
    	Button but2 = new Button("Cancel");
    	Button but3 = new Button("Add Subscription");
    	Button but4 = new Button("Description");
    	Button but5 = new Button("Print");
    	Button but6 = new Button("Show");
    	Button but7 = new Button("Save");
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
    	gp.add(ok2,0,0,2,1);
    	//choice box
    	gp.add(new Label("Comic subscription list: "), 0, 1);
    	ChoiceBox<String> genreCB =  new ChoiceBox<>(FXCollections.observableArrayList(komik));
    	gp.add(genreCB, 1, 1);;
    	HBox tombol = new HBox(20);
    	tombol.getChildren().add(but2);
    	tombol.getChildren().add(but3);
    	tombol.getChildren().add(but4);
    	tombol.getChildren().add(but7);
    	tombol.setAlignment(Pos.CENTER);
    	Label labelresponse = new Label();
//    	but3.setOnAction(e -> 
//    	{
//    	labelresponse.setText("You chose " + genreCB.getValue());
//    	});
    	gp.add(tombol,0,2,2,1);
    	gp.add(labelresponse, 1, 5,2,1);
    	String bls;
    	bls = genreCB.getValue();
    	System.out.println(bls);
		Scene lay2 = new Scene(gp,500,500);
		
    	//scene 3
        HBox greetings = new HBox();
        greetings.setAlignment(Pos.CENTER);
        greetings.setSpacing(10);

        GridPane deskripsi = new GridPane();
        deskripsi.setAlignment(Pos.CENTER);
        deskripsi.setVgap(10);
        deskripsi.setHgap(10);
        deskripsi.setPadding(new Insets(25, 25, 25, 25));
        deskripsi.add(greetings, 0, 0,2,1);
        deskripsi.add(but6, 0, 1,2,1);
        Label namakom = new Label();
        but6.setOnAction(e -> 
    	{
    	namakom.setText(genreCB.getValue());
    	});
        deskripsi.add(new Label("Comic's Title: "), 0, 2);
        deskripsi.add(namakom, 1, 2);
        Scene lay3 = new Scene(deskripsi,500,500);
        
      //scene 4
        GridPane gps4 = new GridPane();
        gps4.setAlignment(Pos.CENTER);
        gps4.setVgap(10);
        gps4.setHgap(10);
        gps4.setPadding(new Insets(25, 25, 25, 25));
        
        gps4.add(new Label("Comic's Title: "), 0, 0);
        TextField tfs4 = new TextField();
        gps4.add(tfs4,1,0);
        
        String[] daftarWebsite = {
        		"https://mangakakalots.com",
        		"https://guya.moe",
        		"https://manganelo.com" };
        ChoiceBox<String> websiteCB =  new ChoiceBox<>(FXCollections.observableArrayList(daftarWebsite));
        gps4.add(websiteCB,2,0);
        
        HBox hbs4 = new HBox(20);
        hbs4.setAlignment(Pos.CENTER);
        Button okbutton = new Button("OK");
        Button cancelbutton = new Button("Cancel");
        hbs4.getChildren().add(cancelbutton);
        hbs4.getChildren().add(okbutton);
        gps4.add(hbs4,0,3,3,1);
        
        Scene scene4 = new Scene(gps4,500,500);
        		
      //next scene actions
    	but1.setOnAction(e -> primaryStage.setScene(lay2));
    	but2.setOnAction(e -> primaryStage.setScene(layout));
    	but3.setOnAction(e -> primaryStage.setScene(scene4));
    	but4.setOnAction(e -> primaryStage.setScene(lay3));
    	cancelbutton.setOnAction(e -> primaryStage.setScene(lay2));
           	
    	primaryStage.setScene(layout);
    	primaryStage.setTitle("Comic Checker");
    	primaryStage.show();
	}
	
	public static void main(String[] args) {
		Application.launch(args);
	}
}