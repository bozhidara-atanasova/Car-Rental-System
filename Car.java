import java.time.LocalDate;

public class Car extends Vehicle implements Rentable, Searchable {

    public enum Status { AVAILABLE, RENTED, REMOVED }

    private Status status = Status.AVAILABLE;
    private Customer currentRenter;
    private LocalDate rentDate;

    public Car(int id, String make, String model, int year, String type) {
        super(id, make, model, year, type);
    }


    @Override
    public void rent(Customer customer, LocalDate from) {
        if (status != Status.AVAILABLE) {
            throw new IllegalStateException("Car not available");
        }
        status         = Status.RENTED;
        currentRenter  = customer;
        rentDate       = from;
    }

    @Override
    public void returnVehicle() {
        if (status != Status.RENTED) {
            throw new IllegalStateException("Car is not rented");
        }
        status        = Status.AVAILABLE;
        currentRenter = null;
        rentDate      = null;
    }

    @Override
    public boolean isAvailable() {
        return status == Status.AVAILABLE;
    }

    /* ---------- Searchable ---------- */

    @Override
    public boolean matches(String key, String value) {
        switch (key.toLowerCase()) {
            case "id":     return String.valueOf(id).equals(value);
            case "make":   return make.equalsIgnoreCase(value);
            case "model":  return model.equalsIgnoreCase(value);
            case "year":   return String.valueOf(year).equals(value);
            case "status": return status.name().equalsIgnoreCase(value);
            default:       return false;
        }
    }


    public Status getStatus()             { return status; }
    public Customer getCurrentRenter()    { return currentRenter; }
    public LocalDate getRentDate()        { return rentDate; }

    public void setStatus(Status status)          { this.status = status; }
    public void setCurrentRenter(Customer renter) { this.currentRenter = renter; }

    @Override
    public String toString() {
        String base = super.toString();
        return status == Status.RENTED
                ? base + " – RENTED by " + currentRenter.getName()
                : base + " – free";
    }
    public void setMake(String make)   { this.make  = make;  }
    public void setModel(String model) { this.model = model; }
    public void setYear(int year)      { this.year  = year;  }
    public void setType(String type)   { this.type  = type;  }
}

