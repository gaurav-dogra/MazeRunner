import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        // put your code here
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        String[] array = input.split("\\s");
        List<String> list = List.of(array);
        for (int i = list.size() - 1; i >= 1; i--) {
            if (i % 2 != 0) {
                System.out.print(list.get(i) + " ");
            }
        }
    }
}