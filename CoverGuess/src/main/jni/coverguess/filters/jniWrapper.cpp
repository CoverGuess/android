#include "jniWrapper.h"
JNIEXPORT void JNICALL Java_info_acidflow_coverguess_processing_filters_NDKFilters_pixellize(
        JNIEnv * env, jobject obj, jstring jFilePath, jint divisionFactor , jint outputWidth, jint outputHeight, jintArray jbgraOut
    ){
        LOGI("JNIWRAPPER", "PIXELLIZE FILTER CALLED");
        jint*  _bgra = env->GetIntArrayElements(jbgraOut, 0);
        const char *filePath = env->GetStringUTFChars(jFilePath, 0);

        if (NULL != filePath){
            // init our output image
            CoverGuessFilter::applyPixelize(filePath, (int) outputHeight, (int) outputWidth, (unsigned char *)_bgra, (int) divisionFactor);
        }else{
            LOGE("JNIWRAPPER", "FILE PATH IS NULL");
        }


        if(NULL != jbgraOut){
            env->ReleaseIntArrayElements(jbgraOut, _bgra, 0);
        }
        if(NULL != jFilePath){
            env->ReleaseStringUTFChars(jFilePath, filePath);
        }
    }