package comicchecker;

import java.awt.*;
import java.awt.event.*;
import java.io.Serializable;
import java.awt.TrayIcon.MessageType;
import java.awt.Window.Type;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;
import javax.swing.SwingUtilities;

import javafx.application.Platform;

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
	 * @param isFromGUI GUI have different method for this
	 * @see WebScraper
	 * @see ComicCheckerApplication#updateSubscription()
	 * @see ComicCheckerApplication#frequentlyUpdateSubscription(int, int, int)
	 */
	public void updateSubscription(WebScraper webScraper, boolean isFromGUI) {
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
					Frame frame = new Frame("");
					
					// Notification display
					String notificationText = "";
					boolean firstItem = true;
					for(String o2 : o.getUpdateChapter()) {
						notificationText += (firstItem ? "" : ", ") + o2;
						if(firstItem == true) firstItem = false;
					}
					notificationText += "\n";
					firstItem = true;
					for(String o2 : o.getUpdateTime()) {
						notificationText += (firstItem ? "" : ", ") + o2;
						if(firstItem == true) firstItem = false;
					}
					
					// Trayicon display
					String toolTip = "";
					PopupMenu popup = new PopupMenu();
					
					for(int i=0; i<o.getUpdateSite().size(); i++) {
						
						String updateChapterRaw = matchSnippetData(i, o.getUpdateChapter());
						Matcher digitChapter = null;
						
						if(Pattern.matches(".*[:\\-_|].*", updateChapterRaw)) {
							digitChapter = Pattern.compile(".*(?= *[:\\-_|])").matcher(updateChapterRaw);
						}else {
							digitChapter = Pattern.compile(".*").matcher(updateChapterRaw);
						}
						
						if(i == 0) {
							toolTip += o.getTitle() + (digitChapter.find() ? " " + digitChapter.group() : "") + " get Updating";
						}else {
								MenuItem otherSite = new MenuItem(o.getUpdateSite().get(i).replaceAll("https:\\/\\/|\\/.*", "") + (digitChapter.find() ? " " + digitChapter.group() : ""));
								String address = o.getUpdateSite().get(i);
								otherSite.addActionListener( e-> {
									try {
										Desktop.getDesktop().browse(new URI(address));
									} catch (Exception e2) {
										e2.printStackTrace();
									}
								});
								
								popup.add(otherSite);
						}
					}
					
					// Assign every value to tray icon
					TrayIcon trayIcon = new TrayIcon(image.getScaledInstance(SystemTray.getSystemTray().getTrayIconSize().width, -1, Image.SCALE_SMOOTH)
							, toolTip);
					
					boolean canPopupShow = popup.getItemCount() > 0;
					
					MenuItem app = new MenuItem("Open App");
					app.addActionListener( e -> {
						app.setEnabled(false);
						GUISimulator.main(new String[] {});
					});
					
					MenuItem close = new MenuItem("Close");
					close.addActionListener(e -> {
						tray.remove(trayIcon);
					});
					
					// GUI have different method
					popup.addSeparator();
					if(!isFromGUI) {
						popup.add(app);
						popup.addSeparator();
					}
					popup.add(close);
					
					if(canPopupShow) trayIcon.setPopupMenu(popup); 
					
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
							if (SwingUtilities.isLeftMouseButton(e)) {
								
								try {
									
									Desktop.getDesktop().browse(new URI(o.getUpdateSite().get(0)));
									
								} catch (Exception e1) {
									e1.printStackTrace();
								}
								
								tray.remove(trayIcon);
							}
							
							if (SwingUtilities.isRightMouseButton(e) && canPopupShow) {
									frame.add(popup);
						            popup.show(frame, e.getXOnScreen(), e.getYOnScreen());
							}
						}
					});
					
					tray.add(trayIcon);
					try {
						frame.setUndecorated(true);
						frame.setType(Type.UTILITY);
						frame.setResizable(false);
						frame.setVisible(true);
					} catch (Exception e) {
			        	e.printStackTrace();
			        }
					
					// Display notification
					trayIcon.displayMessage(o.getTitle() + " have updated", notificationText, MessageType.NONE);
					
				}
			}
		    
		}catch(Exception ex){
		    ex.printStackTrace();
		}
	}
	
	public String matchSnippetData(int indexData, List<String> data) {
		if(indexData > data.size() - 1) {
			return data.get(data.size() - 1);
		}else {
			return data.get(indexData);
		}
	}
	
	@Override
	public String toString() {
		return name;
	}
	
}