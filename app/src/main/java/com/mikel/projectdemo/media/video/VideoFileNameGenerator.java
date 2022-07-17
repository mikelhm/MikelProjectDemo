package com.mikel.projectdemo.media.video;

import android.net.Uri;

import com.danikula.videocache.ProxyCacheUtils;
import com.danikula.videocache.file.FileNameGenerator;

public class VideoFileNameGenerator implements FileNameGenerator {

    @Override
    public String generate(String url) {
        Uri uri = Uri.parse(url);
        String path = uri.getHost() + uri.getPath();
        return ProxyCacheUtils.computeMD5(path);
    }
}
