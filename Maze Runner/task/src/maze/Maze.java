package maze;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Maze {
    private Graph mst = new Graph();
    private static final String WALL = "\u2588\u2588";
    private static final String PASSAGE = "  ";
    private static final String SOLUTION_SYMBOL = "//";
    private int[][] matrix;
    private int maxRowForNode;
    private int maxColForNode;

    public Maze(int squareMaze) {
        this(squareMaze, squareMaze);
    }

    public Maze(String mazeString) {
        createMatrixFromString(mazeString);
        buildMstFromMatrix();
        addExitsToMatrix();
    }

    public Maze(int height, int width) {
        this.maxRowForNode = getMaxRowForNode(height);
        this.maxColForNode = getMaxColForNode(width);
        buildMstFromScratch();
        generateMatrixFromMST(height, width, mst);
        addExitsToMatrix();
    }

    private void buildMstFromMatrix() {
        addNodes(mst);
        for (int i = 1; i <= maxRowForNode; i += 2) {
            for (int j = 1; j <= maxColForNode; j += 2) {
                String currentNode = i + ":" + j;
                if (i != maxRowForNode && matrix[i + 1][j] == 0) {
                    String southernNode = (i + 2) + ":" + j;
                    mst.addLink(currentNode, southernNode, 1);
                }

                if (j != maxColForNode && matrix[i][j + 1] == 0) {
                    String easternNode = i + ":" + (j + 2);
                    mst.addLink(currentNode, easternNode, 1);
                }
            }
        }
    }

    private void createMatrixFromString(String mazeString) {
        int height = mazeString.split("\n").length;
        int width = mazeString.substring(0, mazeString.indexOf("\n")).length() / 2;
        maxRowForNode = getMaxRowForNode(height);
        maxColForNode = getMaxColForNode(width);
        matrix = new int[height][width];

        String[] rows = mazeString.split("\n");
        for (int i = 0; i < rows.length; i++) {
            String row = rows[i];
            for (int j = 0, k = 0; j < row.length() - 1; j += 2, k++) {
                String element = row.substring(j, j + 2);
                matrix[i][k] = element.equals(WALL) ? 1 : 0;
            }
        }
    }

    public boolean isCorrectFormat() {
        return this.toString().matches("[\u2588\\s\n\r]+");
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

    private void generateMatrixFromMST(int height, int width, Graph mst) {
        matrix = new int[height][width];
        fillWithOnes();
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

    private void addExitsToMatrix() {
        int ENTRY_ROW = 0;
        int ENTRY_COL = 1;
        matrix[ENTRY_ROW][ENTRY_COL] = 0;
        matrix[maxRowForNode][maxColForNode + 1] = 0;
        if (matrix[0].length % 2 == 0) {
            matrix[maxRowForNode][maxColForNode + 2] = 0;
        }
    }

    private void buildMstFromScratch() {
        Graph graph = new Graph();
        addNodes(graph);
        linkAllNodes(graph);
        mst = graph.buildMST();
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

    public void findEscape() {

        List<String> escapePath = mst.findEscapePath("1:1", maxRowForNode + ":" + maxColForNode);
        markEntryExit();

        for (int i = 0; i < escapePath.size() - 1; i++) {

            String[] currentNode = escapePath.get(i).split(":");
            int currentNodeRow = Integer.parseInt(currentNode[0]);
            int currentNodeCol = Integer.parseInt(currentNode[1]);

            String[] nextNode = escapePath.get(i + 1).split(":");
            int nextNodeRow = Integer.parseInt(nextNode[0]);
            int nextNodeCol = Integer.parseInt(nextNode[1]);

            matrix[currentNodeRow][currentNodeCol] = 2;
            matrix[nextNodeRow][nextNodeCol] = 2;

            if (currentNodeRow < nextNodeRow) {
                matrix[currentNodeRow + 1][currentNodeCol] = 2;
            } else if (currentNodeRow > nextNodeRow) {
                matrix[currentNodeRow - 1][currentNodeCol] = 2;
            } else if (currentNodeCol < nextNodeCol) {
                matrix[currentNodeRow][currentNodeCol + 1] = 2;
            } else if (currentNodeCol > nextNodeCol) {
                matrix[currentNodeRow][currentNodeCol - 1] = 2;
            }
        }

        System.out.println(printMatrixWithSolution());

    }

    private String printMatrixWithSolution() {
        StringBuilder sb = new StringBuilder();
        for (int[] row : matrix) {
            for (int cell : row) {
                if (cell == 0) {
                    sb.append(PASSAGE);
                } else if (cell == 1) {
                    sb.append(WALL);
                } else if (cell == 2) {
                    sb.append(SOLUTION_SYMBOL);
                }
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    private void markEntryExit() {
        matrix[0][1] = 2; // entry/exit
        matrix[maxRowForNode][maxColForNode + 1] = 2; // entry/exit
        if (matrix[0].length % 2 == 0) {
            matrix[maxRowForNode][maxColForNode + 2] = 2; // entry/exit on extra wall
        }
    }

}
