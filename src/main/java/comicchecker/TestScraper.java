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

public class TestScraper {
	public static void main(String[] args) {
		System.out.println("- Program is started -\n");
		
		WebScraper webScraper = new WebScraper();
		webScraper.addSite(new Type1("https://mangakakalots.com")
				, new Type2("https://guya.moe")
				, new Type3("https://manganelo.com")
				, new WebtoonId("https://webtoons.com/id")
				, new WebtoonEn("https://webtoons.com/en")
				);
		
		Snippet comic = new Snippet("Kaguya-sama: Love is War", "https://guya.moe");
		Snippet comic2 = new Snippet("house daddy","https://webtoons.com/id");
		Snippet comic3 = new Snippet("Lore Olympus","https://webtoons.com/en");
		comic.update(webScraper);
		comic2.update(webScraper);
		comic3.update(webScraper);
		
		printTest(comic);
		printTest(comic2);
		printTest(comic3);
		System.out.println("\n- Program is finished -");
	}
	
	public static void printTest(Object... o) {
		for(Object o2 : o) {
			System.out.println(o2);
			System.out.println("\n---------------\n");
		}
	}
}