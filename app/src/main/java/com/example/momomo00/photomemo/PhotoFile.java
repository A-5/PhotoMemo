package com.example.momomo00.photomemo;

import android.os.Environment;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 *
 * Created by momomo00 on 14/09/17.
 */
public class PhotoFile {
    private static final PhotoFile instance = new PhotoFile();
    private File photoFile;

    private PhotoFile() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
        String photoName = "Sample" + sdf.format(calendar.getTime()) + ".jpg";
        photoFile = new File(
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM),
                photoName);
    }

    public static PhotoFile getInstance() {
        return instance;
    }

    public File getPhotoFile() {
        return photoFile;
    }
}
