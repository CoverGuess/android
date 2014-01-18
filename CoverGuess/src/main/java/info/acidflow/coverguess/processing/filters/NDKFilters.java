package info.acidflow.coverguess.processing.filters;

/**
 * Created by acidflow on 12/01/14.
 */
public class NDKFilters {
    native public static void pixellize(String filePath, int divisionFactor, int outputWidth, int outputHeight, int[] outputAllocation);
}
