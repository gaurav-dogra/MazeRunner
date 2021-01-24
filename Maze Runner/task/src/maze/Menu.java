package maze;

import java.io.*;
import java.util.Objects;
import java.util.Scanner;

public class Menu {

    public static final String SHORT_MENU = "=== Menu ===\n" +
            "1. Generate a new maze\n" +
            "2. Load a maze\n" +
            "0. Exit";

    public static final String BIG_MENU = "=== Menu ===\n" +
            "1. Generate a new maze\n" +
            "2. Load a maze\n" +
            "3. Save the maze\n" +
            "4. Display the maze\n" +
            "5. Find the escape\n" +
            "0. Exit";


    private Maze maze;
    Scanner scanner = new Scanner(System.in);

    public void start() {
        System.out.println(SHORT_MENU);
        while(true) {
            String input = scanner.nextLine().trim();
            if (input.equals("0")) {
                System.out.println("Bye!");
                break;
            }
            String activeMenu = BIG_MENU;
            switch(input) {
                case "1": // new maze
                    newMaze();
                    break;
                case "2": // load maze
                    loadMaze();
                    break;
                case "3": //save maze
                    saveMaze();
                    break;
                case "4": // display maze
                    displayMaze();
                    break;
                case "5": // find escape
                    findEscape();
                    break;
                default:
                    activeMenu = SHORT_MENU;
            }
            System.out.println(activeMenu);
        }
    }

    private void findEscape() {
        maze.findEscape();
    }

    private void displayMaze() {
        new ConsolePrinter().print(maze);
    }

    private void saveMaze() {
        if (maze != null) {
            String fileName = scanner.nextLine().trim();
            FileWriter fileWriter = null;
            try {
                fileWriter = new FileWriter(fileName);
            } catch (IOException e) {
                e.printStackTrace();
            }
            PrintWriter printWriter = new PrintWriter(Objects.requireNonNull(fileWriter));
            printWriter.print(maze.toString());
            printWriter.close();
        }
    }

    private void loadMaze() {
        String fileName = scanner.nextLine().trim();
        try {
            String stringMaze = new Scanner(new File(fileName)).useDelimiter("\\Z").next();
            maze = new Maze(stringMaze);
            if (maze.isCorrectFormat()) {
                System.out.println(fileName + " loaded from the disk");
            } else {
                System.out.println("not correct format");
            }
        } catch (FileNotFoundException e) {
            System.out.println("The file ... does not exist");
        }
    }

    private void newMaze() {
        System.out.println("Enter the size of new maze");
        String input = scanner.nextLine().trim();
        int size = Integer.parseInt(input);
        maze = new Maze(size);
        new ConsolePrinter().print(maze);
    }
}
