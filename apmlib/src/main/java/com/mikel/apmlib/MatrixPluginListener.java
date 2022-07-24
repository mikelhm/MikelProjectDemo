package com.mikel.apmlib;

import android.content.Context;

import com.tencent.matrix.plugin.DefaultPluginListener;
import com.tencent.matrix.report.Issue;
import com.tencent.matrix.util.MatrixLog;

public class MatrixPluginListener extends DefaultPluginListener {
    public static final String TAG = "MatrixPluginListener";
    public MatrixPluginListener(Context context) {
        super(context);
    }

    @Override
    public void onReportIssue(Issue issue) {
        super.onReportIssue(issue);
        //todo 处理性能监控数据
        MatrixLog.e(TAG, issue.toString());
    }
}