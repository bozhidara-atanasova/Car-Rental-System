import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;

public class CarFileWriter {
    private final Path path;

    public CarFileWriter(String file) {
        this.path = Paths.get(file);
    }

    public void write(Collection<Car> cars) throws IOException {
        if (path.getParent() != null) {
            Files.createDirectories(path.getParent());
        }
        try (BufferedWriter bw = Files.newBufferedWriter(path)) {
            bw.write("Id,Make,Model,Year,Type,Status,CurrentRenter\n");
            for (Car c : cars) {
                bw.write(String.format("%d,%s,%s,%d,%s,%s,%s%n",
                        c.getId(), c.getMake(), c.getModel(), c.getYear(),
                        c.getType(), c.getStatus(),
                        c.getCurrentRenter() == null ? "" : c.getCurrentRenter().getName()));
            }
        }
    }
}
