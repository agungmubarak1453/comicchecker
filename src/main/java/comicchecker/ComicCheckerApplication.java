package comicchecker;

/**
 * Class for application object
 * 
 * @author Agung Muabrak
 *
 */
public class ComicCheckerApplication {
	private Snippet snippet;
	private WebScraper webScraper;
	
	public ComicCheckerApplication() {
		snippet = new Snippet();
		webScraper = new WebScraper();
	}
}