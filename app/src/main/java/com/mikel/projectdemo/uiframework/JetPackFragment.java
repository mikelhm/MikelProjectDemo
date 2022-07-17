package com.mikel.projectdemo.uiframework;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mikel.projectdemo.R;
import com.mikel.projectdemo.jetpack.view.ui.Paging2MainActivity;
import com.mikel.projectdemo.jetpack.view.ui.Paging3MainActivity;

import org.jetbrains.annotations.NotNull;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class JetPackFragment extends Fragment {

    public static JetPackFragment build() {
        return new JetPackFragment();
    }

    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        View jetPackRootView = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_jetpack_main, null, true);

        jetPackRootView.findViewById(R.id.paging2_main_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Paging2MainActivity.class);
                startActivity(intent);
            }
        });

        jetPackRootView.findViewById(R.id.paging3_main_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Paging3MainActivity.class);
                startActivity(intent);
            }
        });

        return jetPackRootView;
    }
}
