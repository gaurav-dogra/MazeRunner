import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

class Counter {

    public static boolean checkTheSameNumberOfTimes(int elem, List<Integer> list1, List<Integer> list2) {
        return Collections.frequency(list1, elem) == Collections.frequency(list2, elem);
    }
}

//class Main {
//    public static void main(String[] args) {
//        Scanner scanner = new Scanner(System.in);
//        int elem = scanner.nextInt();
//        String input1 = scanner.nextLine();
//        String input2 = scanner.nextLine();
//        String[] listOne = input1.split("\\s");
//        String[] listTwo = input1.split("\\s");
//        List<Integer> list1 = new ArrayList<>();
//        List<Integer> list2 = new ArrayList<>();
//
//        for (String s : listOne) {
//            list1.add(Integer.parseInt(s));
//        }
//
//        for (String s : listTwo) {
//            list2.add(Integer.parseInt(s));
//        }
//
//        System.out.println(Counter.checkTheSameNumberOfTimes(elem, list1, list2));
//         }
//}