#ifndef __COVER_GUESS_PIXELIZE_FILTER__
#define __COVER_GUESS_PIXELIZE_FILTER__

#include <jni.h>
#include <android/log.h>
#include <string.h>
#include <opencv2/opencv.hpp>
#include "log/logger.h"
using namespace cv;
namespace CoverGuessFilter{
    void applyPixelize(const std::string & filePath, int outputHeight, int outputWidth, unsigned char * outputAllocation, int divisorFactor);
}

#endif