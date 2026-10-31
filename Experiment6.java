import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Experiment 6: Cinema Ticketing Billing System
 * Demonstrates abstract class inheritance, method overriding, and polymorphic calculation.
 */
abstract class MovieTicket {
    private final String seatType;
    private final int ticketCount;
    protected static final double CONVENIENCE_FEE = 20.00;

    public MovieTicket(String seatType, int ticketCount) {
        this.seatType = seatType;
        this.ticketCount = ticketCount;
    }

    public String getSeatType() {
        return seatType;
    }

    public int getTicketCount() {
        return ticketCount;
    }

    public abstract double getBasePricePerTicket();

    public double calculateTotalAmount() {
        return ticketCount * (getBasePricePerTicket() + CONVENIENCE_FEE);
    }
}

class RegularTicket extends MovieTicket {
    public RegularTicket(int count) {
        super("REGULAR", count);
    }

    @Override
    public double getBasePricePerTicket() {
        return 150.00;
    }
}

class PremiumTicket extends MovieTicket {
    public PremiumTicket(int count) {
        super("PREMIUM", count);
    }

    @Override
    public double getBasePricePerTicket() {
        return 250.00;
    }
}

class ReclinerTicket extends MovieTicket {
    public ReclinerTicket(int count) {
        super("RECLINER", count);
    }

    @Override
    public double getBasePricePerTicket() {
        return 400.00;
    }
}

public class Experiment6 {
    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {
            if (!sc.hasNextInt()) {
                return;
            }

            int bookingCount = sc.nextInt();
            List<MovieTicket> orders = new ArrayList<>(bookingCount);

            for (int i = 0; i < bookingCount; i++) {
                String type = sc.next().toUpperCase();
                int count = sc.nextInt();

                MovieTicket ticket = createTicket(type, count);
                if (ticket != null) {
                    orders.add(ticket);
                }
            }

            double totalRevenue = 0.0;
            for (MovieTicket ticket : orders) {
                double ticketTotal = ticket.calculateTotalAmount();
                totalRevenue += ticketTotal;
                System.out.printf("%s: %.2f%n", ticket.getSeatType(), ticketTotal);
            }

            System.out.printf("Total: %.2f%n", totalRevenue);
        }
    }

    private static MovieTicket createTicket(String type, int count) {
        switch (type) {
            case "REGULAR":
                return new RegularTicket(count);
            case "PREMIUM":
                return new PremiumTicket(count);
            case "RECLINER":
                return new ReclinerTicket(count);
            default:
                return null;
        }
    }
}
