package comicchecker;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

/**
 * Class for handle web scraping method from various website type3
 * Knowing for: manganelo.com
 * <br><br>
 * Extends from {@link Site}<br>
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
	public Snippet search(String searchTitle) throws IOException {
		Document doc = Jsoup.connect(url + "/search/story/" + searchTitle.replace(" ", "_").replaceAll("\\W", "")).timeout(30000).get();
		
		String urlComicDetail = "";
		if(!doc.select(".search-story-item").isEmpty()) {
			urlComicDetail = doc.select(".search-story-item").first().select("[href]").attr("href");
		}else {
			return null;
		}
		
		Document docComicDetail = Jsoup.connect(urlComicDetail).timeout(30000).get();
		
		String title = docComicDetail.select(".story-info-right > h1").text();
		String image = docComicDetail.select(".story-info-left .img-loading").attr("src");
		
		String author = "";
		String genre = "";
		if(docComicDetail.select(".story-info-right .table-label").text().contains("Author")) {
			author = docComicDetail.select(".story-info-right .table-value").get(0).text();
			genre = docComicDetail.select(".story-info-right .table-value").get(2).text().replace("-", ",");
		}else {
			author = docComicDetail.select(".story-info-right .table-value").get(1).text();
			genre = docComicDetail.select(".story-info-right .table-value").get(3).text().replace("-", ",");
		}
		
		String description = docComicDetail.select(".panel-story-info-description").text().replaceAll("^Description : ", "");
		
		Element updateInfo = docComicDetail.select(".row-content-chapter > .a-h").first();
		String updateChapter = updateInfo.select("a").text();
		String updateTime = updateInfo.select(".chapter-time.text-nowrap").text();
		String updateLink = updateInfo.select("a").attr("href");
		
		if(updateTime.contains("ago")) {
			return new Snippet(title
					, image
					, author
					, genre
					, description
					, updateChapter
					, updateTime
					, updateLink
					);
		}else {
			return null;
		}
	}

	@Override
	public Snippet getInfo(String searchTitle) throws IOException {
		Document doc = Jsoup.connect(url + "/search/story/" + searchTitle.replace(" ", "_").replaceAll("\\W", "")).timeout(30000).get();
		
		String urlComicDetail = "";
		if(!doc.select(".search-story-item").isEmpty()) {
			urlComicDetail = doc.select(".search-story-item").first().select("[href]").attr("href");
		}else {
			return null;
		}
		
		Document docComicDetail = Jsoup.connect(urlComicDetail).timeout(30000).get();
		
		String title = docComicDetail.select(".story-info-right > h1").text();
		String image = docComicDetail.select(".story-info-left .img-loading").attr("src");
		
		String author = "";
		String genre = "";
		if(docComicDetail.select(".story-info-right .table-label").text().contains("Author")) {
			author = docComicDetail.select(".story-info-right .table-value").get(0).text();
			genre = docComicDetail.select(".story-info-right .table-value").get(2).text().replace("-", ",");
		}else {
			author = docComicDetail.select(".story-info-right .table-value").get(1).text();
			genre = docComicDetail.select(".story-info-right .table-value").get(3).text().replace("-", ",");
		}
			
		String description = docComicDetail.select(".panel-story-info-description").text().replaceAll("^Description : ", "");
		
		return new Snippet(title
				, image
				, author
				, genre
				, description
				, ""
				, ""
				, ""
				);
	}
	
}