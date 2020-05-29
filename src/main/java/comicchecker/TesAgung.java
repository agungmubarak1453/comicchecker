package comicchecker;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class TesAgung {
	public static void main(String[] args) {
		System.out.println("- Program is started -");
		
//		int checkOldUpdate = 0;
//		int page = 1;
//		while(true) {
//			try {
//				System.out.println("page :" + page);
//				Document doc = Jsoup.connect("https://mangakakalots.com/manga_list/?type=latest&category=all&state=all&page=" + page).timeout(30000).get();			
//				Elements comics = doc.select("div.list-truyen-item-wrap");
//				
//				for(Element o : comics) {
//					System.out.println("chekOldUpdate :" + checkOldUpdate);
//					if(checkOldUpdate >= 48) {
//						break;
//					}
//					
//					String title = o.select("h3 > a").text();
//					String image = o.select("a").first().select("img").attr("src");
//					String description = o.select("p").text();
//					
//					String urlForLookUpdateTime = o.select("h3 > a").attr("href");
//					System.out.println("urlForLookUpdateTime :" + urlForLookUpdateTime);
//					Document docUpdatePage = Jsoup.connect(urlForLookUpdateTime).timeout(30000).get();
//					try {
//						Element updateInfo = docUpdatePage.select(".chapter-list > .row").first();
//						String updateChapter = updateInfo.select("span:eq(0) > a").text();
//						String updateTime = updateInfo.select("span:eq(2)").text();
//						String updateLink = updateInfo.select("span:eq(0) > a").attr("href");
//						if(! updateTime.contains("ago")) {
//							checkOldUpdate++;
//							continue;
//						}else {
//							checkOldUpdate = 0;
//						}
//						
//						System.out.println(title);
//						System.out.println(image);
//						System.out.println(description);
//						System.out.println(updateChapter);
//						System.out.println(updateTime);
//						System.out.println(updateLink);
//					}catch(Exception e) {
//						System.out.println(e);
//					}
//				}
//				
//				if(checkOldUpdate >= 48) {
//					break;
//				}
//				page++;
//			} catch (Exception e) {
//				e.printStackTrace();
//				break;
//			}
//		}
		WebScraper webScraper = new WebScraper();
		webScraper.addSite(new Type1("https://mangakakalots.com"));
		List<String> avaibleWebsite = new ArrayList<>();
		avaibleWebsite.add("https://mangakakalots.com");
		Snippet gu = new Snippet("yaiba", avaibleWebsite);
		gu.update(webScraper);
		System.out.println(gu);
		
		System.out.println("- Program is finished -");
	}
}