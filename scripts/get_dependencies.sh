#!/bin/sh
#/bin/bash
####################################################################################################
##                                      OPENCV                                                    ##
####################################################################################################
INITIAL_PATH=`pwd`
PATH_TO_JNI="./CoverGuess/src/main/jni"
OPEN_CV_DIRECTORY_NAME="opencv"
URL_OPENCV_SDK_DOWNLOAD="http://downloads.sourceforge.net/project/opencvlibrary/opencv-android/2.4.8/OpenCV-2.4.8-android-sdk.zip?r=http%3A%2F%2Fopencv.org%2Fdownloads.html&ts=1390061949&use_mirror=kent"
OPEN_CV_ZIP_NAME="OpenCV-2.4.8-android-sdk.zip"
OPEN_CV_UNZIPPED_DIRECTORY="OpenCV-2.4.8-android-sdk"
cd $PATH_TO_JNI
mkdir $OPEN_CV_DIRECTORY_NAME
cd $OPEN_CV_DIRECTORY_NAME
wget $URL_OPENCV_SDK_DOWNLOAD
unzip  $OPEN_CV_ZIP_NAME -d .
mv $OPEN_CV_UNZIPPED_DIRECTORY/sdk/* .
rm -rf $OPEN_CV_UNZIPPED_DIRECTORY
rm -rf $OPEN_CV_ZIP_NAME
####################################################################################################
##                                      NINEOLDS                                                  ##
####################################################################################################
NINE_OLDS_DIRECTORY="lib_NineOldAndroids"
NINE_OLDS_DIRECTORY_TMP="tmp_lib_NineOldAndroids"
NINE_OLDS_DIRECTORY_TMP_LIB="library"
cd $INITIAL_PATH
mkdir $NINE_OLDS_DIRECTORY
mkdir $NINE_OLDS_DIRECTORY_TMP
git clone https://github.com/CoverGuess/NineOldAndroids.git $NINE_OLDS_DIRECTORY_TMP
mv $NINE_OLDS_DIRECTORY_TMP/$NINE_OLDS_DIRECTORY_TMP_LIB/* $NINE_OLDS_DIRECTORY
rm -rf $NINE_OLDS_DIRECTORY_TMP
####################################################################################################
##                                                                                   ##
####################################################################################################
DAOGENERATOR_SRC_GEN_FOLDER="src-gen"
cd $INITIAL_PATH
mkdir CoverGuess/$DAOGENERATOR_SRC_GEN_FOLDER
