import entities.*;

import javax.persistence.*;
import java.io.*;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

public class App {
    public static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public static void main(String[] args) throws IOException {

//        TASK 3. Remove Objects
        tk3RemoveObj();

//        TASK 4. Contains Employee
//        tk4ContainsEmployee();

//        TASK 5. Employees with Salary Over 50 000
//        tk5EmployeesWithSalaryOver();

//        TASK  6. Employees from Department
//        tk6EmployeesFromDepartment();

//        TASK 7. Adding a New Address and Updating Employee
        //TASK: Create a new address with text "Vitoshka 15". Set that address to an employee with a last name, given as an input.
        //The task is left open to interprets how reader understands it. So I did this...
        //1.Can enter any given address(leave it default)
        //2.Finds all employees with given last name and changes their address

//        tk7addAddressAndUpdateEmployee();

//        TASK 8. Addresses with Employee Count
//        tk8AddressesWithEmployeeCount();

//        TASK 9. Get Employee with Project
//        tk9getEmpWithProject();

//        TASK 10. Find Latest 10 Projects
//        tk10FindLatest10Projects();

//        TASK 11. Increase Salaries
//        increaseSalaries();

//        TASK 12. Remove Towns
//        tk12RemoveTowns();

//        TASK 13. Find Employees by First Name
//            findEmployeesByFirstName();

//          14. Employees Maximum Salaries
//        maxSalariesForAllDepartments();


    }

    private static void tk12RemoveTowns() throws IOException {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("soft_uni");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        System.out.print("Remove town by name :     ");
        String inputTownName = reader.readLine().trim();

        entityManager.getTransaction().begin();

        Town townDelete;
        try {
            townDelete = entityManager.createQuery(" FROM Town WHERE name = :town", Town.class)
                    .setParameter("town", inputTownName)
                    .getSingleResult();

        } catch (NoResultException e) {
            System.out.println("This town does not exist in DB");
            return;
        }

        List<Address> addressesToDelete = entityManager
                .createQuery("FROM Address WHERE town.id= :id", Address.class)
                .setParameter("id", townDelete.getId())
                .getResultList();

        addressesToDelete
                .forEach(t -> t.getEmployees()
                        .forEach(em -> em.setAddress(null)));

        addressesToDelete.forEach(entityManager::remove);
        entityManager.remove(townDelete);

        int countDeletedAddresses = addressesToDelete.size();
        System.out.printf("%d address%s in %s deleted",
                countDeletedAddresses,
                countDeletedAddresses == 1 ? "" : "es",
                inputTownName);

        entityManager.getTransaction().commit();

    }

    private static void maxSalariesForAllDepartments() {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("soft_uni");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();

        var filterDepartments = entityManager
                .createQuery("SELECT department.name, MAX(salary) " +
                                "FROM Employee " +
                                "GROUP BY department.name\n" +
                                "HAVING MAX(salary) NOT BETWEEN 30000 AND 70000",
                        Object[].class)
                .getResultList();
        System.out.println("_".repeat(100));
        filterDepartments.forEach(e -> System.out.println(String.format("%-40s - %-10s", e[0], e[1])));

        entityManager.getTransaction().commit();
    }


    private static void findEmployeesByFirstName() throws IOException {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("soft_uni");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        System.out.print("All first names start with... ");
        String startWith = reader.readLine().trim() + "%";

        entityManager.getTransaction().begin();

        List<Employee> resultList = entityManager.createQuery("SELECT emp FROM Employee AS emp where emp.firstName like :start_pattern", Employee.class)
                .setParameter("start_pattern", startWith).getResultList();

        System.out.println("_".repeat(120));
        if (resultList.isEmpty()) {
            System.out.println("No match!");
        }
        resultList.forEach(e -> {
            System.out.println(String.format("%-15s %-20s - %-30s - ($%10.2f)", e.getFirstName(), e.getLastName(), e.getJobTitle(), e.getSalary()));
        });

        entityManager.getTransaction().commit();

    }


    private static void increaseSalaries() {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("soft_uni");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        String strQuery = "SELECT emp FROM Employee AS emp where emp.department.name IN('Engineering', 'Tool Design', 'Marketing', 'Information Services')";
        BigDecimal salaryPercentIncrease = new BigDecimal("1.12");
        entityManager.getTransaction().begin();

        List<Employee> resultList = entityManager.createQuery(strQuery, Employee.class).getResultList();
        resultList.forEach(e -> {
            BigDecimal newCalculatedSalary = e.getSalary().multiply(salaryPercentIncrease);
            e.setSalary(newCalculatedSalary);
        });
        resultList.forEach(e -> System.out.println(e.getFirstName() + " " + e.getLastName() + " " + String.format("($%.2f)", e.getSalary())));

        entityManager.getTransaction().commit();
    }

