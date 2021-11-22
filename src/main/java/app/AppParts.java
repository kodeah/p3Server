package app;

import playback.INTERFACE_Playback;
import storage.ISongStore;

public interface AppParts {

    ISongStore getSongStore();
    INTERFACE_Playback getPlaybackInterface();

}
