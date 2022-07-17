package com.mikel.nativelib;

public class JNIHelper {
    static {
        System.loadLibrary("native_test");
    }

    /***
     * 静态注册
     * @return
     */
    public static native String testJNI();
    public static native String encryptJNI(String originStr);
    public static native String decryptJNI(String enCodeStr);

    /**
     * 动态注册
     */
    public static native String dynamicMethodTest(int intValue, float floatValue, double doubleValue, byte bteValue,
                                                  String strValue, boolean boolValue,
                                                  int[] intArrayValue, float[] floatArrayValue, double[] doubleArrayValue,
                                                  byte[] byteArrayValue, boolean[] boolArrayValue);
}