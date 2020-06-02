package comicchecker;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Class for application object
 * 
 * @author Agung Mubarak
 *
 */
public class ComicCheckerApplication {
	private List<UserData> listUserData;
	private UserData userData;
	private WebScraper webScraper;
	
	public ComicCheckerApplication() {
		// Initialize web scraper
		webScraper = new WebScraper();
		webScraper.addSite(new Type1("https://mangakakalots.com")
				, new Type2("https://guya.moe")
				, new Type3("https://manganelo.com"
				));
		
		// Load properties
		try {
			InputStream input = new FileInputStream("userpreferences//application.properties");
			
            Properties prop = new Properties();
            prop.load(input);

            // Case with saved list
            if(prop.getProperty("isListUserDataEmpty").equals("0")) {
            	// Load list object
            	try {
                    FileInputStream fileIn = new FileInputStream("userpreferences//listuserdata");
                    ObjectInputStream objectIn = new ObjectInputStream(fileIn);
         
                    List<UserData> obj = (List<UserData>) objectIn.readObject();
         
                    objectIn.close();
                    
                    listUserData = obj;
                    
                    // Case with no saved recent user data
                    if(prop.getProperty("recentIndexUserData").equals("null")) {
                    	userData = null;
                    // Case with saved recent user data
                    }else {
                    	userData = listUserData.get(Integer.parseInt(prop.getProperty("recentIndexUserData")));
                    }
                    
                } catch (Exception e) {
                    e.printStackTrace();
                }
            // Case with no saved list
            }else {
            	listUserData = new ArrayList<>();
            }
            	
        } catch (IOException ex) {
            ex.printStackTrace();
        }
	}
	
	public WebScraper getWebScraper() {
		return webScraper;
	}

	public void setWebScraper(WebScraper webScraper) {
		this.webScraper = webScraper;
	}
	
	public List<UserData> getListUserData() {
		return listUserData;
	}
	
	public void setListUserData(List<UserData> listUserData) {
		this.listUserData = listUserData;
	}

	/**
	 * Method for add user data
	 * 
	 * @param userName name of user
	 */
	public void addUserData(String userName) {
		userData = new UserData(userName);
		listUserData.add(userData);
	}
	
	/**
	 * Method for remove user data
	 * 
	 * @param userName name of user want to be removed
	 */
	public void removeUserData(String userName) {
		for(int i=0; i<listUserData.size(); i++) {
			if(listUserData.get(i).getName().equals(userName)) {
				listUserData.remove(i);
			}
		}
	}
	
	/**
	 * Method for set working user data
	 * 
	 * @param userName name of user
	 */
	public void setUserData(String userName) {
		for(UserData o : listUserData) {
			o.getName().equals(userName);
			userData = o;
		}
	}
	
	public UserData getUserData() {
		return userData;
	}

	public void setUserData(UserData userData) {
		this.userData = userData;
	}

	/**
	 * Method for save result of application working
	 */
	public void saveData() {
			// Playing with properties
			try (OutputStream output = new FileOutputStream("userpreferences//application.properties")) {
				Properties prop = new Properties();
				
				// Handle case list in not null
				if(!listUserData.isEmpty()) {
					prop.setProperty("isListUserDataEmpty", "0");
					// Save object in file
					try {
						FileOutputStream fileOut = new FileOutputStream("userpreferences//listuserdata" );
						ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
						objectOut.writeObject(listUserData);
						objectOut.close();
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				// Handle case list in null
				}else {
					prop.setProperty("isListUserDataEmpty", "1");
				}
				
				// Handle working user data not null
				if(userData != null) {
					int recentIndexOfUserData = listUserData.indexOf(userData);
					prop.setProperty("recentIndexUserData", String.valueOf(recentIndexOfUserData));
				// Handle working user data null
				}else {
					prop.setProperty("recentIndexUserData", "null");
				}
				
				prop.store(output, null);
			} catch (IOException io) {
				io.printStackTrace();
			}
	}

	/**
	 * Method for add subscription to working user data
	 * 
	 * @param title title of comic
	 * @param sites sites of want to be scraped
	 */
	public void addSubscription(String title, String... sites) {
		userData.addSubscription(title, sites);
	}
	
	/**
	 * Method for delete subscription for working user data
	 * 
	 * @param title title of comic
	 */
	public void deleteSubscription(String title) {
		userData.deleteSubscription(title);
	}
	
	/**
	 * Method for look update subscription in working user data
	 * <br><br>
	 * Note: mostly give data to one days ago in updating.
	 */
	public void updateSubscription() {
		userData.updateSubscription(webScraper);
	}
	
}