package comicchecker;

import java.util.ArrayList;
import java.util.List;

/**
 * Class for get data from comic website
 * <br><br>
 * Please use method {@link #addSite(Site)} before use method {@link #check(String, List)}
 * because that method not work properly with empty list of site.
 * 
 * @author AgungMubarak
 *
 */
public class WebScraper {
	// Field that contains list of website that can be used
	public List<Site> listOfSite;
	
	public WebScraper() {
		listOfSite = new ArrayList<>();
	}
	
	/**
	 * Method for add site that can be used by WebScraper
	 * 
	 * @param site website can be used by WebScraper
	 */
	public void addSite(Site site) {
		listOfSite.add(site);
	}
	
	/**
	 * Method for web scraping comic update. Make sure valiableUpdateSite parameter exist in list of website in WebScraper
	 * 
	 * @param title title of comic is searched
	 * @param avaibleUpdateSite website can be used in this comic searching
	 * @return {@link Snippet} (comic data)
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
				doesFirstSiteFound = true;
			}else {
				for(String o3 : o.search(title).getUpdateSite()) {
					result.addUpdateSite(o3);
				}
			}
		}
		
		return result;
	}
}
