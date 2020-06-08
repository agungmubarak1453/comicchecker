package comicchecker;

public class backgroundCheck {
	public static void main(String[] args) {
		ComicCheckerApplication app = new ComicCheckerApplication();
		app.setScheduledTime(6, 39);
		app.frequentlyUpdateSubscription(60);
	}
}