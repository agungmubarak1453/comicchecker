package comicchecker;

import java.net.SocketTimeoutException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Class for handle web scraping method from various website type3
 * Knowing for: manganelo.com
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
	Snippet search(String searchTitle) {
		// I'm too lazy for write method for manganelo because this similar to mangakakalots.
		// So i copy from mangakakalots and modify a bit.
		Snippet result = null;
		
		// Mangakakalots have bad data displaying. They diplay unsorted data from comic updating time.
		// So i make a rule if web scraper haven't found data that update atleast one day ago 48 times
		// (that is 2 page in my PC), web scrapper is stopped.
		int checkOldUpdate = 0;
		// Variable for page navigation
		int page = 1;
		
		// While true for infinite loop until data is found or throwing exception
		while(true) {
			try {
				Document doc = Jsoup.connect(getUrl() + "/genre-all/" + page).timeout(30000).get();			
				// I use structured method to get data for sake of efficient
				Elements comics = doc.select(".content-genres-item");
				
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
					String description = o.select(".genres-item-description").text();
					
					String urlForLookUpdateTime = o.select("h3 > a").attr("href");
					// Because Mangakakalots don't display updating time in this page i must get update time for individual comic page
					try {
						Document docUpdatePage = Jsoup.connect(urlForLookUpdateTime).timeout(30000).get();
						Element updateInfo = docUpdatePage.select(".row-content-chapter > .a-h").first();
						String updateChapter = updateInfo.select("a").text();
						String updateTime = updateInfo.select(".chapter-time.text-nowrap").text();
						String updateLink = updateInfo.select("a").attr("href");
						
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
					e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
				break;
			}
		}
		
		return result;
	}
}