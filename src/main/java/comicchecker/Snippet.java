package comicchecker;

import javafx.scene.image.Image;

/**
 * Class that contains title, thumbnail, description, link
 * 
 * @author Agung Mubaraks
 *
 */

public class Snippet {

		private String title;
		private Image thumbnail;
		private String description;
		
		public Snippet(String title) {
			this.title = title;
			update(title);
		}
		
		public void update(String title) {
			
		}

		/**
		 * @return the thumbnail
		 */
		public Image getThumbnail() {
			return thumbnail;
		}
		/**
		 * @param thumbnail the thumbnail to set
		 */
		public void setThumbnail(Image thumbnail) {
			this.thumbnail = thumbnail;
		}
		/**
		 * @return the description
		 */
		public String getDescription() {
			return description;
		}
		/**
		 * @param description the description to set
		 */
		public void setDescription(String description) {
			this.description = description;
		}
		/**
		 * @param title the title to set
		 */
		public void setTitle(String title) {
			this.title = title;
		}
}