import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Experiment 2: Weekly Staff Payroll Engine
 * Demonstrates abstract class inheritance and polymorphic pay computation
 * across full-time, hourly (with overtime), and intern employment categories.
 */
abstract class Employee {
    private final String employeeName;

    public Employee(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public abstract double computeWeeklyPay();
}

class FullTimeEmployee extends Employee {
    private final double fixedSalary;

    public FullTimeEmployee(String employeeName, double fixedSalary) {
        super(employeeName);
        this.fixedSalary = fixedSalary;
    }

    @Override
    public double computeWeeklyPay() {
        return fixedSalary;
    }
}

class HourlyEmployee extends Employee {
    private final double hoursWorked;
    private final double hourlyRate;
    private static final double STANDARD_HOURS = 40.0;
    private static final double OVERTIME_MULTIPLIER = 1.5;

    public HourlyEmployee(String employeeName, double hoursWorked, double hourlyRate) {
        super(employeeName);
        this.hoursWorked = hoursWorked;
        this.hourlyRate = hourlyRate;
    }

    @Override
    public double computeWeeklyPay() {
        if (hoursWorked <= STANDARD_HOURS) {
            return hoursWorked * hourlyRate;
        }
        double regularPay = STANDARD_HOURS * hourlyRate;
        double overtimePay = (hoursWorked - STANDARD_HOURS) * hourlyRate * OVERTIME_MULTIPLIER;
        return regularPay + overtimePay;
    }
}

class InternEmployee extends Employee {
    private final double weeklyStipend;

    public InternEmployee(String employeeName, double weeklyStipend) {
        super(employeeName);
        this.weeklyStipend = weeklyStipend;
    }

    @Override
    public double computeWeeklyPay() {
        return weeklyStipend;
    }
}

public class Experiment2 {
    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {
            if (!sc.hasNextInt()) {
                return;
            }

            int staffCount = sc.nextInt();
            List<Employee> roster = new ArrayList<>(staffCount);

            for (int i = 0; i < staffCount; i++) {
                String category = sc.next().toUpperCase();
                String name = sc.next();

                Employee emp = buildEmployee(category, name, sc);
                if (emp != null) {
                    roster.add(emp);
                }
            }

            double totalPayroll = 0.0;
            for (Employee emp : roster) {
                double pay = emp.computeWeeklyPay();
                totalPayroll += pay;
                System.out.printf("%s: %.2f%n", emp.getEmployeeName(), pay);
            }

            System.out.printf("Total Payroll: %.2f%n", totalPayroll);
        }
    }

    private static Employee buildEmployee(String category, String name, Scanner sc) {
        switch (category) {
            case "FULLTIME":
                return new FullTimeEmployee(name, sc.nextDouble());
            case "HOURLY":
                double hours = sc.nextDouble();
                double rate = sc.nextDouble();
                return new HourlyEmployee(name, hours, rate);
            case "INTERN":
                return new InternEmployee(name, sc.nextDouble());
            default:
                return null;
        }
    }
}
