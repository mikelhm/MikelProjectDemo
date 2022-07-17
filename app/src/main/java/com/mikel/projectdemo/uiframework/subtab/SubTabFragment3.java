package com.mikel.projectdemo.uiframework.subtab;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.mikel.nativelib.JNIHelper;
import com.mikel.projectdemo.R;

import org.jetbrains.annotations.NotNull;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class SubTabFragment3 extends Fragment {

    public static SubTabFragment3 build() {
        return new SubTabFragment3();
    }

    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        View rootView = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_sub_tab_content3, null, true);
        initUI(rootView);
        return rootView;
    }

    private void initUI(View rootView) {
        Button testJNIBtn = rootView.findViewById(R.id.testJNIBtn);
        testJNIBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringBuilder result = new StringBuilder();
                String temp = JNIHelper.testJNI();
                result.append(temp).append("\n");
                result.append("需要加密的字符串:").append("123456789").append("\n");
                String encodeStr = JNIHelper.encryptJNI("123456789");
                result.append("加密后字符串:").append(encodeStr).append("\n");
                String decodeStr = JNIHelper.decryptJNI(encodeStr);
                result.append("解密后字符串:").append(decodeStr).append("\n");


                int[] iArray = {1,2,3};
                float[] fArray = {4f,5f,6f};
                double[] dArray = {7.01,8.02, 9.03};
                byte[] bArray = {0x10, 0x11};
                boolean[] booleans = {true, true, false};
                String ret = JNIHelper.dynamicMethodTest(1,2, 3, (byte) 4,"5", true, iArray, fArray, dArray, bArray, booleans);
                result.append("动态注册返回:").append(ret);
                Toast.makeText(getContext(), result.toString(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
