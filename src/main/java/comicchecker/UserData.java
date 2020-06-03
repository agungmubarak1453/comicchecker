package comicchecker;

import java.awt.*;
import java.awt.event.*;
import java.awt.TrayIcon.MessageType;
import java.io.IOException;
import java.io.Serializable;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

/**
 * Class for store user data
 * 
 * @see ComicCheckerApplication
 * @author Agung Mubarak
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
	 * <br><br>
	 * Method can popup notification.
	 * 
	 * @param webScraper machine of web scraping
	 */
	public void updateSubscription(WebScraper webScraper) {
		// Pop up notification
		try{
		    SystemTray tray = SystemTray.getSystemTray();
		    
		    // Pop up notification for every updating comic
		    for(Snippet o : listOfSubscription) {
				if (o.update(webScraper)) {
					Image image = ImageIO.read(new URL(o.getThumbnail()));
					
					TrayIcon trayIcon = new TrayIcon(image, o.getTitle());
					trayIcon.setImageAutoSize(false);
					tray.add(trayIcon);
					
					String notificationText = o.getUpdateChapter()
												+ "\n" + o.getUpdateTime()
												;
					for(String so : o.getUpdateSite()) {
						notificationText += "\n" + so;
					}
					
					trayIcon.displayMessage(o.getTitle() + " have updated", notificationText, MessageType.NONE);
					
					// I don't know what's happened. But use lambda make this function frequently in bug
					trayIcon.addActionListener(
						new ActionListener() {
			                @Override
			                public void actionPerformed(ActionEvent e) {
								try {
									Desktop.getDesktop().browse(new URI(o.getUpdateSite().get(0)));
								} catch (IOException e1) {
									e1.printStackTrace();
								} catch (URISyntaxException e1) {
									e1.printStackTrace();
								}
								tray.remove(trayIcon);
			                }
						}
					);
				}
			}
		}catch(Exception ex){
		    ex.printStackTrace();
		}
	}
}