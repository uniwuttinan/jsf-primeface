package my.example.service;

import my.example.model.Employee;

import java.io.Serializable;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EmployeeServiceMemory implements Serializable {
    private static final Logger log = Logger.getLogger(EmployeeServiceMemory.class.getName());
    public static HashMap<String, Employee> employeeMap = new HashMap<String, Employee>();
    private static int recordIndex = 0;

    public void mock() {
        add(new Employee("AAA", "aaa", new Date()));
        add(new Employee("BBB", "bbb", new Date()));
        add(new Employee("CCC", "ccc", new Date()));
    }

    public void add(Employee employee) {
        recordIndex++;
        employee.setId(String.valueOf(recordIndex));
        employeeMap.put(employee.getId(), employee);
        log.log(Level.INFO, String.format("Employee added: #%s %s %s", employee.getId(), employee.getFirstName(), employee.getLastName()));
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
            return 1;
        } else {
            return 0;
        }
    }
}
