#include <jni.h>
#include <string.h>
#include <stdlib.h>
#include <android/log.h>
#include "aes.h"
#include <cassert>
static const char *TAG = "native_test";
#define LOGI(fmt, args...) __android_log_print(ANDROID_LOG_INFO,  TAG, fmt, ##args)
#define LOGD(fmt, args...) __android_log_print(ANDROID_LOG_DEBUG, TAG, fmt, ##args)
#define LOGE(fmt, args...) __android_log_print(ANDROID_LOG_ERROR, TAG, fmt, ##args)

extern "C" {
    /**********************************静态注册****************************************/
    JNIEXPORT jstring JNICALL Java_com_mikel_nativelib_JNIHelper_testJNI(
            JNIEnv *env, jclass thiz) {
        char *helloworld = "hello world in C++";
        return env->NewStringUTF(helloworld);
    }

    JNIEXPORT jstring JNICALL
    Java_com_mikel_nativelib_JNIHelper_encryptJNI(JNIEnv *env, jobject thiz, jstring origin) {
        const char *origin_str;
        const char *key_str = "123456789abcdef";
        origin_str = env->GetStringUTFChars(origin, 0);
        char encrypt_str[1024] = {0};
        AES aes_en((unsigned char *) key_str);
        aes_en.Cipher((char *) origin_str, encrypt_str);
        return env->NewStringUTF(encrypt_str);
    }

    JNIEXPORT jstring JNICALL
    Java_com_mikel_nativelib_JNIHelper_decryptJNI(JNIEnv *env, jobject thiz, jstring des) {
        const char *des_str;
        const char *key_str = "123456789abcdef";
        des_str = env->GetStringUTFChars(des, 0);
        char decrypt_str[1024] = {0};
        AES aes_de((unsigned char *) key_str);
        aes_de.InvCipher((char *) des_str, decrypt_str);
        return env->NewStringUTF(decrypt_str);
    }

    /****************************************动态注册*********************************************************/
    jstring dynamic_method_test(JNIEnv *env, jobject thiz,
                                jint intValue, jfloat floatValue, jdouble doubleValue, jbyte byteValue,
                                jstring strValue, jboolean boolValue, jintArray intArrayValue,
                                jfloatArray floatArrayValue, jdoubleArray doubleArrayValue,
                                jbyteArray byteArrayValue, jbooleanArray boolArrayValue) {
        //转指针
        const char* strBuf = env->GetStringUTFChars(strValue, nullptr);
        jint* intBuf = env->GetIntArrayElements(intArrayValue, nullptr);
        jfloat* floatBuf = env->GetFloatArrayElements(floatArrayValue, nullptr);
        jdouble* doubleBuf = env->GetDoubleArrayElements(doubleArrayValue, nullptr);
        jbyte* byteBuf = env->GetByteArrayElements(byteArrayValue, nullptr);
        jboolean* boolBuf = env->GetBooleanArrayElements(boolArrayValue, nullptr);
        //handle buf

        //释放指针
        env->ReleaseStringUTFChars(strValue, strBuf);
        env->ReleaseIntArrayElements(intArrayValue, intBuf, 0);
        env->ReleaseFloatArrayElements(floatArrayValue, floatBuf, 0);
        env->ReleaseDoubleArrayElements(doubleArrayValue, doubleBuf, 0);
        env->ReleaseByteArrayElements(byteArrayValue, byteBuf, 0);
        env->ReleaseBooleanArrayElements(boolArrayValue, boolBuf, 0);
        return env->NewStringUTF(strBuf);
    }

    static JNINativeMethod dynamicMethods[] = {
            //public String dynamicMethodTest(int a, float b, double c, byte d, String e, boolean f, int[] g,
            //                             float[] h, double[] i, byte[] j, boolean[] l)
            {"dynamicMethodTest","(IFDBLjava/lang/String;Z[I[F[D[B[Z)Ljava/lang/String;",(void*)dynamic_method_test},
    };

    static int registerNativeMethods(JNIEnv* env, const char* className, JNINativeMethod* dynamicMethods,int methodsNum){
        jclass clazz;
        clazz = env->FindClass(className);
        if(clazz == NULL){
            return JNI_FALSE;
        }
        //env->RegisterNatives 注册函数需要参数：java类，动态注册函数的数组，动态注册函数的个数
        if(env->RegisterNatives(clazz, dynamicMethods, methodsNum) < 0){
            return JNI_FALSE;
        }
        return JNI_TRUE;
    }

    //指定java层的类路径，然后动态注册函数
    static int registerNatives(JNIEnv* env){
        const char* className  =  "com/mikel/nativelib/JNIHelper";
        return registerNativeMethods(env, className, dynamicMethods, sizeof(dynamicMethods)/ sizeof(dynamicMethods[0]));
    }

    JNIEXPORT jint JNICALL JNI_OnLoad(JavaVM* vm, void* reserved){
        JNIEnv* env = NULL;
        //通过Java虚拟机获取JNIEnv
        int result = vm->GetEnv(reinterpret_cast<void**>(&env), JNI_VERSION_1_6);
        if (result != JNI_OK) {
            return -1;
        }
        assert(env != NULL);
        if(!registerNatives(env)){
            return -1;
        }
        //返回JNI版本
        return JNI_VERSION_1_6;
    }
}