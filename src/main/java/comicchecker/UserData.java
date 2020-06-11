package comicchecker;

import java.awt.*;
import java.awt.event.*;
import java.io.Serializable;
import java.awt.TrayIcon.MessageType;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;

/**
	 * 
	 */
/**
 * Class for store user data
 * <br><br>
 * <b>Field:</b><br>
 * - {@link #name}<br>
 * - {@link #listOfSubscription}
 * 
 * @author Agung Mubarak
 * @see ComicCheckerApplication
 */
public class UserData implements Serializable{
	private static final long serialVersionUID = 2L;
	
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
	
	// Group of method for getter and setter
	
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
	
	// Group of configuration method for Subscription
	
	/**
	 * Method for add subscription
	 * 
	 * @param webScraper machine of webscraping
	 * @param title title of comic
	 * @param sites site that is used for webscraping
	 * @see Snippet
	 * @see WebScraper
	 * @see Site
	 */
	public void addSubscription(WebScraper webScraper, String title, String... sites) {
		listOfSubscription.add(new Snippet(webScraper, title, sites));
	}
	
	/**
	 * Method for add subscription for GUI
	 * 
	 * @param webScraper machine of webscraping
	 * @param title title of comic
	 * @param sites site that is used for webscraping
	 * @see Snippet
	 * @see WebScraper
	 * @see Site
	 */
	public void addSubscription(WebScraper webScraper, String title, List<String> sites) {
		listOfSubscription.add(new Snippet(webScraper, title, sites));
	}
	
	/**
	 * Method for delete subscription
	 * 
	 * @param title title of comic
	 * @see Snippet
	 */
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
	 * <br><br>
	 * Method can popup notification and this use tray icon method.
	 * 
	 * @param webScraper machine of web scraping
	 * @see WebScraper
	 * @see ComicCheckerApplication#updateSubscription()
	 * @see ComicCheckerApplication#frequentlyUpdateSubscription(int, int, int)
	 */
	public void updateSubscription(WebScraper webScraper) {
		// Pop up notification
		if (!SystemTray.isSupported()) {
			return;
		}
		
		try{
			
		    SystemTray tray = SystemTray.getSystemTray();
		    
		    // Pop up notification for every updating comic
		    for(Snippet o : listOfSubscription) {
				if (o.update(webScraper)) {
					Image image = ImageIO.read(new URL(o.getThumbnail()));
					
					Matcher digitChapter = Pattern.compile("\\d\\S*(?=[:-_|])+|\\d\\S*").matcher(o.getUpdateChapter());
					
					TrayIcon trayIcon = new TrayIcon(image.getScaledInstance(SystemTray.getSystemTray().getTrayIconSize().width, -1, image.SCALE_SMOOTH)
							, o.getTitle() + (digitChapter.find() ? " " + digitChapter.group() : "" ) + " get updating");
					
					String notificationText = o.getUpdateChapter()
												+ "\n" + o.getUpdateTime()
												;
					
					for(String so : o.getUpdateSite()) {
						notificationText += "\n" + so;
					}
					
					tray.add(trayIcon);
					
					trayIcon.displayMessage(o.getTitle() + " have updated", notificationText, MessageType.NONE);
					
					// If user click notification or similar for that, user is directed to browser for see updating comic
					// I don't know what's happened. But use lambda make this function frequently in bug
					trayIcon.addActionListener(
						new ActionListener() {
			                @Override
			                public void actionPerformed(ActionEvent e) {
			                	
								try {
									
									Desktop.getDesktop().browse(new URI(o.getUpdateSite().get(0)));
						
								} catch (Exception e1) {
									e1.printStackTrace();
								}
								
								tray.remove(trayIcon);
			                };
						}
					);
					
					trayIcon.addMouseListener(new MouseAdapter() {
						public void mouseClicked(MouseEvent e) {
							if (e.getClickCount() == 1) {
			                	
								try {
									
									Desktop.getDesktop().browse(new URI(o.getUpdateSite().get(0)));

								} catch (Exception e1) {
									e1.printStackTrace();
								}
								
								tray.remove(trayIcon);
							}
						}
					});
					
				}
			}
		    
		}catch(Exception ex){
		    ex.printStackTrace();
		}
	}
	
	@Override
	public String toString() {
		return name;
	}
	
}