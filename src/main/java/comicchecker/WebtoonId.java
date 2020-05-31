package comicchecker;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class WebtoonId {

	public static void main(String[] args) {
		String query = "mistake";
		String url = "https://www.webtoons.com/id/genre";
		try {
			Document doc = Jsoup.connect(url).timeout(30000).get();
			Elements comicList = doc.select("a.card_item");
			for (Element comicItem : comicList) {
				String title = comicItem.select("div.info > p.subj").text();
				
				if (query.equalsIgnoreCase(title)) {
					System.out.println(title);
					
					String author = comicItem.select("p.author").text();
					System.out.println(author);
					
					String thumbnail = comicItem.select("img").attr("src");
					System.out.println(thumbnail);
					
					String comicUrl = comicItem.select("a").attr("href");
					System.out.println(comicUrl);
					
					Document comicPage = Jsoup.connect(comicUrl).timeout(3000).get();
					String desc = comicPage.select("p.summary").text();
					System.out.println(desc);
					
					String genre = comicPage.select("h2.genre").text();
					System.out.println(genre);
					
					String chapterTitle = comicPage.select("span.subj > span").first().text();
					System.out.println(chapterTitle);
					
					String chapterDate = comicPage.select("span.date").first().text();
					chapterDate = webtoonIdDateConverter(chapterDate);
					System.out.println(chapterDate);
					
					
					break;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static String webtoonIdDateConverter(String input) {
		String month = input.substring(5, 8);
		String date = input.substring(9);
		String year = input.substring(0, 4);
		
		switch(month) {
		case "Jan": month = "01"; break;
		case "Feb": month = "02"; break;
		case "Mar": month = "03"; break;
		case "Apr": month = "04"; break;
		case "Mei": month = "05"; break;
		case "Jun": month = "06"; break;
		case "Jul": month = "07"; break;
		case "Agu": month = "08"; break;
		case "Sep": month = "09"; break;
		case "Okt": month = "10"; break;
		case "Nov": month = "11"; break;
		case "Des": month = "12"; break;
		}
		
		String fullDate = String.format("%s/%s/%s", date, month, year);
		return fullDate;
	}
}
