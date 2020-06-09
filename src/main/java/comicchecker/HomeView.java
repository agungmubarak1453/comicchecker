package comicchecker;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.StackPane;

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
				app.saveData();
				System.out.println(app.getUserData().getListOfSubscription());
				refresh();
				changeScene(e, "/SubscriptionView.fxml");
				return;
			}
		}
		
		app.addUserData(name);
		app.saveData();
		refresh();
		changeScene(e, "/SubscriptionView.fxml");
	}
	
	@FXML public void removeUserButtonClicked(ActionEvent e) {
		Object stringObject = (Object) listUserDataCB.getValue();
		String name = stringObject.toString();
		
		for(UserData o: app.getListUserData()) {
			if(name.equals(o.getName())) {
				app.removeUserData(name);
				app.saveData();
				refresh();
				return;
			}
		}
	}

	@Override
	public void refresh() {
		listUserDataCB.getItems().setAll(app.getListUserData());
	}

}