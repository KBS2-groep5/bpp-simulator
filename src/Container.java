import java.util.ArrayList;
import java.util.List;

class Container {
    private List<Box> boxes;

    public Container() {
        this.boxes = new ArrayList<>();
    }

    void addBox(Box box) {
        this.boxes.add(box);
    }

    void removeBox(Box box){this.boxes.remove(box);}

    List<Box> getBoxes() {
        return this.boxes;
    }

    Box getBox(int i){return this.boxes.get(i);}

    int getPercentageFilled() {
        int result = 0;
        for (Box b : this.boxes) {
            result += b.getHeight();
        }
        return result;
    }

    @Override
    public String toString() {
        return "Box(" + this.boxes.size() + "," + this.getPercentageFilled() + ")";
    }
}
