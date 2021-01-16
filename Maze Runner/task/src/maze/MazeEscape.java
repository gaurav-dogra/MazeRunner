package maze;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class MazeEscape {
    private final String maze;
    private Graph graph = new Graph();
    private int size;
    int[][] intMaze;

    public MazeEscape(String stringMaze) {
        this.maze = stringMaze;
    }


    public String getEscape() {
        System.out.println(maze);
        size = getLength();
        System.out.println("size = " + size);
        intMaze = fillMaze(size);
        for (int[] array : intMaze) {
            System.out.println(Arrays.toString(array));
        }

        createGraph();
        Graph graphPath = createPath();

        return null;
    }



    private void createGraph() {
        generateNodes();
        generateLinks();
        System.out.println(graph);
        int exitRow = size % 2 == 0 ? size - 2 : size -1;
        int exitCol = exitRow;

    }

    private void generateLinks() {
        for (int i = 1; i < size - 1; i += 2) {
            for (int j = 1; j < size - 1; j += 2) {
                if (intMaze[i + 1][j] == 0) {
                    graph.addLink(i + ":" + j, (i + 2) + ":" + j, 1);
                }
                if (intMaze[i][j + 1] == 0 && (j + 2) < size -1) {
                    graph.addLink(i + ":" + j, i + ":" + (j + 2), 1);
                }
            }
        }
    }

    private void generateNodes() {
        for (int i = 1; i < size - 1; i += 2) {
            for (int j = 1; j < size - 1; j += 2) {
                graph.addNode(i + ":" + j);
            }
        }
//        System.out.println(graph);
    }

    private int[][] fillMaze(int size) {
        String newLineRemovedMaze = maze.replaceAll("\n", "");
        int[][] intMaze = new int[size][size];
        int pointer = 0;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (newLineRemovedMaze.charAt(pointer) == ' ') {
                    intMaze[i][j] = 0;
                } else {
                    intMaze[i][j] = 1;
                }
                pointer += 2;
            }
        }
        return intMaze;
    }

    private int getLength() {
        return maze.split("\n").length;
    }

}
