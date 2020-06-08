package comicchecker;

import java.net.SocketTimeoutException;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Class for handle web scraping method from various website type2
 * <br><br>
 * Knowing for : guya.moe
 * <br><br>
 * Extends from {@link Site}<br>
 * 
 * @see Site
 * @author Agung Mubarak
 *
 */
public class Type2 extends Site{

	public Type2(String url) {
		super(url);
	}

	@Override
	Snippet search(String title) {
		Snippet result = null;
		
		try {
			
			Document doc = Jsoup.connect(getUrl()).timeout(30000).get();
			// URLs for comics are contained in dropdown menu 
			Elements dropDownItems= doc.select(".dropdown-menu[aria-labelledby='readManga'] > .dropdown-item");
			
			// I have found three comic in Guya.moe
			for(int i=0; i<3; i++) {
				String comicTitle = "";
				
				String urlComicInfo = dropDownItems.get(i).absUrl("href");
				
				try {
					
					Document docComicInfo = Jsoup.connect(urlComicInfo).timeout(30000).get();
					
					Elements comicDetails= docComicInfo.select(".col-lg-8.col-md-7.col-sm-11.col-xs-12.series-content > article");
					
					// In Guya.moe, their comic have alternative title, maybe each comic have 2 or 3 titles and
					// these are write in h1, h2, h3 and other similar for that
					Elements titles = comicDetails.select("h1, h2, h3, h4");
					for(Element o : titles) {
						// This line for checking if title that is searched same as various titles in here
						if(o.text().toLowerCase().contains(title.toLowerCase())) {
							comicTitle = o.text();
							break;
						}
					}
					// If don't, continue next comic
					if(comicTitle.equals("")) {
						continue;
					}
					
					String image = comicDetails.select("picture > img").first().absUrl("src");
					String description = comicDetails.select("p").text();
					
					Element chapterInfo = docComicInfo.select("#chapterTable > .table-default").first();
					String updateChapter = chapterInfo.select(".chapter-title").text();
					
					// Site give date data. So i have to count newness
					String updateTime = chapterInfo.select(".detailed-chapter-upload-date").text();
					DateTimeFormatter  formatter = DateTimeFormatter.ofPattern("'['y, M, d, H, m, s']'");
					LocalDate comicUpdateTime = LocalDate.parse(updateTime, formatter);
					LocalDate nowTime = LocalDate.now();
					int diff = Period.between(comicUpdateTime, nowTime).getDays();
					
					String updateSite = chapterInfo.select(".chapter-title > a").first().absUrl("href");
					
					if(diff <= 1) {
						result = new Snippet(comicTitle
								,image
								,description
								,updateChapter
								,diff + " day ago" // I convert this data to format day like 2 days ago or 1 day ago
								,updateSite
								);
						return result;
					}else {
						continue;
					}
					
				} catch (SocketTimeoutException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
		} catch (SocketTimeoutException e) {
			e.printStackTrace();
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}

	@Override
	Snippet getInfo(String title) {
		Snippet result = null;
		
		try {
			
			Document doc = Jsoup.connect(getUrl()).timeout(30000).get();
			// URLs for comics are contained in dropdown menu 
			Elements dropDownItems= doc.select(".dropdown-menu[aria-labelledby='readManga'] > .dropdown-item");
			
			// I have found three comic in Guya.moe
			for(int i=0; i<3; i++) {
				String comicTitle = "";
				
				String urlComicInfo = dropDownItems.get(i).absUrl("href");
				
				try {
					Document docComicInfo = Jsoup.connect(urlComicInfo).timeout(30000).get();
					
					Elements comicDetails= docComicInfo.select(".col-lg-8.col-md-7.col-sm-11.col-xs-12.series-content > article");
					
					// In Guya.moe, their comic have alternative title, maybe each comic have 2 or 3 titles and
					// these are write in h1, h2, h3 and other similar for that
					Elements titles = comicDetails.select("h1, h2, h3, h4");
					for(Element o : titles) {
						// This line for checking if title that is searched same as various titles in here
						if(o.text().toLowerCase().contains(title.toLowerCase())) {
							comicTitle = o.text();
							break;
						}
					}
					// If don't, continue next comic
					if(comicTitle.equals("")) {
						continue;
					}
					
					String image = comicDetails.select("picture > img").first().absUrl("src");
					String description = comicDetails.select("p").text();
					
					result = new Snippet(comicTitle
							,image
							,description
							,""
							,""
							,""
							);
					return result;
					
				} catch (SocketTimeoutException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
		} catch (SocketTimeoutException e) {
			e.printStackTrace();
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
}