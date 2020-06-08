package comicchecker;

public class backgroundCheck {
	public static void main(String[] args) {
		ComicCheckerApplication app = new ComicCheckerApplication();
		app.setScheduledTime(8, 18);
		app.frequentlyUpdateSubscription(60);
	}
}