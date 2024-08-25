package com.charles.app;

import org.libsdl.app.SDLActivity;

/**
 * A sample wrapper class that just calls SDLActivity
 */

public class FFPlay extends SDLActivity {

    protected String[] getArguments() {
        String url = "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4";
        String url1 = "rtmp://vd2.wmspanel.com/video_demo/stream";
        return new String[]{url, "-x", String.valueOf(mSurface.getWidth()), "-y", String.valueOf(mSurface.getHeight())};
    }

    protected String[] getLibraries() {
        return new String[] {
                "SDL2",
                "ffplay"
        };
    }
}