package comicchecker;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class AddView extends GridPane implements View {
	private SubscriptionView parentView;
	@FXML private ComboBox titleCB;
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
		titleCB.getItems().setAll(app.getListComic());
		sitesPane.getChildren().clear();
		
		for(Site o : app.getWebScraper().listOfSite) {
			CheckBox siteCB = new CheckBox(o.getUrl());
			sitesPane.getChildren().add(siteCB);
		}
	}
	
	@FXML public void okButtonClicked(ActionEvent e){
		List<String> sites = new ArrayList<>();
		
		for(Node o: sitesPane.getChildren()) {
			CheckBox checkBox = (CheckBox) o;
			if (checkBox.isSelected()) {
				sites.add(checkBox.getText());
			}
		}
		
		app.addSubscription(titleCB.getValue().toString(), sites);
		((Stage) ((Node) e.getSource()).getScene().getWindow()).close();
		app.saveData();
		parentView.getSnippetViews().add(new SnippetView(
				parentView, app.getUserData().getListOfSubscription().size() - 1));
		parentView.refresh();
	}

	@FXML public void cancelButtonClicked(ActionEvent e){
		((Stage) ((Node) e.getSource()).getScene().getWindow()).close();
	}
	
	@FXML public void updateListComicClicked(ActionEvent e) {
		app.searchListComicWithWebScraping();
		refresh();
	}
}