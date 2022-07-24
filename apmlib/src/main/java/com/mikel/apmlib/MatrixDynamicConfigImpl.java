package com.mikel.apmlib;
import com.tencent.mrs.plugin.IDynamicConfig;

public class MatrixDynamicConfigImpl implements IDynamicConfig {
    public MatrixDynamicConfigImpl() {}

    public boolean isFPSEnable() { return true;}
    public boolean isTraceEnable() { return true; }
    public boolean isMatrixEnable() { return true; }
    public boolean isDumpHprof() {  return false;}

    @Override
    public String get(String key, String defStr) {
        //hook to change default values
        return defStr;
    }

    @Override
    public int get(String key, int defInt) {
        //hook to change default values
        return defInt;
    }

    @Override
    public long get(String key, long defLong) {
        //hook to change default values
//        if (IDynamicConfig.ExptEnum.clicfg_matrix_trace_evil_method_threshold.name().equals(key)) {
//            return 300; // 默认为700
//        }
        return defLong;
    }

    @Override
    public boolean get(String key, boolean defBool) {
        //hook to change default values
        return defBool;
    }

    @Override
    public float get(String key, float defFloat) {
        //hook to change default values
        return defFloat;
    }
}

