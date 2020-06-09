package comicchecker;

import java.io.IOException;
import java.net.SocketTimeoutException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Handling the scraping for Webtoon Indonesia
 * url : https://webtoons.com/id/
 * 
 * @see Site
 * @author Wutsqo
 * */
public class WebtoonId extends Site {
	public WebtoonId(String url) {
		super(url);
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

	@Override
	Snippet search(String title) {
		Snippet result = null;
		try {
			Document doc = Jsoup.connect(getUrl()+"/genre").timeout(30000).get();
			Elements comicList = doc.select("a.card_item");
			for (Element comicItem : comicList) {
				String x = comicItem.select("div.info > p.subj").text();
				
				if (x.equalsIgnoreCase(title)) {;
					
					String author = comicItem.select("p.author").text();
					
					String thumbnail = comicItem.select("img").attr("src");

					String comicUrl = comicItem.select("a").attr("href");
					
					Document comicPage = Jsoup.connect(comicUrl).timeout(3000).get();
					String desc = comicPage.select("p.summary").text();
					
					String genre = comicPage.select("h2.genre").text();
					
					String chapterTitle = comicPage.select("span.subj > span").first().text();
					
					String chapterDate = comicPage.select("span.date").first().text();
					chapterDate = webtoonIdDateConverter(chapterDate);
					
					result = new Snippet(x
							,thumbnail
							,author
							,genre
							,desc
							,chapterTitle
							,chapterDate
							,comicUrl
							);
					return result;
				}
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	Snippet getInfo(String title) {
		Snippet result = null;
		try {
			Document doc = Jsoup.connect(getUrl()+"/genre").timeout(30000).get();
			String comicUrl = "";
			if(!doc.select("p.subj").isEmpty()) {
				comicUrl = doc.select("a.card_item").first().attr("href");
			} else {
				return result;
			}
			try {
				Document comicPage = Jsoup.connect(comicUrl).timeout(30000).get();
				String comicTitle = comicPage.select("h1.subj").text();
				String comicImage = comicPage.select("span.thmb > img").first().attr("src");
				String comicDesc = comicPage.select("p.summary").text();
				result = new Snippet(comicTitle
						, comicImage
						, ""
						, ""
						, comicDesc
						, ""
						, ""
						, ""
						);
				return result;
				
			} catch (SocketTimeoutException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (SocketTimeoutException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
}
