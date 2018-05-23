import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class NextFitAlgorithm implements BPPAlgorithm {
    private List<Container> containers;
    private List<Box> boxes;
    private long solveTime = 0;


    private int containerCount = 1;

    static final String NAME = "Nextfit";


    NextFitAlgorithm(List<Container> containers, List<Box> boxes) {
        this.containers = containers;
        this.boxes = boxes;
    }

    public List<Container> solveSteps(int steps) {
        List<Container> solution = Stream.generate(Container::new).limit(this.containerCount).collect(Collectors.toList());
        int cursor = 0;
        long startTime = System.nanoTime();
        for (Box b : this.boxes) {
            if (solution.get(cursor).getPercentageFilled() + b.getHeight() > 100) {
                solution.add(new Container());
                this.containerCount+=1;
                cursor += 1;
                if (cursor >= this.containerCount) {
                    return solution;
                }
            }
            solution.get(cursor).addBox(b);
        }
        this.solveTime = System.nanoTime() - startTime;
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
        return this.containerCount;
    }

    public int getBoxCount() {
        return this.boxes.size();
    }

    public long getSolveTime() {
        return solveTime;
    }
}