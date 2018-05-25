import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class BlackSunAlgorithm implements BPPAlgorithm {
    private BPPAlgorithm algorithmCall; //for calling best fit in BlackSun
    private List<Container> containers;
    private List<Box> boxes;

    private List<Container> startSolution;
    private int startContainersCount;
    private int optimalContainers = 0;
    private List<Container> workingSolution;

    private long solveTime = 0;

    static final String NAME = "BlackSun";


    BlackSunAlgorithm(List<Container> containers, List<Box> boxes) {
        this.containers = containers;
        this.boxes = boxes;
    }

    public List<Container> solveSteps(int steps) {
        long startTime = System.nanoTime();

        //Order boxes by size.
        Collections.sort(this.boxes, (a, b) -> b.compareTo(a));

        // Innitial run of Best Fit as starting solution to be optimized.
        this.algorithmCall = new BestFitAlgorithm(this.containers, this.boxes);
        this.startSolution = algorithmCall.getContainers();
        this.startContainersCount = startSolution.size();

        //removing empty containers:
        Iterator<Container> itStart = startSolution.iterator();

        while (itStart.hasNext()) {
            if (itStart.next().getPercentageFilled() < 1) {
                itStart.remove();
            }
        }

        this.workingSolution = startSolution;
        this.startContainersCount = startSolution.size();


        //Determine optimal containers to achieve with algorithm.
        int totalHeight = 0;
        for(Box b : this.boxes){
            totalHeight += b.getHeight();
        }

        this.optimalContainers = ((totalHeight * 106)/10000 ) +1;

        int loopLimiter = 0;

        //Repetitive Best Fit runs to get best solution.
        while ((workingSolution.size() > this.optimalContainers)&& (loopLimiter < startContainersCount)) {
            //Consider each container for optimization -1 because the last container has no options for optimization.
            int WSolContInd = 0;
            while(WSolContInd < workingSolution.size()-1){

                int currentRest = 100-workingSolution.get(WSolContInd).getPercentageFilled();

                Iterator<Container> itWSolution = workingSolution.iterator();

                //Iterate through containers.
                while (itWSolution.hasNext()) {

                    Container WSCont = itWSolution.next();

                    if(WSCont != workingSolution.get(WSolContInd)) {
                        Box wBox = workingSolution.get(WSolContInd).getBox(0);

                        for(Box b : WSCont.getBoxes()){
                            if(wBox == workingSolution.get(WSolContInd).getBox(0) && b.getHeight() <=currentRest){
                                wBox = b;
                            }else if(wBox != workingSolution.get(WSolContInd).getBox(0) && b.getHeight() > wBox.getHeight() && b.getHeight() <= currentRest){
                                wBox = b;
                            }
                        }

                        if(wBox != workingSolution.get(WSolContInd).getBox(0)){
                            workingSolution.get(WSolContInd).addBox(wBox);
                            currentRest = 100 - workingSolution.get(WSolContInd).getPercentageFilled();
                            WSCont.removeBox(wBox);
                        }
                    }
                }


                WSolContInd++;
            }
            //removing empty containers from workingSolution.
            Iterator<Container> itWrkFinal = workingSolution.iterator();

            while (itWrkFinal.hasNext()) {
                if (itWrkFinal.next().getPercentageFilled() < 1) {
                    itWrkFinal.remove();
                }
            }
            loopLimiter++;
        }


        this.solveTime = System.nanoTime() - startTime;
        this.containers = workingSolution;
        System.out.println(workingSolution);
        return workingSolution;


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

