package comicchecker;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class SnippetView extends GridPane implements View{
	private Snippet snippet = null;
	private SubscriptionView parentView;
	private List<Stage> windows = new ArrayList<>();
	@FXML private Text snippetText;
	@FXML private ImageView thumbnail;
	private Image image;
	@FXML private VBox linkPane;
	
	public SnippetView(SubscriptionView parentView, int snippetIndex) {
		snippet = app.getUserData().getListOfSubscription().get(snippetIndex);
		this.parentView = parentView;
		constructorHelper("/SnippetViewTest.fxml");
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		refresh();
	}
	
	@FXML public void updateButtonClicked() {
		snippet.update(app.getWebScraper());
		app.saveData();
		refresh();
	}
	
	@FXML public void infoButtonClicked() {
		snippet.checkInfo(app.getWebScraper());
		app.saveData();
		refresh();
	}
	
	@FXML public void minusButtonClicked() {
		Stage stage = new Stage();
		addWindow(stage);
        stage.setTitle("Delete Confirmation");
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(new Scene(new AlertView("Are you sure to delete this subscription?"
        		, e -> {
        			app.deleteSubscription(snippet.getTitle());
        			app.saveData();
        			closeAllWindows();
        			parentView.getSnippetViews().remove(this);
        			parentView.refresh();
        		}
        		, e -> {
        			stage.close();
        		}
        		)));
        stage.show();
	}
	
	@FXML public void snippetClicked() {
        Stage stage = new Stage();
        addWindow(stage);
        stage.initStyle(StageStyle.UTILITY);
        stage.setTitle("Details View");
        stage.setScene(new Scene(new DetailsView(this)));
        stage.show();
	}
	
	public Snippet getSnippet() {
		return snippet;
	}
	
	public Image getImage() {
		return image;
	}
	
	public void addWindow(Stage window) {
		windows.add(window);
		parentView.addWindow(window);
	}
	
	public void closeAllWindows() {
		for(Stage o : windows) {
			o.close();
		}
	}
	
	@Override
	public void refresh() {
		System.out.println(snippet.getThumbnail());
		image = new Image(snippet.getThumbnail());
		thumbnail.setImage(image);
		snippetText.setText(snippet.getTitle()
				+ (snippet.getUpdateChapter().equals("") ? "" : "\n" + snippet.getUpdateChapter())
				+ (snippet.getUpdateTime().equals("") ? "" : "\n" + snippet.getUpdateTime())
				);
		
		linkPane.getChildren().clear();
		for(String o : snippet.getUpdateSite()) {
			
			Hyperlink linkUpdateSite = new Hyperlink(o.replaceAll("https:\\/\\/|\\/.*", ""));
			linkUpdateSite.setStyle("-fx-font-size: 9;");
			linkUpdateSite.setOnAction( e -> {
				try {
					
					Desktop.getDesktop().browse(new URI(o));
					
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			});
			
			linkPane.getChildren().add(linkUpdateSite);
		}
	}
	
}