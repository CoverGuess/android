package info.acidflow.coverguess.processing.filters;

/**
 * Wrapper to call native methods to apply a filter on a bitmap
 * Created by acidflow on 12/01/14.
 */
public class NDKFilters {
    native public static void pixellize(String filePath, int divisionFactor, int outputWidth, int outputHeight, int[] outputAllocation);
}
