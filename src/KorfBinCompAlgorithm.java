import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class KorfBinCompAlgorithm implements BPPAlgorithm {
    private BPPAlgorithm algorithmCall; //for calling best fit in Korf's
    private BPPAlgorithm optimalSolution;
    private List<Container> containers;
    private List<Box> boxes;

    private List<Container> startSolution;
    private int startContainersCount;
    private List<Container> optimalArray;
    private int optimalContainers = 0;
    private List<Container> workingSolution;

    private long solveTime = 0;

    static final String NAME = "KorfBinCompletion";


    KorfBinCompAlgorithm(List<Container> containers, List<Box> boxes) {
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

        //Again to get a duplicate where i can safely chop-up boxes in.
        this.optimalSolution = new BestFitAlgorithm(this.containers, this.boxes);
        this.optimalArray = optimalSolution.getContainers();
        this.optimalContainers = optimalArray.size();

        //removing empty containers:
        Iterator<Container> itStart = startSolution.iterator();

        while (itStart.hasNext()) {
            if (itStart.next().getPercentageFilled() < 1) {
                itStart.remove();
            }
        }

        Iterator<Container> itOptimal = optimalArray.iterator();

        while (itOptimal.hasNext()) {
            if (itOptimal.next().getPercentageFilled() < 1) {
                itOptimal.remove();
            }
        }

        this.workingSolution = startSolution;
        this.startContainersCount = startSolution.size();
        this.optimalContainers = optimalArray.size();


        //Determine optimal containers to achieve with algorithm.(Boxes can be changed in height here)

        // -2 because the last container has no options for optimization
        for (int iSta = 0; iSta < optimalContainers - 2; iSta++) {

            int iRest = 100 - this.optimalArray.get(iSta).getPercentageFilled();
            List<Box> altBoxes = new ArrayList<>();
            List<Box> restSolveList = new ArrayList<>();

            for (int iStaB = iSta + 1; iStaB < optimalContainers - 1; iStaB++) {
                for (Box b : optimalArray.get(iStaB).getBoxes()) {
                    if (b.getHeight() <= iRest) {
                        altBoxes.add(b);
                    }
                }
            }

            //Getting best solution out of possible boxes.
            if (altBoxes.size() > 0 && altBoxes.size() < 2) {
                restSolveList.add(altBoxes.get(0));
            } else if (altBoxes.size() > 2) {
                for (int iAlt = 0; iAlt < altBoxes.size() - 1; iAlt++) {
                    if (altBoxes.get(iAlt).getHeight() < iRest) {
                        restSolveList.add(altBoxes.get(iAlt));
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

            //Rearranging optimalArray so that the optimalSolution gets implemented.
            if (restSolveList.size() > 0 && restSolveList != null) {
                Box y = optimalArray.get(0).getBox(0);
                int resolveIndex = 0;
                Box sbox = restSolveList.get(resolveIndex);

                Iterator<Box> itRSolve = restSolveList.iterator();

                while (itRSolve.hasNext()) {
                    y = itRSolve.next();
                    Container z = optimalArray.get(0);
                    for (Container c : optimalArray) {
                        for (Box b : c.getBoxes()) {
                            if (b == sbox) {
                                z = c;
                            }
                        }
                    }
                    if (z != optimalArray.get(0)) {
                        Iterator<Box> iRCB = z.getBoxes().iterator();

                        while (iRCB.hasNext()) {
                            if (iRCB.next() == y && y != optimalArray.get(0).getBox(0)) {
                                iRCB.remove();
                            }
                        }
                    }
                    System.out.println(" restsolve.indes : " + restSolveList.get(resolveIndex));
                    this.optimalArray.get(iSta).addBox(restSolveList.get(resolveIndex));
                    resolveIndex++;
                }

            }
        }

        //removing empty containers from OptimalArray.
        Iterator<Container> itOptFinal = optimalArray.iterator();

        while (itOptFinal.hasNext()) {
            if (itOptFinal.next().getPercentageFilled() < 1) {
                itOptFinal.remove();
            }
        }
        this.optimalContainers = this.optimalArray.size();


        //Repetitive Best Fit runs to get best solution.
        while (workingSolution.size() > optimalContainers) {
            int workingSolSize = workingSolution.size();

            // -2 because the last container has no options for optimization.
            for (int iCS = 0; iCS < workingSolSize - 2; iCS++) {

                int currentRest = 100 - workingSolution.get(iCS).getPercentageFilled();
                int containerExchangeIndex = -1;

                Iterator<Container> itWSolution = workingSolution.iterator();

                int WSolContInd = iCS;

                while (itWSolution.hasNext()) {

                    Container WSCont = workingSolution.get(WSolContInd);

                    Iterator<Box> itWScontBoxes = WSCont.getBoxes().iterator();

                    while (itWScontBoxes.hasNext()) {
                        if (itWScontBoxes.next().getHeight() <= currentRest) {
                            workingSolution.get(iCS).addBox(itWScontBoxes.next());
                            itWSolution.next().removeBox(itWScontBoxes.next());
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

            }
        }


        this.solveTime = System.nanoTime() - startTime;
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

