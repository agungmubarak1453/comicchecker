package comicchecker;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class TimeSetView extends GridPane implements View {
	private SubscriptionView parentView;
	
	@FXML private TextField hoursField;
	@FXML private TextField minutesField;
	
	public TimeSetView(SubscriptionView parentView) {
		this.parentView = parentView;
		constructorHelper("/TimeSetView.fxml");
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		refresh();
	}

	@Override
	public void refresh() {
		hoursField.setText(String.valueOf(app.getHoursScheduledTime()));
		minutesField.setText(String.valueOf(app.getMinutesScheduledTime()));
		
		hoursField.textProperty().addListener( (v, oldValue, newValue) -> {
			
			if(!newValue.equals("0"))
			Platform.runLater(() -> { 
				 hoursField.setText(newValue.replaceAll("^0+", ""));
		    });
			
			if(newValue.matches("\\d+")) {
				int value = Integer.parseInt(newValue);
				
				if(value > 23 || value < 0)
				Platform.runLater(() -> {
					hoursField.setText("0");
			    });
				
			}else {
				Platform.runLater(() -> {
					hoursField.setText("0");
			    });
			}
		});
		
		minutesField.textProperty().addListener( (v, oldValue, newValue) -> {
			if(!newValue.equals("0"))
			Platform.runLater(() -> { 
				minutesField.setText(newValue.replaceAll("^0+", ""));
		    });
			
			if(newValue.matches("\\d+")) {
				int value = Integer.parseInt(newValue);
				if(value > 59 || value < 0) 
				Platform.runLater(() -> { 
					minutesField.setText("0");
			    });
			}else {
				Platform.runLater(() -> {
					minutesField.setText("0");
			    });
			}
		});
	}

	@FXML public void secondDigitHoursUpClicked(ActionEvent e) {
		modifierTextField(hoursField, 10);
	}
	
	@FXML public void secondDigitHoursDownClicked(ActionEvent e) {
		modifierTextField(hoursField, -10);
	}
	
	@FXML public void firstDigitHoursUpClicked(ActionEvent e) {
		modifierTextField(hoursField, 1);
	}
	
	@FXML public void firstDigitHoursDownClicked(ActionEvent e) {
		modifierTextField(hoursField, -1);
	}
	
	
	@FXML public void secondDigitMinutesUpClicked(ActionEvent e) {
		modifierTextField(minutesField, 10);
	}
	
	@FXML public void secondDigitMinutesDownClicked(ActionEvent e) {
		modifierTextField(minutesField, -10);
	}
	
	@FXML public void firstDigitMinutesUpClicked(ActionEvent e) {
		modifierTextField(minutesField, 1);
	}
	
	@FXML public void firstDigitMinutesDownClicked(ActionEvent e) {
		modifierTextField(minutesField, -1);
	}
	
	@FXML public void applyButtonClicked(ActionEvent e) {
		app.setScheduledTime(Integer.parseInt(hoursField.getText()), Integer.parseInt(minutesField.getText()));
		Stage window = (Stage) ((Node)e.getSource()).getScene().getWindow();
		window.close();
		app.frequentlyUpdateSubscription();
		app.saveData();
	}
	
	public void modifierTextField(TextField textField, int modifier) {
		int value = Integer.parseInt(textField.getText());
		value += modifier;
		textField.setText(String.valueOf(value));
	}
}
