#include "pixellize.h"
void CoverGuessFilter::applyPixelize(const char* filePath, int outputHeight, int outputWidth, unsigned char * outputAllocation, int divisorFactor){
    LOGI("PIXELIZE_FILTER", "applyPixelize() called");
    Mat toPixelize = imread(filePath, 1);
    int blockXSize = outputWidth / divisorFactor;
    int blockYSize = outputHeight / divisorFactor;
    int pixelCount = blockXSize * blockYSize; // How many pixels we'll read per block - used to find the average colour
    // Integers to hold our total colour values (used to find the average)
    int redSum     = 0;
    int greenSum   = 0;
    int blueSum    = 0;
    int red, green, blue; // Integers to hold our pixel values

     // Loop through each block horizontally
     if(blockXSize != 1 && blockYSize != 1){

            for (int xLoop = 0; xLoop < outputWidth; xLoop += blockXSize){

                // Loop through each block vertically
                for (int yLoop = 0; yLoop < outputHeight; yLoop += blockYSize){

                    // Reset our colour counters for each block
                    redSum     = 0;
                    greenSum   = 0;
                    blueSum    = 0;

                    // Read every pixel in the block and calculate the average colour
                    int minX = std::min(outputWidth - xLoop, blockXSize);
                    int minY = std::min(outputHeight - yLoop, blockYSize);
                    for (int pixXLoop = 0; pixXLoop < minX; pixXLoop++){

                        for (int pixYLoop = 0; pixYLoop < minY; pixYLoop++){
                            // Add each component to its sum
                            redSum   += toPixelize.at<Vec3b>(yLoop + pixYLoop, xLoop + pixXLoop)[2];
                            greenSum += toPixelize.at<Vec3b>(yLoop + pixYLoop, xLoop + pixXLoop)[1];
                            blueSum  += toPixelize.at<Vec3b>(yLoop + pixYLoop, xLoop + pixXLoop)[0];

                        } // End of inner y pixel counting loop

                    } // End of outer x pixel countier loop

                    // Calculate the average colour of the block
                    red   = redSum   / (minX*minY);
                    green = greenSum / (minX*minY);
                    blue  = blueSum  / (minX*minY);

                    // Draw a rectangle of the average colour
                    rectangle(
                        toPixelize,
                        cvPoint(xLoop, yLoop),
                        cvPoint(xLoop + minX, yLoop + minY),
                        CV_RGB(red, green, blue),
                        CV_FILLED,
                        8,
                        0
                    );


                } // End of inner y loop

            } // End of outer x loop
            }
            cvtColor(toPixelize, toPixelize, COLOR_RGB2RGBA, 4);
            memcpy(outputAllocation, toPixelize.data, outputWidth * outputHeight * 4 * sizeof(uchar));
}