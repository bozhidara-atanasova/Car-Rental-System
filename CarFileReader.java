import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.nio.charset.StandardCharsets;

public class CarFileReader {
    private final Path path;

    public CarFileReader(String file) {
        this.path = Paths.get(file);
    }

    public List<Car> read() throws IOException {
        List<Car> cars = new ArrayList<>();
        if (!Files.exists(path)) return cars;

        try (BufferedReader br = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
            br.readLine();                       // skip header
            String line;
            while ((line = br.readLine()) != null) {
                String[] t = line.split(",", -1); // -1 keeps empty cols
                Car c = new Car(
                        Integer.parseInt(t[0]), t[1], t[2],
                        Integer.parseInt(t[3]), t[4]);
                c.setStatus(Car.Status.valueOf(t[5]));
                if (!t[6].isBlank()) c.setCurrentRenter(new Customer(t[6]));
                cars.add(c);
            }
        }
        return cars;
    }
}
