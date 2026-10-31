import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

interface NightServiceable {}

abstract class Cab {
    private String type;
    private double ratePerKm;

    public Cab(String type, double ratePerKm) {
        this.type = type;
        this.ratePerKm = ratePerKm;
    }

    public String getType() {
        return type;
    }

    public double calculateFare(double distanceKm, String time) {
        double rawFare = distanceKm * ratePerKm;
        double fare = Math.max(rawFare, 100.0);

        if ("NIGHT".equalsIgnoreCase(time)) {
            fare *= 1.20;
        }

        return fare;
    }
}

class MiniCab extends Cab {
    public MiniCab() {
        super("MINI", 10.0);
    }
}

class SedanCab extends Cab implements NightServiceable {
    public SedanCab() {
        super("SEDAN", 14.0);
    }
}

class SUVCab extends Cab implements NightServiceable {
    public SUVCab() {
        super("SUV", 18.0);
    }
}

class TripRequest {
    private Cab cab;
    private double distanceKm;
    private String time;

    public TripRequest(Cab cab, double distanceKm, String time) {
        this.cab = cab;
        this.distanceKm = distanceKm;
        this.time = time;
    }

    public Cab getCab() {
        return cab;
    }

    public double getDistanceKm() {
        return distanceKm;
    }

    public String getTime() {
        return time;
    }
}

public class Experiment9 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        if (!scanner.hasNextInt()) return;

        int n = scanner.nextInt();
        List<TripRequest> requests = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            String cabType = scanner.next();
            double km = scanner.nextDouble();
            String time = scanner.next();

            Cab cab = null;
            switch (cabType.toUpperCase()) {
                case "MINI":
                    cab = new MiniCab();
                    break;
                case "SEDAN":
                    cab = new SedanCab();
                    break;
                case "SUV":
                    cab = new SUVCab();
                    break;
            }

            if (cab != null) {
                requests.add(new TripRequest(cab, km, time));
            }
        }

        double totalFare = 0;
        for (TripRequest req : requests) {
            Cab cab = req.getCab();
            if ("NIGHT".equalsIgnoreCase(req.getTime()) && !(cab instanceof NightServiceable)) {
                System.out.printf("%s: night service not available%n", cab.getType());
            } else {
                double fare = cab.calculateFare(req.getDistanceKm(), req.getTime());
                totalFare += fare;
                System.out.printf("%s: %.2f%n", cab.getType(), fare);
            }
        }

        System.out.printf("Total: %.2f%n", totalFare);
        scanner.close();
    }
}
