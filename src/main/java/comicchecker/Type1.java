package comicchecker;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

/**
 * Class for handle web scraping method from various website type1
 * <br><br>
 * Knowing for : mangakakalots.com
 * <br><br>
 * Extends from {@link Site}<br>
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
	public Snippet search(String searchTitle) throws IOException {
		Document doc = Jsoup.connect(url + "/search/" + searchTitle).timeout(30000).get();
		
		String urlComicDetail = "";
		if(!doc.select(".story_item").isEmpty()) {
			urlComicDetail = doc.select(".story_item").first().select("[href]").first().absUrl("href");
		}else {
			return null;
		}
		
		Document docComicDetail = Jsoup.connect(urlComicDetail).timeout(30000).get();
		
		String title = docComicDetail.select(".manga-info-text h1").text();
		String author = docComicDetail.select(".manga-info-text > li:eq(1)").text().replaceAll(",$", "").replaceAll("^Author\\(s\\) : ", "");
		String genre = docComicDetail.select(".manga-info-text > li:eq(6)").text().replaceAll(",$", "").replaceAll("^Genres : ", "");
		String image = docComicDetail.select(".manga-info-pic > img").attr("src");
		String description = docComicDetail.select("#noidungm").text().replaceAll(".+? summary: ", "");
		
		Element updateInfo = docComicDetail.select(".chapter-list > .row").first();
		String updateChapter = updateInfo.select("span:eq(0) > a").text();
		String updateTime = updateInfo.select("span:eq(2)").text();
		String updateLink = updateInfo.select("span:eq(0) > a").first().absUrl("href");
		
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
		Document doc = Jsoup.connect(url + "/search/" + searchTitle).timeout(30000).get();
		
		String urlComicDetail = "";
		if(!doc.select(".story_item").isEmpty()) {
			urlComicDetail = doc.select(".story_item").first().select("[href]").first().absUrl("href");
		}else {
			return null;
		}
			
		Document docComicDetail = Jsoup.connect(urlComicDetail).timeout(30000).get();
		
		String title = docComicDetail.select(".manga-info-text h1").text();
		String image = docComicDetail.select(".manga-info-pic > img").attr("src");
		String author = docComicDetail.select(".manga-info-text > li:eq(1)").text().replaceAll(",$", "").replaceAll("^Author\\(s\\) : ", "");
		String genre = docComicDetail.select(".manga-info-text > li:eq(6)").text().replaceAll(",$", "").replaceAll("^Genres : ", "");
		String description = docComicDetail.select("#noidungm").text().replaceAll(".+? summary: ", "");
		
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