package comicchecker;

import java.util.ArrayList;
import java.util.List;

/**
 * Class for get data from comic website
 * 
 * @author AgungMubarak
 *
 */
public class WebScraper {
	public List<Site> listOfSite;
	
	public WebScraper() {
		listOfSite = new ArrayList<>();
	}
	
	public void addSite(Site site) {
		listOfSite.add(site);
	}
	
	public Snippet check(String title, List<String> avaibleUpdateSite) {
		Snippet result = null;
		boolean doesFirstSiteFound = false;
		
		for(Site o : listOfSite) {
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
