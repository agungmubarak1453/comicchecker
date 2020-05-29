package comicchecker;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javafx.scene.image.Image;

/**
 * Class for handle web scraping method from various website type1
 * Knowing for : mangakakalots.com
 * @author Agung Mubarak
 *
 */
public class Type1 extends Site{

	public Type1(String url) {
		super(url);
	}

	@Override
	void search(String searchTitle) {
		Snippet template = new Snippet();
		
		int checkOldUpdate = 0;
		int page = 0;
		
		while(true) {
			try {
				Document doc = Jsoup.connect("https://mangakakalots.com/manga_list/?type=latest&category=all&state=all&page=" + page).timeout(30000).get();			
				Elements comics = doc.select("div.list-truyen-item-wrap");
				
				for(Element o : comics) {
					if(checkOldUpdate >= 48) {
						break;
					}
					
					String title = o.select("h3 > a").text();
					String thumbnail = o.select("a").first().select("img").attr("src");
					String description = o.select("p").text();
					
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
						
						if(title.contains(searchTitle)) {
							template.setTitle(title);
							template.setThumbnail(new Image(thumbnail));
							template.setDescription(description);
							template.addUpdateSite(urlForLookUpdateTime);
						}
						System.out.println(title);
						System.out.println(thumbnail);
						System.out.println(description);
						System.out.println(updateTime);
					}catch(Exception e) {
						System.out.println(e);
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
	}
}