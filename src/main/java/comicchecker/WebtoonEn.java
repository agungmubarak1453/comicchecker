package comicchecker;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class WebtoonEn {

	public static void main(String[] args) {
		String query = "bluechair";
		String url = "https://www.webtoons.com/search?keyword=" + query;
		try {
			Document doc = Jsoup.connect(url).timeout(30000).get();
			Elements comicList = doc.select("ul.card_lst");
			for (Element comicItem : comicList) {
				String title = comicItem.select("li > a > div.info > p.subj").text();
				if (query.equalsIgnoreCase(title)) {
					System.out.println(title);
					
					String thumbnail = comicItem.select("li > a").select("img").attr("src");
					System.out.println(thumbnail);
					
					String comicUrl = comicItem.select("li > a").attr("href");
					comicUrl = "https://www.webtoons.com/" + comicUrl;
					System.out.println(comicUrl);
					Document comicPage = Jsoup.connect(comicUrl).timeout(30000).get();
					//Elements comicPageList = comicPage.select("p.summary");
					String desc = comicPage.select("p.summary").text();
					System.out.println(desc);
				}
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
}
