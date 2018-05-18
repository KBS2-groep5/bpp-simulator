import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class NextFitAlgorithm implements BPPAlgorithm {
    private List<Container> containers;
    private List<Box> boxes;

    private int containerCount = 1;

    NextFitAlgorithm(List<Container> containers, List<Box> boxes) {
        this.containers = containers;
        this.boxes = boxes;
    }

    public List<Container> solveSteps(int steps) {
        List<Container> solution = Stream.generate(Container::new).limit(this.containerCount).collect(Collectors.toList());
        int cursor = 0;
        for (Box b : this.boxes) {
            if (solution.get(cursor).getPercentageFilled() + b.getHeight() > 100) {
                solution.add(new Container());
                this.containerCount+=1;
                getContainerCount();
                cursor += 1;
                if (cursor >= this.containerCount) {
                    return solution;
                }
            }
            solution.get(cursor).addBox(b);
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