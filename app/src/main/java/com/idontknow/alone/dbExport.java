package com.idontknow.alone;

import android.content.Context;
import android.os.Build;
import android.os.Environment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

public class dbExport {


    public static void exportAllDatabases(final Context context) {
       // Log.d(LOG_TAG, "exportAllDatabases: ");
        File sd = Environment.getExternalStorageDirectory();
        if (sd.canWrite()) {
            final File[] databases = new File(context.getFilesDir().getParentFile().getPath() + "/databases").listFiles();
            for (File databaseFile : databases) {
                final String backupFilename = databaseFile.getName() + "-" + Build.SERIAL +
                        "-" + System.currentTimeMillis() + ".db";
                File backupFile = new File(sd, backupFilename);
                FileChannel inputChannel = null;
                FileChannel outputChannel = null;

                try {
                  //  Log.d(LOG_TAG, "Backing up: " + databaseFile + " to file: " + backupFile);
                    inputChannel = new FileInputStream(databaseFile.getAbsolutePath()).getChannel();
                    outputChannel = new FileOutputStream(backupFile).getChannel();
                    outputChannel.transferFrom(inputChannel, 0, inputChannel.size());
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (inputChannel != null) {
                        try {
                            inputChannel.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    if (outputChannel != null) {
                        try {
                            outputChannel.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        } else {
           // Log.w(LOG_TAG, "Can't write to sdcard");
        }
    }
}