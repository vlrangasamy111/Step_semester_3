import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

abstract class TravelBooking {
    private String mode;
    private double distanceKm;
    private static final double BOOKING_FEE = 50.0;

    public TravelBooking(String mode, double distanceKm) {
        this.mode = mode;
        this.distanceKm = distanceKm;
    }

    public String getMode() {
        return mode;
    }

    public double getDistanceKm() {
        return distanceKm;
    }

    protected abstract double calculateBaseFare();

    public double calculateTotalFare() {
        return calculateBaseFare() + BOOKING_FEE;
    }
}

class BusBooking extends TravelBooking {
    public BusBooking(double distanceKm) {
        super("BUS", distanceKm);
    }

    @Override
    protected double calculateBaseFare() {
        return getDistanceKm() * 2.0;
    }
}

class TrainBooking extends TravelBooking {
    public TrainBooking(double distanceKm) {
        super("TRAIN", distanceKm);
    }

    @Override
    protected double calculateBaseFare() {
        return getDistanceKm() * 1.5;
    }
}

class FlightBooking extends TravelBooking {
    public FlightBooking(double distanceKm) {
        super("FLIGHT", distanceKm);
    }

    @Override
    protected double calculateBaseFare() {
        return 2500.0 + (getDistanceKm() * 4.0);
    }
}

public class Experiment5 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        if (!scanner.hasNextInt()) return;

        int n = scanner.nextInt();
        List<TravelBooking> bookings = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            String mode = scanner.next();
            double distance = scanner.nextDouble();

            switch (mode.toUpperCase()) {
                case "BUS":
                    bookings.add(new BusBooking(distance));
                    break;
                case "TRAIN":
                    bookings.add(new TrainBooking(distance));
                    break;
                case "FLIGHT":
                    bookings.add(new FlightBooking(distance));
                    break;
            }
        }

        for (TravelBooking booking : bookings) {
            System.out.printf("%s: %.2f%n", booking.getMode(), booking.calculateTotalFare());
        }

        scanner.close();
    }
}
