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

    List<Box> getBoxes() {
        return this.boxes;
    }

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
