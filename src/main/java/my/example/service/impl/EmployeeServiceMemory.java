package my.example.service.impl;

import my.example.logger.MyLogger;
import my.example.model.Employee;
import my.example.qualifier.MyService;
import my.example.service.IEmployeeService;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

@ApplicationScoped
@MyService(MyService.EMPLOYEE_SERVICE_MEMORY)
public class EmployeeServiceMemory implements IEmployeeService, Serializable {
    public static HashMap<String, Employee> employeeMap = new HashMap<String, Employee>();
    private static int recordIndex = 0;


    @Inject
    private MyLogger logger;

    @PostConstruct
    public void mock() {
        if (!employeeMap.isEmpty()) {
            return;
        }

        try {
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");

            add(new Employee("Adam", "Adam", formatter.parse("01-01-2001")));
            add(new Employee("Bob", "Bob", formatter.parse("02-02-2002")));
            add(new Employee("Charlie", "Charlie", formatter.parse("03-03-2003")));
        } catch (ParseException e) {
            logger.log(Level.SEVERE, "Error parsing date", e);
        }

        logger.info("Mock data completed");
    }

    public void add(Employee employee) {
        recordIndex++;
        employee.setId(String.valueOf(recordIndex));
        employeeMap.put(employee.getId(), employee);
        logger.log(Level.INFO, String.format("Employee added: #%s %s %s", employee.getId(), employee.getFirstName(), employee.getLastName()));
    }

    public int update(Employee employee) {
        if (employeeMap.containsKey(employee.getId())) {
            employeeMap.put(employee.getId(), employee);
//            log.info("Updated employee: " + employee.getId() + " " + employee.getFirstName() + " " + employee.getLastName());
//            employeeMap.forEach((k, v) -> log.info(k + " " + v.getFirstName() + " " + v.getLastName()));
            return 1;
        } else {
            return 0;
        }
    }

    public List<Employee> search(Employee employee) {
        List<Employee> employeeList = new ArrayList<Employee>();
        for (Map.Entry<String, Employee> entry : employeeMap.entrySet()) {
            // if the first name and last name are empty, return all employees
            if (employee.getFirstName().isEmpty() && employee.getLastName().isEmpty()) {
                employeeList.add(entry.getValue());
                continue;
            }

            // search by first name and last name
            if (
                    employee.getFirstName().equalsIgnoreCase(entry.getValue().getFirstName()) ||
                            employee.getLastName().equalsIgnoreCase(entry.getValue().getLastName())
            ) {
                employeeList.add(entry.getValue());
            }
        }
        return employeeList;
    }

    public int delete(String id) {
        if (employeeMap.containsKey(id)) {
            employeeMap.remove(id);
            logger.info("Deleted employee: " + id);
            return 1;
        } else {
            logger.warning("Employee deletion not found: " + id);
            return 0;
        }
    }

    public Employee getByID(String id) {
        return employeeMap.getOrDefault(id, null);
    }
}
