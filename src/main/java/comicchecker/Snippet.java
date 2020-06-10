package comicchecker;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Class that contains data for comic
 * <b>Field:</b><br>
 * - {@link #title}<br>
 * - {@link #thumbnail}<br>
 * - {@link #author}<br>
 * - {@link #genre}<br>
 * - {@link #description}<br>
 * - {@link #updateChapter}<br>
 * - {@link #updateTime}<br>
 * - {@link #updateSite}<br>
 * - {@link #avaibleUpdateSite}<br>
 * 
 * @author Agung Mubarak
 * @see UserData
 */

public class Snippet implements Serializable{
		private static final long serialVersionUID = 2L;
		
		private String title;
		private String thumbnail;
		private String description;
		private String author;
		private String genre;
		private String updateChapter;
		private String updateTime;
		private List<String> updateSite;
		
		private List<String> avaibleUpdateSite;
		
		/**
		 * Constructor method but only create data for title and avaibleUpdateSite, and others data in null or "".
		 * 
		 * @see WebScraper
		 * @see WebScraper#checkInfo(String, List)
		 * @param title title of comic
		 * @param avaibleUpdateSite site for used in web sraping
		 */
		public Snippet(WebScraper webScraper, String title, String... sites) {
			// I am confused for take others data with web scraping. I haven't found list of comic in mangakakalots
			this.title = title;
			thumbnail = "";
			author = "";
			genre = "";
			description = "";
			updateChapter = "";
			updateTime = "";
			updateSite = new ArrayList<>();
			avaibleUpdateSite = new ArrayList<>();
			for(String o : sites) {
				avaibleUpdateSite.add(o);
			}
			
			checkInfo(webScraper);
		}
		
		/**
		 * Constructor method but only create data for title and avaibleUpdateSite, and others data in null or "".
		 * 
		 * @see WebScraper
		 * @see WebScraper#checkInfo(String, List)
		 * @param title title of comic
		 * @param avaibleUpdateSite site for used in web sraping
		 */
		public Snippet(WebScraper webScraper, String title, List<String> avaibleUpdateSite) {
			this.title = title;
			thumbnail = "";
			author = "";
			genre = "";
			description = "";
			updateChapter = "";
			updateTime = "";
			updateSite = new ArrayList<>();
			this.avaibleUpdateSite = avaibleUpdateSite;
			
			checkInfo(webScraper);
		}
		
		/**
		 * Constructor method for create Snippet template
		 * 
		 * @param title title of comic
		 * @param thumbnail address of image
		 * @param author author of comic
		 * @param genre genre of comic
		 * @param description description of comic
		 * @param updateChapter last chapter of updated comic
		 * @param updateTime time of comic updating
		 * @param updateSite site of updated comic
		 * @see Site
		 */
		public Snippet(String title, String thumbnail, String author, String genre, String description
				,String updateChapter, String updateTime, String updateSite) {
			this.title = title;
			this.thumbnail = thumbnail;
			this.author = author;
			this.genre = genre;
			this.description = description;
			this.updateChapter = updateChapter;
			this.updateTime = updateTime;
			this.updateSite = new ArrayList<>();
			this.updateSite.add(updateSite);
			avaibleUpdateSite = new ArrayList<>(); // avaibleUpdateSite not be used, so this field in empty
		}
		
		/**
		 * Method for update snippet. This use web scraping
		 * 
		 * @param webScraper machine for web scraping
		 * @return boolean if snippet update or not
		 * @see WebScraper
		 */
		public boolean update(WebScraper webScraper) {
			Snippet result = webScraper.check(title, avaibleUpdateSite);
			if(result != null) {
				title = result.getTitle();
				thumbnail = result.getThumbnail();
				author = result.getAuthor();
				genre = result.getGenre();
				description = result.getDescription();
				updateChapter = result.getUpdateChapter();
				updateTime = result.getUpdateTime();
				updateSite = result.getUpdateSite();
				
				return true;
			}else {
				return false;
			}
		}
		
		/**
		 * Method for get comic info
		 * 
		 * @param webScraper machine for web scraping
		 * @return boolean if that have info
		 * @see WebScraper
		 */
		public boolean checkInfo(WebScraper webScraper) {
			Snippet result = webScraper.checkInfo(title, avaibleUpdateSite);
			if(result != null) {
				title = result.getTitle();
				thumbnail = result.getThumbnail();
				author = result.getAuthor();
				genre = result.getGenre();
				description = result.getDescription();
				
				return true;
			}else {
				return false;
			}
		}
		
		// Group of method for getter and setter
		
		public String getTitle() {
			return title;
		}

		public void setTitle(String title) {
			this.title = title;
		}

		public String getThumbnail() {
			return thumbnail;
		}

		public void setThumbnail(String thumbnail) {
			this.thumbnail = thumbnail;
		}

		public String getAuthor() {
			return author;
		}

		public void setAuthor(String author) {
			this.author = author;
		}

		public String getGenre() {
			return genre;
		}

		public void setGenre(String genre) {
			this.genre = genre;
		}

		public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description;
		}
		
		public String getUpdateChapter() {
			return updateChapter;
		}

		public void setUpdateChapter(String updateChapter) {
			this.updateChapter = updateChapter;
		}

		public String getUpdateTime() {
			return updateTime;
		}

		public void setUpdateTime(String updateTime) {
			this.updateTime = updateTime;
		}

		public List<String> getUpdateSite() {
			return updateSite;
		}

		public void addUpdateSite(String url) {
			updateSite.add(url);
		}

		public List<String> getAvaibleUpdateSite() {
			return avaibleUpdateSite;
		}

		public void setAvaibleUpdateSite(ArrayList<String> avaibleUpdateSite) {
			this.avaibleUpdateSite = avaibleUpdateSite;
		}
		
		public void addAvaibleUpdateSite(String... sites) {
			for(String o : sites) {
				avaibleUpdateSite.add(o);
			}
		}
		
		@Override
		public String toString() {
			return "title :" + title
					+ "\nthumbnail :" + thumbnail
					+ "\ndescription :" + description
					+ "\nupdateChapter :" + updateChapter
					+ "\nupdateTime :" + updateTime
					+ "\nupdateSite :" + updateSite
					+ "\navaibleUpdateSite :" + avaibleUpdateSite
					;
		}
}