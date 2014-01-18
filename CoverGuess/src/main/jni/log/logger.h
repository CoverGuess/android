#ifndef __COVER_GUESS_LOGGER__
#define __COVER_GUESS_LOGGER__
#include <android/log.h>
#define  LOGI(tag, ...)  __android_log_print(ANDROID_LOG_INFO,tag,__VA_ARGS__)
#define  LOGE(tag, ...)  __android_log_print(ANDROID_LOG_ERROR,tag,__VA_ARGS__)
#endif