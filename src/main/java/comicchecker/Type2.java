package comicchecker;

/**
 * Class for handle web scraping method from various website type2
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
		return null;
	}
}