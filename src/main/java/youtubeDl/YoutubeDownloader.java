package youtubeDl;

import utils.id.IdGenerator;
import utils.id.NaiveIdGenerator;
import utils.log.ILog;
import utils.scripts.Script;
import utils.scripts.ScriptBuilder;
import utils.scripts.ScriptExecutionResult;
import utils.scripts.ScriptExecutor;

import java.io.File;
import java.io.IOException;
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
            final String targetDirectory )
    {
		// Returns the final path of the downloaded song.
		
		final Long tmpId = idGenerator.getFreeId();
        final String dlTempDirPath =
                tmpDirPath +
				"/" + "partyPartyPlaylistServer_downloading_" + tmpId.toString();

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
		
		//Then locate the downloaded file:
		File dlTmpDir = new File(dlTempDirPath);
		if (!dlTmpDir.isDirectory()) {
			log.error("'" + dlTempDirPath + "' is not a directory!");
			return null;
		}
		
		File[] files = dlTmpDir.listFiles();
		if (files.length != 1) {
            log.error("'" + dlTempDirPath + "' should contain exactly" +
					" one file, but has " + files.length + " files!");
		}
		File songFile = files[0];
		
		try {
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

            new File(dlTempDirPath).delete();
			
			File finalSongFile = new File(songPathTarget);
			if (!finalSongFile.isFile()) {
                log.error("Final downloaded song file not there!");
				return null;
			}
			
			return songPathTarget;
			
		} catch ( IOException e) {
            log.error(e.getMessage());
			e.printStackTrace();
			return null;
		}
	}
	
}
