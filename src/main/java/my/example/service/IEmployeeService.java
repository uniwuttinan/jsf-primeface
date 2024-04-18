package my.example.service;

import my.example.model.Employee;

import java.util.List;


public interface IEmployeeService {
    void add(Employee employee);

    int update(Employee employee);

    int delete(String id);

    List<Employee> search(Employee employee);

    Employee getByID(String id);
}
