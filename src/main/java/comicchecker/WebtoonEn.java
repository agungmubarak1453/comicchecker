package comicchecker;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Handling the scraping for Webtoon
 * url : https://webtoons.com/en/
 * 
 * @see Site
 * @author Wutsqo
 * */
public class WebtoonEn extends Site {	
	public WebtoonEn(String url) {
		super(url);
	}

	public static String webtoonEnDateConverter(String input) {
		String month = input.substring(0, 3);
		int x = input.indexOf(",");
		String date = input.substring(4,x);
		String year = input.substring(x+2);
		
		switch(month) {
		case "Jan": month = "01"; break;
		case "Feb": month = "02"; break;
		case "Mar": month = "03"; break;
		case "Apr": month = "04"; break;
		case "May": month = "05"; break;
		case "Jun": month = "06"; break;
		case "Jul": month = "07"; break;
		case "Aug": month = "08"; break;
		case "Sep": month = "09"; break;
		case "Oct": month = "10"; break;
		case "Nov": month = "11"; break;
		case "Dec": month = "12"; break;
		}
		
		String fullDate = String.format("%s/%s/%s", date, month, year);
		return fullDate;
	}

	@Override
	Snippet search(String title) {
		Snippet result = null;
		try {
			Document doc = Jsoup.connect(getUrl()+"/genre").timeout(30000).get();
			Elements comicList = doc.select("a.card_item");
			for (Element comicItem : comicList) {
				String x = comicItem.select("div.info > p.subj").text();
				
				if (x.equalsIgnoreCase(title)) {
					
					String author = comicItem.select("p.author").text();
					
					String thumbnail = comicItem.select("img").attr("src");
					
					String comicUrl = comicItem.select("a").attr("href");
					
					Document comicPage = Jsoup.connect(comicUrl).timeout(3000).get();
					String desc = comicPage.select("p.summary").text();
					
					String genre = comicPage.select("h2.genre").text();
					
					String chapterTitle = comicPage.select("span.subj > span").first().text();
					
					String chapterDate = comicPage.select("span.date").first().text();
					chapterDate = webtoonEnDateConverter(chapterDate);
					
					result = new Snippet(x
							,thumbnail
							,desc
							,chapterTitle
							,chapterDate
							,comicUrl
							,genre
							,author
							);
					return result;
				}
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
		
	}
}
