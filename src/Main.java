import com.diamante.percolation.PercolationStats;


public class Main {

    public static void main(String[] args) {
        // Jar Location:
        // C:\Users\steel\IdeaProjects\
        // PercolationProject\out\artifacts\PercolationProject_jar>
        int size = 200;
        int trials = 30;
        if (args.length > 1) {
            size = Integer.parseInt(args[0]);
            trials = Integer.parseInt(args[1]);
        }
            PercolationStats percolationStats = new PercolationStats(size, trials);
            percolationStats.printStatistics();
        }
    }

