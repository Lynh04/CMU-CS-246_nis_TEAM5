package MIDTERM_EXAM_CMU;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public interface IEMPLOYEE {
    long BASIC_SALARY = 115000;

    double CalculateSalary();

    double CalculateAllowance();
}

abstract class EMPLOYEE implements IEMPLOYEE {
    private String EmpID;
    private String EmpName;
    private Date EmpDateOfBirth;
    private Date StartDate;

    public EMPLOYEE() {
    }

    public EMPLOYEE(String EmpID, String EmpName, Date EmpDateOfBirth, Date StartDate) {
        this.EmpID = EmpID;
        this.EmpName = EmpName;
        this.EmpDateOfBirth = EmpDateOfBirth;
        this.StartDate = StartDate;
    }

    public String getEmpID() {
        return EmpID;
    }

    public void setEmpID(String EmpID) {
        this.EmpID = EmpID;
    }

    public String getEmpName() {
        return EmpName;
    }

    public void setEmpName(String EmpName) {
        this.EmpName = EmpName;
    }

    public Date getEmpDateOfBirth() {
        return EmpDateOfBirth;
    }

    public void setEmpDateOfBirth(Date empDateOfBirth) {
        EmpDateOfBirth = empDateOfBirth;
    }

    public Date getStartDate() {
        return StartDate;
    }

    public void setStartDate(Date StartDate) {
        this.StartDate = StartDate;
    }

    public void input() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter EmpID: ");
        String EmpID = sc.nextLine();
        System.out.println("Enter EmpName: ");
        String EmpName = sc.nextLine();
        System.out.println("Enter employee date of birth (dd/MM/yyyy): ");
        String dobString = sc.nextLine();
        try {
            this.EmpDateOfBirth = new SimpleDateFormat("dd/MM/yyyy").parse(dobString);
        } catch (ParseException e) {
            System.out.println("Invalid date format.");
        }
        System.out.println("Enter Start Date (dd/MM/yyyy): ");
        String startDateString = sc.nextLine();
        try {
            this.StartDate = new SimpleDateFormat("dd/MM/yyyy").parse(startDateString);
        } catch (ParseException e) {
            System.out.println("Invalid date format.");
        }
    }

    public void output() {
        System.out.println("Employee ID: " + EmpID);
        System.out.println("Employee name: " + EmpName);
        System.out.println("Employee Date of birth: " + EmpDateOfBirth);
        System.out.println("Employee Start Date : " + StartDate);
        System.out.println("Salary: " + CalculateSalary());
        System.out.println("Allowance " + CalculateAllowance());
    }

    public int CalculateSeniority() {
        LocalDate currentDate = LocalDate.now();
        LocalDate startDate = LocalDate.ofInstant(StartDate.toInstant(), StartDate.getTimezone().toZoneId());
        Period diff = Period.between(startDate, currentDate);
        int seniority = diff.getYears();
        return seniority;
    }

    public void setEmpDateOfBirth(LocalDate dob) {
        this.EmpDateOfBirth = java.util.Date.from(dob.atStartOfDay()
                .atZone(java.time.ZoneId.systemDefault())
                .toInstant());
    }

    public void setStartDate(LocalDate startDate) {
        this.StartDate = java.util.Date.from(startDate.atStartOfDay()
                .atZone(java.time.ZoneId.systemDefault())
                .toInstant());
    }
}

class EMP_FULLTIME extends EMPLOYEE {
    private double coefficientSalary;

    public double getCoefficientSalary() {
        return coefficientSalary;
    }

    public void setCoefficientSalary(double coefficientSalary) {
        this.coefficientSalary = coefficientSalary;
    }

    @Override
    public double CalculateSalary() {
        return BASIC_SALARY * coefficientSalary + CalculateAllowance();
    }

    @Override
    public double CalculateAllowance() {
        int seniority = CalculateSeniority();
        if (seniority >= 10) {
            return 1000000;
        } else if (seniority >= 5) {
            return 500000;
        } else {
            return 0;
        }
    }

    public void input() {
        super.input();
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter coefficientSalary: ");
        coefficientSalary = sc.nextDouble();
    }

    public void output() {
        super.output();
        System.out.println("CoefficientSalary: " + coefficientSalary);
    }
}

class EMP_PARTTIME extends EMPLOYEE {
    private int numberOfWorkDays;

    public int getNumberOfWorkDays() {
        return numberOfWorkDays;
    }

    public void setNumberOfWorkDays(int numberOfWorkDays) {
        this.numberOfWorkDays = numberOfWorkDays;
    }

    @Override
    public double CalculateSalary() {
        return BASIC_SALARY * numberOfWorkDays / 26 + CalculateAllowance();
    }

