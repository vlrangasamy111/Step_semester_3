import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

abstract class LibraryItem {
    private String title;
    private int daysLate;

    public LibraryItem(String title, int daysLate) {
        this.title = title;
        this.daysLate = daysLate;
    }

    public String getTitle() {
        return title;
    }

    public int getDaysLate() {
        return daysLate;
    }

    public abstract double calculateFine();
}

class BookItem extends LibraryItem {
    public BookItem(String title, int daysLate) {
        super(title, daysLate);
    }

    @Override
    public double calculateFine() {
        return getDaysLate() * 2.0;
    }
}

class DVDItem extends LibraryItem {
    public DVDItem(String title, int daysLate) {
        super(title, daysLate);
    }

    @Override
    public double calculateFine() {
        return Math.min(getDaysLate() * 5.0, 50.0);
    }
}

class MagazineItem extends LibraryItem {
    public MagazineItem(String title, int daysLate) {
        super(title, daysLate);
    }

    @Override
    public double calculateFine() {
        return getDaysLate() * 1.0;
    }
}

public class Experiment3 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        if (!scanner.hasNextInt()) return;

        int n = scanner.nextInt();
        List<LibraryItem> items = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            String type = scanner.next();
            String title = scanner.next();
            int daysLate = scanner.nextInt();

            switch (type.toUpperCase()) {
                case "BOOK":
                    items.add(new BookItem(title, daysLate));
                    break;
                case "DVD":
                    items.add(new DVDItem(title, daysLate));
                    break;
                case "MAGAZINE":
                    items.add(new MagazineItem(title, daysLate));
                    break;
            }
        }

        double totalFines = 0;
        for (LibraryItem item : items) {
            double fine = item.calculateFine();
            totalFines += fine;
            System.out.printf("%s: %.2f%n", item.getTitle(), fine);
        }

        System.out.printf("Total Fines: %.2f%n", totalFines);
        scanner.close();
    }
}
