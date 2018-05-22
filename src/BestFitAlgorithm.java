import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class BestFitAlgorithm implements BPPAlgorithm {
    private List<Container> containers;
    private List<Box> boxes;
    private long solveTime = 0;

    static final String NAME = "Bestfit";



    private int containerCount = 1;

    BestFitAlgorithm(List<Container> containers, List<Box> boxes) {
        this.containers = containers;
        this.boxes = boxes;
    }

    public List<Container> solveSteps(int steps) {
        List<Container> solution = Stream.generate(Container::new).limit(this.containerCount).collect(Collectors.toList());
        int cursor = 0;
        int t = 0;
        long startTime = System.nanoTime();

        for (Box b : this.boxes) {
            int h = 100;
            int g = 0;
            for (Container c : solution){
                g = solution.get(cursor).getPercentageFilled() + b.getHeight();
                if(h > g){
                    h = g;
                    t = cursor;
                }
                cursor += 1;
            }
            if (h == 100){
                solution.add(new Container());
                t = cursor;
            }
            solution.get(t).addBox(b);
            cursor = 0;
            t = 0;
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
        return this.containers.size();
    }

    public int getBoxCount() {
        return this.boxes.size();
    }
    public long getSolveTime() {
        return solveTime;
    }
}