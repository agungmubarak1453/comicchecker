package comicchecker;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class TesAgung {
	public static void main(String[] args) {
		System.out.println("- Program is started -");
		
		int checkOldUpdate = 0;
		int page = 200;
		while(true) {
			try {
				System.out.println("page :" + page);
				Document doc = Jsoup.connect("https://mangakakalots.com/manga_list/?type=latest&category=all&state=all&page=" + page).timeout(30000).get();			
				Elements comics = doc.select("div.list-truyen-item-wrap");
				
				for(Element o : comics) {
					System.out.println("chekOldUpdate :" + checkOldUpdate);
					if(checkOldUpdate >= 48) {
						break;
					}
					
					String title = o.select("h3 > a").text();
//					String image = o.select("a").first().select("img").attr("src");
//					String description = o.select("p").text();
					
					String urlForLookUpdateTime = o.select("h3 > a").attr("href");
					System.out.println("urlForLookUpdateTime :" + urlForLookUpdateTime);
					Document docUpdatePage = Jsoup.connect(urlForLookUpdateTime).timeout(30000).get();
					try {
						String updateTime = docUpdatePage.select(".chapter-list > .row").first().select("span:eq(2)").text();						
						if(! updateTime.contains("ago")) {
							checkOldUpdate++;
							continue;
						}else {
							checkOldUpdate = 0;
						}
						
						System.out.println(title);
//						System.out.println(image);
//						System.out.println(description);
						System.out.println(updateTime);
					}catch(NullPointerException e) {
						System.out.println(e.getStackTrace());
					}
				}
				
				if(checkOldUpdate >= 48) {
					break;
				}
				page++;
			} catch (Exception e) {
				e.printStackTrace();
				break;
			}
		}
		
		System.out.println("- Program is finished -");
	}
}