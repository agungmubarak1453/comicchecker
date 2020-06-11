package comicchecker;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Properties;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Class for application object
 * <br><br>
 * Make sure {@link #listUserData} not empty (please remember this application can saving their data)
 * before use configuration method for subscription. If {@link #listUserData} is empty, use configuration method
 * for user data. Before exiting very recommend to access method {@link #saveData()}.
 * <br><br>
 * <b>Field:</b><br>
 * - {@link #webScraper}<br>
 * - {@link #listComic}<br>
 * - {@link #listUserData}<br>
 * - {@link #userData}
 * 
 * @author Agung Mubarak
 *
 */
public class ComicCheckerApplication {
	private int hoursScheduledTime = 14;
	private int minutesScheduledTime = 0;
	
	private WebScraper webScraper;
	private List<String> listComic;
	private transient List<UserData> listUserData;
	private UserData userData;
	
	/**
	 * This constructor access /userpreferences for find recent configuration
	 */
	public ComicCheckerApplication() {
		// Initialize web scraper
		webScraper = new WebScraper();
		webScraper.addSite(new Type1("https://mangakakalots.com")
				, new Type2("https://guya.moe")
				, new Type3("https://manganelo.com")
				, new WebtoonEn("https://webtoons.com/en")
				, new WebtoonId("https://webtoons.com/id")
				);
		
		
		listComic = new ArrayList<>();
		checkDirectory();
		searchListComicInLocal();
		loadData();
	}
	
	// Group of method getter and setter
	
	public int getHoursScheduledTime() {
		return hoursScheduledTime;
	}

	public int getMinutesScheduledTime() {
		return minutesScheduledTime;
	}

	public void setScheduledTime(int hoursScheduledTime, int minutesScheduledTime) {
		this.hoursScheduledTime = hoursScheduledTime;
		this.minutesScheduledTime = minutesScheduledTime;
	}
	
	public WebScraper getWebScraper() {
		return webScraper;
	}

	public void setWebScraper(WebScraper webScraper) {
		this.webScraper = webScraper;
	}
	
	public List<String> getListComic() {
		return listComic;
	}

	public void setListComic(List<String> listComic) {
		this.listComic = listComic;
	}

	public List<UserData> getListUserData() {
		return listUserData;
	}
	
	public void setListUserData(List<UserData> listUserData) {
		this.listUserData = listUserData;
	}

	public UserData getUserData() {
		return userData;
	}
	
	public void setUserData(UserData userData) {
		this.userData = userData;
	}
	
	// Group of configuration method for list comic
	
	/**
	 * Method for get list comic data from local
	 */
	public void searchListComicInLocal() {
		try {
			File listComicFile = new File("userpreferences/listofcomic.txt");
			
			if(!listComicFile.createNewFile()) {
				BufferedReader br = new BufferedReader(new FileReader("userpreferences/listofcomic.txt"));
				String input = "";
				while( (input = br.readLine()) != null) {
					if(!input.equals("")) listComic.add(input);
				}
				
				br.close();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Method for get comic data from internet.
	 * 
	 * @see WebScraper#searchListComic(PrintWriter)
	 */
	public void searchListComicWithWebScraping() {
		try {
			
			// Result of searching from internet is saved in local for efficiently.
			PrintWriter pw = new PrintWriter(new FileWriter(new File("userpreferences/listofcomic.txt"), false));
			
			List<String> result = webScraper.searchListComic(pw);
			if(!result.isEmpty()) {
				listComic = result;
			}
			
			pw.close();
			
		} catch (Exception e){
			e.printStackTrace();
		}
	}
	
	// Group of configuration method for user data
	
	/**
	 * Method for add user data
	 * <br><br>
	 * This method automically set the new user data as working user data
	 * 
	 * @see UserData
	 * @param userName name of user
	 */
	public void addUserData(String userName) {
		userData = new UserData(userName);
		listUserData.add(userData);
	}
	
	/**
	 * Method for remove user data
	 * 
	 * @see UserData
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
	 * @see UserData
	 * @param userName name of user
	 */
	public void setUserData(String userName) {
		for(UserData o : listUserData) {
			o.getName().equals(userName);
			userData = o;
		}
	}
	
	// Group of configuration method for subScription
	
	/**
	 * Method for add subscription to working user data
	 * 
	 * @see UserData
	 * @param title title of comic
	 * @param sites sites of want to be scraped
	 */
	public void addSubscription(String title, String... sites) {
		userData.addSubscription(webScraper, title, sites);
	}
	
	/**
	 * Method for add subscription to working user data
	 * 
	 * @see UserData
	 * @param title title of comic
	 * @param sites sites of want to be scraped
	 */
	public void addSubscription(String title, List<String> sites) {
		userData.addSubscription(webScraper, title, sites);
	}
	
	/**
	 * Method for delete subscription for working user data
	 * 
	 * @see UserData
	 * @param title title of comic
	 */
	public void deleteSubscription(String title) {
		userData.deleteSubscription(title);
	}
	
	/**
	 * Method for look update subscription in working user data
	 * <br><br>
	 * This method can bring notification. Notification method write in {@link UserData#updateSubscription(WebScraper)}.
	 * <br><br>
	 * Note: mostly give data to one days ago in updating.
	 * 
	 * @see UserData
	 */
	public void updateSubscription() {
		userData.updateSubscription(webScraper);
	}
	
	/**
	 * Method for frequently look update subscription in working user data
	 * <br><br>
	 * This method can bring notification. Notification method write in {@link UserData#updateSubscription(WebScraper)}.
	 * This method also save data after update.
	 * <br><br>
	 * Note: For now this is fixed in every one day for fixed time
	 * 
	 * @see UserData
	 * @param hours 24 hour-clock format of time want to be scheduled
	 * @param minutes minutes of time want to be scheduled
	 * @param terminateAfterMinutes how minutes application can exit after updating
	 */
	public void frequentlyUpdateSubscription(int terminateAfterMinutes) {
		// creating timer task
		Timer timer = new Timer();  
		
		TimerTask timerTask = new TimerTask() {  
			@Override  
			public void run() {  
				loadData();
				if(userData != null) {
					updateSubscription();
					saveData();
				}
			};
		};
		
		TimerTask timerCloseProgram = new TimerTask() {  
			@Override  
			public void run() {  
				System.exit(0);
			};
		};
		
		Calendar time = Calendar.getInstance();
		time.set(Calendar.HOUR_OF_DAY, hoursScheduledTime);
		time.set(Calendar.MINUTE, minutesScheduledTime);
		System.out.println(time.getTime());
		
		timer.schedule(timerTask, time.getTime());
		
		time.add(Calendar.MINUTE, terminateAfterMinutes);
		System.out.println(time.getTime());
		
		timer.schedule(timerCloseProgram, time.getTime());
	}
	
	/**
	 * Method for frequently look update subscription in working user data (modified for GUI)
	 * <br><br>
	 * This method can bring notification. Notification method write in {@link UserData#updateSubscription(WebScraper)}.
	 * This method also save data after update.
	 * <br><br>
	 * Note: For now this is fixed in every one day for fixed time
	 * 
	 * @see UserData
	 * @param hours 24 hour-clock format of time want to be scheduled
	 * @param minutes minutes of time want to be scheduled
	 * @param terminateAfterMinutes how minutes application can exit after updating
	 */
	public void frequentlyUpdateSubscription() {
		// creating timer task
		Timer timer = new Timer();  
		
		TimerTask timerTask = new TimerTask() {  
			@Override  
			public void run() {  
				updateSubscription();
				saveData();
			};
		};
		
		Calendar time = Calendar.getInstance();
		time.set(Calendar.HOUR_OF_DAY, hoursScheduledTime);
		time.set(Calendar.MINUTE, minutesScheduledTime);
		System.out.println(time.getTime());
		
		timer.schedule(timerTask, time.getTime());
	}
	
	// Opening method
	/**
	 * Method for check directory and create it if doesn't exist
	 */
	public void checkDirectory() {
		File dir1 = new File("userpreferences");
		File dir2 = new File("imgcache");
		dir1.mkdir();
		dir2.mkdir();
		
		if(new File("Read this! for daily updating.txt").exists()) {
			return;
		}
		
		try {
			InputStream guideFile = getClass().getResourceAsStream("/guide/Read this! for daily updating.txt");
			Path copied = Paths.get("Read this! for daily updating.txt");
			Files.copy(guideFile, copied);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Method for load data from local
	 */
	public void loadData() {
		// Load properties
		try {
		
			InputStream input = new FileInputStream("userpreferences/application.properties");
			
            Properties prop = new Properties();
            prop.load(input);
            
            // Load scheduledTime
            hoursScheduledTime = Integer.parseInt(prop.getProperty("hoursScheduledTime"));
            minutesScheduledTime = Integer.parseInt(prop.getProperty("minutesScheduledTime"));

            // Case with saved list
            if(prop.getProperty("isListUserDataEmpty").equals("0")) {
            	// Load list object
            	try {
                    InputStream fileIn = new FileInputStream("userpreferences/listuserdata.ser");
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
                // Exception for error in load listUserData object    
                } catch (Exception e) {
                	e.printStackTrace();
                    listUserData = new ArrayList<>();
                }
            // Case with no saved list
            }else {
            	listUserData = new ArrayList<>();
            }
        // Exception in properties like file not found or properties have weird content
        } catch (Exception ex) {
            ex.printStackTrace();
            listUserData = new ArrayList<>();
        }
	}
	
	// Exit method
	/**
	 * Method for save result of application working
	 */
	public void saveData() {
		try {
			// Playing with properties
			OutputStream output = new FileOutputStream("userpreferences/application.properties");
			Properties prop = new Properties();
			
			// For scheduledTime
			prop.setProperty("hoursScheduledTime", String.valueOf(hoursScheduledTime));
			prop.setProperty("minutesScheduledTime", String.valueOf(minutesScheduledTime));
			
			// Handle case list in not null
			if(!listUserData.isEmpty()) {
				prop.setProperty("isListUserDataEmpty", "0");
				// Save object in file
				FileOutputStream fileOut = new FileOutputStream("userpreferences/listuserdata.ser");
				ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
				objectOut.writeObject(listUserData);
				objectOut.close();
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
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}