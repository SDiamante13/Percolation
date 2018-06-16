package com.diamante.percolation;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private int[][] siteGrid;
    private int rowLength;
    private int colLength;
    private WeightedQuickUnionUF siteUnion;
    private boolean percolationFlag;
    private double openSiteCount;
    private double size;

    public Percolation(int n) {// create n-by-n grid, with all sites blocked
        siteGrid = new int[n][n];
        rowLength = siteGrid.length;
        colLength = siteGrid[0].length;
        size = rowLength * colLength;
        siteUnion = new WeightedQuickUnionUF(n*n);
        UniteTopRow();
        UniteBottomRow();
        percolationFlag = false;
        openSiteCount = 0;
    }

    public void open(int row, int col) { // open site (row, col) if it is not open already
        siteGrid[row][col] = 1; // this site is now open for business
        if(checkLeft(row, col)){
            uniteLeft(row, col);
        }
        if(checkUp(row, col)){
            uniteUp(row, col);
        }
        if(checkRight(row, col)){
            uniteRight(row, col);
        }
        if(checkDown(row, col)){
            uniteDown(row, col);
        }
        if(percolates()){
            percolationFlag = true;
        }
        openSiteCount++;
    }
    public boolean isOpen(int row, int col) { // is site (row, col) open?
        return siteGrid[row][col] == 1;
    }
    public boolean isFull(int row, int col){ // is site (row, col) full?
        int unionIndex = mapGridToUnion(row, col, colLength);
        // all top rows will be united to start
        if(siteUnion.connected(unionIndex, 0)){
                return true;
            }
        return false;
    }

    public double numberOfOpenSites(){ // number of open sites
        return openSiteCount;
    }

    public boolean percolates(){ // does the system percolate?
        int bottomRow = rowLength - 1;
        // checks if last element is Full
        // I could check any element and this would also work since they all will be united
            if(isFull(bottomRow, colLength-1)){
                return true;
            }
        return false;
    }


    //*******************************************


    public int[][] getSiteGrid() {
        return siteGrid;
    }

    public int getRowLength() {
        return rowLength;
    }

    public int getColLength() {
        return colLength;
    }
    public double getSize(){return size;}

    public WeightedQuickUnionUF getSiteUnion() {
        return siteUnion;
    }

    public boolean isPercolationFlag() {
        return percolationFlag;
    }

    public void printGrid() {
        for (int row = 0; row < siteGrid.length; row++) {
            for (int col = 0; col < siteGrid[0].length; col++) {
                System.out.print(siteGrid[row][col] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    private void UniteTopRow() {
        int topRowIndex = 0;
        while(topRowIndex < colLength - 1) {
            siteUnion.union(topRowIndex, ++topRowIndex);
        }
    }
    private void UniteBottomRow() {
        int bottomRowIndex = mapGridToUnion(rowLength-1, 0, colLength);
        while(bottomRowIndex < rowLength * colLength - 1) {
            siteUnion.union(bottomRowIndex, ++bottomRowIndex);
        }
    }

    public int mapGridToUnion(int row, int col, int gridLength){
        return row*gridLength + col;
    }

    public int[] mapUnionToGrid(int index, int colLength) {
//        for(var i=0; i<rows; i++){
////            //check if the index parameter is in the row
////            if(index < (columns * i) + columns && index >= columns * i){
////                //return x, y
////                return [index - columns * i, i];
////            }
////        }

        for (int i = 0; i < colLength; i++) {
            if(index < (colLength * i) + colLength && index >= colLength*i){
                return new int[]{i, index-colLength*i};
            }
        }
        return null;
    }

    public boolean checkLeft(int row, int col) {
        if(col == 0){
            return false;
        }
        return isOpen(row, col - 1);
    }

    public boolean checkUp(int row, int col) {
        if(row == 0){
            return false;
        }
        return isOpen(row - 1, col);
    }

    public boolean checkRight(int row, int col) {
        if(col == siteGrid[0].length-1) {
            return false;
        }
        return isOpen(row, col + 1);
    }

    public boolean checkDown(int row, int col) {
        if(row == siteGrid.length-1){
            return false;
        }
        return isOpen(row + 1, col);
    }

    public void uniteLeft(int row, int col) {
        int unitedIndex = mapGridToUnion(row, col, colLength);
        siteUnion.union(unitedIndex, unitedIndex-1); // connect new open site to old open site
    }

    public void uniteUp(int row, int col) {
        int unitedIndex = mapGridToUnion(row, col, colLength);
        siteUnion.union(unitedIndex, unitedIndex-colLength); // connect new open site to old open site
    }

    public void uniteRight(int row, int col) {
        int unitedIndex = mapGridToUnion(row, col, colLength);
        siteUnion.union(unitedIndex, unitedIndex+1); // connect new open site to old open site
    }

    public void uniteDown(int row, int col) {
        int unitedIndex = mapGridToUnion(row, col, colLength);
        siteUnion.union(unitedIndex, unitedIndex + colLength); // connect new open site to old open site
    }

}
