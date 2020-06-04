package comicchecker;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Class for get data from comic website
 * <br><br>
 * Please use method {@link #addSite(Site)} before use method {@link #check(String, List) or {@link #checkInfo(String, List)}
 * because that method not work properly with empty list of site.
 * <br><br>
 * <b>Field:</b><br>
 * - {@link #listOfSite}<br>
 * 
 * @author AgungMubarak
 * @see ComicCheckerApplication
 *
 */
public class WebScraper {
	/**
	 * Field that contains list of website that can be used
	 * @see Site
	 */
	public List<Site> listOfSite;
	
	public WebScraper() {
		listOfSite = new ArrayList<>();
	}
	
	/**
	 * Method for add site that can be used by WebScraper
	 * 
	 * @param sites websites can be used by WebScraper
	 * @see Site
	 */
	public void addSite(Site... sites) {
		for(Site o : sites) {
			listOfSite.add(o);
		}
	}
	
	/**
	 * Method for web scraping comic update
	 * <br><br>
	 * Make sure valiableUpdateSite parameter exist in list of website in WebScraper.
	 * 
	 * @param title title of comic is searched
	 * @param avaibleUpdateSite website can be used in this comic searching
	 * @return {@link Snippet} (comic data)
	 * @see Site
	 * @see Site#search(String)
	 */
	public Snippet check(String title, List<String> avaibleUpdateSite) {
		Snippet result = null;
		// Every comic data like title; thumbnail; description; etc. is taken from first site 
		// Because that use this boolean
		boolean doesFirstSiteFound = false;
		
		for(Site o : listOfSite) {
			// Check site can be used for this snippet
			boolean isSiteChecked = false;
			for(String o2 : avaibleUpdateSite) {
				if(o.getUrl().equals(o2)) {
					isSiteChecked = true;
					break;
				}
			}
			if(!isSiteChecked) {
				continue;
			}
			
			// Mostly comic data is taken from first site
			// URLs for chapter update are taken from many site
			if(!doesFirstSiteFound) {
				result = o.search(title);
				if(result != null) {
					doesFirstSiteFound = true;
				}
			}else {
				Snippet otherSite = o.search(title);
				if(otherSite != null) {
					if(!result.getUpdateChapter().equals(otherSite.getUpdateChapter())) {
						result.setUpdateChapter(result.getUpdateChapter() + ", " + otherSite.getUpdateChapter());
					}
					
					if(!result.getUpdateTime().equals(otherSite.getUpdateTime())) {
						result.setUpdateTime(result.getUpdateTime() + ", " + otherSite.getUpdateTime());
					}
					
					for(String o3 : otherSite.getUpdateSite()) {
						result.addUpdateSite(o3);
					}
				}
			}
		}
		
		return result;
	}
	
	/**
	 * Method for web scraping comic info
	 * <br><br>
	 * Make sure valiableUpdateSite parameter exist in list of website in WebScraper.
	 * 
	 * @param title title of comic is searched
	 * @param avaibleUpdateSite website can be used in this comic searching
	 * @return {@link Snippet} (comic data)
	 * @see Site
	 * @see Site#getInfo(String)
	 */
	public Snippet checkInfo(String title, List<String> avaibleUpdateSite) {
		Snippet result = null;
		// Every comic general info like title; thumbnail; description is taken from a site 
		
		for(Site o : listOfSite) {
			// Check site can be used for this snippet
			boolean isSiteChecked = false;
			for(String o2 : avaibleUpdateSite) {
				if(o.getUrl().equals(o2)) {
					isSiteChecked = true;
					break;
				}
			}
			if(!isSiteChecked) {
				continue;
			}
			
			result = o.getInfo(title);
			System.out.println(title);
		}
		
		return result;
	}
	
	/**
	 * Method for get data list comic from internet
	 * 
	 * @param writerForLocalData writer for writing in local data
	 * @return list of comic title
	 */
	public List<String> searchListComic(PrintWriter writerForLocalData) {
		List<String> result = new ArrayList<>();
		
		int mangaThatSearched = 0;
		int amountOfManga = 100;
		while(mangaThatSearched<=amountOfManga) {
			try {
				
				Document doc = Jsoup.connect("https://myanimelist.net/topmanga.php?limit=" + mangaThatSearched)
						.timeout(30000)
						.get();
				Elements comics = doc.select(".ranking-list .detail > a");
				for(Element o : comics) {
					writerForLocalData.println(o.text());
					result.add(o.text());
				}
			
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			mangaThatSearched += 50;
		}
		
		return result;
	}
	
}
