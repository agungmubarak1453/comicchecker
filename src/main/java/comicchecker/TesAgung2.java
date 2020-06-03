package comicchecker;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.RenderedImage;
import java.awt.TrayIcon.MessageType;

import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class TesAgung2 {

	public static void main(String[] args) {
		ComicCheckerApplication application= new ComicCheckerApplication();
		application.updateSubscription();
//		printTest(application.getUserData().getListOfSubscription());
//		application.saveData();
		
//		try{
//		    SystemTray tray = SystemTray.getSystemTray();
//		    
////			Image image = Toolkit.getDefaultToolkit().createImage("http://www.digitalphotoartistry.com/rose1.jpg");
//			Image image = ImageIO.read(new URL("http://www.digitalphotoartistry.com/rose1.jpg"));
//			TrayIcon trayIcon = new TrayIcon(image);
//			trayIcon.setImageAutoSize(true);
//			tray.add(trayIcon);
//			trayIcon.displayMessage("Xian Ni" + " have updated", image.toString(), MessageType.NONE);
//		}catch(Exception ex){
//		    System.err.print(ex);
//		}
	}
	
	public static void printTest(Object... o) {
		for(Object o2 : o) {
			System.out.println(o2);
		}
	}
	
}
