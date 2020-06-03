package comicchecker;

import java.net.SocketTimeoutException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Class for handle web scraping method from various website type1
 * <br><br>
 * Knowing for : mangakakalots.com
 * 
 * @see Site
 * @author Agung Mubarak
 *
 */
public class Type1 extends Site{

	public Type1(String url) {
		super(url);
	}

	@Override
	Snippet search(String searchTitle) {
		Snippet result = null;
		
		try {
			Document doc = Jsoup.connect(getUrl() + "/search/" + searchTitle).timeout(30000).get();
			String urlComicDetail = "";
			if(!doc.select(".story_item").isEmpty()) {
				urlComicDetail = doc.select(".story_item").first().select("[href]").first().attr("href");
			}else {
				return result;
			}
			
			try {
				Document docComicDetail = Jsoup.connect(urlComicDetail).timeout(30000).get();
				
				String title = docComicDetail.select(".manga-info-text h1").text();
				String image = docComicDetail.select(".manga-info-pic > img").attr("src");
				String description = docComicDetail.select("#noidungm").text().replaceAll(".+? summary: ", "");
				
				Element updateInfo = docComicDetail.select(".chapter-list > .row").first();
				String updateChapter = updateInfo.select("span:eq(0) > a").text();
				String updateTime = updateInfo.select("span:eq(2)").text();
				String updateLink = updateInfo.select("span:eq(0) > a").attr("href");
				
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

	@Override
	Snippet getInfo(String searchTitle) {
		Snippet result = null;
		
		try {
			Document doc = Jsoup.connect(getUrl() + "/search/" + searchTitle).timeout(30000).get();
			String urlComicDetail = "";
			if(!doc.select(".story_item").isEmpty()) {
				urlComicDetail = doc.select(".story_item").first().select("[href]").first().attr("href");
			}else {
				return result;
			}
			
			try {
				Document docComicDetail = Jsoup.connect(urlComicDetail).timeout(30000).get();
				
				String title = docComicDetail.select(".manga-info-text h1").text();
				String image = docComicDetail.select(".manga-info-pic > img").attr("src");
				String description = docComicDetail.select("#noidungm").text().replaceAll(".+? summary: ", "");
				
				Element updateInfo = docComicDetail.select(".chapter-list > .row").first();
				
				result = new Snippet(title
						, image
						, description
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