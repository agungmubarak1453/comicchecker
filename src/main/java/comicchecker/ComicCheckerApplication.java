package comicchecker;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
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
		try (InputStream input = new FileInputStream("userpreferences//application.properties")) {

            Properties prop = new Properties();

            // load a properties file
            prop.load(input);

            if(prop.getProperty("isnotempty").equals("1")) {
            	
            }
            	
        } catch (IOException ex) {
            ex.printStackTrace();
        }
	}
	
	public void addUserData(String userName) {
		userData = new UserData(userName);
		listUserData.add(userData);
	}
	
	public void removeUserData(String userName) {
		if(listUserData.isEmpty()) {
			return;
		}
		
		for(int i=0; i<listUserData.size(); i++) {
			if(listUserData.get(i).getName().equals(userName)) {
				listUserData.remove(i);
			}
		}
	}
	
	public void setUserData(String userName) {
		for(UserData o : listUserData) {
			o.equals(userName);
			userData = o;
		}
	}
	
	public void saveUserData() {
			if(listUserData.isEmpty()) {
				return;
			}
		
			try {
				FileOutputStream fileOut = new FileOutputStream("userpreferences//listuserdata" );
				ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
				objectOut.writeObject(listUserData);
				objectOut.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			
			try (OutputStream output = new FileOutputStream("userpreferences//application.properties")) {
				
				Properties prop = new Properties();
				
				prop.setProperty("isListUserDataEmpty", "0");
				if(userData != null) {
					int recentIndexOfUserData = listUserData.indexOf(userData);
					prop.setProperty("recentIndexUserData", String.valueOf(recentIndexOfUserData));
				}
				
				// save properties to project root folder
				prop.store(output, null);
				
				System.out.println(prop);
			} catch (IOException io) {
				io.printStackTrace();
			}
	}
	
	public void addSubscription(String title, String... sites) {
		userData.addSubscription(title, sites);
	}
	
	public void deleteSubscription(String title) {
		userData.deleteSubscription(title);
	}
	
	 public static void main(String[] args) {

        try (OutputStream output = new FileOutputStream("userpreferences//application.properties")) {

            Properties prop = new Properties();

            // set the properties value
            prop.setProperty("isnotempty", "1");
            // save properties to project root folder
            prop.store(output, null);

            System.out.println(prop);
        } catch (IOException io) {
            io.printStackTrace();
        }
        
        try (InputStream input = new FileInputStream("userpreferences//application.properties")) {

            Properties prop = new Properties();

            // load a properties file
            prop.load(input);

            if(prop.getProperty("isnotempty").equals("1")) {
            	System.out.println("Dapat");
            }

        } catch (IOException ex) {
            ex.printStackTrace();
        }
        
    }
}