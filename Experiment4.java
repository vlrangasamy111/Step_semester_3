import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Experiment 4: Tiered Electricity Billing System
 * Demonstrates abstract class polymorphism with distinct billing models:
 * Home connections use tiered slab rates, Shop connections apply a flat
 * surcharge, and Factory connections enforce a minimum floor charge.
 */
abstract class ElectricConnection {
    private final String connectionCategory;
    private final double unitsConsumed;

    public ElectricConnection(String connectionCategory, double unitsConsumed) {
        this.connectionCategory = connectionCategory;
        this.unitsConsumed = unitsConsumed;
    }

    public String getConnectionCategory() {
        return connectionCategory;
    }

    public double getUnitsConsumed() {
        return unitsConsumed;
    }

    public abstract double computeBill();
}

class HomeElectricConnection extends ElectricConnection {
    private static final double SLAB1_RATE = 5.00;
    private static final double SLAB2_RATE = 7.00;
    private static final double SLAB1_LIMIT = 100.0;

    public HomeElectricConnection(double unitsConsumed) {
        super("HOME", unitsConsumed);
    }

    @Override
    public double computeBill() {
        double consumed = getUnitsConsumed();
        if (consumed <= SLAB1_LIMIT) {
            return consumed * SLAB1_RATE;
        }
        return (SLAB1_LIMIT * SLAB1_RATE) + ((consumed - SLAB1_LIMIT) * SLAB2_RATE);
    }
}

class ShopElectricConnection extends ElectricConnection {
    private static final double PER_UNIT_RATE = 8.00;
    private static final double FIXED_SURCHARGE = 100.00;

    public ShopElectricConnection(double unitsConsumed) {
        super("SHOP", unitsConsumed);
    }

    @Override
    public double computeBill() {
        return (getUnitsConsumed() * PER_UNIT_RATE) + FIXED_SURCHARGE;
    }
}

class FactoryElectricConnection extends ElectricConnection {
    private static final double PER_UNIT_RATE = 6.00;
    private static final double MINIMUM_CHARGE = 1000.00;

    public FactoryElectricConnection(double unitsConsumed) {
        super("FACTORY", unitsConsumed);
    }

    @Override
    public double computeBill() {
        return Math.max(getUnitsConsumed() * PER_UNIT_RATE, MINIMUM_CHARGE);
    }
}

public class Experiment4 {
    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {
            if (!sc.hasNextInt()) {
                return;
            }

            int connectionCount = sc.nextInt();
            List<ElectricConnection> accounts = new ArrayList<>(connectionCount);

            for (int i = 0; i < connectionCount; i++) {
                String category = sc.next().toUpperCase();
                double units = sc.nextDouble();

                ElectricConnection conn = buildConnection(category, units);
                if (conn != null) {
                    accounts.add(conn);
                }
            }

            double totalBilled = 0.0;
            for (ElectricConnection account : accounts) {
                double bill = account.computeBill();
                totalBilled += bill;
                System.out.printf("%s: %.2f%n", account.getConnectionCategory(), bill);
            }

            System.out.printf("Total: %.2f%n", totalBilled);
        }
    }

    private static ElectricConnection buildConnection(String category, double units) {
        switch (category) {
            case "HOME":
                return new HomeElectricConnection(units);
            case "SHOP":
                return new ShopElectricConnection(units);
            case "FACTORY":
                return new FactoryElectricConnection(units);
            default:
                return null;
        }
    }
}
