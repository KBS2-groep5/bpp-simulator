import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FirstFitAlgorithm implements BPPAlgorithm {
    private List<Container> containers;
    private List<Box> boxes;

    private int containerCount = 138;

    FirstFitAlgorithm(List<Container> containers, List<Box> boxes) {
        this.containers = containers;
        this.boxes = boxes;
    }

    public List<Container> solveSteps(int steps) {
        List<Container> solution = Stream.generate(Container::new).limit(this.containerCount).collect(Collectors.toList());
        int cursor = 0;
        for (Box b : this.boxes) {
            while (solution.get(cursor).getPercentageFilled() + b.getHeight() > 100) {
                cursor += 1;
                System.out.println("test" + cursor);
            }
            solution.get(cursor).addBox(b);
            cursor = 0;
        }
        return solution;
    }

    public List<Container> getContainers() {
        return this.containers;
    }

    public List<Box> getBoxes() {
        return this.boxes;
    }

    public void setContainers(List<Container> containers) {
        this.containers = containers;
    }

    public void setBoxes(List<Box> boxes) {
        this.boxes = boxes;
    }

    public int getContainerCount() {
        return this.containers.size();
    }

    public int getBoxCount() {
        return this.boxes.size();
    }
}
