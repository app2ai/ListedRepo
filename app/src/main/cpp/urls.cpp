#include <jni.h>
#include <string>

using namespace std;

extern "C" JNIEXPORT jstring JNICALL
Java_com_example_listedapplication_MyApplication_00024Companion_getBaseUrl(
        JNIEnv *env,
        jobject thiz
)   {
        string domain = "https://api.inopenapp.com/";
        return env->NewStringUTF(domain.c_str());

    }

extern "C" JNIEXPORT jstring JNICALL
Java_com_example_listedapplication_repo_DashboardRemoteApiRepository_00024Companion_proxyPath(
        JNIEnv *env,
        jobject thiz
) {
        string api_path = "/api/v1";
        return env-> NewStringUTF(api_path.c_str());
}

extern "C"
JNIEXPORT jstring JNICALL
Java_com_example_listedapplication_MyApplication_00024Companion_getToken(JNIEnv *env,
                                                                         jobject thiz) {
    string token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6MjU5MjcsImlhdCI6MTY3NDU1MDQ1MH0.dCkW0ox8tbjJA2GgUx2UEwNlbTZ7Rr38PVFJevYcXFI";
    return env->NewStringUTF(token.c_str());
}