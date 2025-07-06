import java.io.IOException;
import java.util.Scanner;

public class CarRentalApp {
    public static void main(String[] args) throws IOException {
        CarFileReader reader = new CarFileReader("data/cars.csv");
        CarFileWriter writer = new CarFileWriter("data/cars.csv");
        CarRentalService service = new CarRentalService(reader, writer);
        RentalManager manager = new RentalManager(service);

        Scanner in = new Scanner(System.in);
        System.out.println("Car Rental System – type 'help'.");

        boolean running = true;
        while (running) {
            System.out.print("> ");
            running = manager.execute(in.nextLine());
        }
        service.save();   // autosave on exit
        System.out.println("Bye.");
    }
}
