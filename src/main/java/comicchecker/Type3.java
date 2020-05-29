package comicchecker;

/**
 * Class for handle web scraping method from various website type3
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
	Snippet search(String title) {
		return null;
	}
}