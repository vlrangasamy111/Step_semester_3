import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Experiment 9: Urban Cab Fare Calculator
 * Demonstrates marker interfaces for runtime capability checking,
 * base rate calculations, minimum fare floors, and nighttime surcharges.
 */
interface NightServiceable {
    // Marker interface indicating nighttime operational eligibility
}

abstract class Cab {
    private final String modelName;
    private final double perKmRate;

    public Cab(String modelName, double perKmRate) {
        this.modelName = modelName;
        this.perKmRate = perKmRate;
    }

    public String getModelName() {
        return modelName;
    }

    public double getPerKmRate() {
        return perKmRate;
    }

    public double calculateFare(double distanceKm, String timeOfDay) {
        double distanceCharge = distanceKm * perKmRate;
        double baseFare = Math.max(distanceCharge, 100.00);

        if ("NIGHT".equalsIgnoreCase(timeOfDay)) {
            baseFare *= 1.20;
        }

        return baseFare;
    }
}

class MiniCab extends Cab {
    public MiniCab() {
        super("MINI", 10.00);
    }
}

class SedanCab extends Cab implements NightServiceable {
    public SedanCab() {
        super("SEDAN", 14.00);
    }
}

class SUVCab extends Cab implements NightServiceable {
    public SUVCab() {
        super("SUV", 18.00);
    }
}

class TripBooking {
    private final Cab vehicle;
    private final double distanceKm;
    private final String timeOfDay;

    public TripBooking(Cab vehicle, double distanceKm, String timeOfDay) {
        this.vehicle = vehicle;
        this.distanceKm = distanceKm;
        this.timeOfDay = timeOfDay;
    }

    public Cab getVehicle() {
        return vehicle;
    }

    public double getDistanceKm() {
        return distanceKm;
    }

    public String getTimeOfDay() {
        return timeOfDay;
    }
}

public class Experiment9 {
    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {
            if (!sc.hasNextInt()) {
                return;
            }

            int n = sc.nextInt();
            List<TripBooking> bookings = new ArrayList<>(n);

            for (int i = 0; i < n; i++) {
                String model = sc.next().toUpperCase();
                double km = sc.nextDouble();
                String timeOfDay = sc.next();

                Cab vehicle = createCab(model);
                if (vehicle != null) {
                    bookings.add(new TripBooking(vehicle, km, timeOfDay));
                }
            }

            double totalRevenue = 0.0;
            for (TripBooking booking : bookings) {
                Cab vehicle = booking.getVehicle();
                boolean isNight = "NIGHT".equalsIgnoreCase(booking.getTimeOfDay());

                if (isNight && !(vehicle instanceof NightServiceable)) {
                    System.out.printf("%s: night service not available%n", vehicle.getModelName());
                } else {
                    double fare = vehicle.calculateFare(booking.getDistanceKm(), booking.getTimeOfDay());
                    totalRevenue += fare;
                    System.out.printf("%s: %.2f%n", vehicle.getModelName(), fare);
                }
            }

            System.out.printf("Total: %.2f%n", totalRevenue);
        }
    }

    private static Cab createCab(String model) {
        switch (model) {
            case "MINI":
                return new MiniCab();
            case "SEDAN":
                return new SedanCab();
            case "SUV":
                return new SUVCab();
            default:
                return null;
        }
    }
}
