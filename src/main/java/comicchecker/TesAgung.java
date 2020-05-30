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
		
		try {
			Document doc = Jsoup.connect("https://guya.moe").timeout(30000).get();
			Elements dropDownItems= doc.select(".dropdown-menu[aria-labelledby='readManga'] > .dropdown-item");
			for(int i=0; i<3; i++) {
				List<String> titleList = new ArrayList<>();
				
				String urlComicInfo = dropDownItems.get(i).absUrl("href");
				Document docComicInfo = Jsoup.connect(urlComicInfo).timeout(30000).get();
				
				Elements comicDetails= docComicInfo.select(".col-lg-8.col-md-7.col-sm-11.col-xs-12.series-content > article");
				
				Elements titles = comicDetails.select("h1, h2, h3, h4");
				for(Element o : titles) {
					titleList.add(o.text());
				}
				
				String image = comicDetails.select("picture > img").first().absUrl("src");
				String description = comicDetails.select("p").text();
				
				Element chapterInfo = docComicInfo.select("#chapterTable > .table-default").first();
				String updateChapter = chapterInfo.select(".chapter-title").text();
				
				String updateTime = chapterInfo.select(".detailed-chapter-upload-date").text();
				DateTimeFormatter  formatter = DateTimeFormatter.ofPattern("'['y, M, d, H, m, s']'");
				LocalDate comicUpdateTime = LocalDate.parse(updateTime, formatter);
				LocalDate nowTime = LocalDate.now();
				int diff = Period.between(comicUpdateTime, nowTime).getDays();
				
				String updateSite = chapterInfo.select(".chapter-title > a").first().absUrl("href");

				printTest(titleList
						,image
						,description
						,updateChapter
						,updateTime
						,comicUpdateTime
						,nowTime
						,diff + (diff > 1 ? " days ago" : " day ago")
						,updateSite
						);
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		
//		LocalDate today = LocalDate.now();
//		LocalDate yesterday = today.minusDays(1);
//		
//		int diff = Period.between(today, yesterday).getDays();
//		
//		LocalDateTime now = LocalDateTime.now();
//	    LocalDateTime sixMinutesBehind = now.minusMinutes(6);
//	 
//	    Duration duration = Duration.between(now, sixMinutesBehind);
//	    long diff = Math.abs(duration.toMinutes());
//	 
//		printTest(now, sixMinutesBehind, diff);
		
		System.out.println("\n- Program is finished -");
	}
	
	public static void printTest(Object... o) {
		for(Object o2 : o) {
			System.out.println(o2);
		}
	}
}