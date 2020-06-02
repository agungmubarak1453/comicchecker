package comicchecker;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Class for store user data
 * 
 * @author Agung Mubarak
 *
 */
public class UserData implements Serializable{
	private String name;
	private List<Snippet> listOfSubscription;
	
	/**
	 * Constructor method
	 * 
	 * @param name user name
	 */
	public UserData(String name){
		this.name = name;
		listOfSubscription = new ArrayList<>();
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Snippet> getListOfSubscription() {
		return listOfSubscription;
	}
	
	public void setListOfSubscription(List<Snippet> listOfSubscription) {
		this.listOfSubscription = listOfSubscription;
	}
	
	public void addSubscription(String title, String... sites) {
		listOfSubscription.add(new Snippet(title, sites));
	}
	
	public void deleteSubscription(String title) {
		if(listOfSubscription.isEmpty()) {
			return;
		}
		
		for(int i=0; i<listOfSubscription.size(); i++) {
			if(listOfSubscription.get(i).getTitle().equals(title)) {
				listOfSubscription.remove(i);
			}
		}
	}
	
	/**
	 * Method for update every subscription
	 * 
	 * @param webScraper machine of web scraping
	 */
	public void updateSubscription(WebScraper webScraper) {
		for(Snippet o : listOfSubscription) {
			o.update(webScraper);
		}
	}
}