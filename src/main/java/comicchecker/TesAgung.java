package comicchecker;

import java.time.*;
import java.time.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class TesAgung {
	public static void main(String[] args) {
		System.out.println("- Program is started -\n");
		
		WebScraper webScraper = new WebScraper();
		webScraper.addSite(new Type1("https://mangakakalots.com"),new Type2("https://guya.moe"));
		
		Snippet comic = new Snippet("kimetsu", "https://mangakakalots.com");
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