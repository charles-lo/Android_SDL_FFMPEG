package com.charles.app;

import org.libsdl.app.SDLActivity;

/**
 * A sample wrapper class that just calls SDLActivity
 */

public class FFPlay extends SDLActivity {
    protected String[] getArguments() {
        return new String[]{"http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4", "-x", String.valueOf(mSurface.getWidth()), "-y", String.valueOf(mSurface.getHeight())};
    }
}