package comicchecker;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.scene.control.CheckBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class AvaibleUpdateSiteView extends VBox implements View {
	private DetailsView parentView;

	public AvaibleUpdateSiteView(DetailsView parentView) {
		this.parentView = parentView;
		constructorHelper("/AvaibleUpdateSiteView.fxml");
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		refresh();
	}

	@Override
	public void refresh() {
		List<String> avaibleUpdateSite = parentView.getParentView().getSnippet().getAvaibleUpdateSite();
		
		for(Site o : app.getWebScraper().listOfSite) {
			CheckBox cb = new CheckBox(o.getUrl());
			cb.setTextFill(Color.rgb(255, 84, 192));
			
			for(String o2 : avaibleUpdateSite) {
				if(o.getUrl().equals(o2)) {
					cb.setSelected(true);
					break;
				}
			}
			
			cb.selectedProperty().addListener( (v, oldValue, newValue) ->{
				if(newValue == true) {
					avaibleUpdateSite.add(cb.getText());
					app.saveData();
				}else {
					avaibleUpdateSite.remove(cb.getText());
					app.saveData();
				}
			});
			
			this.getChildren().add(cb);
		}
	}

}
