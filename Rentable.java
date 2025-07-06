import java.time.LocalDate;

public interface Rentable {
    void rent(Customer customer, LocalDate from);
    void returnVehicle();
    boolean isAvailable();
}
