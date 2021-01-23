package maze;

import java.util.Arrays;
import java.util.Random;

public class Maze {

    private Graph mst = new Graph();
    private static final String WALL = "\u2588\u2588";
    private static final String PASSAGE = "  ";
    private static final String SOLUTION = "\\\\";
    private int[][] matrix;
    private int maxRowForNode;
    private int maxColForNode;
    private final int ENTRY_ROW = 0;
    private final int ENTRY_COL = 1;

    public Maze(int squareMaze) {
        this(squareMaze, squareMaze);
    }

    public Maze(String mazeString) {
        matrix = generateMatrix(mazeString);
    }

    private int[][] generateMatrix(String mazeString) {
        String[] rows = mazeString.split("\n");
        int rowSize = rows.length;
        int colSize = rows[0].length()/2;
        int[][] matrix = new int[rowSize][colSize];
        for (int i = 0; i < rowSize; i++) {
            String row = rows[i];
            for (int j = 0, k = 0; j < row.length(); j += 2, k++) {
                if (row.charAt(j) == ' ') {
                    matrix[i][k] = 0;
                } else if (row.charAt(j) == '\u2588') {
                    matrix[i][k] = 1;
                }
            }
        }
        return matrix;
    }

    public boolean isCorrectFormat() {
        return this.toString().matches("[\u2588\\s\n\r]+");
    }

    public Maze(int height, int width) {
        this.maxRowForNode = getMaxRowForNode(height);
//        System.out.println("maxRowForNode = " + maxRowForNode);
        this.maxColForNode = getMaxColForNode(width);
//        System.out.println("maxColForNode = " + maxColForNode);
        buildMST();
        generateMatrixFromMST(height, width);
        addExits(width);
    }

    private int getMaxColForNode(int width) {
        return width % 2 == 0 ? width - 3 : width - 2;
    }

    private int getMaxRowForNode(int height) {
        return height % 2 == 0 ? height - 3 : height - 2;
    }

    private void fillWithOnes() {
        Arrays.stream(matrix).forEach(row -> Arrays.fill(row, 1));
    }

    private void generateMatrixFromMST(int height, int width) {
        matrix = new int[height][width];
        fillWithOnes();

//        System.out.println(mst);
        for (int i = 1; i <= maxRowForNode; i += 2) {
            for (int j = 1; j <= maxColForNode; j += 2) {
                matrix[i][j] = 0;
                if (mst.isConnected(i + ":" + j, i + ":" + (j + 2))) {
                    matrix[i][j + 1] = 0;
                }
                if (mst.isConnected(i + ":" + j, i + 2 + ":" + j)) {
                    matrix[i + 1][j] = 0;
                }
            }
        }
    }

    private void addExits(int width) {
        matrix[ENTRY_ROW][ENTRY_COL] = 0;
        matrix[maxRowForNode][maxColForNode + 1] = 0;
        if (width % 2 == 0) {
            matrix[maxRowForNode][maxColForNode + 2] = 0;
        }

    }

    private void buildMST() {
        Graph graph = new Graph();
        addNodes(graph);
//        System.out.println("graph after nodes " + graph);
        linkAllNodes(graph);
//        System.out.println("graph after all nodes are linked " + graph);
        mst = graph.buildMST();
//        System.out.println("mst = " + mst);
    }

    private void addNodes(Graph graph) {
        for (int i = 1; i <= maxRowForNode; i += 2) {
            for (int j = 1; j <= maxColForNode; j += 2) {
                graph.addNode(i + ":" + j);
            }
        }
    }

    private void linkAllNodes(Graph graph) {
        Random rnd = new Random();
        for (int i = 1; i <= maxRowForNode; i += 2) {
            for (int j = 1; j <= maxColForNode; j += 2) {
                if (i + 2 <= maxRowForNode) {
                    graph.addLink(i + ":" + j, (i + 2) + ":" + j, rnd.nextInt(100));
                }

                if (j + 2 <= maxColForNode) {
                    graph.addLink(i + ":" + j, i + ":" + (j + 2), rnd.nextInt(100));
                }
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int[] row : matrix) {
            for (int cell : row) {
                String block = cell == 1 ? WALL : PASSAGE;
                sb.append(block);
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    public void printSolution() {
        for (int[] row : matrix) {
            for (int cell : row) {
                System.out.print(cell + " ");
            }
            System.out.println();
        }
    }

}
