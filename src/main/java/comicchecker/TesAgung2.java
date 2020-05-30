package comicchecker;

import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class TesAgung2 {

	public static void main(String[] args) {
		try {
			Document doc = Jsoup.connect("https://manganelo.com/search/agung").timeout(30000).get();
			String urlComicDetail = "";
			if(!doc.select(".search-story-item").isEmpty()) {
				urlComicDetail = doc.select(".search-story-item").first().select("[href]").attr("href");
			}else {
				printTest("Ngak ketemu");
				return;
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
				
				printTest(title
						, image
						, description
						, updateChapter
						, updateTime
						, updateLink
						);
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
	}
	
	public static void printTest(Object... o) {
		for(Object o2 : o) {
			System.out.println(o2);
		}
	}
}
