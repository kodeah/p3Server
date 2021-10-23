package app;

public class MAIN {

	private static App appInstance;
	
	public static App getAppInstance() {
		return appInstance;
	}

	public static void main(String[] args) {
		try {
			appInstance = new App(  );
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
