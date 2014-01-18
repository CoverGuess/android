#ifndef __JNI_WRAPPER_FILTERS__
#define __JNI_WRAPPER_FILTERS__

#include <jni.h>
#include <android/log.h>
#include <string.h>
#include "log/logger.h"
#include "pixellize.h"


extern "C"{
    JNIEXPORT void JNICALL Java_info_acidflow_coverguess_processing_filters_NDKFilters_pixellize(
        JNIEnv * env, jobject obj, jstring jFilePath, jint divisionFactor , jint outputWidth, jint outputHeight, jintArray jbgraOut
    );
};
#endif