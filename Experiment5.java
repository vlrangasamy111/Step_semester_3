import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Experiment 5: Multi-Modal Travel Fare Calculator
 * Demonstrates encapsulation of a shared booking fee in an abstract base class
 * with mode-specific fare logic in Bus, Train, and Flight subclasses.
 */
abstract class TransportBooking {
    private final String transportMode;
    private final double tripDistanceKm;
    private static final double PLATFORM_BOOKING_FEE = 50.00;

    public TransportBooking(String transportMode, double tripDistanceKm) {
        this.transportMode = transportMode;
        this.tripDistanceKm = tripDistanceKm;
    }

    public String getTransportMode() {
        return transportMode;
    }

    public double getTripDistanceKm() {
        return tripDistanceKm;
    }

    protected abstract double computeDistanceFare();

    public double computeTotalFare() {
        return computeDistanceFare() + PLATFORM_BOOKING_FEE;
    }
}

class BusTrip extends TransportBooking {
    private static final double RATE_PER_KM = 2.00;

    public BusTrip(double tripDistanceKm) {
        super("BUS", tripDistanceKm);
    }

    @Override
    protected double computeDistanceFare() {
        return getTripDistanceKm() * RATE_PER_KM;
    }
}

class TrainTrip extends TransportBooking {
    private static final double RATE_PER_KM = 1.50;

    public TrainTrip(double tripDistanceKm) {
        super("TRAIN", tripDistanceKm);
    }

    @Override
    protected double computeDistanceFare() {
        return getTripDistanceKm() * RATE_PER_KM;
    }
}

class FlightTrip extends TransportBooking {
    private static final double BASE_AIRFARE = 2500.00;
    private static final double RATE_PER_KM = 4.00;

    public FlightTrip(double tripDistanceKm) {
        super("FLIGHT", tripDistanceKm);
    }

    @Override
    protected double computeDistanceFare() {
        return BASE_AIRFARE + (getTripDistanceKm() * RATE_PER_KM);
    }
}

public class Experiment5 {
    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {
            if (!sc.hasNextInt()) {
                return;
            }

            int tripCount = sc.nextInt();
            List<TransportBooking> itinerary = new ArrayList<>(tripCount);

            for (int i = 0; i < tripCount; i++) {
                String mode = sc.next().toUpperCase();
                double distance = sc.nextDouble();

                TransportBooking booking = buildBooking(mode, distance);
                if (booking != null) {
                    itinerary.add(booking);
                }
            }

            for (TransportBooking booking : itinerary) {
                System.out.printf("%s: %.2f%n",
                        booking.getTransportMode(), booking.computeTotalFare());
            }
        }
    }

    private static TransportBooking buildBooking(String mode, double distance) {
        switch (mode) {
            case "BUS":
                return new BusTrip(distance);
            case "TRAIN":
                return new TrainTrip(distance);
            case "FLIGHT":
                return new FlightTrip(distance);
            default:
                return null;
        }
    }
}
