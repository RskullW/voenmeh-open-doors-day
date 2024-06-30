#include <jni.h>

JNIEXPORT jstring JNICALL
Java_ru_voenmeh_openday_domain_utils_NativeHost_1iosKt_prefsName(JNIEnv *env, jclass clazz)  {
}

JNIEXPORT void JNICALL
Java_ru_voenmeh_openday_domain_utils_NativeHost_1androidKt_prefsName(JNIEnv *env, jclass clazz) {
    return (*env)->NewStringUTF(env, "voenmeh:openDay");
}