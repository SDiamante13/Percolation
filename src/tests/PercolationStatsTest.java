package tests;

import com.diamante.percolation.PercolationStats;
import org.junit.jupiter.api.*;

public class PercolationStatsTest {

    @Test
    public void testRunOneTrialWithNxNGrid(){
        PercolationStats percolationStats = new PercolationStats(200, 30);
        System.out.printf("Mean of p*: %.6f ", percolationStats.mean());
        System.out.println();
        System.out.printf("Standard Deviation of p*: %.6f ", percolationStats.stddev());
        System.out.println();
        System.out.print("95% Confidence Interval: ");
        System.out.format("[ %.6f, %.6f]", percolationStats.confidenceLo(), percolationStats.confidenceHi());
    }

}
