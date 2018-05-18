import java.util.ArrayList;
import java.util.List;

public class BestFitAlgorithm {

    //TODO: Change variable names to Jefta's version.
    private list<Container> containers;
    private list<Packet> packets;
    private int restTotal;
    private Container currentBest;
    private int currentRest = 100; //TODO: change 100 to Container.size selected with slider.
    private long solveTime = 0;

    static final String NAME = "Brute Force";


    long startTime = System.nanoTime();

    //Itterate through all te generated packets.
    for(Packet p : packets){
        int x = 0;
        //look through all containers currently open.
        for (Container c ; containers){
            x = c.getRest - p.getSize;
            if (x >= o && x < currentRest){
                currentBest = c;
                currentRest = x;
            }
        }
        //Check to see if a fitting container has been found.
        if(currentBest != null && currentRest < 100){ //TODO: change 100 to Container.size selected with slider.
            currentBest.addPacket(p);
            currentBest.setRest(x);
        }
        //If no suitable container was found (or there are no containers yet) , construct a new container and fill with packet.
        else {
            Container y = new container; //TODO: gebruiken Factory ipv gewone Constructor, zodat de initializatie naam generatie automatisch gaat?(geen risico van dubbele itteratie op zelfde naam)
            y.addPacket(p);
            y.setRest(x);
            containers.add(y);
        }

        }
    }

}
