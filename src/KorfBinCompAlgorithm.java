import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class KorfBinCompAlgorithm implements BPPAlgorithm {
    private BPPAlgorithm algorithmCall; //for calling best fit in Korf's
    private List<Container> containers;
    private List<Box> boxes;

    private List<Container> startSolution;
    private int startContainersCount;
    private List<Container> optimalArray = new ArrayList<>();
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
        for(Container c : startSolution){
            this.optimalArray.add(new Container());

            for(Box b : c.getBoxes()){
                int h = b.getHeight();
                this.optimalArray.get(optimalArray.size()-1).addBox(new Box(h));
            }
        }
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
                System.out.println("altboxes size = 1 test "); //TODO remove check
            } else if (altBoxes.size() > 2) {
                System.out.println("altboxes size > 1 test "); //TODO: remove check
                for (int iAlt = 0; iAlt < altBoxes.size(); iAlt++) {

                    System.out.println("iALT = " + iAlt);

                    if (altBoxes.get(iAlt).getHeight() < iRest) {
                        restSolveList.add(altBoxes.get(iAlt));
                        iRest -= altBoxes.get(iAlt).getHeight();
                    }
                    else if (iRest >= 1 && restSolveList.size() > 1 && altBoxes.size() > 1 && restSolveList != null && iAlt >= 1) {
                        int rSLindex = restSolveList.size()-1;
                        int x = altBoxes.get(iAlt).getHeight() - iRest;
                        int y = restSolveList.get(rSLindex).getHeight() + iRest;
                        altBoxes.get(iAlt).setHeight(x);
                        restSolveList.get(rSLindex).setHeight(y);
                        iRest = 0;
                    }
                }
            }

            //Rearranging optimalArray so that the optimalSolution gets implemented.
            if (restSolveList.size() > 0) {
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

        System.out.println("OptimalArray after estimation runs   : " + this.optimalArray);

        System.out.println("Workingsolution before best fit runs : " + workingSolution);

        int tempTestInt = 0; //TODO: remove test int.


        //Repetitive Best Fit runs to get best solution.
        while (workingSolution.size() > optimalContainers) {
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
            tempTestInt++; //TODO: remove temp int
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

