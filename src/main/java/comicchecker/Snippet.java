package comicchecker;

import java.util.ArrayList;

import javafx.scene.image.Image;

/**
 * Class that contains title, thumbnail, description, link
 * 
 * @author Agung Mubarak
 *
 */

public class Snippet {

		private String title;
		private Image thumbnail;
		private String description;
		private String updateTime;
		private ArrayList<String> updateSite;
		
		private ArrayList<String> avaibleUpdateSite;
		
		public Snippet(String title, ArrayList<String> avaibleUpdateSite) {
			this.title = title;
			this.avaibleUpdateSite = avaibleUpdateSite;
			update(title);
		}
		
		public void update(String title) {
			
		}

		public Image getThumbnail() {
			return thumbnail;
		}

		public void setThumbnail(Image thumbnail) {
			this.thumbnail = thumbnail;
		}

		public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description;
		}

		public String getUpdateTime() {
			return updateTime;
		}

		public void setUpdateTime(String updateTime) {
			this.updateTime = updateTime;
		}

		public ArrayList<String> getUpdateSite() {
			return updateSite;
		}

		public void setUpdateSite(ArrayList<String> updateSite) {
			this.updateSite = updateSite;
		}

		public ArrayList<String> getAvaibleUpdateSite() {
			return avaibleUpdateSite;
		}

		public void setAvaibleUpdateSite(ArrayList<String> avaibleUpdateSite) {
			this.avaibleUpdateSite = avaibleUpdateSite;
		}
}