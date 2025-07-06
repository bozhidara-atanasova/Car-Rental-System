import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class RentalManager {
    private final CarRentalService service;
    private final Scanner scanner = new Scanner(System.in);

    public RentalManager(CarRentalService service) {
        this.service = service;
    }

    /** @return false → exit */
    public boolean execute(String line) {
        if (line == null || line.isBlank()) return true;
        String[] parts = line.trim().split("\\s+", 2);
        String cmd = parts[0].toLowerCase();

        try {
            switch (cmd) {
                case "add"    -> add(parts.length > 1 ? parts[1] : "");
                case "remove" -> service.removeCar(parseId(parts));
                case "rent"   -> rent(parts.length > 1 ? parts[1] : "");
                case "return" -> service.returnCar(parseId(parts));
                case "list"   -> service.available().forEach(System.out::println);
                case "search" -> search(parts.length > 1 ? parts[1] : "");
                case "save"   -> { service.save(); System.out.println("Saved."); }
                case "help"   -> help();
                case "edit"  -> edit(parts.length > 1 ? parts[1] : "");
                case "exit", "quit" -> { return false; }
                default       -> System.out.println("Unknown command. Type 'help'.");
            }
        } catch (Exception ex) {
            System.err.println("⚠ " + ex.getMessage());
        }
        return true;
    }

//helpers

    private void help() {
        System.out.println("""
            Commands:
              add <id> <make> <model> <year> <type>
              remove <id>
              rent <id> <CustomerName> <yyyy-MM-dd>
              return <id>
              list
              search <field>=<value>
              save
              exit
            """);
    }

    private void add(String args) {
        String[] t = args.split("\\s+");
        if (t.length < 5) throw new IllegalArgumentException("add needs 5 arguments");
        service.addCar(new Car(
                Integer.parseInt(t[0]), t[1], t[2],
                Integer.parseInt(t[3]), t[4]));
    }

    private void edit(String args) {
        String[] p = args.split("\\s+", 2);
        if (p.length < 2) throw new IllegalArgumentException("edit <id> field=value ...");
        int id = Integer.parseInt(p[0]);
        Car car = service.getById(id);
        if (car == null) throw new IllegalArgumentException("No car with id " + id);

        String[] kvPairs = p[1].split("\\s+");
        for (String kv : kvPairs) {
            String[] kvArr = kv.split("=", 2);
            if (kvArr.length < 2) continue;
            switch (kvArr[0].toLowerCase()) {
                case "make"  -> car.setMake(kvArr[1]);
                case "model" -> car.setModel(kvArr[1]);
                case "year"  -> car.setYear(Integer.parseInt(kvArr[1]));
                case "type"  -> car.setType(kvArr[1]);
                default      -> System.out.println("Unknown field: " + kvArr[0]);
            }
        }
        System.out.println("Car " + id + " updated.");
    }

    private void rent(String args) {
        String[] t = args.split("\\s+");
        if (t.length < 3) throw new IllegalArgumentException("rent needs 3 arguments");
        int id = Integer.parseInt(t[0]);
        String customer = t[1];
        LocalDate date = LocalDate.parse(t[2]);
        service.rentCar(id, customer, date);
    }

    private void search(String arg) {
        String[] kv = arg.split("=", 2);
        if (kv.length < 2) throw new IllegalArgumentException("search format: field=value");
        List<Car> res = service.search(kv[0], kv[1]);
        res.forEach(System.out::println);
        System.out.println(res.size() + " result(s).");
    }

    private int parseId(String[] parts) {
        if (parts.length < 2) throw new IllegalArgumentException("Missing id");
        return Integer.parseInt(parts[1]);
    }
}
