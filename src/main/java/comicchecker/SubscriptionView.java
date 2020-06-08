package comicchecker;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Pagination;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class SubscriptionView extends BorderPane implements View {
	private View parentView;
	private List<SnippetView> snippetViews = new ArrayList<>();
	private List<Stage> windows = new ArrayList<>();
	@FXML ComboBox<UserData> workingUserDataCB;
	@FXML Pagination subscriptionPage;
	
	@Override
	public void initialize (URL location, ResourceBundle resources) {
		workingUserDataCB.getItems().setAll(app.getListUserData());
		workingUserDataCB.setValue(app.getUserData());
		
		workingUserDataCB.valueProperty().addListener( (v, oldValue, newValue) -> {
			if(newValue != oldValue) {
				closeAllWindows();
				app.setUserData(newValue);
				refreshSnippetViews();
				refresh();
			}
		});
		
		refreshSnippetViews();
		refresh();
	}
	
	@FXML public void allUpdateButtonClicked(ActionEvent e) {
		app.updateSubscription();
		refresh();
	}
	
	@FXML public void addComicButtonClicked(ActionEvent e) {
		Stage stage = new Stage();
        addWindow(stage);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setTitle("Add Comic");
        stage.setScene(new Scene(new addView(this)));
        stage.show();
	}
	
	@FXML public void backButtonClicked(ActionEvent e) {
		changeScene(e, "/HomeView.fxml");
	}
	
	@FXML public void timeSetButtonClicked(ActionEvent e) {
		Stage stage = new Stage();
        addWindow(stage);
        stage.initStyle(StageStyle.UTILITY);
        stage.setTitle("Set Time View");
        stage.setScene(new Scene(new TimeSetView(this)));
        stage.show();
	}

	public List<SnippetView> getSnippetViews() {
		return snippetViews;
	}

	public void setSnippetViews(List<SnippetView> snippetViews) {
		this.snippetViews = snippetViews;
	}

	public void addWindow(Stage window) {
		windows.add(window);
	}
	
	public void closeAllWindows() {
		for(Stage o : windows) {
			o.close();
		}
	}
	
	public void refreshSnippetViews() {
		snippetViews.clear();
		for(int i=0; i<app.getUserData().getListOfSubscription().size() ; i++) {
			snippetViews.add(new SnippetView(this, i));
		}
	}
	
	@Override
	public void refresh() {
		int currentPage = subscriptionPage.getCurrentPageIndex();
		subscriptionPage.setPageCount(
				(snippetViews.size() % 9 > 0 ? 1 : 0)
				+ snippetViews.size() / 9
				);
		
		System.out.println(subscriptionPage.getPageCount() + " " + currentPage);
		
		subscriptionPage.setPageFactory((pageIndex) -> {
			
			try {
				
				Pane listOfSubscriptionPane = FXMLLoader.load(getClass()
						.getResource("/ListOfSubscriptionView.fxml"));
				
				int lastIndexUsually = pageIndex * 9 + 9;
				int lastIndex = (lastIndexUsually < snippetViews.size() ? lastIndexUsually : snippetViews.size());
				for(int i=pageIndex*9;
						i < lastIndex; 
						i++){
					listOfSubscriptionPane.getChildren().add(snippetViews.get(i));
				}
				
				return listOfSubscriptionPane;
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			return null;
		});
		
		if(subscriptionPage.getPageCount() <= currentPage) {
			subscriptionPage.setCurrentPageIndex(subscriptionPage.getPageCount() - 1);
		}else {
			subscriptionPage.setCurrentPageIndex(currentPage);
		}
	}

}