package comicchecker;

import java.lang.reflect.Field;
import java.time.*;
import java.time.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.sun.tools.javac.Main;

public class TesAgung {
	public static void main(String[] args) {
		System.out.println("- Program is started -\n");
		
		WebScraper webScraper = new WebScraper();
		webScraper.addSite(new Type1("https://mangakakalots.com")
				, new Type2("https://guya.moe")
				, new Type3("https://manganelo.com")
				);
		
		Snippet comic = new Snippet("tales of gods", "https://mangakakalots.com", "https://manganelo.com", "https://guya.moe");
		comic.update(webScraper);
		
		printTest(comic);
		
		System.out.println("\n- Program is finished -");
	}
	
	public static void printTest(Object... o) {
		for(Object o2 : o) {
			System.out.println(o2);
		}
	}
}