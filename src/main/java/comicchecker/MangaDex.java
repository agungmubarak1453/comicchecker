package comicchecker;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class MangaDex {

	public static void main(String[] args) {
		String query = "Creepy Cat";
		try {
			Document doc = Jsoup.connect("https://mangadex.org/titles/0/2/").timeout(30000).get();
			Elements comicList = doc.select("div.text-truncate");
			//System.out.println(comicList);
			for (Element comicItem : comicList) {
				String title = comicItem.select("a").text();
				System.out.println(title);
			}
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
}
