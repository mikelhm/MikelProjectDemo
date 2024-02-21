package com.mikel.projectdemo.uiframework.subtab;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import com.mikel.projectdemo.R;
import com.mikel.projectdemo.apm.ApmTestActivity;
import com.mikel.projectdemo.presenter.FileHandlePresenter;
import com.mikel.projectdemo.video.VideoTestActivity;

import org.jetbrains.annotations.NotNull;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

public class SubTabFragment1 extends Fragment {
    FileHandlePresenter fileHandlePresenter;
    public static SubTabFragment1 build() {
        return new SubTabFragment1();
    }

    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        View rootView = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_sub_tab_content1, null, true);
        fileHandlePresenter = new FileHandlePresenter(this);
        initUI(rootView);
        return rootView;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull @NotNull String[] permissions, @NonNull @NotNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        fileHandlePresenter.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        fileHandlePresenter.onActivityResult(requestCode, resultCode, data);
    }

    private void initUI(View rootView) {
        Button readFileBtn = rootView.findViewById(R.id.read_file_from_external_btn);
        readFileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 * android 6.0以上动态权限申请：读权限
                 */
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && ContextCompat.checkSelfPermission(getActivity(),
                        Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, FileHandlePresenter.PERMISSION_CODE_READ_EXTERNAL);
                } else {
                    fileHandlePresenter.requestReadExternalStorage();
                }
            }
        });

        Button writeFileBtn = rootView.findViewById(R.id.write_file_to_external_btn);
        writeFileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 * android 6.0以上动态权限申请：写权限
                 */
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && ContextCompat.checkSelfPermission(getActivity(),
                        Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, FileHandlePresenter.PERMISSION_CODE_WRITE_EXTERNAL);
                } else {
                    fileHandlePresenter.handleWriteExternalStorage();
                }
            }
        });

        Button takePicVideoBtn = rootView.findViewById(R.id.take_pic_video_btn);
        takePicVideoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 * android 6.0以上动态权限申请:相机+写权限
                 */
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && (ContextCompat.checkSelfPermission(getActivity(),
                        Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(getActivity(),
                        Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED )) {
                    requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.CAMERA}, FileHandlePresenter.PERMISSION_CODE_CAMERA);
                } else {
                    fileHandlePresenter.showBottomSheetDialog();
                }
            }
        });




        Button testMemBtn = rootView.findViewById(R.id.apm_entry_btn);
        testMemBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ApmTestActivity.startActivity(getActivity());
            }
        });

        Button exoTestBtn = rootView.findViewById(R.id.exo_entry_btn);
        exoTestBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VideoTestActivity.startActivity(getActivity());
            }
        });
    }
}
