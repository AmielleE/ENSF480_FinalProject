public class Plane {
    private String model;
    private int capacity;

    public Plane(String model, int capacity) {
        this.model = model;
        this.capacity = capacity;
    }

    public String getModel() { return model; }
    public int getCapacity() { return capacity; }

    @Override
    public String toString() {
        return "Plane{" + "model='" + model + '\'' + ", capacity=" + capacity + '}';
    }
}
