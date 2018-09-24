import java.util.ArrayList;

public class Box<T extends Fruit> {
    private ArrayList<T> box;

    public Box() {
        box = new ArrayList<>();
    }
    public ArrayList<T> getBox() {
        return box;
    }

    public void addFruit(T fruit) {
        box.add(fruit);
    }

    public float getWeight() {
        float weight;
        if (box.size() > 0)
            weight = box.size() * box.get(0).getWeight();
        else weight = 0;
        return weight;
    }

    public boolean compare(Box boxToCompare) {
        return this.getWeight() == boxToCompare.getWeight();
    }

    public void emptyBox(Box<T> toBox){
        for (T fruit : this.box) {
            toBox.addFruit(fruit);
        }
    }
}
