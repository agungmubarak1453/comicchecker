package comicchecker;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

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
	Snippet search(String searchTitle) {
		Snippet result = null;
		Snippet template = new Snippet();
		
		int checkOldUpdate = 0;
		int page = 0;
		
		while(true) {
			try {
				Document doc = Jsoup.connect(getUrl() + "/manga_list/?type=latest&category=all&state=all&page=" + page).timeout(30000).get();			
				Elements comics = doc.select("div.list-truyen-item-wrap");
				
				for(Element o : comics) {
					if(checkOldUpdate >= 48) {
						break;
					}
					
					String title = o.select("h3 > a").text();
					if(!title.toLowerCase().contains(searchTitle.toLowerCase())) {
						continue;
					}
					String thumbnail = o.select("a").first().select("img").attr("src");
					String description = o.select("p").text();
					
					String urlForLookUpdateTime = o.select("h3 > a").attr("href");
					Document docUpdatePage = Jsoup.connect(urlForLookUpdateTime).timeout(30000).get();
					try {
						Element updateInfo = docUpdatePage.select(".chapter-list > .row").first();
						String updateChapter = updateInfo.select("span:eq(0) > a").text();
						String updateTime = updateInfo.select("span:eq(2)").text();
						String updateLink = updateInfo.select("span:eq(0) > a").attr("href");
						
						if(! updateTime.contains("ago")) {
							checkOldUpdate++;
							continue;
						}else {
							checkOldUpdate = 0;
						}
						
						System.out.println(searchTitle + " ketemu");
						template.setTitle(title);
						template.setThumbnail(thumbnail);
						template.setDescription(description);
						template.setUpdateChapter(updateChapter);
						template.setUpdateTime(updateTime);
						template.addUpdateSite(updateLink);
						result = template;
						return result;
					}catch(Exception e) {
//						System.out.println(e);
						e.printStackTrace();
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
		return result;
	}
}