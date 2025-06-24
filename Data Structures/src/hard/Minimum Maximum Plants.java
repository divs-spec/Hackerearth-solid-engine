import java.io.*;
import java.util.*;

public class Main {

    // Function to compute max plants in a single row (no adjacent placements)
    public static int maxPlantsInRow(int width, boolean[] broken) {
        int count = 0;
        boolean lastPlanted = false;

        for (int i = 0; i < width; i++) {
            if (!broken[i] && !lastPlanted) {
                count++;
                lastPlanted = true;
            } else {
                lastPlanted = false;
            }
        }

        return count;
    }

    // Function to calculate the total maximum plants for entire garden
    public static int calculateMaxPlants(int rows, int cols, boolean[][] garden) {
        int totalMaxPlants = 0;

        for (int i = 0; i < rows; i++) {
            boolean[] broken = new boolean[cols];
            for (int j = 0; j < cols; j++) {
                broken[j] = garden[i][j];
            }
            totalMaxPlants += maxPlantsInRow(cols, broken);
        }

        return totalMaxPlants;
    }

    // Function to calculate minimum number of plants to cover all unbroken columns
    public static int calculateMinCoverage(int rows, int cols, boolean[][] garden) {
        int minCoverage = 0;

        for (int j = 0; j < cols; j++) {
            boolean hasPlantableCell = false;
            for (int i = 0; i < rows; i++) {
                if (!garden[i][j]) {
                    hasPlantableCell = true;
                    break;
                }
            }
            if (hasPlantableCell) {
                minCoverage++;
            }
        }

        return minCoverage;
    }

    public static void main(String[] args) throws IOException {
        // Fast I/O
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        // Read dimensions
        st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken()); // rows
        int m = Integer.parseInt(st.nextToken()); // columns

        // Read number of broken pots
        int b = Integer.parseInt(br.readLine());

        // Initialize the garden
        boolean[][] garden = new boolean[n][m]; // false = not broken, true = broken

        // Mark broken pots
        for (int i = 0; i < b; i++) {
            st = new StringTokenizer(br.readLine());
            int r = Integer.parseInt(st.nextToken());
            int c = Integer.parseInt(st.nextToken());
            if (r >= 0 && r < n && c >= 0 && c < m) {
                garden[r][c] = true;
            }
        }

        // Compute results
        int maxPlants = calculateMaxPlants(n, m, garden);
        int minCoverage = calculateMinCoverage(n, m, garden);

        // Output
        System.out.println(maxPlants + " " + minCoverage);
    }
}
