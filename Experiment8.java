import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Experiment 8: College Student Fee Assessment Engine
 * Demonstrates default interface methods, composition with inheritance,
 * and selective transport fee inclusion.
 */
interface TransportUser {
    default double getTransportFee() {
        return 12000.00;
    }
}

abstract class Student {
    private final String studentName;

    public Student(String studentName) {
        this.studentName = studentName;
    }

    public String getStudentName() {
        return studentName;
    }

    public abstract double calculateTuitionFee();

    public double calculateTotalFee() {
        double fee = calculateTuitionFee();
        if (this instanceof TransportUser) {
            fee += ((TransportUser) this).getTransportFee();
        }
        return fee;
    }
}

class DayScholar extends Student implements TransportUser {
    public DayScholar(String studentName) {
        super(studentName);
    }

    @Override
    public double calculateTuitionFee() {
        return 40000.00;
    }
}

class Hosteller extends Student {
    public Hosteller(String studentName) {
        super(studentName);
    }

    @Override
    public double calculateTuitionFee() {
        return 40000.00 + 60000.00;
    }
}

class ScholarshipStudent extends Student implements TransportUser {
    public ScholarshipStudent(String studentName) {
        super(studentName);
    }

    @Override
    public double calculateTuitionFee() {
        return 20000.00;
    }
}

public class Experiment8 {
    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {
            if (!sc.hasNextInt()) {
                return;
            }

            int n = sc.nextInt();
            List<Student> students = new ArrayList<>(n);

            for (int i = 0; i < n; i++) {
                String type = sc.next().toUpperCase();
                String name = sc.next();

                Student s = createStudent(type, name);
                if (s != null) {
                    students.add(s);
                }
            }

            double totalCollected = 0.0;
            for (Student student : students) {
                double fee = student.calculateTotalFee();
                totalCollected += fee;
                System.out.printf("%s: %.2f%n", student.getStudentName(), fee);
            }

            System.out.printf("Total Collected: %.2f%n", totalCollected);
        }
    }

    private static Student createStudent(String type, String name) {
        switch (type) {
            case "DAY_SCHOLAR":
                return new DayScholar(name);
            case "HOSTELLER":
                return new Hosteller(name);
            case "SCHOLAR":
                return new ScholarshipStudent(name);
            default:
                return null;
        }
    }
}
