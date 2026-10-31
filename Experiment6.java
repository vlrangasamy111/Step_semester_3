import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

abstract class MovieTicket {
    private String seatType;
    private int count;
    private static final double CONVENIENCE_FEE = 20.0;

    public MovieTicket(String seatType, int count) {
        this.seatType = seatType;
        this.count = count;
    }

    public String getSeatType() {
        return seatType;
    }

    public int getCount() {
        return count;
    }

    public abstract double getPricePerTicket();

    public double calculateTotalAmount() {
        return getCount() * (getPricePerTicket() + CONVENIENCE_FEE);
    }
}

class RegularTicket extends MovieTicket {
    public RegularTicket(int count) {
        super("REGULAR", count);
    }

    @Override
    public double getPricePerTicket() {
        return 150.0;
    }
}

class PremiumTicket extends MovieTicket {
    public PremiumTicket(int count) {
        super("PREMIUM", count);
    }

    @Override
    public double getPricePerTicket() {
        return 250.0;
    }
}

class ReclinerTicket extends MovieTicket {
    public ReclinerTicket(int count) {
        super("RECLINER", count);
    }

    @Override
    public double getPricePerTicket() {
        return 400.0;
    }
}

public class Experiment6 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        if (!scanner.hasNextInt()) return;

        int n = scanner.nextInt();
        List<MovieTicket> bookings = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            String seat = scanner.next();
            int count = scanner.nextInt();

            switch (seat.toUpperCase()) {
                case "REGULAR":
                    bookings.add(new RegularTicket(count));
                    break;
                case "PREMIUM":
                    bookings.add(new PremiumTicket(count));
                    break;
                case "RECLINER":
                    bookings.add(new ReclinerTicket(count));
                    break;
            }
        }

        double totalCollected = 0;
        for (MovieTicket booking : bookings) {
            double amount = booking.calculateTotalAmount();
            totalCollected += amount;
            System.out.printf("%s: %.2f%n", booking.getSeatType(), amount);
        }

        System.out.printf("Total: %.2f%n", totalCollected);
        scanner.close();
    }
}
