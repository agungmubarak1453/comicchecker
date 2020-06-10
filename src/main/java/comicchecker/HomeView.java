package comicchecker;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class HomeView extends StackPane implements View{
	@FXML private ComboBox<UserData> listUserDataCB;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		refresh();
		
		if(app.getUserData() != null) {
			listUserDataCB.setValue(app.getUserData());
		}
	}
	
	@FXML public void enterAddUserButtonClicked(ActionEvent e) {
		Object stringObject = (Object) listUserDataCB.getValue();
		String name = stringObject.toString();
		
		for(UserData o: app.getListUserData()) {
			if(name.equals(o.getName())) {
				app.setUserData(o);
				SubscriptionView.setMainWindow((Stage) ((Node)e.getSource()).getScene().getWindow());
				changeScene(e, "/SubscriptionView.fxml");
				app.saveData();
				return;
			}
		}
		
		if(!name.equals("")) {
			app.addUserData(name);
			SubscriptionView.setMainWindow((Stage) ((Node)e.getSource()).getScene().getWindow());
			changeScene(e, "/SubscriptionView.fxml");
			app.saveData();
		}
	}
	
	@FXML public void removeUserButtonClicked(ActionEvent e) {
		Object stringObject = (Object) listUserDataCB.getValue();
		String name = stringObject.toString();
		
		for(UserData o: app.getListUserData()) {
			if(name.equals(o.getName())) {
				app.removeUserData(name);
				refresh();
				app.saveData();
				return;
			}
		}
	}

	@Override
	public void refresh() {
		listUserDataCB.getItems().setAll(app.getListUserData());
	}

}