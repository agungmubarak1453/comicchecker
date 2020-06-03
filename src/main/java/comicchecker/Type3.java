package comicchecker;

import java.net.SocketTimeoutException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Class for handle web scraping method from various website type3
 * Knowing for: manganelo.com
 * 
 * @see Site
 * @author Agung Mubarak
 *
 */
public class Type3 extends Site{

	public Type3(String url) {
		super(url);
	}

	@Override
	Snippet search(String searchTitle) {
		Snippet result = null;
		
		try {
			Document doc = Jsoup.connect(getUrl() + "/search/" + searchTitle.replace(" ", "_").replace(",", "")).timeout(30000).get();
			String urlComicDetail = "";
			if(!doc.select(".search-story-item").isEmpty()) {
				urlComicDetail = doc.select(".search-story-item").first().select("[href]").attr("href");
			}else {
				return result;
			}
			
			try {
				Document docComicDetail = Jsoup.connect(urlComicDetail).timeout(30000).get();
				
				String title = docComicDetail.select(".story-info-right > h1").text();
				String image = docComicDetail.select(".story-info-left .img-loading").attr("src");
				String description = docComicDetail.select(".panel-story-info-description").text().replaceAll("^Description : ", "");
				
				Element updateInfo = docComicDetail.select(".row-content-chapter > .a-h").first();
				String updateChapter = updateInfo.select("a").text();
				String updateTime = updateInfo.select(".chapter-time.text-nowrap").text();
				String updateLink = updateInfo.select("a").attr("href");
				
				if(updateTime.contains("ago")) {
					result = new Snippet(title
							, image
							, description
							, updateChapter
							, updateTime
							, updateLink
							);
					return result;
				}
				
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