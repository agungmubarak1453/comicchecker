package comicchecker;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class WebtoonId {

	public static void main(String[] args) {
		String url = "https://www.webtoons.com/search?keyword=you";
		try {
			Document doc = Jsoup.connect(url).timeout(30000).get();
			Elements comicList = doc.select("div.card_wrap search");
			System.out.println(comicList);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
}
