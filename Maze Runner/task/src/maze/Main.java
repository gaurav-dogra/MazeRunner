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
        String stringMaze = null;
        boolean isDisplayEnabled = false;
        boolean isSaveEnabled = false;

        do {
            printMenu(isSaveEnabled, isDisplayEnabled);
            input = scanner.next();
            switch (input) {
                case "1":
                    System.out.println("Enter the size of a new maze");
                    int userInput = scanner.nextInt();
                    Maze maze = new Maze(userInput, userInput);
                    maze.digPassages();
                    stringMaze = maze.toString();
                    new ConsolePrinter().print(maze);
                    isSaveEnabled = true;
                    isDisplayEnabled = true;

                    break;
                case "2":
                    System.out.println("Enter the file name");
                    String loadFileName = scanner.next();
                    try {
                        stringMaze = new Scanner(new File(loadFileName)).useDelimiter("\\Z").next();
                        if (isCorrectFormat(stringMaze)) {
                            isDisplayEnabled = true;
                        } else {
                            System.out.println("not correct format");
                        }
                    } catch (FileNotFoundException e) {
                        System.out.println("The file ... does not exist");
                    }
                    break;
                case "3":
                    System.out.println("Enter the file name");
                    String saveFileName = scanner.next();
                    try (PrintWriter writer = new PrintWriter(saveFileName)) {
                        writer.print(stringMaze);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                case "4":
                    if (stringMaze != null) {
                        System.out.println(stringMaze);
                    }
                    break;
                case "5":
                    MazeEscape me = new MazeEscape(stringMaze);
                    stringMaze = me.getEscape();
                case "0":
                    break;
                default:
                    System.out.println("Incorrect option. Please try again");
            }

        } while (!input.equals("0"));
        System.out.println("Bye!");


    }

    private static boolean isCorrectFormat(String content) {
        return content.matches("[\\s\r\n\u2588]+");
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
