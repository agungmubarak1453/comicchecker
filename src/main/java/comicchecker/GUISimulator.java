package comicchecker;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Hyperlink;
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

import java.awt.Desktop;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

/**
 * Class for build GUI of this application
 * 
 * @author Agung Mubarak
 *
 */
public class GUISimulator extends Application{
	private ComicCheckerApplication app;
	
	public GUISimulator() {
		ComicCheckerApplication app = new ComicCheckerApplication();
		
		
	}
	ArrayList<String> daftarKomik = new ArrayList<String>();
	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO: Be creative guys!
		Text ok = new Text("Input your name!");
    	Text ok2 = new Text("Welcome to comic checker");
    	Button but1 = new Button("Next");
    	Button but2 = new Button("Cancel");
    	Button but3 = new Button("Add");
    	Button but4 = new Button("Description");
    	Button but5 = new Button("Print");
    	Button but6 = new Button("Show");
    	Button but7 = new Button("Save");
    	Button but8 = new Button("Back");
    	Button deletebutton = new Button("Delete");
    	String[] daftarWebsite = {
        		"https://mangakakalots.com",
        		"https://guya.moe",
        		"https://manganelo.com",
        		"https://www.webtoons.com/en/",
        		"https://www.webtoons.com/en/"};
        ChoiceBox<String> websiteCB =  new ChoiceBox<>(FXCollections.observableArrayList(daftarWebsite));
    	//scene 1
        TextField name = new TextField();
    	VBox scene1 = new VBox(10,ok,name,but1);
    	scene1.setAlignment(Pos.CENTER);
    	Scene layout = new Scene(scene1,500,500);
    	//scene2
    	GridPane gp = new GridPane();
        gp.setAlignment(Pos.CENTER);
        gp.setVgap(10);
        gp.setHgap(10);
        gp.setPadding(new Insets(25, 25, 25, 25));
    	gp.add(ok2,0,0,2,1);
    	but1.setOnAction(e -> 
    	{
    	UserData user1 = new UserData(name.getText());
    	});
    	//choice box
    	gp.add(new Label("Comic subscription list: "), 0, 1);
    	ComboBox<String> genreCB =  new ComboBox<String>(FXCollections.observableArrayList(daftarKomik));
    	gp.add(genreCB, 1, 1);;
    	HBox tombol = new HBox(20);
    	tombol.getChildren().add(but2);
    	tombol.getChildren().add(but3);
    	tombol.getChildren().add(but4);
    	tombol.getChildren().add(deletebutton);
    	tombol.getChildren().add(but7);
    	tombol.setAlignment(Pos.CENTER);
    	gp.add(tombol,0,2,2,1);
    	//gp.add(labelresponse, 1, 5,2,1);
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
        deskripsi.add(but6, 0, 1,3,1);
        
        Label namakomik = new Label();
        Hyperlink link = new Hyperlink();
        Label desc = new Label();
        
        
        	
        
        but6.setOnAction(e -> 
    	{
    	namakomik.setText(genreCB.getValue());
    	link.setText(websiteCB.getValue());
    	Snippet komik = new Snippet(app.getWebScraper(),genreCB.getValue() , websiteCB.getValue() );
    	desc.setText(komik.getDescription());
    	});
        
        deskripsi.add(new Label("Nama Komik: "), 0, 2);
        deskripsi.add(namakomik, 1, 2);
        deskripsi.add(new Label("Web: "), 0, 3);
        deskripsi.add(link, 1, 3);
        deskripsi.add(new Label("Description: "), 0, 4);
        
        //deskripsi.add(child, columnIndex, rowIndex);
        Desktop d = Desktop.getDesktop();
        deskripsi.add(but5, 2, 3);
        deskripsi.add(but8, 0, 5);
        
        but5.setOnAction(e -> 
    	{
    	try {
			d.browse(new URI("www.google.com"));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (URISyntaxException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    	});
        
        
        Scene lay3 = new Scene(deskripsi,500,500);
      //scene 4
        String[] options = {"Naruto",
                "Boruto",
                "Narto",
                "Borto",
                "Parto",
                "Karto",
                "Jarto",
                "Inuyasha",
                "Eyeshield 21"};
        
        String[] daftarWebsite = {
        		"https://mangakakalots.com",
        		"https://guya.moe",
        		"https://manganelo.com",
        		"https://www.webtoons.com/en/",
				"https://www.webtoons.com/en/"};
      	ChoiceBox<String> websiteCB =  new ChoiceBox<>(FXCollections.observableArrayList(daftarWebsite));
      	
        StackPane root = new StackPane();
        GridPane container = new GridPane();
        container.setAlignment(Pos.CENTER);
        HBox searchBox = new HBox(10);

        TextField text = new TextField();


        text.textProperty().addListener((observable, oldValue, newValue) -> {
            if(container.getChildren().size()>1){
                container.getChildren().remove(1);
            }
            container.add(populateDropDownMenu(newValue, options),0,1); 
        });

        Button cancelbutton = new Button("Cancel");
        Button okbutton = new Button("OK");
        
        okbutton.setOnAction(e -> {
        	String title = text.getText();
        	String website = websiteCB.getValue();
        	daftarKomik.add(title);
        	genreCB.getItems().add(title);
        	app.addSubscription(title,website);
        	});
        
        searchBox.getChildren().addAll(text,websiteCB,cancelbutton,okbutton);

        container.add(searchBox, 0, 0);

        root.getChildren().add(container);
        Scene scene4 = new Scene(root,500,500);
        
        
      //button command actions
        deletebutton.setOnAction(e -> {
        	String title = genreCB.getValue();
        	genreCB.getItems().remove(title);
        	app.deleteSubscription(title);
        });
        
        		
      //next scene actions
    	but1.setOnAction(e -> primaryStage.setScene(lay2));
    	but2.setOnAction(e -> primaryStage.setScene(layout));
    	but3.setOnAction(e -> primaryStage.setScene(scene4));
    	but4.setOnAction(e -> primaryStage.setScene(lay3));
    	cancelbutton.setOnAction(e -> primaryStage.setScene(lay2));
    	but8.setOnAction(e -> primaryStage.setScene(lay2));
           	
    	primaryStage.setScene(layout);
    	primaryStage.setTitle("Comic Checker");
    	primaryStage.show();
	}
	
	public static VBox populateDropDownMenu(String text, String[] options){
        VBox dropDownMenu = new VBox();
        dropDownMenu.setBackground(new Background(new BackgroundFill(Color.WHITE, null,null))); // colors just for example
        dropDownMenu.setAlignment(Pos.CENTER);

        for(String option : options){ 
            if(!text.replace(" ", "").isEmpty() && option.toUpperCase().contains(text.toUpperCase())){ 
                Label label = new Label(option);  
                dropDownMenu.getChildren().add(label); 
            }
        }

        return dropDownMenu;
	}
	
	public static void main(String[] args) {
		Application.launch(args);
	}
}