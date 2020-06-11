package comicchecker;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.controlsfx.control.textfield.TextFields;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class AddView extends GridPane implements View {
	private SubscriptionView parentView;
	
	@FXML private TextField titleField;
	@FXML private FlowPane sitesPane;
	
	public AddView(SubscriptionView parentView) {
		this.parentView = parentView;
		constructorHelper("/AddView.fxml");
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		refresh();
	}

	@Override
	public void refresh() {
		TextFields.bindAutoCompletion(titleField, app.getListComic());
		sitesPane.getChildren().clear();
		
		for(Site o : app.getWebScraper().listOfSite) {
			CheckBox siteCB = new CheckBox(o.getUrl());
			sitesPane.getChildren().add(siteCB);
		}
	}
	
	@FXML public void okButtonClicked(ActionEvent e){
		new Thread( () -> {
			List<String> sites = new ArrayList<>();
			for(Node o: sitesPane.getChildren()) {
				CheckBox checkBox = (CheckBox) o;
				if (checkBox.isSelected()) {
					sites.add(checkBox.getText());
				}
			}
			
			app.addSubscription(titleField.getText(), sites);
			parentView.getSnippetViews().add(new SnippetView(
					parentView, parentView.getSnippetViews().size()));
			app.saveData();
			
			Platform.runLater( () -> {
				parentView.refresh();
			});
			
		}).start();
		
		((Stage) ((Node) e.getSource()).getScene().getWindow()).close();
	}

	@FXML public void cancelButtonClicked(ActionEvent e){
		((Stage) ((Node) e.getSource()).getScene().getWindow()).close();
	}
	
	@FXML public void updateListComicClicked(ActionEvent e) {
		new Thread( () -> {
			app.searchListComicWithWebScraping();
			Platform.runLater( () -> {
				refresh();
			});
			
		}).start();
	}
	
}