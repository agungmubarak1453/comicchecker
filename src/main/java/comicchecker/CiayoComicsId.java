package comicchecker;

import java.io.IOException;
import java.net.SocketTimeoutException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class CiayoComicsId extends Site {

	public CiayoComicsId(String url) {
		super(url);
	}

	public static void main(String[] args) {
		String judul = "blue serenade";
		try {
			Document doc = Jsoup.connect("https://www.ciayo.com/id/search?query="+judul).timeout(30000).get();
			Elements comicList = doc.select("div.is-search");
			for (Element comicItem : comicList) {
				String title = comicItem.select("div.comic-description > h3").text();
				if(judul.equalsIgnoreCase(title)) {
					String author = comicItem.select("div.comic-description > h5 > strong").text();
					System.out.println(author);
					
					String thumbnail = doc.select("div.comic-cover > img").attr("src");
					System.out.println(thumbnail);
					
					String genre = doc.select("div.comic-description > h4 > span").text();
					System.out.println(genre);
					
					String comicUrl = doc.select("a.comic-link").attr("href");
					System.out.println(comicUrl);
					Document comicPage = Jsoup.connect("https://www.ciayo.com"+comicUrl).timeout(30000).get();
					
					String desc = comicPage.select("div.comic-detail > div > p").text();
					System.out.println(desc);
					
					String chapterTitle = comicPage.select("div.chapter-info > h3").text();
					System.out.println(chapterTitle);
					
					String chapterDate = comicPage.select("div.chapter-meta > h5 > time").text();
					System.out.println(chapterDate);
					
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	Snippet search(String title) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	Snippet getInfo(String title) {
		
		return null;
	}

}
