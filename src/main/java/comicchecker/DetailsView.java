package comicchecker;

import java.awt.Desktop;
import java.net.URI;
import java.net.URL;

import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class DetailsView extends GridPane implements View {
	private SnippetView parentView;
	
	@FXML private ImageView thumbnail;
	@FXML private Text title;
	@FXML private Text description;
	@FXML private Text author;
	@FXML private Text genre;
	@FXML private Text updateChapter;
	@FXML private Text updateTime;
	@FXML private VBox sitesPane;
	
	public DetailsView(SnippetView parentView) {
		this.parentView = parentView;
		constructorHelper("/DetailsView.fxml");
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		refresh();
	}

	@Override
	public void refresh() {
		thumbnail.setImage(parentView.getImage());
		title.setText(parentView.getSnippet().getTitle());
		description.setText(parentView.getSnippet().getDescription().length() > 250 ?
				parentView.getSnippet().getDescription().substring(0, 250) : parentView.getSnippet().getDescription());
		author.setText(parentView.getSnippet().getAuthor());
		genre.setText(parentView.getSnippet().getGenre());
		updateChapter.setText(parentView.getSnippet().getUpdateChapter());
		updateTime.setText(parentView.getSnippet().getUpdateTime());
		
		sitesPane.getChildren().clear();
		for(String o : parentView.getSnippet().getUpdateSite()) {
			Hyperlink linkUpdateSite = new Hyperlink(o);
			linkUpdateSite.setStyle("-fx-font-size: 12;");
			linkUpdateSite.setOnAction( e -> {
				try {
					
					Desktop.getDesktop().browse(new URI(o));
					
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			});
			
			sitesPane.getChildren().add(linkUpdateSite);
		}
	}
	
	@FXML public void updateButtonClicked() {
		new Thread( () -> {
			parentView.getSnippet().update(app.getWebScraper());
			app.saveData();
			
			Platform.runLater( () -> {
				parentView.refresh();
				refresh();
			});
		}).start();
	}
	
	@FXML public void infoButtonClicked() {
		new Thread( () -> {
			parentView.getSnippet().checkInfo(app.getWebScraper());
			app.saveData();
			
			Platform.runLater( () -> {
				parentView.refresh();
				refresh();
			});
		}).start();
	}
	
	@FXML public void minusButtonClicked() {
		Stage stage = new Stage();
		parentView.addWindow(stage);
        stage.setTitle("Delete Confirmation");
        stage.initStyle(StageStyle.UNDECORATED);
        
        stage.setScene(new Scene(new AlertView("Are you sure to delete this subscription?"
        		, e -> {
        			new Thread( () -> {
        				app.deleteSubscription(parentView.getSnippet().getTitle());
        				parentView.getParentView().getSnippetViews().remove(parentView);
        				app.saveData();
        				
        				Platform.runLater( () -> {
        					parentView.getParentView().refresh();
        				});
        			}).start();
        			parentView.closeAllWindows();
        		}
        		, e -> {
        			stage.close();
        		}
        		)));
        
        stage.show();
	}
	
	@FXML public void avaibleSiteInfoButtonClicked(ActionEvent e) {
		Stage stage = new Stage();
		parentView.addWindow(stage);
        stage.setTitle("Avaible Site");
        stage.initStyle(StageStyle.UTILITY);
        stage.setScene(new Scene(new AvaibleUpdateSiteView(this)));
        stage.show();
	}
	
	public SnippetView getParentView() {
		return parentView;
	}
	
}