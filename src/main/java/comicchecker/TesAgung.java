package comicchecker;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class TesAgung {
	Document doc;
	public void main(String args[]) {
		try {
			doc = Jsoup.connect("https://mangakakalots.com/manga_list/?type=latest&category=all&state=all&page=1").timeout(6000).get();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}