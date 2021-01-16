package maze;

import java.util.Arrays;
import java.util.Random;

public class Maze {
    private static final String WALL = "\u2588\u2588";
    private static final String PASSAGE = "  ";

    private final int[][] matrix;
    private final int heightLimit;
    private final int widthLimit;

    Maze(int height, int width) {
        matrix = new int[height][width];
        fillWithOnes();
        heightLimit = isHeightEven() ? matrix.length - 1 : matrix.length;
        widthLimit = isWidthEven() ? matrix[0].length - 1 : matrix[0].length;
    }

    private void fillWithOnes() {
        Arrays.stream(matrix).forEach(row -> Arrays.fill(row, 1));
    }

    private boolean isHeightEven() {
        return matrix.length % 2 == 0;
    }

    private boolean isWidthEven() {
        return matrix[0].length % 2 == 0;
    }

    public void digPassages() {
        Graph mst = buildMST();
        for (int i = 1; i < heightLimit; i += 2) {
            for (int j = 1; j < widthLimit; j += 2) {
                matrix[i][j] = 0;
                if (mst.isConnected(i + ":" + j, i + ":" + (j + 2))) {
                    matrix[i][j + 1] = 0;
                }
                if (mst.isConnected(i + ":" + j, i + 2 + ":" + j)) {
                    matrix[i + 1][j] = 0;
                }
            }
        }

        addExits();
    }

    private void addExits() {
        matrix[0][1] = 0;
        int bottomRow = isHeightEven() ? matrix.length - 3 : matrix.length - 2;
        int rightCol = isWidthEven() ? matrix[0].length - 2 : matrix[0].length - 1;

        matrix[bottomRow][rightCol] = 0;
        if (isWidthEven())
            matrix[bottomRow][rightCol + 1] = 0;
//        System.out.println(matrix.length);
//        System.out.println(matrix[0].length);
    }

    private Graph buildMST() {
        Graph graph = new Graph();
        addNodes(graph);
        linkAllNodes(graph);

        return graph.buildMST();
    }

    private void addNodes(Graph graph) {
        for (int i = 1; i < heightLimit; i += 2) {
            for (int j = 1; j < widthLimit; j += 2) {
                graph.addNode(i + ":" + j);
            }
        }
    }

    private void linkAllNodes(Graph graph) {
        Random rnd = new Random();
        for (int i = 1; i < heightLimit; i += 2) {
            for (int j = 1; j < widthLimit; j += 2) {
                if (i + 2 <= heightLimit - 1) {
                    graph.addLink(i + ":" + j, (i + 2) + ":" + j, rnd.nextInt(100));
                }

                if (j + 2 <= widthLimit - 1) {
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
}
