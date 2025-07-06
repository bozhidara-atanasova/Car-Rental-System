import java.io.IOException;
import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class CarRentalService {

    private final Map<Integer, Car> cars = new HashMap<>();
    private final CarFileWriter writer;

    public CarRentalService(CarFileReader reader, CarFileWriter writer) throws IOException {
        this.writer = writer;
        for (Car c : reader.read()) cars.put(c.getId(), c);
    }


    public void addCar(Car c) {
        if (cars.containsKey(c.getId())) {
            throw new IllegalArgumentException("Duplicate ID " + c.getId());
        }
        cars.put(c.getId(), c);
    }

    public void removeCar(int id) {
        get(id).setStatus(Car.Status.REMOVED);
    }

    public List<Car> available() {
        return filter(c -> c.getStatus() == Car.Status.AVAILABLE);
    }

    public void rentCar(int id, String customerName, LocalDate date) {
        get(id).rent(new Customer(customerName), date);
    }

    public void returnCar(int id) {
        get(id).returnVehicle();
    }

    public List<Car> search(String key, String value) {
        return filter(c -> c.matches(key, value));
    }

    public void save() throws IOException {
        writer.write(cars.values());
    }


    private Car get(int id) {
        Car c = cars.get(id);
        if (c == null) throw new NoSuchElementException("No car with id " + id);
        return c;
    }

    private List<Car> filter(Predicate<Car> predicate) {
        return cars.values().stream()
                .filter(predicate)
                .collect(Collectors.toList());
    }

    public Collection<Car> all() {
        return cars.values();
    }
    public Car getById(int id) {
        return cars.get(id);
    }
}