    private static void tk10FindLatest10Projects() {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("soft_uni");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();

//       TODO: 1st get last 10 started projects from DB
//       TODO: 2nd sort by project name result from entityManager query!
        
        entityManager.createQuery("SELECT p FROM Project AS p ORDER BY p.startDate DESC ", Project.class).setMaxResults(10)
                .getResultStream()
                .sorted(Comparator.comparing(Project::getName))
                .forEach(p -> {
                    String stringBuilder = "_".repeat(50) + System.lineSeparator() +
                            String.format("Project name: %s", p.getName()) + System.lineSeparator() +
                            String.format("\t Project Description: %s", p.getDescription()) + System.lineSeparator() +
                            "\t Project Start Date: " + p.getStartDate() + System.lineSeparator() +
                            "\t Project End Date: " + p.getEndDate();
                    System.out.println(stringBuilder);
                });
        entityManager.getTransaction().commit();
    }

    private static void tk9getEmpWithProject() throws IOException {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("soft_uni");
        EntityManager em = entityManagerFactory.createEntityManager();

        System.out.print("Enter employee's id:  ");
        int inputId;
        em.getTransaction().begin();
        try {
            inputId = Integer.parseInt(reader.readLine());
            List<Employee> resultListEmp = em.createQuery("SELECT emp FROM Employee AS emp Where emp.id =:input_id", Employee.class)
                    .setParameter("input_id", inputId).getResultList();
            System.out.println("_".repeat(50));
            if (resultListEmp.isEmpty()) {
                System.out.println("No employee with this id!");
                return;
            }
            Employee resultEmp = resultListEmp.get(0);
            String employeeFormatter = String.format("%s %s - %s", resultEmp.getFirstName(), resultEmp.getLastName(), resultEmp.getJobTitle());
            System.out.println(employeeFormatter);
            if (!resultEmp.getProjects().isEmpty()) {
                resultEmp.getProjects().stream().sorted(Comparator.comparing(Project::getName)).forEach(e -> System.out.println("   + " + e.getName()));
            } else System.out.println("    Not enlisted at any project!");

        } catch (IllegalArgumentException e) {
            System.out.println("-".repeat(25));
            System.out.println("|| WRONG INPUT TYPE!!! ||");
            System.out.println("-".repeat(25));
            e.printStackTrace();
        }
        em.getTransaction().commit();
    }


    private static void tk8AddressesWithEmployeeCount() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("soft_uni");
        EntityManager em = emf.createEntityManager();

