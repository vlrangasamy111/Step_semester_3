import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Experiment 3: Library Overdue Fine Calculator
 * Demonstrates abstract class hierarchy with item-specific fine policies:
 * Books charge a flat daily rate, DVDs have a capped maximum, and Magazines
 * use the lowest daily penalty.
 */
abstract class BorrowedItem {
    private final String itemTitle;
    private final int overdueDays;

    public BorrowedItem(String itemTitle, int overdueDays) {
        this.itemTitle = itemTitle;
        this.overdueDays = overdueDays;
    }

    public String getItemTitle() {
        return itemTitle;
    }

    public int getOverdueDays() {
        return overdueDays;
    }

    public abstract double computeOverdueFine();
}

class BorrowedBook extends BorrowedItem {
    private static final double DAILY_FINE = 2.00;

    public BorrowedBook(String itemTitle, int overdueDays) {
        super(itemTitle, overdueDays);
    }

    @Override
    public double computeOverdueFine() {
        return getOverdueDays() * DAILY_FINE;
    }
}

class BorrowedDVD extends BorrowedItem {
    private static final double DAILY_FINE = 5.00;
    private static final double MAX_FINE = 50.00;

    public BorrowedDVD(String itemTitle, int overdueDays) {
        super(itemTitle, overdueDays);
    }

    @Override
    public double computeOverdueFine() {
        return Math.min(getOverdueDays() * DAILY_FINE, MAX_FINE);
    }
}

class BorrowedMagazine extends BorrowedItem {
    private static final double DAILY_FINE = 1.00;

    public BorrowedMagazine(String itemTitle, int overdueDays) {
        super(itemTitle, overdueDays);
    }

    @Override
    public double computeOverdueFine() {
        return getOverdueDays() * DAILY_FINE;
    }
}

public class Experiment3 {
    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {
            if (!sc.hasNextInt()) {
                return;
            }

            int itemCount = sc.nextInt();
            List<BorrowedItem> overdueItems = new ArrayList<>(itemCount);

            for (int i = 0; i < itemCount; i++) {
                String category = sc.next().toUpperCase();
                String title = sc.next();
                int days = sc.nextInt();

                BorrowedItem item = buildItem(category, title, days);
                if (item != null) {
                    overdueItems.add(item);
                }
            }

            double totalFines = 0.0;
            for (BorrowedItem item : overdueItems) {
                double fine = item.computeOverdueFine();
                totalFines += fine;
                System.out.printf("%s: %.2f%n", item.getItemTitle(), fine);
            }

            System.out.printf("Total Fines: %.2f%n", totalFines);
        }
    }

    private static BorrowedItem buildItem(String category, String title, int days) {
        switch (category) {
            case "BOOK":
                return new BorrowedBook(title, days);
            case "DVD":
                return new BorrowedDVD(title, days);
            case "MAGAZINE":
                return new BorrowedMagazine(title, days);
            default:
                return null;
        }
    }
}
