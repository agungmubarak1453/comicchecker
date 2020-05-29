package comicchecker;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class Test {

	public static void main(String[] args) {
		Document doc;
		List<String> a = new ArrayList<>();
		try {
			doc = Jsoup.connect("https://www.wikihow.com/Prevent-Coronavirus-if-You-Are-at-Risk").get();
			String title = doc.title();
			System.out.print(title);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
