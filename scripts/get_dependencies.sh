#/bin/bash
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
