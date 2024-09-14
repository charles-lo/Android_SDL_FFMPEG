package com.charles.app;

import android.Manifest;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;

import org.libsdl.app.SDLActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashSet;

/**
 * A sample wrapper class that just calls SDLActivity
 */

public class FFPlay extends SDLActivity {

    @Override
    protected String[] getArguments() {
        String url = "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4";
        String url1 = "rtmp://vd2.wmspanel.com/video_demo/stream";
        String url2 = "rtsp://192.168.1.163:1945/";
        String url3 = getFilesDir().getPath() + "/test.mp4";
        String url4 = "/storage/emulated/0/DCIM/Camera/VID_20240822_103949.mp4";
        return new String[]{url3, "-x", String.valueOf(mSurface.getWidth()), "-y", String.valueOf(mSurface.getHeight())};
    }

    @Override
    protected String[] getLibraries() {
        return new String[] {
                "SDL2",
                "ffplay"
        };
    }

    final static String TAG = FFPlay.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (checkVideosReadPermission()) {
            for (String file : getAllMedia()) {
                Log.d(TAG, " video file path: " + file);
            }
        }

        copyAssets();

        super.onCreate(savedInstanceState);
    }

    public static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 123;


    public boolean checkVideosReadPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (checkSelfPermission(
                    Manifest.permission. READ_MEDIA_VIDEO) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(
                        new String[] { Manifest.permission.READ_MEDIA_VIDEO },
                        MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                return false;
            }
        } else if (Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            if (checkSelfPermission(
                    Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(
                                    new String[] { Manifest.permission.READ_EXTERNAL_STORAGE },
                                    MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                return false;
            }
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE:
                if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
                    finish();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions,
                        grantResults);
        }
    }

    public ArrayList<String> getAllMedia() {
        HashSet<String> videoItemHashSet = new HashSet<>();
        String[] projection = { MediaStore.Video.VideoColumns.DATA ,MediaStore.Video.Media.DISPLAY_NAME};
        Cursor cursor = getContentResolver().query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, projection, null, null, null);
        try {
            assert cursor != null;
            cursor.moveToFirst();
            do{
                videoItemHashSet.add((cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA))));
            }while(cursor.moveToNext());

            cursor.close();
        } catch (Exception ignored) {
        }
        return new ArrayList<>(videoItemHashSet);
    }

    private void copyAssets() {
        AssetManager assetManager = getAssets();
        String[] files = null;
        try {
            files = assetManager.list("video");
        } catch (IOException e) {
            Log.e("tag", "Failed to get asset file list.", e);
        }

        String File_PATH = getFilesDir().getPath() + "/";
        File mWorkingPath = new File(File_PATH);
        if (!mWorkingPath.exists()) {
            mWorkingPath.mkdirs();
        }

        assert files != null;
        for (String file : files) {
            try {
                File outFile = new File(mWorkingPath, file);
                if (outFile.exists()) {
                    continue;
                }

                InputStream in = getAssets().open("video/" + file);
                OutputStream out = new FileOutputStream(outFile);
                byte[] buf = new byte[1024];
                int len;
                while ((len = in.read(buf)) > 0) {
                    out.write(buf, 0, len);
                }

                in.close();
                out.close();
            } catch (IOException ignored) {
            }
        }
    }
}