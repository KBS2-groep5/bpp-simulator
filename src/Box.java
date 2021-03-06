public class Box {
    private int height;

    public Box(int height) {
        this.height = height;
    }

    int getHeight() {
        return this.height;
    }

    void setHeight(int height) {
        this.height = height;
    }

    @Override
    public String toString() {
        return "Box{" + "height=" + height + '}';
    }

    public int compareTo(Box b){
        return this.height > b.height ? 1 : -1;
    }
}