        em.getTransaction().begin();
        List<Address> addressList = em.createQuery("SELECT a FROM Address as a GROUP BY a.text ORDER BY a.employees.size DESC", Address.class)
                .setMaxResults(10).getResultList();
        System.out.println("_".repeat(50));
        addressList.forEach(e -> System.out.println(String.format("%s, %s - %d employees"
                , e.getText(), e.getTown().getName(), e.getEmployees().size())));
        em.getTransaction().commit();
    }

    private static void tk7addAddressAndUpdateEmployee() throws IOException {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("soft_uni");
        EntityManager em = emf.createEntityManager();
        System.out.print("Enter new address text(default 'Vitoshka 15'):   ");
        String input = reader.readLine().trim();
        String newTextAddress = input.equals("") ? "Vitoshka 15" : input;

        em.getTransaction().begin();

        if (!checkIfAddressIsExistByText(em, newTextAddress)) {
            Address address = new Address();
            address.setText(newTextAddress);
            em.persist(address);

            System.out.print("Enter employee last name: ");
            String empLastName = reader.readLine().trim();
            List<Employee> lastNameMatches = em.createQuery("SELECT emp FROM Employee AS emp " +
                    " WHERE emp.lastName LIKE :input_name ", Employee.class).setParameter("input_name", empLastName).getResultList();

            lastNameMatches.forEach(e -> e.setAddress(address));
            em.getTransaction().commit();
            System.out.println("_".repeat(50));
            System.out.println("All employees with given last name are set with new the address!");
        } else {
            System.out.println("_".repeat(50));
            System.out.println("The address already exist in DB!");
        }
    }

    private static boolean checkIfAddressIsExistByText(EntityManager em, String newAddressByText) {
        List<Address> resultAddressList = em.createQuery("SELECT ad FROM Address As   ad WHERE ad.text = :new_address", Address.class)
                .setParameter("new_address", newAddressByText).getResultList();
        return resultAddressList.stream().anyMatch(a -> a.getText().equals(newAddressByText));
    }


    private static void tk6EmployeesFromDepartment() throws IOException {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("soft_uni");
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        System.out.print("Enter Department name (default='Research and Development'):  ");
        String input = reader.readLine().trim();
        String departmentName = input.equals("") ? "Research and Development" : input;
        String queryStr = "SELECT emp FROM Employee AS emp\n" +
                "INNER JOIN Department AS dep on emp.department = dep\n" +
                "WHERE dep.name=:department_name\n" +
                "ORDER BY emp.salary,emp.id";
        List<Employee> employeesList = em.createQuery(queryStr, Employee.class)
                .setParameter("department_name", departmentName).getResultList();
        System.out.println(employeesInGivenDepartment(employeesList, departmentName));
        em.getTransaction().commit();
    }

    private static String employeesInGivenDepartment(List<Employee> list, String depart) {
        String resultLine = "_".repeat(100).concat(System.lineSeparator());
        if (list.isEmpty()) {
            return resultLine + String.format("No employees in department:  %s", depart).trim();
        }
        StringBuilder stringBuilder = new StringBuilder();
        String formatter = "%s %s from %s - $%.2f";
        list.forEach(e -> stringBuilder.append(String.format(formatter, e.getFirstName(), e.getLastName(), depart, e.getSalary()))
                .append(System.lineSeparator()));
        return resultLine + stringBuilder.toString().trim();
    }


    private static void tk5EmployeesWithSalaryOver() throws IOException {
        System.out.print("Enter salary value (default 50k):   ");
        String input = reader.readLine();
        BigDecimal aboveSalary = BigDecimal.valueOf(Double.parseDouble(input.equals("") ? "50000" : input));

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("soft_uni");
        EntityManager em = emf.createEntityManager();

        em.getTransaction().begin();
        List<Employee> employeeList = em.createQuery("select emp from Employee as emp where emp.salary>:aboveSalary", Employee.class)
                .setParameter("aboveSalary", aboveSalary).getResultList();
        System.out.println(printEmpAbove(employeeList, aboveSalary));
        em.getTransaction().commit();
    }

    private static String printEmpAbove(List<Employee> emps, BigDecimal money) {
        String resultLine = "_".repeat(50).concat(System.lineSeparator());
        if (emps.isEmpty()) {
            return resultLine + String.format("There are no employees above %.2f", money);
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(resultLine);
        emps.forEach(e -> stringBuilder.append(e.getFirstName()).append(System.lineSeparator()));
        return stringBuilder.toString();
    }

    private static void tk3RemoveObj() {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("soft_uni");
        EntityManager em = factory.createEntityManager();
        em.getTransaction().begin();
        List<Town> fromTown = em.createQuery("SELECT t FROM Town As t", Town.class).getResultList();
        List<Town> namesBiggerThan5 = fromTown.stream().filter(e -> e.getName().length() > 5).collect(Collectors.toList());
        namesBiggerThan5.forEach(em::detach);
        namesBiggerThan5.forEach(t -> t.setName(t.getName().toLowerCase()));
        namesBiggerThan5.forEach(em::merge);
        em.flush();
        em.getTransaction().commit();
        System.out.println("_".repeat(50));
        fromTown.forEach(e -> System.out.println(e.getId() + " | " + e.getName()));
    }

    private static void tk4ContainsEmployee() throws IOException {
        System.out.print("Enter employee full name:   ");
        String inputFullName = reader.readLine().trim();
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("soft_uni");
        EntityManager em = emf.createEntityManager();

        em.getTransaction().begin();
        List<Employee> employeeList = em.createQuery("SELECT emp FROM Employee AS emp WHERE CONCAT(emp.firstName,' ',emp.lastName)=:name", Employee.class)
                .setParameter("name", inputFullName).getResultList();
        em.getTransaction().commit();
        System.out.println("_".repeat(50));
        System.out.println(checkIfEmployeeExist(employeeList));
    }

    private static String checkIfEmployeeExist(List<Employee> list) {
        if (list.isEmpty()) {
            return "No, the employee does not exist!";
        }
        return "Yes, the employee exist!";
    }
}

