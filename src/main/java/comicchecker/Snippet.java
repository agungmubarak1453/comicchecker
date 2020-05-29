package comicchecker;

import java.util.ArrayList;
import java.util.List;

/**
 * Class that contains data for comic
 * 
 * @author Agung Mubarak
 *
 */

public class Snippet {
		private String title;
		private String thumbnail;
		private String description;
		private String updateChapter;
		private String updateTime;
		private List<String> updateSite;
		
		private List<String> avaibleUpdateSite;
		
		/**
		 * Constructor method but only create data for title and avaibleUpdateSite, and others data in null or "".
		 * 
		 * @see WebScraper
		 * @param title title of comic
		 * @param avaibleUpdateSite site for used in web sraping
		 */
		public Snippet(String title, List<String> avaibleUpdateSite) {
			// I am confused for take others data with web scraping. I haven't found list of comic in mangakakalots
			this.title = title;
			thumbnail = null;
			description = "";
			updateChapter = "";
			updateTime = "";
			updateSite = new ArrayList<>();
			this.avaibleUpdateSite = avaibleUpdateSite;
		}
		
		/**
		 * Constructor method for create Snippet template
		 */
		public Snippet() {
			title = "";
			thumbnail = null;
			description = "";
			updateChapter = "";
			updateTime = "";
			updateSite = new ArrayList<>();
			avaibleUpdateSite = new ArrayList<>();
		}
		
		/**
		 * Method for update snippet. This use web scraping
		 */
		public void update(WebScraper webScraper) {
			Snippet result = webScraper.check(title, avaibleUpdateSite);
			this.title = result.getTitle();
			thumbnail = result.getThumbnail();
			description = result.getDescription();
			updateChapter = result.getUpdateChapter();
			updateTime = result.getUpdateTime();
			updateSite = result.getUpdateSite();
		}
		
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