package com.mikel.projectdemo.presenter;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.mikel.baselib.manager.ThreadManager;
import com.mikel.projectdemo.R;
import com.mikel.projectdemo.utils.FileHelper;

import org.jetbrains.annotations.NotNull;
import java.io.File;
import java.io.FileOutputStream;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class FileHandlePresenter {
    public static final String TAG = "FileHandlePresenter";
    public static final int PERMISSION_CODE_READ_EXTERNAL = 1;
    public static final int PERMISSION_CODE_WRITE_EXTERNAL = 2;
    public static final int PERMISSION_CODE_CAMERA = 3;
    public static final int REQUEST_CODE_READ_FILE_FROM_EXTERNAL = 1000;
    public static final int REQUEST_CODE_WRITE_IMAGE = 1001;
    public static final int REQUEST_CODE_WRITE_VIDEO = 1002;
    private Fragment mFragment;
    private BottomSheetDialog bottomSheetDialog;
    public FileHandlePresenter(Fragment fragment) {
        mFragment = fragment;
    }

    /**
     * 打开文件选择器
     */
    public void requestReadExternalStorage() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        //指定多种类型的文件
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("*/*");
        mFragment.startActivityForResult(Intent.createChooser(intent, "选择文件"), REQUEST_CODE_READ_FILE_FROM_EXTERNAL);
    }

    /**
     *   将沙盒中的文件/data/data/包名/cache/test.txt 保存到外部存储区域 sdcard/test.txt
     */
    public void handleWriteExternalStorage() {
        File sandFile = new File(mFragment.getActivity().getCacheDir() + File.separator + "test.txt");
        if(!sandFile.exists()) {
            Toast.makeText(mFragment.getActivity(), "请先手动创建test.txt 并且保存到/data/data/包名/cache/下", Toast.LENGTH_LONG).show();
            return;
        }
        ThreadManager.getInstence().postTask(new Runnable() {
            @Override
            public void run() {
                Uri externalUri = null;
                if(Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
                    File externalFile = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
                    File destFile = new File(externalFile + File.separator + "test.txt");
                    externalUri = Uri.fromFile(destFile);
                } else {
                    ContentResolver resolver = mFragment.getActivity().getContentResolver();
                    ContentValues values = new ContentValues();
                    values.put(MediaStore.Downloads.DISPLAY_NAME, "test.txt");
                    values.put(MediaStore.Downloads.RELATIVE_PATH, Environment.DIRECTORY_DOWNLOADS);//保存路径
                    Uri uri = MediaStore.Files.getContentUri("external");
                    externalUri = resolver.insert(uri, values);
                }

                boolean ret = FileHelper.copySandFileToExternalUri(mFragment.getActivity(), sandFile.getAbsolutePath(), externalUri);
                mFragment.getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(mFragment.getActivity(), "从沙盒" + sandFile.getAbsolutePath() + "中拷贝文件到外部存储"
                                + Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath()
                                + ", 结果= " + ret, Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
    }

    public void requestWriteImage() {
        Intent intent = new Intent();
        intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
        Uri mediaUri = null;
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            mediaUri = getImgeUriAboveQ(mFragment.getActivity(), "Image_" + System.currentTimeMillis());
        } else {
            File dicmFile = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
            File mediaFile = new File(dicmFile.getAbsoluteFile() + "Image_" +  System.currentTimeMillis() +".jpg");
            mediaUri = Uri.fromFile(mediaFile);
        }
        intent.putExtra(MediaStore.EXTRA_OUTPUT, mediaUri);
        mFragment.startActivityForResult(intent, REQUEST_CODE_WRITE_IMAGE);
    }

    public void requestWriteVideo() {
        Intent intent = new Intent();
        intent.setAction("android.media.action.VIDEO_CAPTURE");
        intent.addCategory("android.intent.category.DEFAULT");
        Uri mediaUri = null;
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            mediaUri = getVideoUriAboveQ(mFragment.getActivity(), "Video_" + System.currentTimeMillis());
        } else {
            File dicmFile = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
            File mediaFile = new File(dicmFile.getAbsoluteFile() + "Video_" +  System.currentTimeMillis() +".mp4");
            mediaUri = Uri.fromFile(mediaFile);
        }
        intent.putExtra(MediaStore.EXTRA_OUTPUT, mediaUri);
        mFragment.startActivityForResult(intent, REQUEST_CODE_WRITE_VIDEO);
    }

    private Uri getImgeUriAboveQ(Context context, String imageName) {
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
            return null;
        }
        Uri imageUri = null;
        ContentResolver resolver = context.getContentResolver();
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.DISPLAY_NAME, imageName);//图片名
        values.put(MediaStore.Images.Media.DESCRIPTION, imageName); //描述
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");//类型
        values.put(MediaStore.Images.Media.RELATIVE_PATH, Environment.DIRECTORY_DCIM);//保存路径
        Uri externalUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        imageUri = resolver.insert(externalUri, values);
        return imageUri;
    }

    private Uri getVideoUriAboveQ(Context context, String videoName) {
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
            return null;
        }
        Uri videoUri = null;
        ContentResolver resolver = context.getContentResolver();
        ContentValues values = new ContentValues();
        values.put(MediaStore.Video.Media.DISPLAY_NAME, videoName);//视频名
        values.put(MediaStore.Video.Media.DESCRIPTION, videoName); //描述
        values.put(MediaStore.Images.Media.MIME_TYPE, "video/mp4");//类型
        values.put(MediaStore.Video.Media.RELATIVE_PATH, Environment.DIRECTORY_DCIM);//保存路径
        Uri externalUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
        videoUri = resolver.insert(externalUri, values);
        return videoUri;
    }

    /**
     * 申请权限回调
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    public void onRequestPermissionsResult(int requestCode, @NonNull @NotNull String[] permissions, @NonNull @NotNull int[] grantResults) {
        if(requestCode == PERMISSION_CODE_READ_EXTERNAL) {
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                requestReadExternalStorage();
            }
        } else if(requestCode == PERMISSION_CODE_WRITE_EXTERNAL) {
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                handleWriteExternalStorage();
            }
        } else if (requestCode == PERMISSION_CODE_CAMERA){
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                showBottomSheetDialog();
            }
        }
    }

    /**
     * 文件选择后回调
     * @param requestCode
     * @param resultCode
     * @param data
     */
    public void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        if(resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CODE_READ_FILE_FROM_EXTERNAL:
                    ThreadManager.getInstence().postTask(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                Uri fileUri = data.getData();
                                File destFile = File.createTempFile("temp", ".tmp", mFragment.getActivity().getCacheDir());
                                Log.d(TAG, " read external storage file = "+ fileUri.toString() + ", dest path = " + destFile.getAbsolutePath());
                                FileHelper.copyFieUriToInnerStorage(mFragment.getActivity(), fileUri, destFile);
                                //todo 外部存储的文件uri 已变成了 应用包下的文件destFile，后续可以new File操作destFile
                                mFragment.getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(mFragment.getActivity(), "Read External Storage file Success! Save Path = "
                                                + destFile.getAbsolutePath(), Toast.LENGTH_LONG).show();
                                    }
                                });
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });
                    break;
                case REQUEST_CODE_WRITE_IMAGE:
                    Log.d(TAG, " on activity result write image  intent = " + data);
                    Toast.makeText(mFragment.getActivity(), "保存图片成功", Toast.LENGTH_SHORT).show();
                    break;
                case REQUEST_CODE_WRITE_VIDEO:
                    Log.d(TAG, " on activity result write  video intent = " + data);
                    Toast.makeText(mFragment.getActivity(), "保存视频成功", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
        }
    }

    public void showBottomSheetDialog() {
        bottomSheetDialog = new BottomSheetDialog(mFragment.getActivity());
        View dialogView= LayoutInflater.from(mFragment.getActivity())
                .inflate(R.layout.layout_bottom_sheet_image_video, null);
        TextView pictureTv= (TextView) dialogView.findViewById(R.id.picture_tv);
        TextView videoTv= (TextView) dialogView.findViewById(R.id.video_tv);
        TextView cancelTv= (TextView) dialogView.findViewById(R.id.cancel_tv);
        pictureTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestWriteImage();
                bottomSheetDialog.dismiss();
            }
        });
        videoTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestWriteVideo();
                bottomSheetDialog.dismiss();
            }
        });

        cancelTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDialog.dismiss();
            }
        });

        bottomSheetDialog.setContentView(dialogView);
        bottomSheetDialog.show();
    }
}
