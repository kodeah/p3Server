package youtubeDl;

import utils.id.IdGenerator;
import utils.id.NaiveIdGenerator;
import utils.log.ILog;
import utils.scripts.Script;
import utils.scripts.ScriptBuilder;
import utils.scripts.ScriptExecutionResult;
import utils.scripts.ScriptExecutor;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;

public class YoutubeDownloader {

	private final String tmpDirPath;
    private final ILog log;
	
	private final IdGenerator idGenerator = new NaiveIdGenerator();
	//This one is not used for the final song id,
	//but to identify songs from different downloads.
	
	public YoutubeDownloader(
	        final String tmpDirPath,
            final ILog log )
    {
		this.tmpDirPath = tmpDirPath;
        this.log = log;
	}

	public String downloadFromUrl(
	        final String downloadUrl,
            final String targetDirectory ) throws Exception {
		// Returns the final path of the downloaded song.

		final Long tmpId = idGenerator.getFreeId();
		final String dlTempDirPath =
				tmpDirPath +
						"/" + "p3Server_downloading_" + tmpId.toString();

		new File(dlTempDirPath).mkdir();
		final Script dlScript = new ScriptBuilder()
				.appendLine("youtube-dl",
						"--extract-audio",
						"--audio-format", "mp3",
						"--restrict-filenames",
						"--no-playlist",
						"-o", "%(title)s.%(ext)s",
						downloadUrl)
				.toScript();
		ScriptExecutionResult dlScriptResult = new ScriptExecutor(dlTempDirPath, log).execute(dlScript);
		log.log(dlScriptResult.output());
		log.log(dlScriptResult.errors());
		if (!dlScriptResult.success()) {
			throw new Exception("youtube-dl failed. See logs for details.");
		}

		//Then locate the downloaded file:
		File dlTmpDir = new File(dlTempDirPath);
		if (!dlTmpDir.isDirectory()) {
			final String errorString = "Assuming temporary download directory at '" + dlTempDirPath + "', but it is not a directory!";
			throw new Exception(errorString);
		}

		File[] files = dlTmpDir.listFiles();
		if (files.length != 1) {
			final String errorString ="Temporary download directory at '" + dlTempDirPath + "' should contain exactly" +
					" one file, but has " + files.length + " files!";
			throw new Exception(errorString);
		}
		File songFile = files[0];

		String songPathTmp = songFile.getCanonicalPath();
		String songPathTarget = targetDirectory +
				"/" + songFile.getName();

		//Move the file to the permanent storage location:
		log.log("Moving song from '" + songPathTmp +
				"' to '" + songPathTarget + "' (post dl script).");
		Files.move(Path.of(songPathTmp), Path.of(songPathTarget));

		final Script updateScript = new ScriptBuilder()
				.appendLine("mpc", "update")
				.toScript();
		ScriptExecutionResult updateScriptResult = new ScriptExecutor(tmpDirPath, log).execute(updateScript);
		log.log(updateScriptResult.output());
		log.log(updateScriptResult.errors());
		if(!updateScriptResult.success()) {
			throw new Exception("'mpc update' failed.");
		}

		new File(dlTempDirPath).delete();

		File finalSongFile = new File(songPathTarget);
		if (!finalSongFile.isFile()) {
			final String errorString = "Assuming downloaded song file at '" + songPathTarget + "', but it is not a file!";
			throw new Exception(errorString);
		}
			
		return songPathTarget;
	}
	
}
