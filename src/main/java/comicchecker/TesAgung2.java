package comicchecker;

import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class TesAgung2 {

	public static void main(String[] args) {
		ComicCheckerApplication application= new ComicCheckerApplication();
//		application.addUserData("Irza");
//		application.addSubscription("Golden Kamui", "https://manganelo.com");
//		application.addSubscription("Peerless Dad", "https://manganelo.com");
//		application.updateSubscription();
		printTest(application.getListUserData().get(0).getListOfSubscription());
		printTest(application.getUserData().getListOfSubscription());
//		application.saveData();
	}
	
	public static void printTest(Object... o) {
		for(Object o2 : o) {
			System.out.println(o2);
		}
	}
	
}
