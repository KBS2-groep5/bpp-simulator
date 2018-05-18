import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        Random rand = new Random(5);
        List<Container> containers = new ArrayList<>();
        List<Box> boxes = new ArrayList<>();
        for (int i = 0; i < 77; i++) {
            boxes.add(new Box(5 + rand.nextInt(95)));
        }
        new AlgorithmGUI(new NextFitAlgorithm(containers, boxes));
    }
}
