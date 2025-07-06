import java.util.Objects;

public abstract class Vehicle {
    protected  int id;
    protected  String make;
    protected  String model;
    protected  int year;
    protected  String type;

    protected Vehicle(int id, String make, String model, int year, String type) {
        this.id   = id;
        this.make = Objects.requireNonNull(make);
        this.model = Objects.requireNonNull(model);
        this.year = year;
        this.type = Objects.requireNonNull(type);
    }

    public int getId()          { return id; }
    public String getMake()     { return make; }
    public String getModel()    { return model; }
    public int getYear()        { return year; }
    public String getType()     { return type; }

    @Override
    public String toString() {
        return String.format("#%d %d %s %s (%s)", id, year, make, model, type);
    }
}
