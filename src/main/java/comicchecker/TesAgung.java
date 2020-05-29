package comicchecker;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class TesAgung {
	public static void main(String[] args) {
		System.out.println("- Program is started -\n");
		
		try {
			Document doc = Jsoup.connect("https://guya.moe").timeout(30000).get();
			Elements dropDownItems= doc.select(".dropdown-menu[aria-labelledby='readManga'] > .dropdown-item");
			for(int i=0; i<3; i++) {
				List<String> titleList = new ArrayList<>();
				
				String urlComicInfo = dropDownItems.get(i).absUrl("href");
				Document docComicInfo = Jsoup.connect(urlComicInfo).timeout(30000).get();
				
				Elements comicDetails= docComicInfo.select(".col-lg-8.col-md-7.col-sm-11.col-xs-12.series-content > article");
				
				Elements titles = comicDetails.select("h1, h2, h3, h4");
				for(Element o : titles) {
//					System.out.println(o.text());
					titleList.add(o.text());
				}
				
				String image = comicDetails.select("picture > img").first().absUrl("src");
				String description = comicDetails.select("p").text();
				
				Element chapterInfo = docComicInfo.select("#chapterTable > .table-default").first();
				String updateChapter = chapterInfo.select(".chapter-title").text();
				String updateTime = chapterInfo.select(".detailed-chapter-upload-date").text();
				String updateSite = chapterInfo.select(".chapter-title > a").first().absUrl("href");
				
				System.out.println(titleList);
				System.out.println(image);
				System.out.println(description);
				System.out.println(updateChapter);
				System.out.println(updateTime);
				System.out.println(updateSite);
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		System.out.println("\n- Program is finished -");
	}
}