package comicchecker;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;

import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
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
	
	private SubscriptionView parentView = null;
	private DetailsView childView = null;
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
		new Thread( () -> {
			snippet.update(app.getWebScraper());
			app.saveData();
			
			Platform.runLater( () -> {
				refresh();
			});
		}).start();
	}
	
	@FXML public void infoButtonClicked() {
		new Thread( () -> {
			snippet.checkInfo(app.getWebScraper());
			app.saveData();
			
			Platform.runLater( () -> {
				refresh();
			});
		}).start();
	}
	
	@FXML public void minusButtonClicked() {
		Stage stage = new Stage();
		addWindow(stage);
        stage.setTitle("Delete Confirmation");
        stage.initStyle(StageStyle.UNDECORATED);
        
        stage.setScene(new Scene(new AlertView("Are you sure to delete this subscription?"
        		, e -> {
        			new Thread( () -> {
        				app.deleteSubscription(snippet.getTitle());
        				parentView.getSnippetViews().remove(this);
        				app.saveData();
        				
        				Platform.runLater( () -> {
        					parentView.refresh();
        				});
        			}).start();
        			
        			closeAllWindows();
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
        childView = (new DetailsView(this));
        stage.setScene(new Scene(childView));
        stage.show();
	}
	
	public SubscriptionView getParentView() {
		return parentView;
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
		try {
			image = cacheImage(snippet.getThumbnail());
			thumbnail.setImage(image);
		} catch (Exception e) {
			e.printStackTrace();
			image = thumbnail.getImage();
			thumbnail.setImage(image);
		}
		
		
		String text = "";
		text += snippet.getTitle() + "\n";
		boolean firstItem = true;
		for(String o : snippet.getUpdateChapter()) {
			text += (firstItem ? "" : ", ") + o;
			if(firstItem == true) firstItem = false;
		}
		text += "\n";
		firstItem = true;
		for(String o : snippet.getUpdateTime()) {
			text += (firstItem ? "" : ", ") + o;
			if(firstItem == true) firstItem = false;
		}
		
		snippetText.setText(text);
		
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
		
		if (childView != null) childView.refresh();
	}
	
	public Image cacheImage(String thumbnail) throws IOException 
	{
		String imageFileName = thumbnail.replaceAll("\\W", "");
		
		if(imageFileName.isEmpty()) {
			throw new IOException("Url is empty");
		}
		
		File imageFile = new File("imgcache/" + imageFileName);
		if(imageFile.createNewFile()) {
			Image imageJavaFX = new Image(thumbnail);
			
			try {
				ImageIO.write(SwingFXUtils.fromFXImage(imageJavaFX, null), "jpg", imageFile);
			} catch (Exception e) {
				imageFile.delete();
				throw e;
			}
				
			return imageJavaFX;
		}else {
			return new Image(imageFile.toURI().toString());
		}
	}
	
}