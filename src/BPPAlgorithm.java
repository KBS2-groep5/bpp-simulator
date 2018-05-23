import java.util.List;

public interface BPPAlgorithm {
    List<Container> solveSteps(int steps);

    List<Container> getContainers();

    List<Box> getBoxes();

    void setContainers(List<Container> containers);

    void setBoxes(List<Box> boxes);

    int getContainerCount();

    int getBoxCount();

    long getSolveTime();
}
