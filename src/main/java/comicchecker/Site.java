package comicchecker;

import java.io.IOException;

/**
 * Class for handle web scraping method from various website
 * <br><br>
 * <b>Field:</b><br>
 * - {@link #url}<br>
 * 
 * @see Type1
 * @see Type2
 * @see Type3
 * @see WebScraper
 * @author Agung Mubarak
 *
 */
public abstract class Site {
	protected String url;
	
	/**
	 * Constructor method with create url data for web scraping
	 * 
	 * @param url url of site to web scraping
	 */
	protected Site(String url) {
		this.url = url;
	}
	
	protected String getUrl() {
		return url;
	}
	
	/**
	 * Method for web scraping data of comic that is searched
	 * 
	 * @param title title is searched
	 * @return {@link Snippet} (comic data)
	 * @throws IOException web scraping failed to get data maybe connection or other
	 * @see WebScraper#check(String, java.util.List)
	 */
	abstract protected Snippet search(String title) throws IOException;
	/**
	 * Method for web scraping info of comic that is searched
	 * 
	 * @param title title is searched
	 * @return {@link Snippet} (comic data)
	 * @throws IOException web scraping failed to get data maybe connection or other
	 * @see WebScraper#checkInfo(String, java.util.List)
	 */
	abstract protected Snippet getInfo(String title) throws IOException;
	
	@Override
	public String toString() {
		return url;
	}
}