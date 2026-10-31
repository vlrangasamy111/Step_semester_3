import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

interface TransportUser {
    default double getTransportFee() {
        return 12000.0;
    }
}

abstract class Student {
    private String name;

    public Student(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public abstract double calculateTuition();

    public double calculateTotalFee() {
        double fee = calculateTuition();
        if (this instanceof TransportUser) {
            fee += ((TransportUser) this).getTransportFee();
        }
        return fee;
    }
}

class DayScholar extends Student implements TransportUser {
    public DayScholar(String name) {
        super(name);
    }

    @Override
    public double calculateTuition() {
        return 40000.0;
    }
}

class Hosteller extends Student {
    public Hosteller(String name) {
        super(name);
    }

    @Override
    public double calculateTuition() {
        return 40000.0 + 60000.0;
    }
}

class ScholarshipStudent extends Student implements TransportUser {
    public ScholarshipStudent(String name) {
        super(name);
    }

    @Override
    public double calculateTuition() {
        return 20000.0;
    }
}

public class Experiment8 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        if (!scanner.hasNextInt()) return;

        int n = scanner.nextInt();
        List<Student> students = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            String type = scanner.next();
            String name = scanner.next();

            switch (type.toUpperCase()) {
                case "DAY_SCHOLAR":
                    students.add(new DayScholar(name));
                    break;
                case "HOSTELLER":
                    students.add(new Hosteller(name));
                    break;
                case "SCHOLAR":
                    students.add(new ScholarshipStudent(name));
                    break;
            }
        }

        double totalCollected = 0;
        for (Student student : students) {
            double fee = student.calculateTotalFee();
            totalCollected += fee;
            System.out.printf("%s: %.2f%n", student.getName(), fee);
        }

        System.out.printf("Total Collected: %.2f%n", totalCollected);
        scanner.close();
    }
}
