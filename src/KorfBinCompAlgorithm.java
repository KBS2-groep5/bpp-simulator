import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.lang.Math;

public class KorfBinCompAlgorithm implements BPPAlgorithm {
    private List<Container> containers;
    private List<Box> boxes;
    private long solveTime = 0;

    static final String NAME = "KorfBinCompletion";

    private int optimalContainers = 0;
    private int optimalRest = 0;
    private int totalBoxHeight = 0;
    private int containerCount = 0;

    KorfBinCompAlgorithm(List<Container> containers, List<Box> boxes) {
        this.containers = containers;
        this.boxes = boxes;

        //Determine goal for algorithm to achieve.
        for(Box x : this.boxes){
            this.totalBoxHeight+= x.getHeight();
        }


        this.optimalContainers = (this.totalBoxHeight / 100) +1;



    }

    public List<Container> solveSteps(int steps){
        List<Container> solution = Stream.generate(Container::new).limit(this.containerCount).collect(Collectors.toList());
        long startTime = System.nanoTime();





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
