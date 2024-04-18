package my.example.service.impl;

import my.example.model.Employee;
import my.example.qualifier.MyService;
import my.example.service.IEmployeeService;

import javax.enterprise.context.ApplicationScoped;
import java.io.Serializable;
import java.util.List;

@ApplicationScoped
@MyService(MyService.EMPLOYEE_SERVICE_DB)
public class EmployeeServiceDB implements IEmployeeService, Serializable {

    @Override
    public void add(Employee employee) {
        throw new UnsupportedOperationException("Database implementation not yet supported.");
    }

    @Override
    public int update(Employee employee) {
        throw new UnsupportedOperationException("Database implementation not yet supported.");
    }

    @Override
    public int delete(String id) {
        throw new UnsupportedOperationException("Database implementation not yet supported.");
    }

    @Override
    public List<Employee> search(Employee employee) {
        throw new UnsupportedOperationException("Database implementation not yet supported.");
    }

    @Override
    public Employee getByID(String id) {
        throw new UnsupportedOperationException("Database implementation not yet supported.");
    }
}
