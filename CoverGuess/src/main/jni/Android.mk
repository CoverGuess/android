LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)
OPENCV_LIB_TYPE := STATIC
include opencv/native/jni/OpenCV.mk

LOCAL_MODULE    := coverguessfilter
LOCAL_SRC_FILES := coverguess/filters/jniWrapper.cpp coverguess/filters/pixellize.cpp
LOCAL_LDLIBS +=  -llog -ldl -lm

include $(BUILD_SHARED_LIBRARY)