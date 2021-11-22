package youtubeDl;

import org.junit.jupiter.api.Test;
import utils.log.PrintLog;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class YoutubeDownloaderTest {

	@Test
	public void test() {

		final String songDirPath = "/tmp/youtubeDlTest83745643747";

		YoutubeDownloader ytdl = new YoutubeDownloader(
				"/tmp",
				songDirPath,
				new PrintLog() );

		// Prepare/clean output folder:
		final File songFile = new File(songDirPath + "/Lacchesi_Mac_Declos_-_Give_It_To_Me_Nelly_X-Rated_Edit.mp3");
		if(songFile.exists()) {
			songFile.delete();
		}
		final File songDir = new File(songDirPath);
		if(songDir.exists()) {
			songDir.delete();
		}
		assertEquals(false, songDir.exists());
		songDir.mkdir();

		// Perform tested action:
		String target = null;
		try {
			target = ytdl.downloadFromUrl(
					"https://soundcloud.com/mac-declos/lacchesi-mac-declos-give-it-to-me-nelly-x-rated-edit-1" );
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}

		// Check if test successful:
		System.out.println(target);
		assertEquals(true, new File(target).exists());
	}

}
