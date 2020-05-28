package comicchecker;

/**
 * Class for handle web scraping method from various website
 * 
 * @author Agung Mubarak
 *
 */
public abstract class Site {
	private String url;
	
	public Site(String url) {
		this.url = url;
	}
	
	abstract void search(String title);
}