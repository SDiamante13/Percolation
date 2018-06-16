package tests;

import com.diamante.percolation.Percolation;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;
import org.junit.jupiter.api.*;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

public class PercolationTest {

    Percolation percolation;
    int[][] grid;
    WeightedQuickUnionUF union;

    @BeforeEach
    public void setUp() {
        int n = 4;
        percolation = new Percolation(n);
        grid = percolation.getSiteGrid();
        union = percolation.getSiteUnion();
    }

    @Test
    @DisplayName("Testing UnionUF")
    public void testUnionUF(){
        int n = 9;
        WeightedQuickUnionUF uf = new WeightedQuickUnionUF(n);
        assertEquals(9,uf.count());
        uf.union(3,4);
        assertEquals(8,uf.count());
        // connected(p,q) creates a union between p and q
        // thus they share the same parent
        assertTrue(uf.connected(3,4));
        // find(p) ... finds the parent of p
        assertEquals(3, uf.find(4));
        assertEquals(3, uf.find(3));
    }

    @Test
    @DisplayName("Testing Is Open?")
    public void testIsOpen_WhenOpen_ShouldReturnTrue() {
        percolation.open(1, 1);
        assertEquals(1, grid[1][1]);
        assertTrue(percolation.isOpen(1, 1));
        //percolation.printGrid();
    }

    @Test
    @DisplayName("Testing Check Methods")
    public void checkTests() {
        // Check Left
        percolation.open(2,0);
        percolation.open(2,1);
        percolation.printGrid();
        assertTrue(percolation.checkLeft(2,1));
        assertFalse(percolation.checkLeft(2,0));
        // Check Up
        System.out.println();
        percolation.open(1,1);
        percolation.printGrid();
        assertTrue(percolation.checkUp(2,1));
        assertFalse(percolation.checkUp(0,1));
        // Check Right
        System.out.println();
        percolation.open(2,2);
        percolation.printGrid();
        assertTrue(percolation.checkRight(2,1));
        assertFalse(percolation.checkRight(0, grid[0].length-1));
        // Check Down
        System.out.println();
        percolation.printGrid();
        assertTrue(percolation.checkDown(1,1));
        assertFalse(percolation.checkDown(grid.length-1, 0));
    }

    @Test
    @DisplayName("Testing Unite Methods")
    void uniteTests() {
        // unite Left
        assertFalse(union.connected(9,8));
        percolation.uniteLeft(2, 1);
        assertTrue(union.connected(9,8));

        // unite Up
        assertFalse(union.connected(9,5));
        percolation.uniteUp(2, 1);
        assertTrue(union.connected(9,5));

        // unite Right
        percolation.open(2, 2);
        assertFalse(union.connected(9,10));
        percolation.uniteRight(2, 1);
        assertTrue(union.connected(9,10));

        // unite Down
        percolation.open(3, 1);
        assertFalse(union.connected(9,13));
        percolation.uniteDown(2, 1);
        assertTrue(union.connected(9,13));
    }

    @Test
    @DisplayName("Testing Open Check and Unite Methods")
    void openTestEverything() {
        percolation.open(2,0);
        percolation.open(0,2);
        percolation.open(1,1);
        percolation.open(3,3);
        percolation.open(2,1);
        //percolation.printGrid();
        //System.out.println();
        //0  0  1  0
        //0  1  0  0
        //1 (1) 0  0
        //0  0  0  1
        assertTrue(union.connected(9, 8));
        assertTrue(union.connected(9, 5));
        assertFalse(union.connected(9, 10));
        assertFalse(percolation.isFull(2, 1));

        percolation.open(3,2);
        percolation.open(1,2);
        percolation.open(0,0);
        percolation.open(3,0);
        percolation.printGrid();
        assertTrue(percolation.isFull(3, 0));
        assertTrue(percolation.isFull(1, 2));
        assertTrue(percolation.percolates());
    }

    @Test
    void testMapUnionToGrid() {
        //percolation.mapUnionToGrid(9, percolation.getColLength());
        assertEquals(2 ,percolation.mapUnionToGrid(9, percolation.getColLength())[0]);
        assertEquals(1 ,percolation.mapUnionToGrid(9, percolation.getColLength())[1]);
        assertEquals(1 ,percolation.mapUnionToGrid(6, percolation.getColLength())[0]);
        assertEquals(2 ,percolation.mapUnionToGrid(6, percolation.getColLength())[1]);
    }

    @Test
    @DisplayName("Testing top row connection")
    void testTopBottomRowConnection(){
        Percolation p1 = new Percolation(10);
        WeightedQuickUnionUF u1 = p1.getSiteUnion();
        assertTrue(u1.connected(0, 9));
        assertTrue(u1.connected(3, 7));
        assertTrue(u1.connected(4, 6));
        assertTrue(u1.connected(90, 98));
        assertTrue(u1.connected(93, 99));
        assertTrue(u1.connected(94, 96));

    }

    @Test
    @DisplayName("Testing percolation with 8 x 8 grid")
    void testWith8x8Grid() {
        // initialize 8 x 8 grid and 64 array union
        int n = 8;
        int randomRow, randomCol, randomUnionNumber, randomUnionIndex = 0;
        Percolation perc8 = new Percolation(n);
        int colLength = perc8.getColLength();
        //int[][] grid8 = perc8.getSiteGrid();
        //WeightedQuickUnionUF union = perc8.getSiteUnion();

        // open random sites until the system percolates
        ArrayList<Integer> indexList = new ArrayList<>();
        for (int i = 0; i < n*n; i++) {
            indexList.add(i); // indexList contains 0 : 63
        }
        while(!perc8.isPercolationFlag()) {
            randomUnionIndex = StdRandom.uniform(indexList.size());
            randomUnionNumber = indexList.get(randomUnionIndex);
            randomRow = perc8.mapUnionToGrid(randomUnionNumber, colLength)[0];
            randomCol = perc8.mapUnionToGrid(randomUnionNumber, colLength)[1];
            perc8.open(randomRow, randomCol);
            indexList.remove(indexList.indexOf(randomUnionNumber));
        }
        System.out.println("Percolation complete");
        System.out.println("Number of Open Sites: " + perc8.numberOfOpenSites());
        System.out.printf("Percolation Threshold: %.3f", perc8.numberOfOpenSites()/perc8.getSize());
        System.out.println();
        perc8.printGrid();
    }
}
