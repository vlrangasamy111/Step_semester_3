import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Experiment 7: Courier Shipping & Parcel Insurance Calculator
 * Demonstrates interface abstraction, conditional capability detection via instanceof,
 * and polymorphic fee calculation.
 */
interface Insurable {
    double calculateInsurance();
}

abstract class Parcel {
    private final String parcelType;
    private final double weightKg;
    private final double declaredValue;

    public Parcel(String parcelType, double weightKg, double declaredValue) {
        this.parcelType = parcelType;
        this.weightKg = weightKg;
        this.declaredValue = declaredValue;
    }

    public String getParcelType() {
        return parcelType;
    }

    public double getWeightKg() {
        return weightKg;
    }

    public double getDeclaredValue() {
        return declaredValue;
    }

    public abstract double calculateFreightCharge();
}

class StandardParcel extends Parcel {
    public StandardParcel(double weightKg, double declaredValue) {
        super("STANDARD", weightKg, declaredValue);
    }

    @Override
    public double calculateFreightCharge() {
        return 40.00 + (10.00 * getWeightKg());
    }
}

class ExpressParcel extends Parcel implements Insurable {
    public ExpressParcel(double weightKg, double declaredValue) {
        super("EXPRESS", weightKg, declaredValue);
    }

    @Override
    public double calculateFreightCharge() {
        return 80.00 + (15.00 * getWeightKg());
    }

    @Override
    public double calculateInsurance() {
        return getDeclaredValue() * 0.02;
    }
}

class FragileParcel extends Parcel implements Insurable {
    public FragileParcel(double weightKg, double declaredValue) {
        super("FRAGILE", weightKg, declaredValue);
    }

    @Override
    public double calculateFreightCharge() {
        return 40.00 + (10.00 * getWeightKg()) + 50.00;
    }

    @Override
    public double calculateInsurance() {
        return getDeclaredValue() * 0.02;
    }
}

public class Experiment7 {
    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {
            if (!sc.hasNextInt()) {
                return;
            }

            int n = sc.nextInt();
            List<Parcel> consignments = new ArrayList<>(n);

            for (int i = 0; i < n; i++) {
                String type = sc.next().toUpperCase();
                double weight = sc.nextDouble();
                double value = sc.nextDouble();

                Parcel parcel = createParcel(type, weight, value);
                if (parcel != null) {
                    consignments.add(parcel);
                }
            }

            double grandTotal = 0.0;
            for (Parcel parcel : consignments) {
                double charge = parcel.calculateFreightCharge();
                double insurance = (parcel instanceof Insurable)
                        ? ((Insurable) parcel).calculateInsurance()
                        : 0.00;
                double total = charge + insurance;
                grandTotal += total;

                System.out.printf("%s: Charge=%.2f Insurance=%.2f Total=%.2f%n",
                        parcel.getParcelType(), charge, insurance, total);
            }

            System.out.printf("Grand Total: %.2f%n", grandTotal);
        }
    }

    private static Parcel createParcel(String type, double weight, double value) {
        switch (type) {
            case "STANDARD":
                return new StandardParcel(weight, value);
            case "EXPRESS":
                return new ExpressParcel(weight, value);
            case "FRAGILE":
                return new FragileParcel(weight, value);
            default:
                return null;
        }
    }
}
