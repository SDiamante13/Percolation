package com.diamante.percolation;


import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

import java.util.ArrayList;

public class PercolationStats {

    private Percolation percolation;
    private ArrayList<Integer> indexList;
    private double[] percolationThresholds;
    private int randomUnionIndex;
    private int randomUnionNumber;
    private int randomRow;
    private int randomCol;
    private int colLength;
    private int rowLength;
    private double openSiteCount;
    private double size;


    public PercolationStats(int n, int trials) { // perform trials independent experiments on an n-by-n grid
        percolationThresholds = new double[trials];
        for (int trialCount = 0; trialCount < trials; trialCount++) {
            percolation = new Percolation(n);
            colLength = percolation.getColLength();
            rowLength = percolation.getRowLength();
            size = percolation.getSize();
            indexList = new ArrayList<>();
            initializeArrayList();
            while (!percolation.isPercolationFlag()) {
                randomUnionIndex = StdRandom.uniform(indexList.size());
                randomUnionNumber = indexList.get(randomUnionIndex);
                randomRow = percolation.mapUnionToGrid(randomUnionNumber, colLength)[0];
                randomCol = percolation.mapUnionToGrid(randomUnionNumber, colLength)[1];
                percolation.open(randomRow, randomCol);
                indexList.remove(indexList.indexOf(randomUnionNumber));
            }
            openSiteCount = percolation.numberOfOpenSites();
            percolationThresholds[trialCount] = calculatePrecolationThreshold();
        }
    }

    private double calculatePrecolationThreshold() {
        return openSiteCount / size;
    }

    public double mean() { // sample mean of percolation threshold
        // takes in array
        return StdStats.mean(percolationThresholds);
    }

    public double stddev() {// sample standard deviation of percolation threshold
        return StdStats.stddev(percolationThresholds);
    }

    public double confidenceLo(){  // low  endpoint of 95% confidence interval
        return mean() - 1.96*stddev()/Math.sqrt(size);
    }

    public double confidenceHi(){  // high endpoint of 95% confidence interval
        return mean() + 1.96*stddev()/Math.sqrt(size);
    }

    private void initializeArrayList() {
        for (int i = 0; i < size; i++) {
            indexList.add(i); // indexList contains 0 : size-1
        }
    }

    public void printStatistics(){
        System.out.printf("Mean of p*: %.6f ", mean());
        System.out.println();
        System.out.printf("Standard Deviation of p*: %.6f ", stddev());
        System.out.println();
        System.out.print("95% Confidence Interval: ");
        System.out.format("[ %.6f, %.6f]", confidenceLo(), confidenceHi());
    }
}
