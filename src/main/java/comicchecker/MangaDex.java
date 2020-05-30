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
			int page = 1;
			
			while (true) {
				Document doc = Jsoup.connect("https://mangadex.org/titles/0/" + page + "/").timeout(30000).get();
				Elements comicList = doc.select("div.manga-entry");
				 
				for (Element comicItem : comicList) {
					String title = comicItem.select("div.text-truncate > a").text();
					if (query.equalsIgnoreCase(title)) {
						System.out.println(title);
						
						String thumbnail = comicItem.select("div.rounded > a").select("img").attr("src");
						System.out.println(thumbnail);
						
						String desc = comicItem.select("div").text();
						int x = desc.indexOf("Follow ");
						desc = desc.substring(x+7);
						System.out.println(desc);
						break;
					}
				}
				page = page + 1;
			}
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
}
