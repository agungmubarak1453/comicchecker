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
		
		// Mangakakalots have bad data displaying. They diplay unsorted data from comic updating time.
		// So i make a rule if web scraper haven't found data that update atleast one day ago 48 times
		// (that is 2 page in my PC), web scrapper is stopped.
		int checkOldUpdate = 0;
		// Variable for page navigation
		int page = 1;
		
		//Refresh time
		int timeForRefresh = 3;
		// While true for infinite loop until data is found or throwing exception
		while(true) {
			try {
				Document doc = Jsoup.connect(getUrl() + "/manga_list/?type=latest&category=all&state=all&page=" + page).timeout(30000).get();			
				// I use structured method to get data for sake of efficient
				Elements comics = doc.select("div.list-truyen-item-wrap");
				
				for(Element o : comics) {
					if(checkOldUpdate >= 48) {
						break;
					}
					
					String title = o.select("h3 > a").text();
					// Searching use case-insensitive
					if(!title.toLowerCase().contains(searchTitle.toLowerCase())) {
						continue;
					}
					String thumbnail = o.select("a").first().select("img").attr("src");
					String description = o.select("p").text();
					
					String urlForLookUpdateTime = o.select("h3 > a").attr("href");
					// Because Mangakakalots don't display updating time in this page i must get update time for individual comic page
					try {
						Document docUpdatePage = Jsoup.connect(urlForLookUpdateTime).timeout(30000).get();
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
						
						result = new Snippet(title
								,thumbnail
								,description
								,updateChapter
								,updateTime
								,updateLink
								);
						return result;
					}catch(Exception e) {
						e.printStackTrace();
					}
				}
				
				// Continuing break of checkOldUpdate in for
				if(checkOldUpdate >= 48) {
					break;
				}
				page++;
			} catch (SocketTimeoutException e) {
				if(timeForRefresh > 0) {
					timeForRefresh--;
					continue;
				}else {
					e.printStackTrace();
				}
			} catch (Exception e) {
				e.printStackTrace();
				break;
			}
			
			timeForRefresh = 3;
		}
		
		return result;
	}
}