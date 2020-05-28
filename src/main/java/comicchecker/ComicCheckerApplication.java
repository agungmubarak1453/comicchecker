package comicchecker;

/**
 * Class for application object
 * 
 * @author Agung Mubarak
 *
 */
public class ComicCheckerApplication {
	private Snippet snippet;
	private WebScraper webScraper;
	
	public ComicCheckerApplication() {
		webScraper = new WebScraper();
	}
}