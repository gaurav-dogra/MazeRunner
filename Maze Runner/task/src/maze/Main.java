package maze;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String input;
        Maze maze = null;
        boolean isDisplayEnabled = false;
        boolean isSaveEnabled = false;

        do {
            printMenu(isSaveEnabled, isDisplayEnabled);
            input = scanner.next();
            switch (input) {
                case "1": // new maze
                    System.out.println("Enter the size of a new maze");
                    int userInput = scanner.nextInt();
                    maze = new Maze(userInput);
                    new ConsolePrinter().print(maze);
                    isSaveEnabled = true;
                    isDisplayEnabled = true;
                    break;

                case "2": // Load maze from File
                    System.out.println("Enter the file name");
                    String loadFileName = scanner.next();
                    try {
                        String strMaze = new Scanner(new File(loadFileName)).useDelimiter("\\Z").next();
                        maze = new Maze(strMaze);
                        if (maze.isCorrectFormat()) {
                            System.out.println(loadFileName + " loaded from the disk");
                            isDisplayEnabled = true;
                        } else {
                            System.out.println("not correct format");
                        }
                    } catch (FileNotFoundException e) {
                        System.out.println("The file ... does not exist");
                    }
                    break;

                case "3": // Save Maze to a file
                    System.out.println("Enter the file name");
                    String saveFileName = scanner.next();
                    try (PrintWriter writer = new PrintWriter(saveFileName)) {
                        assert maze != null;
                        writer.write(maze.toString());
                        System.out.println("Maze saved to " + saveFileName);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;

                case "4": // display the maze
                        System.out.println(maze);
                        break;

                case "5": // find the escape
                    assert maze != null;
                    maze.printSolution();
                    break;

                case "0":
                    break;
                default:
                    System.out.println("Incorrect option. Please try again");
            }

        } while (!input.equals("0"));
        System.out.println("Bye!");


    }

    private static void printMenu(boolean isSaveEnabled, boolean isDisplayEnabled) {
        StringBuilder sb = new StringBuilder("=== Menu ===\n" +
                "1. Generate a new maze\n" +
                "2. Load a maze\n");

        if (isSaveEnabled) {
            sb.append("3. Save the maze\n");
        }

        if (isDisplayEnabled) {
            sb.append("4. Display the maze\n");
            sb.append("5. Find the escape\n");
        }

        sb.append("0. Exit");
        System.out.println(sb);
    }
}