    @Override
    public double CalculateAllowance() {
        int seniority = CalculateSeniority();
        if (seniority >= 10) {
            return 500000;
        } else if (seniority >= 5) {
            return 300000;
        } else {
            return 0;
        }
    }

    public void input() {
        super.input();
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter number of workdays: ");
        numberOfWorkDays = sc.nextInt();
    }

    public void output() {
        super.output();
        System.out.println("Number Of Workdays : " + numberOfWorkDays);
    }
}

class EMP_LIST {
    private ArrayList<EMPLOYEE> list;

    public EMP_LIST() {
        list = new ArrayList<EMPLOYEE>();
    }

    public void DisplayMenu() {
        System.out.println("******** EMPLOYEE MANAGEMENT ************");
        System.out.println("1. Add New employee");
        System.out.println("2.Update employee information");
        System.out.println("3. Delete employee");
        System.out.println("4.Find employee ");
        System.out.println("5.Display all employees");
        System.out.println("6.EXIT!");
    }

    public void AddNew() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Choose type of employee: ");
        System.out.println("1.Full time employee.");
        System.out.println("2.Part time employee.");
        int choice = sc.nextInt();
        if (choice == 1) {
            EMP_FULLTIME emp = new EMP_FULLTIME();
            emp.input();
            list.add(emp);
        } else if (choice == 2) {
            EMP_PARTTIME emp = new EMP_PARTTIME();
            emp.input();
            list.add(emp);
        }
    }

    public void Update() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter Employee ID: ");
        int id = sc.nextInt();
        EMPLOYEE emp = FindEmployee(id);
        if (emp == null) {
            System.out.println("Employee not found: ");
            return;
        }
        System.out.println("Choose the information to update: ");
        System.out.println("1.Employee name");
        System.out.println("2.Employee date of birth");
        System.out.println("3.Employee start date: ");
        System.out.println("4. Full time employee coefficient salary");
        System.out.println("5. part time employee number of workdays");
        int choice = sc.nextInt();
        switch (choice) {
            case 1:
                System.out.println("Enter new employee name: ");
                String name = sc.nextLine();
                emp.setEmpName(name);
                break;
            case 2:
                System.out.println("Enter new employee date of birth (dd/MM/yyyy): ");
                LocalDate dob = LocalDate.parse(sc.next(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                emp.setEmpDateOfBirth(dob);
                break;
            case 3:
                System.out.println("Enter new employee start date (dd/MM/yyyy): ");
                LocalDate StartDate = LocalDate.parse(sc.next(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                emp.setStartDate(StartDate);
                break;
            case 4:
                if (emp instanceof EMP_FULLTIME) {
                    System.out.println("Enter new coefficient salary: ");
                    double coefSalary = sc.nextDouble();
                    ((EMP_FULLTIME) emp).setCoefficientSalary(coefSalary);
                } else {
                    System.out.println("invalid choice");
                }
                break;
            case 5:
                if (emp instanceof EMP_PARTTIME) {
                    System.out.println("enter number of workdays: ");
                    int numWorkDays = sc.nextInt();
                    ((EMP_PARTTIME) emp).setNumberOfWorkDays(numWorkDays);
                } else {
                    System.out.println("invalid choice");
                }
                break;
            default:
                System.out.println("Invalid choice");
                break;
        }
    }

    public void Delete() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter employee id: ");
        int id = sc.nextInt();
        EMPLOYEE emp = FindEmployee(id);
        if (emp == null) {
            System.out.println("Employee not found");
            return;
        }
        list.remove(emp);
        System.out.println("Employee deleted: ");
    }

    public EMPLOYEE FindEmployee(int id) {
        for (EMPLOYEE emp : list) {
            if (emp.getEmpID().equals(id)) {
                return emp;
            }
        }
        return null;
    }

    public void Find() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter employee ID: ");
        int id = sc.nextInt();
        EMPLOYEE emp = FindEmployee(id);
        if (emp == null) {
            System.out.println("Employee not found.");
        } else {
            emp.output();
        }
    }

    public void DisplayAll() {
        for (EMPLOYEE emp : list) {
            emp.output();
        }
    }
}

class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        EMP_LIST empList = new EMP_LIST();
        while (true) {
            empList.DisplayMenu();
            System.out.print("Enter your choice: ");
            int choice = sc.nextInt();
            switch (choice) {
                case 1:
                    empList.AddNew();
                    break;
                case 2:
                    empList.Update();
                    break;
                case 3:
                    empList.Delete();
                    break;
                case 4:
                    empList.Find();
                    break;
                case 5:
                    empList.DisplayAll();
                    break;
                case 6:
                    System.out.println("Goodbye!");
                    System.exit(0);
                default:
                    System.out.println("Invalid choice.");
                    break;
            }
        }
    }
}
