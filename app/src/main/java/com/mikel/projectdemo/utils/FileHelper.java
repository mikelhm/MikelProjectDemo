package com.mikel.projectdemo.utils;

import android.content.ContentResolver;
import android.content.Context;
import android.net.Uri;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class FileHelper {
    public static final String TAG = "FileUtil_TAG";
    /**
     * 通过uri拷贝外部存储的文件到自己应用的沙盒目录
     * @param uri 外部存储文件的uri
     * @param destFile 沙盒文件陆军
     */
    public static void copyFieUriToInnerStorage(Context context, Uri uri, File destFile) {
        InputStream inputStream = null;
        FileOutputStream fileOutputStream = null;
        try {
            inputStream = context.getContentResolver().openInputStream(uri);
            if(destFile.exists()) {
                destFile.delete();
            }
            fileOutputStream = new FileOutputStream(destFile);
            byte[] buffer = new byte[4096];
            int redCount;
            while ((redCount = inputStream.read(buffer)) >= 0) {
                fileOutputStream.write(buffer, 0, redCount);
            }
        } catch (Exception e) {
            Log.e(TAG, " copy file uri to inner storage e = " + e.toString());
        } finally {
            try {
                if(fileOutputStream != null) {
                    fileOutputStream.flush();
                    fileOutputStream.getFD().sync();
                    fileOutputStream.close();
                }
                if(inputStream != null) {
                    inputStream.close();
                }
            } catch (Exception e) {
                Log.e(TAG, " close stream e = " + e.toString());
            }
        }
    }

    /**
     * 拷贝沙盒中的文件到外部存储区域
     * @param filePath 沙盒文件路径
     * @param  externalUri 外部存储文件的 uri
     */
    public static boolean copySandFileToExternalUri(Context context, String filePath, Uri externalUri) {
        ContentResolver contentResolver = context.getContentResolver();
        InputStream inputStream = null;
        OutputStream outputStream = null;
        boolean ret = false;
        try {
            outputStream = contentResolver.openOutputStream(externalUri);
            File sandFile = new File(filePath);
            if(sandFile.exists()) {
                inputStream = new FileInputStream(sandFile);

                int readCount = 0;
                byte[] buffer = new byte[1024];
                while ((readCount = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0 , readCount);
                    outputStream.flush();
                }
            }
            ret = true;
        } catch (Exception e) {
            Log.e(TAG, "copy SandFile To ExternalUri. e = " + e.toString());
            ret = false;
        } finally {
            try {
                if(outputStream != null) {
                    outputStream.close();
                }
                if(inputStream != null) {
                    inputStream.close();
                }
                Log.d(TAG, " input stream and output stream close successful.");
            } catch (Exception e) {
                e.printStackTrace();
                Log.e(TAG, " input stream and output stream close fail. e = " + e.toString());
            }
            return ret;
        }
    }
}
