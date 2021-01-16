package maze;

public class ConsolePrinter implements IPrint {

    @Override
    public void print(Object obj) {
        System.out.println(obj);
    }
}
