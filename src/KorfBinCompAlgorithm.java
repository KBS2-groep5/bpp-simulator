import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.lang.Math;

public class KorfBinCompAlgorithm implements BPPAlgorithm {
    private BPPAlgorithm algorithmCall; //for calling best fit in Korf's
    private BPPAlgorithm optimalSolution;
    private List<Container> containers;
    private List<Box> boxes;
    private long solveTime = 0;

    static final String NAME = "KorfBinCompletion";

    private int optimalContainers = 0;
    private int optimalRest = 0; //TODO: echt nodig?
    private int totalBoxHeight = 0;
    private int containerCount = 0;


    KorfBinCompAlgorithm(List<Container> containers, List<Box> boxes) {
        this.containers = containers;
        this.boxes = boxes;
    }

    public List<Container> solveSteps(int steps){
        List<Container> startSolution;
        int startContainersCount;
        long startTime = System.nanoTime();

        //Order boxes by size.
        Collections.sort(boxes,(a,b) -> b.compareTo(a));

        // Innitial run of Best Fit as starting solution to be optimized.
        this.algorithmCall = new BestFitAlgorithm(this.containers,this.boxes);
        startSolution = algorithmCall.getContainers();
        startContainersCount = algorithmCall.getContainerCount();

        //Again to get a duplicate which i can safely chop up boxes in.
        this.optimalSolution = new BestFitAlgorithm(this.containers,this.boxes);
        List<Container> optimalArray = optimalSolution.getContainers();
        this.optimalContainers = optimalArray.size();

        //Determine optimal containers to achieve with algorithm.(Boxes can be changed in height here)

        //TODO: set " 5 " to correct max.

        for(int iSta = 0; iSta < optimalArray.size()-2; iSta++ ) {

            int iRest = 100 - optimalArray.get(iSta).getPercentageFilled();
            ArrayList<Box> altBoxes = new ArrayList<>();
            List<Box> restSolveList = new ArrayList<>();

            List<Box> cOboxes = optimalArray.get(iSta+1).getBoxes();
            for(int iOAC = 0; iOAC < cOboxes.size()-1; iOAC++){
                int cBHeight = optimalArray.get(iSta+1).getBox(iOAC).getHeight();
                if(cBHeight < iRest){
                    altBoxes.add(optimalArray.get(iSta+1).getBox(iOAC));
                }
            }

            //Getting best solution out of possible boxes.
            if (altBoxes.size() > 0 && altBoxes.size() < 2) {
                restSolveList.add(altBoxes.get(0));
            } else if (altBoxes.size() > 2) {
                for (int iAlt = 0; iAlt < altBoxes.size() - 1; iAlt++) {
                    if (altBoxes.get(iAlt).getHeight() < iRest) {
                        restSolveList.add(altBoxes.get(iAlt));
                        altBoxes.remove(altBoxes.get(iAlt));
                        iRest -= altBoxes.get(iAlt).getHeight();
                    } else if (iRest > 0 && restSolveList.size() > 0 && altBoxes.size() > 0) {
                        int x = altBoxes.get(iAlt).getHeight() - iRest;
                        int y = restSolveList.get(iAlt - 1).getHeight() + iRest;
                        altBoxes.get(iAlt).setHeight(x);
                        restSolveList.get(iAlt - 1).setHeight(y);
                        iRest = 0;
                    }

                }
            }

            //Rearranging optimalArray so that the solution gets implemented.
            for (int iRL = 0; iRL < restSolveList.size()-1; iRL++) {
                optimalArray.get(iSta).addBox(restSolveList.get(iRL));
                optimalArray.get(iSta+1).removeBox(restSolveList.get(iRL));
            }

            //removing empty containers from OptimalArray.
            if(optimalArray.size() > 0) {
                for (int cI = 0; cI < optimalArray.size()-1; cI++) {
                    int fill = optimalArray.get(cI).getBoxes().size();
                    if (fill < 1) {
                        optimalArray.remove(cI);
                    }
                }
            }


            this.optimalContainers = optimalArray.size();
            System.out.println("optimalContainers: " + optimalContainers);
            System.out.println("Optimal Array: " + optimalArray);


            //Repetitive Best Fit runs to get optimal solution.

        /*

        List<Container> solution = Stream.generate(Container::new).limit(this.containerCount).collect(Collectors.toList());
        int cursor = 0;
        int t = 0;

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

        */

        }

        this.solveTime = System.nanoTime() - startTime;
        return algorithmCall.getContainers(); //TODO remove temp test fix


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

