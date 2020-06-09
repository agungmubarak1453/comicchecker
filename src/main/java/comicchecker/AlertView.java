package comicchecker;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

public class AlertView extends GridPane implements View {
	@FXML private Text message;
	@FXML private Button oKButton;
	@FXML private Button cancelButton;
	
	public AlertView(String message, EventHandler<ActionEvent> okEvent, EventHandler<ActionEvent> cancelEvent) {
		constructorHelper("/AlertView.fxml");
		this.message.setText(message);
		oKButton.setOnAction(okEvent);
		cancelButton.setOnAction(cancelEvent);
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
	}

	@Override
	public void refresh() {
	}

}