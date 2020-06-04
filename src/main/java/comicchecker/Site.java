package comicchecker;

/**
 * Class for handle web scraping method from various website
 * 
 * @see Type1
 * @see Type2
 * @see Type3
 * @author Agung Mubarak
 *
 */
public abstract class Site {
	private String url;
	
	/**
	 * Constructor method with create url data for web scraping
	 * 
	 * @param url url of site to web scraping
	 */
	public Site(String url) {
		this.url = url;
	}
	
	public String getUrl() {
		return url;
	}
	
	/**
	 * Method for web scraping data of comic that is searched
	 * 
	 * @param title title is searched
	 * @return {@link Snippet} (comic data)
	 */
	abstract Snippet search(String title);
	/**
	 * Method for web scraping info of comic that is searched
	 * 
	 * @param title title is searched
	 * @return {@link Snippet} (comic data)
	 */
	abstract Snippet getInfo(String title);
	
	@Override
	public String toString() {
		return url;
	}
}